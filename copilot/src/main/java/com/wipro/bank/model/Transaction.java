package com.wipro.bank.model;

import java.time.LocalDateTime;

/**
 * Transaction Model Class
 */
public class Transaction {
    private int id;
    private long accountNo;
    private int customerId;
    private int amount;
    private String transactionType;
    private LocalDateTime updatedTimestamp;
    
    // Constructors
    public Transaction() {}
    
    public Transaction(int id, long accountNo, int customerId, int amount, 
                      String transactionType, LocalDateTime updatedTimestamp) {
        this.id = id;
        this.accountNo = accountNo;
        this.customerId = customerId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.updatedTimestamp = updatedTimestamp;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public long getAccountNo() {
        return accountNo;
    }
    
    public void setAccountNo(long accountNo) {
        this.accountNo = accountNo;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public int getAmount() {
        return amount;
    }
    
    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    public String getTransactionType() {
        return transactionType;
    }
    
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    
    public LocalDateTime getUpdatedTimestamp() {
        return updatedTimestamp;
    }
    
    public void setUpdatedTimestamp(LocalDateTime updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }
    
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", accountNo=" + accountNo +
                ", customerId=" + customerId +
                ", amount=" + amount +
                ", transactionType='" + transactionType + '\'' +
                ", updatedTimestamp=" + updatedTimestamp +
                '}';
    }
}
