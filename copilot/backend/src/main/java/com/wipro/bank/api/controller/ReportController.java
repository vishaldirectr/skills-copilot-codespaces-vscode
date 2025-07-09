package com.wipro.bank.api.controller;

import com.wipro.bank.api.dto.ApiResponse;
import com.wipro.bank.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST API Controller for Reports
 */
@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {
    
    @Autowired
    private ReportService reportService;
    
    /**
     * Get paginated account report
     * GET /api/reports/accounts
     */
    @GetMapping("/accounts")
    public ResponseEntity<ApiResponse<Object>> getAccountReport(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        try {
            // Implementation would use ReportService to get paginated data
            return ResponseEntity.ok(ApiResponse.success("Account report retrieved", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to generate account report"));
        }
    }
    
    /**
     * Get summary report
     * GET /api/reports/summary
     */
    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<Object>> getSummaryReport() {
        try {
            // Implementation would use ReportService to get summary data
            return ResponseEntity.ok(ApiResponse.success("Summary report retrieved", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to generate summary report"));
        }
    }
    
    /**
     * Health check endpoint
     * GET /api/reports/health
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok(ApiResponse.success("Report service is healthy", "OK"));
    }
}
