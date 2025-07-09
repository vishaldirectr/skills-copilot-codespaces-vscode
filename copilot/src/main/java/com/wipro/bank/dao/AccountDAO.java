package com.wipro.bank.dao;

import com.wipro.bank.model.Account;
import com.wipro.bank.util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Account Data Access Object
 */
public class AccountDAO {
    
    /**
     * Generate unique account number using timestamp format: MMddHHmmssSSSSSSS
     */
    public long generateAccountNumber() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddHHmmssSSSSSSS");
        String accountNoStr = now.format(formatter);
        
        // Ensure 17 digits by padding if necessary
        while (accountNoStr.length() < 17) {
            accountNoStr = "0" + accountNoStr;
        }
        
        return Long.parseLong(accountNoStr.substring(0, 17));
    }
    
    /**
     * Create a new account
     */
    public boolean createAccount(Account account) throws SQLException {
        String query = "INSERT INTO ACCOUNT (ACCOUNT_NO, ACCOUNT_TYPE, CUSTOMER_ID, ACCOUNT_STATUS, BALANCE) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setLong(1, account.getAccountNo());
            stmt.setString(2, account.getAccountType());
            stmt.setInt(3, account.getCustomerId());
            stmt.setString(4, account.getAccountStatus());
            stmt.setInt(5, account.getBalance());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Get account by account number
     */
    public Account getAccountByNumber(long accountNo) throws SQLException {
        String query = "SELECT * FROM ACCOUNT WHERE ACCOUNT_NO = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setLong(1, accountNo);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Account account = new Account();
                    account.setAccountNo(rs.getLong("ACCOUNT_NO"));
                    account.setAccountType(rs.getString("ACCOUNT_TYPE"));
                    account.setCustomerId(rs.getInt("CUSTOMER_ID"));
                    account.setAccountStatus(rs.getString("ACCOUNT_STATUS"));
                    account.setUpdatedTimestamp(rs.getTimestamp("UPDATED_TIMESTAMP").toLocalDateTime());
                    account.setBalance(rs.getInt("BALANCE"));
                    return account;
                }
            }
        }
        return null;
    }
    
    /**
     * Update account balance
     */
    public boolean updateBalance(long accountNo, int newBalance) throws SQLException {
        String query = "UPDATE ACCOUNT SET BALANCE = ?, UPDATED_TIMESTAMP = CURRENT_TIMESTAMP WHERE ACCOUNT_NO = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, newBalance);
            stmt.setLong(2, accountNo);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Get accounts by customer ID
     */
    public List<Account> getAccountsByCustomerId(int customerId) throws SQLException {
        String query = "SELECT * FROM ACCOUNT WHERE CUSTOMER_ID = ?";
        List<Account> accounts = new ArrayList<>();
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, customerId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Account account = new Account();
                    account.setAccountNo(rs.getLong("ACCOUNT_NO"));
                    account.setAccountType(rs.getString("ACCOUNT_TYPE"));
                    account.setCustomerId(rs.getInt("CUSTOMER_ID"));
                    account.setAccountStatus(rs.getString("ACCOUNT_STATUS"));
                    account.setUpdatedTimestamp(rs.getTimestamp("UPDATED_TIMESTAMP").toLocalDateTime());
                    account.setBalance(rs.getInt("BALANCE"));
                    accounts.add(account);
                }
            }
        }
        return accounts;
    }
    
    /**
     * Check if account exists and is active
     */
    public boolean isAccountActiveAndExists(long accountNo) throws SQLException {
        String query = "SELECT ACCOUNT_STATUS FROM ACCOUNT WHERE ACCOUNT_NO = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setLong(1, accountNo);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String status = rs.getString("ACCOUNT_STATUS");
                    return "Active".equals(status);
                }
            }
        }
        return false;
    }
    
    /**
     * Get account details with customer information for reporting
     */
    public List<Object[]> getAccountsWithCustomerInfo(int offset, int limit) throws SQLException {
        String query = """
            SELECT c.NAME, a.ACCOUNT_NO, a.BALANCE, a.UPDATED_TIMESTAMP,
                   COALESCE(t.TRANSACTION_TYPE, 'No Transaction') as LAST_TRANSACTION
            FROM ACCOUNT a
            INNER JOIN CUSTOMER c ON a.CUSTOMER_ID = c.ID
            LEFT JOIN TRANSACTION_DETAILS t ON a.ACCOUNT_NO = t.ACCOUNT_NO 
                AND t.UPDATED_TIMESTAMP = (
                    SELECT MAX(UPDATED_TIMESTAMP) 
                    FROM TRANSACTION_DETAILS 
                    WHERE ACCOUNT_NO = a.ACCOUNT_NO
                )
            ORDER BY a.UPDATED_TIMESTAMP DESC
            LIMIT ? OFFSET ?
            """;
        
        List<Object[]> results = new ArrayList<>();
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Object[] row = new Object[5];
                    row[0] = rs.getString("NAME");
                    row[1] = rs.getLong("ACCOUNT_NO");
                    row[2] = rs.getInt("BALANCE");
                    row[3] = rs.getTimestamp("UPDATED_TIMESTAMP");
                    row[4] = rs.getString("LAST_TRANSACTION");
                    results.add(row);
                }
            }
        }
        return results;
    }
    
    /**
     * Get total account count
     */
    public int getTotalAccountCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM ACCOUNT";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}
