package com.wipro.bank;

import com.wipro.bank.service.BankMenuService;
import com.wipro.bank.util.DatabaseUtil;

/**
 * Main Bank Application Entry Point
 * Console-based bank application for relationship managers
 */
public class BankApplication {
    
    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("    WELCOME TO DIGITAL BANK APPLICATION     ");
        System.out.println("===========================================");
        
        try {
            // Initialize database connection
            DatabaseUtil.initializeDatabase();
            
            // Start the menu service
            BankMenuService menuService = new BankMenuService();
            menuService.displayMainMenu();
            
        } catch (Exception e) {
            System.err.println("Error starting the application: " + e.getMessage());
            System.exit(1);
        } finally {
            // Close database connection
            DatabaseUtil.closeConnection();
        }
    }
}
