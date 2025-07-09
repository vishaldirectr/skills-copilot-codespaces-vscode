package com.wipro.bank.service;

import com.wipro.bank.dao.CustomerDAO;
import com.wipro.bank.dao.AccountDAO;
import com.wipro.bank.model.Customer;
import com.wipro.bank.model.Account;
import com.wipro.bank.util.DateUtil;
import com.wipro.bank.util.StringUtil;

import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Customer Service Class for Customer Management
 */
public class CustomerService {
    
    private CustomerDAO customerDAO;
    private AccountDAO accountDAO;
    
    public CustomerService() {
        this.customerDAO = new CustomerDAO();
        this.accountDAO = new AccountDAO();
    }
    
    /**
     * Create a new customer with validation
     * Input format: name,dob,email,phone,accountType,accountStatus,balance
     */
    public String createCustomer(String csvInput) {
        try {
            String[] parts = csvInput.split(",");
            
            if (parts.length != 7) {
                return "Error: Invalid input format. Expected: name,dob,email,phone,accountType,accountStatus,balance";
            }
            
            String name = StringUtil.clean(parts[0]);
            String dobStr = StringUtil.clean(parts[1]);
            String email = StringUtil.clean(parts[2]);
            String phone = StringUtil.clean(parts[3]);
            String accountType = StringUtil.clean(parts[4]);
            String accountStatus = StringUtil.clean(parts[5]);
            String balanceStr = StringUtil.clean(parts[6]);
            
            // Validate all fields
            String validationError = validateCustomerInput(name, dobStr, email, phone, accountType, accountStatus, balanceStr);
            if (validationError != null) {
                return validationError;
            }
            
            // Parse validated data
            LocalDate dob = DateUtil.parseDate(dobStr);
            int balance = Integer.parseInt(balanceStr);
            
            // Check for duplicate email/phone
            if (customerDAO.emailExists(email)) {
                return "Error: Email already exists in the system.";
            }
            
            if (customerDAO.phoneExists(phone)) {
                return "Error: Phone number already exists in the system.";
            }
            
            // Create customer
            Customer customer = new Customer();
            customer.setId(customerDAO.getNextCustomerId());
            customer.setName(name);
            customer.setDateOfBirth(dob);
            customer.setEmail(email);
            customer.setPhone(phone);
            
            boolean customerCreated = customerDAO.createCustomer(customer);
            
            if (!customerCreated) {
                return "Error: Failed to create customer.";
            }
            
            // Create account
            Account account = new Account();
            account.setAccountNo(accountDAO.generateAccountNumber());
            account.setAccountType(accountType);
            account.setCustomerId(customer.getId());
            account.setAccountStatus(accountStatus);
            account.setBalance(balance);
            
            boolean accountCreated = accountDAO.createAccount(account);
            
            if (!accountCreated) {
                return "Error: Customer created but failed to create account.";
            }
            
            return String.format("Success: Customer created successfully.\\nCustomer ID: %d\\nAccount Number: %d", 
                                customer.getId(), account.getAccountNo());
            
        } catch (SQLException e) {
            return "Error: Database operation failed. Please try again.";
        } catch (NumberFormatException e) {
            return "Error: Invalid balance amount. Please enter a valid number.";
        } catch (Exception e) {
            return "Error: An unexpected error occurred. Please try again.";
        }
    }
    
    /**
     * Validate customer input data
     */
    private String validateCustomerInput(String name, String dobStr, String email, String phone, 
                                       String accountType, String accountStatus, String balanceStr) {
        
        // Validate name
        if (StringUtil.isNullOrEmpty(name)) {
            return "Error: Customer name is required.";
        }
        if (!StringUtil.isWithinLength(name, 100)) {
            return "Error: Customer name must be within 100 characters.";
        }
        if (!StringUtil.isAlphabetic(name)) {
            return "Error: Customer name should contain only alphabets and spaces.";
        }
        
        // Validate date of birth
        if (StringUtil.isNullOrEmpty(dobStr)) {
            return "Error: Date of birth is required.";
        }
        if (!DateUtil.isValidDateFormat(dobStr)) {
            return "Error: Invalid date format. Please use DD-MM-YYYY format.";
        }
        LocalDate dob = DateUtil.parseDate(dobStr);
        if (!DateUtil.isAdult(dob)) {
            return "Error: Customer must be at least 18 years old.";
        }
        
        // Validate email
        if (StringUtil.isNullOrEmpty(email)) {
            return "Error: Email is required.";
        }
        if (!StringUtil.isWithinLength(email, 100)) {
            return "Error: Email must be within 100 characters.";
        }
        if (!StringUtil.isValidEmail(email)) {
            return "Error: Invalid email format.";
        }
        
        // Validate phone
        if (StringUtil.isNullOrEmpty(phone)) {
            return "Error: Phone number is required.";
        }
        if (!StringUtil.isValidPhone(phone)) {
            return "Error: Phone number must be 10-15 digits.";
        }
        
        // Validate account type
        if (StringUtil.isNullOrEmpty(accountType)) {
            return "Error: Account type is required.";
        }
        if (!accountType.equals("Current") && !accountType.equals("Savings") && !accountType.equals("DMAT")) {
            return "Error: Account type must be Current, Savings, or DMAT.";
        }
        
        // Validate account status
        if (StringUtil.isNullOrEmpty(accountStatus)) {
            return "Error: Account status is required.";
        }
        if (!accountStatus.equals("Active") && !accountStatus.equals("Inactive") && !accountStatus.equals("Suspended")) {
            return "Error: Account status must be Active, Inactive, or Suspended.";
        }
        
        // Validate balance
        if (StringUtil.isNullOrEmpty(balanceStr)) {
            return "Error: Balance is required.";
        }
        if (!StringUtil.isNumeric(balanceStr)) {
            return "Error: Balance must be a valid number.";
        }
        
        try {
            int balance = Integer.parseInt(balanceStr);
            if (balance < 0) {
                return "Error: Balance cannot be negative.";
            }
            if (balanceStr.length() >= 10) {
                return "Error: Balance must be less than 10 digits.";
            }
        } catch (NumberFormatException e) {
            return "Error: Invalid balance amount.";
        }
        
        return null; // No validation errors
    }
}
