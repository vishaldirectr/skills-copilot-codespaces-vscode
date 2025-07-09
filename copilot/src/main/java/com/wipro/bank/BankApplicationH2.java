package com.wipro.bank;

import com.wipro.bank.service.BankMenuService;
import com.wipro.bank.util.H2DatabaseUtil;
import java.sql.SQLException;

/**
 * Bank Application with H2 Database for Testing
 * This version uses H2 in-memory database for easy testing without MySQL setup
 */
public class BankApplicationH2 {
    
    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("    WELCOME TO DIGITAL BANK APPLICATION    ");
        System.out.println("         (H2 Database Version)           ");
        System.out.println("===========================================");
        
        try {
            // Initialize H2 database
            H2DatabaseUtil.initializeDatabase();
            
            // Test database connection
            if (H2DatabaseUtil.testConnection()) {
                System.out.println("✅ Database is ready with sample data!");
                System.out.println();
                
                // Start the menu service
                BankMenuService menuService = new BankMenuService();
                menuService.displayMainMenu();
            } else {
                System.out.println("❌ Database test failed. Please check configuration.");
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Database initialization failed: " + e.getMessage());
            System.out.println("\nPlease ensure H2 database JAR is in the classpath.");
        } catch (Exception e) {
            System.err.println("❌ Application error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close database connection
            H2DatabaseUtil.closeConnection();
        }
    }
}
