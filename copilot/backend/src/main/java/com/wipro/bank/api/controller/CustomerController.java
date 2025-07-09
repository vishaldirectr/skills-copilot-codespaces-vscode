package com.wipro.bank.api.controller;

import com.wipro.bank.api.dto.CustomerRequest;
import com.wipro.bank.api.dto.ApiResponse;
import com.wipro.bank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST API Controller for Customer Management
 */
@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;
    
    /**
     * Create a new customer
     * POST /api/customers
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createCustomer(@RequestBody CustomerRequest request) {
        try {
            // Convert DTO to service input format
            String csvInput = String.format("%s,%s,%s,%s,%s,%s,%d",
                request.getName(),
                request.getDateOfBirth(),
                request.getEmail(),
                request.getPhone(),
                request.getAccountType(),
                request.getAccountStatus(),
                request.getBalance()
            );
            
            String result = customerService.createCustomer(csvInput);
            
            if (result.startsWith("Success:")) {
                return ResponseEntity.ok(ApiResponse.success("Customer created successfully", result));
            } else {
                return ResponseEntity.badRequest(ApiResponse.error(result));
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Internal server error occurred"));
        }
    }
    
    /**
     * Get customer information by ID
     * GET /api/customers/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getCustomer(@PathVariable int id) {
        try {
            // Implementation would use CustomerDAO to get customer details
            return ResponseEntity.ok(ApiResponse.success("Customer retrieved successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve customer"));
        }
    }
    
    /**
     * Health check endpoint
     * GET /api/customers/health
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok(ApiResponse.success("Customer service is healthy", "OK"));
    }
}
