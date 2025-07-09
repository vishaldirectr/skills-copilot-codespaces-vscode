package com.wipro.bank.service;

import com.wipro.bank.dao.AccountDAO;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * Report Service Class for Generating Reports
 */
public class ReportService {
    
    private AccountDAO accountDAO;
    private static final int ROWS_PER_PAGE = 5;
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy, h:mm a");
    
    public ReportService() {
        this.accountDAO = new AccountDAO();
    }
    
    /**
     * Generate paginated report of accounts with customer information
     */
    public void generateReport() {
        Scanner scanner = new Scanner(System.in);
        try {
            int totalRecords = accountDAO.getTotalAccountCount();
            
            if (totalRecords == 0) {
                System.out.println("No records found to display.");
                return;
            }
            
            System.out.println("\\n=== BANK ACCOUNT REPORT ===");
            System.out.println("Total Records: " + totalRecords);
            System.out.println("Showing " + ROWS_PER_PAGE + " records per page");
            System.out.println("Press ENTER to load next page, 'q' to quit");
            System.out.println("================================================");
            
            int currentPage = 0;
            int offset = 0;
            
            while (offset < totalRecords) {
                // Get data for current page
                List<Object[]> pageData = accountDAO.getAccountsWithCustomerInfo(offset, ROWS_PER_PAGE);
                
                if (pageData.isEmpty()) {
                    break;
                }
                
                currentPage++;
                int endRecord = Math.min(offset + ROWS_PER_PAGE, totalRecords);
                
                System.out.printf("\\nPage %d (Records %d-%d of %d)\\n", 
                                 currentPage, offset + 1, endRecord, totalRecords);
                System.out.println("================================================");
                
                // Display header
                System.out.printf("%-20s %-17s %-12s %-20s %-15s\\n", 
                                 "Customer Name", "Account Number", "Balance", "Last Updated", "Last Transaction");
                System.out.println("------------------------------------------------");
                
                // Display data
                for (Object[] row : pageData) {
                    String customerName = (String) row[0];
                    Long accountNumber = (Long) row[1];
                    Integer balance = (Integer) row[2];
                    Timestamp lastUpdated = (Timestamp) row[3];
                    String lastTransaction = (String) row[4];
                    
                    // Format the timestamp
                    String formattedDate = lastUpdated.toLocalDateTime().format(DISPLAY_FORMAT);
                    
                    // Truncate long names for display
                    String displayName = customerName.length() > 18 ? 
                                        customerName.substring(0, 15) + "..." : customerName;
                    
                    System.out.printf("%-20s %-17d %-12d %-20s %-15s\\n", 
                                     displayName, accountNumber, balance, formattedDate, lastTransaction);
                }
                
                System.out.println("================================================");
                
                // Check if there are more records
                if (offset + ROWS_PER_PAGE >= totalRecords) {
                    System.out.println("End of report reached.");
                    break;
                }
                
                // Wait for user input
                System.out.print("Press ENTER for next page, 'q' to quit: ");
                String input = scanner.nextLine().trim();
                
                if ("q".equalsIgnoreCase(input)) {
                    System.out.println("Report generation cancelled by user.");
                    break;
                }
                
                offset += ROWS_PER_PAGE;
            }
            
        } catch (SQLException e) {
            System.err.println("Error: Failed to generate report. Database operation failed.");
        } catch (Exception e) {
            System.err.println("Error: An unexpected error occurred while generating report.");
        } finally {
            // Note: Scanner is managed by the calling method to avoid closing System.in
        }
    }
    
    /**
     * Generate summary statistics
     */
    public void generateSummaryReport() {
        try {
            System.out.println("\\n=== BANK SUMMARY REPORT ===");
            
            int totalAccounts = accountDAO.getTotalAccountCount();
            System.out.println("Total Accounts: " + totalAccounts);
            
            if (totalAccounts > 0) {
                System.out.println("\\nPress ENTER to view detailed report...");
                // Note: Using the shared scanner from BankMenuService
                generateReport();
            }
            
        } catch (SQLException e) {
            System.err.println("Error: Failed to generate summary report. Database operation failed.");
        } catch (Exception e) {
            System.err.println("Error: An unexpected error occurred while generating summary report.");
        }
    }
}
