package com.wipro.bank.api.dto;

import java.time.LocalDate;

/**
 * Customer Request DTO for API
 */
public class CustomerRequest {
    private String name;
    private String dateOfBirth; // DD-MM-YYYY format
    private String email;
    private String phone;
    private String accountType;
    private String accountStatus;
    private Integer balance;
    
    // Constructors
    public CustomerRequest() {}
    
    public CustomerRequest(String name, String dateOfBirth, String email, String phone, 
                          String accountType, String accountStatus, Integer balance) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone = phone;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
        this.balance = balance;
    }
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }
    
    public String getAccountStatus() { return accountStatus; }
    public void setAccountStatus(String accountStatus) { this.accountStatus = accountStatus; }
    
    public Integer getBalance() { return balance; }
    public void setBalance(Integer balance) { this.balance = balance; }
}
