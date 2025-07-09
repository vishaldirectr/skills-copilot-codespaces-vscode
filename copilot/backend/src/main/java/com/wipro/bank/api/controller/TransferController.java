package com.wipro.bank.api.controller;

import com.wipro.bank.api.dto.TransferRequest;
import com.wipro.bank.api.dto.ApiResponse;
import com.wipro.bank.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST API Controller for Fund Transfers
 */
@RestController
@RequestMapping("/api/transfers")
@CrossOrigin(origins = "*")
public class TransferController {
    
    @Autowired
    private TransferService transferService;
    
    /**
     * Transfer funds between accounts
     * POST /api/transfers
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Object>> transferFunds(@RequestBody TransferRequest request) {
        try {
            // Convert DTO to service input format
            String transferInput = String.format("%s,%s,%d",
                request.getFromAccountNo(),
                request.getToAccountNo(),
                request.getAmount()
            );
            
            String result = transferService.transferFunds(transferInput);
            
            if (result.startsWith("Success:")) {
                return ResponseEntity.ok(ApiResponse.success("Transfer completed successfully", result));
            } else {
                return ResponseEntity.badRequest(ApiResponse.error(result));
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Internal server error occurred"));
        }
    }
    
    /**
     * Get transfer history for an account
     * GET /api/transfers/history/{accountNo}
     */
    @GetMapping("/history/{accountNo}")
    public ResponseEntity<ApiResponse<Object>> getTransferHistory(
            @PathVariable String accountNo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        try {
            // Implementation would use TransactionDAO to get history
            return ResponseEntity.ok(ApiResponse.success("Transfer history retrieved", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve transfer history"));
        }
    }
    
    /**
     * Health check endpoint
     * GET /api/transfers/health
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok(ApiResponse.success("Transfer service is healthy", "OK"));
    }
}
