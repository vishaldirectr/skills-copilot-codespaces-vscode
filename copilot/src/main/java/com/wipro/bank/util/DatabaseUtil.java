package com.wipro.bank.util;

import java.sql.*;

/**
 * Database Utility Class for MySQL Connection Management
 * Handles database initialization, connection management, and schema creation
 */
public class DatabaseUtil {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bank_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";
    private static Connection connection;
    
    /**
     * Initialize database connection and create tables if they don't exist
     */
    public static void initializeDatabase() throws SQLException {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Database connection established successfully.");
            
            // Create tables if they don't exist
            createTables();
            
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found. Please add MySQL connector to classpath.", e);
        } catch (SQLException e) {
            System.err.println("Failed to connect to database. Please ensure MySQL is running and credentials are correct.");
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
     * Create database tables with proper constraints
     */
    private static void createTables() throws SQLException {
        String[] createTableQueries = {
            // Customer table
            """
            CREATE TABLE IF NOT EXISTS CUSTOMER (
                ID INT PRIMARY KEY,
                NAME VARCHAR(100) NOT NULL,
                DOB DATE NOT NULL,
                EMAIL VARCHAR(100) NOT NULL,
                PHONE VARCHAR(15) NOT NULL,
                UNIQUE(EMAIL),
                UNIQUE(PHONE)
            )
            """,
            
            // Account table
            """
            CREATE TABLE IF NOT EXISTS ACCOUNT (
                ACCOUNT_NO BIGINT PRIMARY KEY,
                ACCOUNT_TYPE VARCHAR(10) NOT NULL CHECK (ACCOUNT_TYPE IN ('Current', 'Savings', 'DMAT')),
                CUSTOMER_ID INT NOT NULL,
                ACCOUNT_STATUS VARCHAR(10) NOT NULL CHECK (ACCOUNT_STATUS IN ('Active', 'Inactive', 'Suspended')),
                UPDATED_TIMESTAMP DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                BALANCE INT NOT NULL DEFAULT 0,
                FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMER(ID) ON DELETE CASCADE
            )
            """,
            
            // Transaction details table
            """
            CREATE TABLE IF NOT EXISTS TRANSACTION_DETAILS (
                ID INT AUTO_INCREMENT PRIMARY KEY,
                ACCOUNT_NO BIGINT NOT NULL,
                CUSTOMER_ID INT NOT NULL,
                AMOUNT INT NOT NULL,
                TRANSACTION_TYPE CHAR(10) NOT NULL CHECK (TRANSACTION_TYPE IN ('CREDIT', 'DEBIT')),
                UPDATED_TIMESTAMP DATETIME DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (ACCOUNT_NO) REFERENCES ACCOUNT(ACCOUNT_NO) ON DELETE CASCADE,
                FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMER(ID) ON DELETE CASCADE
            )
            """
        };
        
        try (Statement stmt = connection.createStatement()) {
            for (String query : createTableQueries) {
                stmt.executeUpdate(query);
            }
            System.out.println("Database tables created/verified successfully.");
        }
    }
}
