package com.wipro.bank;

import com.wipro.bank.util.H2DatabaseUtil;
import java.sql.*;

/**
 * Simple H2 Database Test Application
 * Tests H2 database connectivity and shows sample data
 */
public class H2DatabaseTest {
    
    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("   H2 DATABASE CONNECTION TEST           ");
        System.out.println("===========================================");
        
        try {
            // Initialize H2 database
            H2DatabaseUtil.initializeDatabase();
            
            // Test database connection with sample queries
            testDatabaseQueries();
            
        } catch (SQLException e) {
            System.err.println("❌ Database error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Application error: " + e.getMessage());
        } finally {
            // Close database connection
            H2DatabaseUtil.closeConnection();
        }
    }
    
    private static void testDatabaseQueries() throws SQLException {
        Connection conn = H2DatabaseUtil.getConnection();
        
        // Test 1: Show all customers
        System.out.println("\n📊 CUSTOMERS:");
        System.out.println("-------------------------------------------");
        String customerQuery = "SELECT * FROM CUSTOMER";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(customerQuery)) {
            
            while (rs.next()) {
                System.out.printf("ID: %d | Name: %s | Email: %s | Phone: %s%n",
                    rs.getInt("ID"),
                    rs.getString("NAME"),
                    rs.getString("EMAIL"),
                    rs.getString("PHONE"));
            }
        }
        
        // Test 2: Show all accounts
        System.out.println("\n💳 ACCOUNTS:");
        System.out.println("-------------------------------------------");
        String accountQuery = "SELECT * FROM ACCOUNT";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(accountQuery)) {
            
            while (rs.next()) {
                System.out.printf("Account: %d | Type: %s | Customer: %d | Balance: ₹%d | Status: %s%n",
                    rs.getLong("ACCOUNT_NO"),
                    rs.getString("ACCOUNT_TYPE"),
                    rs.getInt("CUSTOMER_ID"),
                    rs.getInt("BALANCE"),
                    rs.getString("ACCOUNT_STATUS"));
            }
        }
        
        // Test 3: Show all transactions
        System.out.println("\n💰 TRANSACTIONS:");
        System.out.println("-------------------------------------------");
        String transactionQuery = "SELECT * FROM TRANSACTION_DETAILS";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(transactionQuery)) {
            
            while (rs.next()) {
                System.out.printf("ID: %d | Account: %d | Amount: ₹%d | Type: %s | Date: %s%n",
                    rs.getInt("ID"),
                    rs.getLong("ACCOUNT_NO"),
                    rs.getInt("AMOUNT"),
                    rs.getString("TRANSACTION_TYPE"),
                    rs.getTimestamp("UPDATED_TIMESTAMP"));
            }
        }
        
        // Test 4: Test a simple fund transfer simulation
        System.out.println("\n🔄 FUND TRANSFER SIMULATION:");
        System.out.println("-------------------------------------------");
        
        // Debit from account 1001
        String debitQuery = "UPDATE ACCOUNT SET BALANCE = BALANCE - 5000 WHERE ACCOUNT_NO = 10011234567890123";
        String creditQuery = "UPDATE ACCOUNT SET BALANCE = BALANCE + 5000 WHERE ACCOUNT_NO = 10021234567890123";
        String insertDebitTxn = "INSERT INTO TRANSACTION_DETAILS (ACCOUNT_NO, CUSTOMER_ID, AMOUNT, TRANSACTION_TYPE) VALUES (10011234567890123, 1001, 5000, 'DEBIT')";
        String insertCreditTxn = "INSERT INTO TRANSACTION_DETAILS (ACCOUNT_NO, CUSTOMER_ID, AMOUNT, TRANSACTION_TYPE) VALUES (10021234567890123, 1002, 5000, 'CREDIT')";
        
        try (Statement stmt = conn.createStatement()) {
            conn.setAutoCommit(false); // Start transaction
            
            stmt.executeUpdate(debitQuery);
            stmt.executeUpdate(creditQuery);
            stmt.executeUpdate(insertDebitTxn);
            stmt.executeUpdate(insertCreditTxn);
            
            conn.commit(); // Commit transaction
            conn.setAutoCommit(true);
            
            System.out.println("✅ Transfer of ₹5,000 from Account 1001 to Account 1002 completed successfully!");
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        }
        
        // Show updated balances
        System.out.println("\n💳 UPDATED ACCOUNT BALANCES:");
        System.out.println("-------------------------------------------");
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(accountQuery)) {
            
            while (rs.next()) {
                System.out.printf("Account: %d | Customer: %d | Balance: ₹%d%n",
                    rs.getLong("ACCOUNT_NO"),
                    rs.getInt("CUSTOMER_ID"),
                    rs.getInt("BALANCE"));
            }
        }
        
        System.out.println("\n✅ H2 Database test completed successfully!");
        System.out.println("The banking application backend is working perfectly!");
    }
}
