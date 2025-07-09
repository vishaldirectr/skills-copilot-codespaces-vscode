package com.wipro.bank.dao;

import com.wipro.bank.model.Transaction;
import com.wipro.bank.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Transaction Data Access Object
 */
public class TransactionDAO {
    
    /**
     * Create a new transaction record
     */
    public boolean createTransaction(Transaction transaction) throws SQLException {
        String query = "INSERT INTO TRANSACTION_DETAILS (ACCOUNT_NO, CUSTOMER_ID, AMOUNT, TRANSACTION_TYPE) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setLong(1, transaction.getAccountNo());
            stmt.setInt(2, transaction.getCustomerId());
            stmt.setInt(3, transaction.getAmount());
            stmt.setString(4, transaction.getTransactionType());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Get transaction history for an account
     */
    public List<Transaction> getTransactionHistory(long accountNo, int offset, int limit) throws SQLException {
        String query = "SELECT * FROM TRANSACTION_DETAILS WHERE ACCOUNT_NO = ? ORDER BY UPDATED_TIMESTAMP DESC LIMIT ? OFFSET ?";
        List<Transaction> transactions = new ArrayList<>();
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setLong(1, accountNo);
            stmt.setInt(2, limit);
            stmt.setInt(3, offset);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setId(rs.getInt("ID"));
                    transaction.setAccountNo(rs.getLong("ACCOUNT_NO"));
                    transaction.setCustomerId(rs.getInt("CUSTOMER_ID"));
                    transaction.setAmount(rs.getInt("AMOUNT"));
                    transaction.setTransactionType(rs.getString("TRANSACTION_TYPE"));
                    transaction.setUpdatedTimestamp(rs.getTimestamp("UPDATED_TIMESTAMP").toLocalDateTime());
                    transactions.add(transaction);
                }
            }
        }
        return transactions;
    }
    
    /**
     * Get latest transaction for an account
     */
    public Transaction getLatestTransaction(long accountNo) throws SQLException {
        String query = "SELECT * FROM TRANSACTION_DETAILS WHERE ACCOUNT_NO = ? ORDER BY UPDATED_TIMESTAMP DESC LIMIT 1";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setLong(1, accountNo);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setId(rs.getInt("ID"));
                    transaction.setAccountNo(rs.getLong("ACCOUNT_NO"));
                    transaction.setCustomerId(rs.getInt("CUSTOMER_ID"));
                    transaction.setAmount(rs.getInt("AMOUNT"));
                    transaction.setTransactionType(rs.getString("TRANSACTION_TYPE"));
                    transaction.setUpdatedTimestamp(rs.getTimestamp("UPDATED_TIMESTAMP").toLocalDateTime());
                    return transaction;
                }
            }
        }
        return null;
    }
    
    /**
     * Get all transactions with pagination
     */
    public List<Transaction> getAllTransactions(int offset, int limit) throws SQLException {
        String query = "SELECT * FROM TRANSACTION_DETAILS ORDER BY UPDATED_TIMESTAMP DESC LIMIT ? OFFSET ?";
        List<Transaction> transactions = new ArrayList<>();
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setId(rs.getInt("ID"));
                    transaction.setAccountNo(rs.getLong("ACCOUNT_NO"));
                    transaction.setCustomerId(rs.getInt("CUSTOMER_ID"));
                    transaction.setAmount(rs.getInt("AMOUNT"));
                    transaction.setTransactionType(rs.getString("TRANSACTION_TYPE"));
                    transaction.setUpdatedTimestamp(rs.getTimestamp("UPDATED_TIMESTAMP").toLocalDateTime());
                    transactions.add(transaction);
                }
            }
        }
        return transactions;
    }
    
    /**
     * Get total transaction count
     */
    public int getTotalTransactionCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM TRANSACTION_DETAILS";
        
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
