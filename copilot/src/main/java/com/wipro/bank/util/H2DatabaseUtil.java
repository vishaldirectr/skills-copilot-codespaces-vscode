package com.wipro.bank.util;

import java.sql.*;

/**
 * H2 Database Utility Class for In-Memory Database Testing
 * Handles database initialization, connection management, and schema creation
 */
public class H2DatabaseUtil {
    
    private static final String DB_URL = "jdbc:h2:mem:bank_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";
    private static Connection connection;
    
    /**
     * Initialize database connection and create tables if they don't exist
     */
    public static void initializeDatabase() throws SQLException {
        try {
            // Load H2 JDBC driver
            Class.forName("org.h2.Driver");
            
            // Establish connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("H2 Database connection established successfully.");
            
            // Create tables if they don't exist
            createTables();
            
            // Insert sample data
            insertSampleData();
            
        } catch (ClassNotFoundException e) {
            throw new SQLException("H2 JDBC Driver not found. Please add H2 JAR to classpath.", e);
        } catch (SQLException e) {
            System.err.println("Failed to connect to H2 database: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Get database connection
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            initializeDatabase();
        }
        return connection;
    }
    
    /**
     * Create database tables
     */
    private static void createTables() throws SQLException {
        String[] createTableQueries = {
            // Create CUSTOMER table
            "CREATE TABLE IF NOT EXISTS CUSTOMER (" +
                "ID INT PRIMARY KEY, " +
                "NAME VARCHAR(100) NOT NULL, " +
                "DOB DATE NOT NULL, " +
                "EMAIL VARCHAR(100) NOT NULL UNIQUE, " +
                "PHONE VARCHAR(15) NOT NULL UNIQUE, " +
                "CREATED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
            ")",
            
            // Create ACCOUNT table
            "CREATE TABLE IF NOT EXISTS ACCOUNT (" +
                "ACCOUNT_NO BIGINT PRIMARY KEY, " +
                "ACCOUNT_TYPE VARCHAR(10) NOT NULL CHECK (ACCOUNT_TYPE IN ('Current', 'Savings', 'DMAT')), " +
                "CUSTOMER_ID INT NOT NULL, " +
                "ACCOUNT_STATUS VARCHAR(10) NOT NULL CHECK (ACCOUNT_STATUS IN ('Active', 'Inactive', 'Suspended')), " +
                "UPDATED_TIMESTAMP TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "BALANCE INT NOT NULL DEFAULT 0 CHECK (BALANCE >= 0), " +
                "FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMER(ID) ON DELETE CASCADE " +
            ")",
            
            // Create TRANSACTION_DETAILS table
            "CREATE TABLE IF NOT EXISTS TRANSACTION_DETAILS (" +
                "ID INT AUTO_INCREMENT PRIMARY KEY, " +
                "ACCOUNT_NO BIGINT NOT NULL, " +
                "CUSTOMER_ID INT NOT NULL, " +
                "AMOUNT INT NOT NULL CHECK (AMOUNT > 0), " +
                "TRANSACTION_TYPE VARCHAR(10) NOT NULL CHECK (TRANSACTION_TYPE IN ('CREDIT', 'DEBIT')), " +
                "UPDATED_TIMESTAMP TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (ACCOUNT_NO) REFERENCES ACCOUNT(ACCOUNT_NO) ON DELETE CASCADE, " +
                "FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMER(ID) ON DELETE CASCADE " +
            ")",
            
            // Create indexes for better performance
            "CREATE INDEX IF NOT EXISTS idx_customer_email ON CUSTOMER(EMAIL)",
            "CREATE INDEX IF NOT EXISTS idx_customer_phone ON CUSTOMER(PHONE)",
            "CREATE INDEX IF NOT EXISTS idx_account_customer ON ACCOUNT(CUSTOMER_ID)",
            "CREATE INDEX IF NOT EXISTS idx_transaction_account ON TRANSACTION_DETAILS(ACCOUNT_NO)",
            "CREATE INDEX IF NOT EXISTS idx_transaction_customer ON TRANSACTION_DETAILS(CUSTOMER_ID)",
            "CREATE INDEX IF NOT EXISTS idx_transaction_timestamp ON TRANSACTION_DETAILS(UPDATED_TIMESTAMP)"
        };
        
        try (Statement stmt = connection.createStatement()) {
            for (String query : createTableQueries) {
                stmt.execute(query);
            }
            System.out.println("Database tables created successfully.");
        }
    }
    
    /**
     * Insert sample data for testing
     */
    private static void insertSampleData() throws SQLException {
        String[] sampleDataQueries = {
            // Insert sample customers
            "INSERT INTO CUSTOMER (ID, NAME, DOB, EMAIL, PHONE) VALUES " +
                "(1001, 'John Doe', '1990-01-15', 'john.doe@email.com', '9876543210'), " +
                "(1002, 'Jane Smith', '1985-05-20', 'jane.smith@email.com', '9876543211'), " +
                "(1003, 'Bob Johnson', '1992-03-10', 'bob.johnson@email.com', '9876543212')",
            
            // Insert sample accounts
            "INSERT INTO ACCOUNT (ACCOUNT_NO, ACCOUNT_TYPE, CUSTOMER_ID, ACCOUNT_STATUS, BALANCE) VALUES " +
                "(10011234567890123, 'Savings', 1001, 'Active', 50000), " +
                "(10021234567890123, 'Current', 1002, 'Active', 75000), " +
                "(10031234567890123, 'Savings', 1003, 'Active', 30000)",
            
            // Insert sample transactions
            "INSERT INTO TRANSACTION_DETAILS (ACCOUNT_NO, CUSTOMER_ID, AMOUNT, TRANSACTION_TYPE) VALUES " +
                "(10011234567890123, 1001, 10000, 'CREDIT'), " +
                "(10021234567890123, 1002, 5000, 'DEBIT'), " +
                "(10031234567890123, 1003, 15000, 'CREDIT')"
        };
        
        try (Statement stmt = connection.createStatement()) {
            for (String query : sampleDataQueries) {
                try {
                    stmt.execute(query);
                } catch (SQLException e) {
                    // Ignore if data already exists
                    if (!e.getMessage().contains("already exists") && !e.getMessage().contains("duplicate")) {
                        System.out.println("Sample data insertion warning: " + e.getMessage());
                    }
                }
            }
            System.out.println("Sample data inserted successfully.");
        }
    }
    
    /**
     * Close database connection
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
    
    /**
     * Execute a test query to verify connection
     */
    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM CUSTOMER")) {
                if (rs.next()) {
                    System.out.println("Database test successful. Customer count: " + rs.getInt(1));
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Database test failed: " + e.getMessage());
            return false;
        }
        return false;
    }
}
