-- Database Setup Script for Digital Bank Application

-- Create database
CREATE DATABASE IF NOT EXISTS bank_db;
USE bank_db;

-- Create CUSTOMER table
CREATE TABLE IF NOT EXISTS CUSTOMER (
    ID INT PRIMARY KEY,
    NAME VARCHAR(100) NOT NULL,
    DOB DATE NOT NULL,
    EMAIL VARCHAR(100) NOT NULL UNIQUE,
    PHONE VARCHAR(15) NOT NULL UNIQUE,
    CREATED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create ACCOUNT table
CREATE TABLE IF NOT EXISTS ACCOUNT (
    ACCOUNT_NO BIGINT PRIMARY KEY,
    ACCOUNT_TYPE VARCHAR(10) NOT NULL CHECK (ACCOUNT_TYPE IN ('Current', 'Savings', 'DMAT')),
    CUSTOMER_ID INT NOT NULL,
    ACCOUNT_STATUS VARCHAR(10) NOT NULL CHECK (ACCOUNT_STATUS IN ('Active', 'Inactive', 'Suspended')),
    UPDATED_TIMESTAMP DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    BALANCE INT NOT NULL DEFAULT 0 CHECK (BALANCE >= 0),
    FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMER(ID) ON DELETE CASCADE
);

-- Create TRANSACTION_DETAILS table
CREATE TABLE IF NOT EXISTS TRANSACTION_DETAILS (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    ACCOUNT_NO BIGINT NOT NULL,
    CUSTOMER_ID INT NOT NULL,
    AMOUNT INT NOT NULL CHECK (AMOUNT > 0),
    TRANSACTION_TYPE CHAR(10) NOT NULL CHECK (TRANSACTION_TYPE IN ('CREDIT', 'DEBIT')),
    UPDATED_TIMESTAMP DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ACCOUNT_NO) REFERENCES ACCOUNT(ACCOUNT_NO) ON DELETE CASCADE,
    FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMER(ID) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX idx_customer_email ON CUSTOMER(EMAIL);
CREATE INDEX idx_customer_phone ON CUSTOMER(PHONE);
CREATE INDEX idx_account_customer ON ACCOUNT(CUSTOMER_ID);
CREATE INDEX idx_transaction_account ON TRANSACTION_DETAILS(ACCOUNT_NO);
CREATE INDEX idx_transaction_customer ON TRANSACTION_DETAILS(CUSTOMER_ID);
CREATE INDEX idx_transaction_timestamp ON TRANSACTION_DETAILS(UPDATED_TIMESTAMP);

-- Insert sample data (optional)
INSERT INTO CUSTOMER (ID, NAME, DOB, EMAIL, PHONE) VALUES
(1001, 'John Doe', '1990-01-15', 'john.doe@email.com', '9876543210'),
(2002, 'Jane Smith', '1985-03-22', 'jane.smith@email.com', '9876543211'),
(3003, 'Bob Johnson', '1992-07-08', 'bob.johnson@email.com', '9876543212');

INSERT INTO ACCOUNT (ACCOUNT_NO, ACCOUNT_TYPE, CUSTOMER_ID, ACCOUNT_STATUS, BALANCE) VALUES
(12345678901234567, 'Savings', 1001, 'Active', 50000),
(23456789012345678, 'Current', 2002, 'Active', 75000),
(34567890123456789, 'DMAT', 3003, 'Active', 100000);

INSERT INTO TRANSACTION_DETAILS (ACCOUNT_NO, CUSTOMER_ID, AMOUNT, TRANSACTION_TYPE) VALUES
(12345678901234567, 1001, 10000, 'CREDIT'),
(23456789012345678, 2002, 5000, 'DEBIT'),
(34567890123456789, 3003, 15000, 'CREDIT');

-- Display table information
DESCRIBE CUSTOMER;
DESCRIBE ACCOUNT;
DESCRIBE TRANSACTION_DETAILS;

-- Show sample data
SELECT 'CUSTOMER TABLE' as 'TABLE_NAME';
SELECT * FROM CUSTOMER;

SELECT 'ACCOUNT TABLE' as 'TABLE_NAME';
SELECT * FROM ACCOUNT;

SELECT 'TRANSACTION_DETAILS TABLE' as 'TABLE_NAME';
SELECT * FROM TRANSACTION_DETAILS;
