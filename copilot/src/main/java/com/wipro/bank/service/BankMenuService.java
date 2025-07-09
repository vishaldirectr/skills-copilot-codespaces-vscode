package com.wipro.bank.service;

import java.util.Scanner;

/**
 * Bank Menu Service Class for Console Menu Management
 */
public class BankMenuService {
    
    private CustomerService customerService;
    private TransferService transferService;
    private ReportService reportService;
    private Scanner scanner;
    
    public BankMenuService() {
        this.customerService = new CustomerService();
        this.transferService = new TransferService();
        this.reportService = new ReportService();
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Display main menu and handle user interactions
     */
    public void displayMainMenu() {
        boolean running = true;
        
        while (running) {
            try {
                System.out.println("\\n" + "=".repeat(50));
                System.out.println("              MAIN MENU");
                System.out.println("=".repeat(50));
                System.out.println("1. Create Customer");
                System.out.println("2. Bank Transfer");
                System.out.println("3. Report");
                System.out.println("4. Exit");
                System.out.println("=".repeat(50));
                System.out.print("Please select an option (1-4): ");
                
                String choice = scanner.nextLine().trim();
                
                switch (choice) {
                    case "1":
                        handleCreateCustomer();
                        break;
                    case "2":
                        handleBankTransfer();
                        break;
                    case "3":
                        handleReport();
                        break;
                    case "4":
                        System.out.println("\nThank you for using Digital Bank Application!");
                        System.out.println("Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("\\nInvalid option. Please select 1, 2, 3, or 4.");
                        break;
                }
                
            } catch (Exception e) {
                System.err.println("\\nError: An unexpected error occurred. Please try again.");
            }
        }
        
        scanner.close();
    }
    
    /**
     * Handle Create Customer menu
     */
    private void handleCreateCustomer() {
        boolean inSubMenu = true;
        
        while (inSubMenu) {
            System.out.println("\\n" + "=".repeat(50));
            System.out.println("           CREATE CUSTOMER");
            System.out.println("=".repeat(50));
            System.out.println("Input Format: name,dob,email,phone,accountType,accountStatus,balance");
            System.out.println("Example: John Doe,15-01-1990,john@email.com,9876543210,Savings,Active,5000");
            System.out.println("\\nAccount Types: Current, Savings, DMAT");
            System.out.println("Account Status: Active, Inactive, Suspended");
            System.out.println("Date Format: DD-MM-YYYY");
            System.out.println("=".repeat(50));
            System.out.println("1. Enter Customer Details");
            System.out.println("2. Back to Main Menu");
            System.out.println("=".repeat(50));
            System.out.print("Please select an option (1-2): ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    System.out.print("\\nEnter customer details: ");
                    String customerInput = scanner.nextLine().trim();
                    
                    if (!customerInput.isEmpty()) {
                        System.out.println("\\nProcessing customer creation...");
                        String result = customerService.createCustomer(customerInput);
                        System.out.println("\\n" + result);
                    } else {
                        System.out.println("\\nError: Please enter customer details.");
                    }
                    
                    System.out.println("\\nPress ENTER to continue...");
                    scanner.nextLine();
                    break;
                    
                case "2":
                    inSubMenu = false;
                    break;
                    
                default:
                    System.out.println("\\nInvalid option. Please select 1 or 2.");
                    break;
            }
        }
    }
    
    /**
     * Handle Bank Transfer menu
     */
    private void handleBankTransfer() {
        boolean inSubMenu = true;
        
        while (inSubMenu) {
            System.out.println("\\n" + "=".repeat(50));
            System.out.println("           BANK TRANSFER");
            System.out.println("=".repeat(50));
            System.out.println("Input Format: fromAccountNo,toAccountNo,amount");
            System.out.println("Example: 12345678901234567,98765432109876543,5000");
            System.out.println("\\nNote: Account numbers must be 17 digits");
            System.out.println("      Amount must be positive");
            System.out.println("=".repeat(50));
            System.out.println("1. Enter Transfer Details");
            System.out.println("2. Back to Main Menu");
            System.out.println("=".repeat(50));
            System.out.print("Please select an option (1-2): ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    System.out.print("\\nEnter transfer details: ");
                    String transferInput = scanner.nextLine().trim();
                    
                    if (!transferInput.isEmpty()) {
                        System.out.println("\\nProcessing fund transfer...");
                        String result = transferService.transferFunds(transferInput);
                        System.out.println("\\n" + result);
                    } else {
                        System.out.println("\\nError: Please enter transfer details.");
                    }
                    
                    System.out.println("\\nPress ENTER to continue...");
                    scanner.nextLine();
                    break;
                    
                case "2":
                    inSubMenu = false;
                    break;
                    
                default:
                    System.out.println("\\nInvalid option. Please select 1 or 2.");
                    break;
            }
        }
    }
    
    /**
     * Handle Report menu
     */
    private void handleReport() {
        boolean inSubMenu = true;
        
        while (inSubMenu) {
            System.out.println("\\n" + "=".repeat(50));
            System.out.println("              REPORTS");
            System.out.println("=".repeat(50));
            System.out.println("1. Generate Account Report");
            System.out.println("2. Generate Summary Report");
            System.out.println("3. Back to Main Menu");
            System.out.println("=".repeat(50));
            System.out.print("Please select an option (1-3): ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    System.out.println("\\nGenerating detailed account report...");
                    reportService.generateReport();
                    System.out.println("\\nPress ENTER to continue...");
                    scanner.nextLine();
                    break;
                    
                case "2":
                    System.out.println("\\nGenerating summary report...");
                    reportService.generateSummaryReport();
                    System.out.println("\\nPress ENTER to continue...");
                    scanner.nextLine();
                    break;
                    
                case "3":
                    inSubMenu = false;
                    break;
                    
                default:
                    System.out.println("\\nInvalid option. Please select 1, 2, or 3.");
                    break;
            }
        }
    }
}
