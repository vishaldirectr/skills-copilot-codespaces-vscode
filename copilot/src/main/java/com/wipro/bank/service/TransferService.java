package com.wipro.bank.service;

import com.wipro.bank.dao.AccountDAO;
import com.wipro.bank.dao.TransactionDAO;
import com.wipro.bank.model.Account;
import com.wipro.bank.model.Transaction;
import com.wipro.bank.util.StringUtil;

import java.sql.SQLException;

/**
 * Transfer Service Class for Fund Transfers
 */
public class TransferService {
    
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;
    
    public TransferService() {
        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();
    }
    
    /**
     * Transfer funds between accounts
     * Input format: fromAccountNo,toAccountNo,amount
     */
    public String transferFunds(String transferInput) {
        try {
            String[] parts = transferInput.split(",");
            
            if (parts.length != 3) {
                return "Error: Invalid input format. Expected: fromAccountNo,toAccountNo,amount";
            }
            
            String fromAccountStr = StringUtil.clean(parts[0]);
            String toAccountStr = StringUtil.clean(parts[1]);
            String amountStr = StringUtil.clean(parts[2]);
            
            // Validate input
            String validationError = validateTransferInput(fromAccountStr, toAccountStr, amountStr);
            if (validationError != null) {
                return validationError;
            }
            
            long fromAccountNo = Long.parseLong(fromAccountStr);
            long toAccountNo = Long.parseLong(toAccountStr);
            int amount = Integer.parseInt(amountStr);
            
            // Check if accounts exist and are active
            if (!accountDAO.isAccountActiveAndExists(fromAccountNo)) {
                return "Error: Source account does not exist or is not active.";
            }
            
            if (!accountDAO.isAccountActiveAndExists(toAccountNo)) {
                return "Error: Destination account does not exist or is not active.";
            }
            
            // Get account details
            Account fromAccount = accountDAO.getAccountByNumber(fromAccountNo);
            Account toAccount = accountDAO.getAccountByNumber(toAccountNo);
            
            if (fromAccount == null || toAccount == null) {
                return "Error: Unable to retrieve account details.";
            }
            
            // Check sufficient balance
            if (fromAccount.getBalance() < amount) {
                return "Error: Insufficient balance in source account.";
            }
            
            // Perform transfer
            int newFromBalance = fromAccount.getBalance() - amount;
            int newToBalance = toAccount.getBalance() + amount;
            
            // Update balances
            boolean fromUpdated = accountDAO.updateBalance(fromAccountNo, newFromBalance);
            boolean toUpdated = accountDAO.updateBalance(toAccountNo, newToBalance);
            
            if (!fromUpdated || !toUpdated) {
                return "Error: Failed to update account balances. Transaction aborted.";
            }
            
            // Create transaction records
            Transaction debitTransaction = new Transaction();
            debitTransaction.setAccountNo(fromAccountNo);
            debitTransaction.setCustomerId(fromAccount.getCustomerId());
            debitTransaction.setAmount(amount);
            debitTransaction.setTransactionType("DEBIT");
            
            Transaction creditTransaction = new Transaction();
            creditTransaction.setAccountNo(toAccountNo);
            creditTransaction.setCustomerId(toAccount.getCustomerId());
            creditTransaction.setAmount(amount);
            creditTransaction.setTransactionType("CREDIT");
            
            boolean debitRecorded = transactionDAO.createTransaction(debitTransaction);
            boolean creditRecorded = transactionDAO.createTransaction(creditTransaction);
            
            if (!debitRecorded || !creditRecorded) {
                return "Warning: Transfer completed but transaction records may be incomplete.";
            }
            
            return String.format("Success: Transfer completed successfully.\\nAmount: %d\\nFrom Account: %d (New Balance: %d)\\nTo Account: %d (New Balance: %d)",
                                amount, fromAccountNo, newFromBalance, toAccountNo, newToBalance);
            
        } catch (SQLException e) {
            return "Error: Database operation failed. Please try again.";
        } catch (NumberFormatException e) {
            return "Error: Invalid account number or amount format.";
        } catch (Exception e) {
            return "Error: An unexpected error occurred. Please try again.";
        }
    }
    
    /**
     * Validate transfer input
     */
    private String validateTransferInput(String fromAccountStr, String toAccountStr, String amountStr) {
        
        // Validate from account number
        if (StringUtil.isNullOrEmpty(fromAccountStr)) {
            return "Error: Source account number is required.";
        }
        if (!StringUtil.isValidAccountNumber(fromAccountStr)) {
            return "Error: Invalid source account number format. Must be 17 digits.";
        }
        
        // Validate to account number
        if (StringUtil.isNullOrEmpty(toAccountStr)) {
            return "Error: Destination account number is required.";
        }
        if (!StringUtil.isValidAccountNumber(toAccountStr)) {
            return "Error: Invalid destination account number format. Must be 17 digits.";
        }
        
        // Check if accounts are different
        if (fromAccountStr.equals(toAccountStr)) {
            return "Error: Source and destination accounts cannot be the same.";
        }
        
        // Validate amount
        if (StringUtil.isNullOrEmpty(amountStr)) {
            return "Error: Transfer amount is required.";
        }
        if (!StringUtil.isNumeric(amountStr)) {
            return "Error: Transfer amount must be a valid number.";
        }
        
        try {
            int amount = Integer.parseInt(amountStr);
            if (amount <= 0) {
                return "Error: Transfer amount must be positive.";
            }
            if (amountStr.length() >= 10) {
                return "Error: Transfer amount must be less than 10 digits.";
            }
        } catch (NumberFormatException e) {
            return "Error: Invalid transfer amount.";
        }
        
        return null; // No validation errors
    }
}
