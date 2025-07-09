# Digital Bank Application

A modern banking application with both console-based Java backend and web interface for comprehensive banking operations.

## 🚀 Quick Start - Web Application

### Demo Mode (No Setup Required)
The web application runs in demo mode with mock data - no database or backend setup needed!

1. **Run the Web App:**
   ```bash
   # Option 1: Double-click the batch file
   start-web-app.bat
   
   # Option 2: Manual start
   cd web-app
   python -m http.server 3000
   ```

2. **Access the Application:**
   - Open: http://localhost:3000
   - **Demo Credentials:**
     - User: `user@digitalbank.com` / `user123`
     - Admin: `admin@digitalbank.com` / `admin123`

3. **Demo Features:**
   - Instant login/logout (no delays)
   - Customer creation with auto-generated accounts (ACC001, ACC002, etc.)
   - Fund transfers between demo accounts
   - Real-time reports showing created customers and transactions
   - **Admin Dashboard** with full management capabilities
   - No backend API required - all data stored locally

## 👨‍💼 Admin Features

### Admin Dashboard Access
- **Admin Login:** `admin@digitalbank.com` / `admin123`
- **Full Management Interface:** Comprehensive admin panel with real-time data

### Admin Capabilities
- **Customer Management:**
  - View all customers in a table format
  - Edit customer balances
  - Delete customers
  - Real-time customer data updates

- **Account Management:**
  - View account statistics (total accounts, total balance, average balance)
  - Account details with status and history
  - View transaction history for any account

- **Transaction Monitoring:**
  - Real-time transaction feed
  - Transaction volume analytics
  - Success rate monitoring
  - Transaction summary reports

- **Reports & Analytics:**
  - Generate customer reports
  - Generate transaction reports  
  - Generate balance reports
  - Generate full system reports
  - Download reports (CSV simulation)

- **System Settings:**
  - Reset demo data to original state
  - Clear all data
  - View system information
  - Monitor system health

## 🌐 Multi-Platform Support

### Web Browser (All Platforms)
- Access via any modern web browser
- Responsive design for desktop, tablet, and mobile
- Works on Windows, macOS, Linux, iOS, Android

### iOS PWA (Progressive Web App)
- Add to Home Screen for app-like experience
- Offline capable
- See `iOS_Setup_Guide.md` for detailed instructions

## Features

- **Customer Management**: Create customer profiles with validation
- **Fund Transfers**: Transfer money between accounts with real-time balance updates
- **Report Generation**: Generate paginated reports with customer and account information
- **Data Validation**: Comprehensive input validation using custom utility classes
- **Web Interface**: Modern, responsive web UI with instant operations
- **Demo Mode**: Fully functional without backend setup

## Project Structure

```
src/main/java/com/wipro/bank/
├── BankApplication.java          # Main entry point
├── model/                        # Data models
│   ├── Customer.java
│   ├── Account.java
│   └── Transaction.java
├── dao/                          # Data Access Objects
│   ├── CustomerDAO.java
│   ├── AccountDAO.java
│   └── TransactionDAO.java
├── service/                      # Business logic
│   ├── BankMenuService.java
│   ├── CustomerService.java
│   ├── TransferService.java
│   └── ReportService.java
└── util/                         # Utility classes
    ├── DatabaseUtil.java
    ├── DateUtil.java
    └── StringUtil.java
```

## Database Schema

### CUSTOMER Table
- ID (INT, PK) - Auto-generated (starts at 1001, increments by 1001)
- NAME (VARCHAR(100)) - Customer name
- DOB (DATE) - Date of birth
- EMAIL (VARCHAR(100)) - Email address (unique)
- PHONE (VARCHAR(15)) - Phone number (unique)

### ACCOUNT Table
- ACCOUNT_NO (BIGINT, PK) - 17-digit unique account number
- ACCOUNT_TYPE (VARCHAR(10)) - Current/Savings/DMAT
- CUSTOMER_ID (INT, FK) - References CUSTOMER.ID
- ACCOUNT_STATUS (VARCHAR(10)) - Active/Inactive/Suspended
- UPDATED_TIMESTAMP (DATETIME) - Last update time
- BALANCE (INT) - Account balance

### TRANSACTION_DETAILS Table
- ID (INT, PK) - Auto-generated transaction ID
- ACCOUNT_NO (BIGINT, FK) - References ACCOUNT.ACCOUNT_NO
- CUSTOMER_ID (INT, FK) - References CUSTOMER.ID
- AMOUNT (INT) - Transaction amount
- TRANSACTION_TYPE (CHAR(10)) - CREDIT/DEBIT
- UPDATED_TIMESTAMP (DATETIME) - Transaction time

## Prerequisites

- Java 8 or higher
- MySQL 8.0 or higher
- MySQL Connector/J JDBC driver

## Database Setup

1. Install and start MySQL server
2. Create a database named `bank_db`
3. Update database credentials in `DatabaseUtil.java`:
   ```java
   private static final String DB_URL = "jdbc:mysql://localhost:3306/bank_db";
   private static final String DB_USER = "your_username";
   private static final String DB_PASSWORD = "your_password";
   ```

## Installation & Running

1. **Clone/Download** the project
2. **Add MySQL Connector** to your classpath
3. **Configure database** connection in DatabaseUtil.java
4. **Compile** the project:
   ```bash
   javac -cp "path/to/mysql-connector-j.jar" src/main/java/com/wipro/bank/*.java src/main/java/com/wipro/bank/*/*.java
   ```
5. **Run** the application:
   ```bash
   java -cp "path/to/mysql-connector-j.jar;src/main/java" com.wipro.bank.BankApplication
   ```

## Usage

### Main Menu Options
1. **Create Customer** - Add new customer with account
2. **Bank Transfer** - Transfer funds between accounts
3. **Report** - Generate account reports with pagination
4. **Exit** - Close the application

### Input Formats

#### Create Customer
Format: `name,dob,email,phone,accountType,accountStatus,balance`
Example: `John Doe,15-01-1990,john@email.com,9876543210,Savings,Active,5000`

#### Bank Transfer
Format: `fromAccountNo,toAccountNo,amount`
Example: `12345678901234567,98765432109876543,5000`

## Validation Rules

- **Customer Name**: Max 100 characters, alphabets and spaces only
- **Date of Birth**: DD-MM-YYYY format, must be 18+ years old
- **Email**: Max 100 characters, valid email format, unique
- **Phone**: 10-15 digits, numeric only, unique
- **Account Type**: Current, Savings, or DMAT only
- **Account Status**: Active, Inactive, or Suspended only
- **Balance**: Positive integer, less than 10 digits
- **Account Number**: Exactly 17 digits

## Features

### Error Handling
- Custom validation with user-friendly error messages
- Database connection error handling
- Transaction rollback on failure
- No Java stack traces exposed to users

### Report Generation
- Paginated display (5 rows per page)
- Real-time data from database
- Customer name, account number, balance, last updated, last transaction
- Interactive navigation (ENTER for next page, 'q' to quit)

### Security
- Prepared statements to prevent SQL injection
- Input validation and sanitization
- Database constraints (PK, FK, CHECK constraints)

## 🎯 Latest Updates (January 2025)

### ✅ Transfer Validation Fixed
- **Issue**: Transfer form required exactly 17-digit account numbers
- **Solution**: Updated validation to accept demo account format (ACC001, ACC002, etc.)
- **Result**: Transfers now work seamlessly with demo accounts

### ✅ Performance Optimized
- Removed all artificial delays from login/redirect/operations
- Instant UI updates and real-time data refresh
- Fast, responsive user experience

### ✅ Demo Mode Features
- Mock customer data (John Doe/ACC001, Jane Smith/ACC002, Mike Johnson/ACC003)
- Real-time reports showing created customers and transactions
- Auto-generating account numbers (ACC004, ACC005, etc.)
- No backend API required - fully client-side functional

## Building JAR

To create an executable JAR file:

1. Create a manifest file `MANIFEST.MF`:
   ```
   Main-Class: com.wipro.bank.BankApplication
   Class-Path: mysql-connector-j.jar
   ```

2. Compile and package:
   ```bash
   jar cfm BankApplication.jar MANIFEST.MF -C src/main/java .
   ```

3. Run the JAR:
   ```bash
   java -jar BankApplication.jar
   ```

## Author

Digital Bank Application - Employee ID: [Your Employee Number]

## License

This project is developed for Digital Bank internal use.
