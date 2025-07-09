package com.wipro.bank.model;

import java.time.LocalDateTime;

/**
 * Account Model Class
 */
public class Account {
    private long accountNo;
    private String accountType;
    private int customerId;
    private String accountStatus;
    private LocalDateTime updatedTimestamp;
    private int balance;
    
    // Constructors
    public Account() {}
    
    public Account(long accountNo, String accountType, int customerId, 
                  String accountStatus, LocalDateTime updatedTimestamp, int balance) {
        this.accountNo = accountNo;
        this.accountType = accountType;
        this.customerId = customerId;
        this.accountStatus = accountStatus;
        this.updatedTimestamp = updatedTimestamp;
        this.balance = balance;
    }
    
    // Getters and Setters
    public long getAccountNo() {
        return accountNo;
    }
    
    public void setAccountNo(long accountNo) {
        this.accountNo = accountNo;
    }
    
    public String getAccountType() {
        return accountType;
    }
    
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public String getAccountStatus() {
        return accountStatus;
    }
    
    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
    
    public LocalDateTime getUpdatedTimestamp() {
        return updatedTimestamp;
    }
    
    public void setUpdatedTimestamp(LocalDateTime updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }
    
    public int getBalance() {
        return balance;
    }
    
    public void setBalance(int balance) {
        this.balance = balance;
    }
    
    @Override
    public String toString() {
        return "Account{" +
                "accountNo=" + accountNo +
                ", accountType='" + accountType + '\'' +
                ", customerId=" + customerId +
                ", accountStatus='" + accountStatus + '\'' +
                ", updatedTimestamp=" + updatedTimestamp +
                ", balance=" + balance +
                '}';
    }
}
