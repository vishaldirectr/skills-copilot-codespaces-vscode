package com.wipro.bank.api.dto;

/**
 * Transfer Request DTO for API
 */
public class TransferRequest {
    private String fromAccountNo;
    private String toAccountNo;
    private Integer amount;
    
    // Constructors
    public TransferRequest() {}
    
    public TransferRequest(String fromAccountNo, String toAccountNo, Integer amount) {
        this.fromAccountNo = fromAccountNo;
        this.toAccountNo = toAccountNo;
        this.amount = amount;
    }
    
    // Getters and Setters
    public String getFromAccountNo() { return fromAccountNo; }
    public void setFromAccountNo(String fromAccountNo) { this.fromAccountNo = fromAccountNo; }
    
    public String getToAccountNo() { return toAccountNo; }
    public void setToAccountNo(String toAccountNo) { this.toAccountNo = toAccountNo; }
    
    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }
}
