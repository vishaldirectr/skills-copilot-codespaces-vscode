// Digital Bank Web Application JavaScript
const API_BASE_URL = 'http://localhost:8080/api';

// Demo mode flag
const DEMO_MODE = true;

// Shared data management functions
function loadSharedData() {
    const savedCustomers = localStorage.getItem('digitalBankCustomers');
    const savedTransactions = localStorage.getItem('digitalBankTransactions');
    
    if (savedCustomers) {
        return {
            customers: JSON.parse(savedCustomers),
            transactions: JSON.parse(savedTransactions) || []
        };
    }
    
    // Default data if nothing saved
    return {
        customers: [
            { id: 1, name: 'John Doe', email: 'john@example.com', phone: '123-456-7890', accountNumber: 'ACC001', balance: 5000 },
            { id: 2, name: 'Jane Smith', email: 'jane@example.com', phone: '987-654-3210', accountNumber: 'ACC002', balance: 7500 },
            { id: 3, name: 'Mike Johnson', email: 'mike@example.com', phone: '555-123-4567', accountNumber: 'ACC003', balance: 3200 }
        ],
        transactions: [
            { id: 1, fromAccount: 'ACC001', toAccount: 'ACC002', amount: 500, description: 'Transfer to Jane', date: '2025-01-08' },
            { id: 2, fromAccount: 'ACC002', toAccount: 'ACC003', amount: 300, description: 'Payment to Mike', date: '2025-01-07' }
        ]
    };
}

function saveSharedData(customers, transactions) {
    localStorage.setItem('digitalBankCustomers', JSON.stringify(customers));
    localStorage.setItem('digitalBankTransactions', JSON.stringify(transactions));
}

// Load shared data
const sharedData = loadSharedData();
const mockCustomers = sharedData.customers;
const mockTransactions = sharedData.transactions;

// Session validation function
function validateSession() {
    const userSession = localStorage.getItem('bankUserSession');
    if (!userSession) {
        window.location.href = 'login.html';
        return null;
    }
    
    const session = JSON.parse(userSession);
    if (!session.isAuthenticated) {
        window.location.href = 'login.html';
        return null;
    }
    
    return session;
}

// Initialize the application
document.addEventListener('DOMContentLoaded', function() {
    console.log('Digital Bank Web App Initialized');
    
    // Validate session first (only if not on login page)
    if (!window.location.href.includes('login.html')) {
        const session = validateSession();
        if (!session) return;
    }
    
    // Setup form event listeners
    setupEventListeners();
    
    // Check API connectivity (non-blocking)
    checkApiHealth(); // Don't await this
});

// Setup event listeners for forms
function setupEventListeners() {
    // Customer form
    const customerForm = document.getElementById('customerForm');
    if (customerForm) {
        customerForm.addEventListener('submit', handleCustomerSubmit);
    }
    
    // Transfer form
    const transferForm = document.getElementById('transferForm');
    if (transferForm) {
        transferForm.addEventListener('submit', handleTransferSubmit);
    }
    
    // Smooth scrolling for navigation links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
            }
        });
    });
}

// Check API health
async function checkApiHealth() {
    // Skip API health check for demo mode
    console.log('Running in demo mode - using local data only');
    console.log('Backend API connection skipped for standalone demo');
    return Promise.resolve();
}

// Handle customer form submission
async function handleCustomerSubmit(event) {
    event.preventDefault();
    
    const formData = {
        name: document.getElementById('customerName').value.trim(),
        dateOfBirth: document.getElementById('dateOfBirth').value.trim(),
        email: document.getElementById('email').value.trim(),
        phone: document.getElementById('phone').value.trim(),
        accountType: document.getElementById('accountType').value,
        accountStatus: document.getElementById('accountStatus').value,
        balance: parseInt(document.getElementById('balance').value)
    };
    
    // Client-side validation
    if (!validateCustomerForm(formData)) {
        return;
    }
    
    try {
        showLoading('customerResult', 'Creating customer...');
        
        if (DEMO_MODE) {
            // Demo mode - simulate customer creation
            const newCustomer = {
                id: mockCustomers.length + 1,
                name: formData.name,
                email: formData.email,
                phone: formData.phone,
                accountNumber: `ACC${String(mockCustomers.length + 1).padStart(3, '0')}`,
                balance: formData.balance // Use the entered balance
            };
            
            mockCustomers.push(newCustomer);
            
            // Save to localStorage
            saveSharedData(mockCustomers, mockTransactions);
            
            // Instant response - no delay
            showResult('customerResult', `Customer created successfully! Account Number: ${newCustomer.accountNumber}`, 'success');
            document.getElementById('customerForm').reset();
            showNotification('Customer created successfully!', 'success');
            
            // Auto-refresh reports if they're currently displayed
            const reportResult = document.getElementById('reportResult');
            if (reportResult && reportResult.innerHTML.includes('Customer Account Reports')) {
                displayReports(mockCustomers);
            }
            
        } else {
            // Real API mode
            const response = await fetch(`${API_BASE_URL}/customers`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData)
            });
            
            const result = await response.json();
            
            if (result.success) {
                showResult('customerResult', result.message, 'success');
                document.getElementById('customerForm').reset();
                showNotification('Customer created successfully!', 'success');
            } else {
                showResult('customerResult', result.message, 'error');
            }
        }
        
    } catch (error) {
        console.error('Error creating customer:', error);
        showResult('customerResult', 'Demo mode: Customer creation simulated locally. In production, this would connect to the backend API.', 'info');
    }
}

// Handle transfer form submission
async function handleTransferSubmit(event) {
    event.preventDefault();
    
    const formData = {
        fromAccountNo: document.getElementById('fromAccount').value.trim(),
        toAccountNo: document.getElementById('toAccount').value.trim(),
        amount: parseInt(document.getElementById('transferAmount').value)
    };
    
    // Client-side validation
    if (!validateTransferForm(formData)) {
        return;
    }
    
    try {
        showLoading('transferResult', 'Processing transfer...');
        
        if (DEMO_MODE) {
            // Demo mode - simulate transfer
            const fromCustomer = mockCustomers.find(c => c.accountNumber === formData.fromAccountNo);
            const toCustomer = mockCustomers.find(c => c.accountNumber === formData.toAccountNo);
            
            if (!fromCustomer || !toCustomer) {
                showResult('transferResult', 'Account not found. Please check account numbers.', 'error');
                return;
            }
            
            if (fromCustomer.balance < formData.amount) {
                showResult('transferResult', 'Insufficient balance for transfer.', 'error');
                return;
            }
            
            // Instant transfer processing - no delay
            fromCustomer.balance -= formData.amount;
            toCustomer.balance += formData.amount;
            
            const newTransaction = {
                id: mockTransactions.length + 1,
                fromAccount: formData.fromAccountNo,
                toAccount: formData.toAccountNo,
                amount: formData.amount,
                description: 'Demo transfer',
                date: new Date().toISOString().split('T')[0]
            };
            
            mockTransactions.push(newTransaction);
            
            // Save to localStorage
            saveSharedData(mockCustomers, mockTransactions);
            
            showResult('transferResult', `Transfer successful! $${formData.amount} transferred from ${formData.fromAccountNo} to ${formData.toAccountNo}`, 'success');
            document.getElementById('transferForm').reset();
            showNotification('Transfer completed successfully!', 'success');
            
            // Auto-refresh reports if they're currently displayed
            const reportResult = document.getElementById('reportResult');
            if (reportResult && reportResult.innerHTML.includes('Customer Account Reports')) {
                displayReports(mockCustomers);
            }
            
        } else {
            // Real API mode
            const response = await fetch(`${API_BASE_URL}/transfers`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData)
            });
            
            const result = await response.json();
            
            if (result.success) {
                showResult('transferResult', result.message, 'success');
                document.getElementById('transferForm').reset();
                showNotification('Transfer completed successfully!', 'success');
            } else {
                showResult('transferResult', result.message, 'error');
            }
        }
        
    } catch (error) {
        console.error('Error processing transfer:', error);
        showResult('transferResult', 'Demo mode: Transfer simulated locally. Use account numbers ACC001, ACC002, ACC003 for testing.', 'info');
    }
}

// Load reports
async function loadReports() {
    try {
        showLoading('reportResult', 'Loading reports...');
        
        if (DEMO_MODE) {
            // Demo mode - instant data loading
            displayReports(mockCustomers);
            showNotification('Reports loaded successfully!', 'success');
        } else {
            // Real API mode
            const response = await fetch(`${API_BASE_URL}/reports/accounts?page=0&size=5`);
            const result = await response.json();
            
            if (result.success) {
                displayReports(result.data);
                showNotification('Reports loaded successfully!', 'success');
            } else {
                showResult('reportResult', result.message, 'error');
            }
        }
        
    } catch (error) {
        console.error('Error loading reports:', error);
        showResult('reportResult', 'Demo mode: Displaying sample account data.', 'info');
        displayReports(mockCustomers);
    }
}

// Load summary report
async function loadSummary() {
    try {
        showLoading('reportResult', 'Loading summary...');
        
        if (DEMO_MODE) {
            // Demo mode - instant summary calculation
            const totalCustomers = mockCustomers.length;
            const totalBalance = mockCustomers.reduce((sum, customer) => sum + customer.balance, 0);
            const totalTransactions = mockTransactions.length;
            
            displaySummary({
                totalCustomers,
                totalBalance,
                totalTransactions,
                averageBalance: Math.round(totalBalance / totalCustomers)
            });
            showNotification('Summary loaded successfully!', 'success');
            // Real API mode
            const response = await fetch(`${API_BASE_URL}/reports/summary`);
            const result = await response.json();
            
            if (result.success) {
                displaySummary(result.data);
                showNotification('Summary loaded successfully!', 'success');
            } else {
                showResult('reportResult', result.message, 'error');
            }
        }
        
    } catch (error) {
        console.error('Error loading summary:', error);
        showResult('reportResult', 'Demo mode: Displaying summary from sample data.', 'info');
        
        // Fallback to demo summary
        const totalCustomers = mockCustomers.length;
        const totalBalance = mockCustomers.reduce((sum, customer) => sum + customer.balance, 0);
        const totalTransactions = mockTransactions.length;
        
        displaySummary({
            totalCustomers,
            totalBalance,
            totalTransactions,
            averageBalance: Math.round(totalBalance / totalCustomers)
        });
    }
}

// Validation functions
function validateCustomerForm(data) {
    if (!data.name || data.name.length > 100) {
        showResult('customerResult', 'Error: Customer name is required and must be within 100 characters.', 'error');
        return false;
    }
    
    if (!data.dateOfBirth || !isValidDateFormat(data.dateOfBirth)) {
        showResult('customerResult', 'Error: Please enter date of birth in DD-MM-YYYY format.', 'error');
        return false;
    }
    
    if (!data.email || !isValidEmail(data.email)) {
        showResult('customerResult', 'Error: Please enter a valid email address.', 'error');
        return false;
    }
    
    if (!data.phone || !isValidPhone(data.phone)) {
        showResult('customerResult', 'Error: Phone number must be 10-15 digits.', 'error');
        return false;
    }
    
    if (!data.accountType) {
        showResult('customerResult', 'Error: Please select an account type.', 'error');
        return false;
    }
    
    if (!data.accountStatus) {
        showResult('customerResult', 'Error: Please select an account status.', 'error');
        return false;
    }
    
    if (!data.balance || data.balance < 0) {
        showResult('customerResult', 'Error: Please enter a valid initial balance.', 'error');
        return false;
    }
    
    return true;
}

function validateTransferForm(data) {
    if (!data.fromAccountNo || !isValidAccountNumber(data.fromAccountNo)) {
        const errorMsg = DEMO_MODE ? 
            'Error: From account number must be in format ACC001, ACC002, etc. or 17 digits.' :
            'Error: From account number must be exactly 17 digits.';
        showResult('transferResult', errorMsg, 'error');
        return false;
    }
    
    if (!data.toAccountNo || !isValidAccountNumber(data.toAccountNo)) {
        const errorMsg = DEMO_MODE ?
            'Error: To account number must be in format ACC001, ACC002, etc. or 17 digits.' :
            'Error: To account number must be exactly 17 digits.';
        showResult('transferResult', errorMsg, 'error');
        return false;
    }
    
    if (data.fromAccountNo === data.toAccountNo) {
        showResult('transferResult', 'Error: From and To account numbers cannot be the same.', 'error');
        return false;
    }
    
    if (!data.amount || data.amount <= 0) {
        showResult('transferResult', 'Error: Transfer amount must be greater than 0.', 'error');
        return false;
    }
    
    return true;
}

// Utility validation functions
function isValidDateFormat(dateStr) {
    const regex = /^(\d{2})-(\d{2})-(\d{4})$/;
    return regex.test(dateStr);
}

function isValidEmail(email) {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
}

function isValidPhone(phone) {
    const regex = /^\d{10,15}$/;
    return regex.test(phone);
}

function isValidAccountNumber(accountNo) {
    if (DEMO_MODE) {
        // In demo mode, accept both ACC### format and 17-digit format
        const demoFormatRegex = /^ACC\d{3}$/;
        const standardFormatRegex = /^\d{17}$/;
        return demoFormatRegex.test(accountNo) || standardFormatRegex.test(accountNo);
    } else {
        // In production mode, only accept 17-digit format
        const regex = /^\d{17}$/;
        return regex.test(accountNo);
    }
}

// UI helper functions
function showLoading(elementId, message) {
    const element = document.getElementById(elementId);
    element.innerHTML = `
        <div class="alert alert-info d-flex align-items-center">
            <div class="spinner-border spinner-border-sm me-2" role="status">
                <span class="visually-hidden">Loading...</span>
            </div>
            ${message}
        </div>
    `;
}

function showResult(elementId, message, type) {
    const element = document.getElementById(elementId);
    const alertClass = type === 'success' ? 'alert-success' : 'alert-danger';
    const icon = type === 'success' ? 'fas fa-check-circle' : 'fas fa-exclamation-circle';
    
    element.innerHTML = `
        <div class="alert ${alertClass} d-flex align-items-center">
            <i class="${icon} me-2"></i>
            <div>${message}</div>
        </div>
    `;
    
    // Auto-hide success messages after 5 seconds
    if (type === 'success') {
        setTimeout(() => {
            element.innerHTML = '';
        }, 5000);
    }
}

function showNotification(message, type) {
    // Create toast notification
    const toastContainer = document.getElementById('toastContainer') || createToastContainer();
    
    const toast = document.createElement('div');
    toast.className = `toast align-items-center text-white bg-${type === 'success' ? 'success' : type === 'warning' ? 'warning' : 'danger'} border-0`;
    toast.setAttribute('role', 'alert');
    toast.innerHTML = `
        <div class="d-flex">
            <div class="toast-body">
                <i class="fas ${type === 'success' ? 'fa-check-circle' : type === 'warning' ? 'fa-exclamation-triangle' : 'fa-exclamation-circle'} me-2"></i>
                ${message}
            </div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
        </div>
    `;
    
    toastContainer.appendChild(toast);
    
    const bsToast = new bootstrap.Toast(toast);
    bsToast.show();
    
    // Remove toast after it's hidden
    toast.addEventListener('hidden.bs.toast', () => {
        toast.remove();
    });
}

function createToastContainer() {
    const container = document.createElement('div');
    container.id = 'toastContainer';
    container.className = 'toast-container position-fixed bottom-0 end-0 p-3';
    document.body.appendChild(container);
    return container;
}

function displayReports(data) {
    // Display actual customer data
    let tableRows = '';
    
    if (data && data.length > 0) {
        tableRows = data.map(customer => {
            const lastTransaction = mockTransactions
                .filter(t => t.fromAccount === customer.accountNumber || t.toAccount === customer.accountNumber)
                .sort((a, b) => new Date(b.date) - new Date(a.date))[0];
            
            const lastTransactionType = lastTransaction 
                ? (lastTransaction.toAccount === customer.accountNumber ? 'CREDIT' : 'DEBIT')
                : 'N/A';
            
            const lastUpdated = new Date().toLocaleDateString('en-IN', {
                day: '2-digit',
                month: 'short', 
                year: 'numeric',
                hour: '2-digit',
                minute: '2-digit',
                hour12: true
            });
            
            return `
                <tr>
                    <td><strong>${customer.name}</strong></td>
                    <td><code>${customer.accountNumber}</code></td>
                    <td><span class="text-success fw-bold">$${customer.balance.toLocaleString()}</span></td>
                    <td><small class="text-muted">${lastUpdated}</small></td>
                    <td>
                        <span class="badge ${lastTransactionType === 'CREDIT' ? 'bg-success' : lastTransactionType === 'DEBIT' ? 'bg-danger' : 'bg-secondary'}">
                            ${lastTransactionType}
                        </span>
                    </td>
                </tr>
            `;
        }).join('');
    } else {
        tableRows = `
            <tr>
                <td colspan="5" class="text-center text-muted">
                    <i class="fas fa-info-circle me-2"></i>
                    No customer data available. Create a customer first to see reports.
                </td>
            </tr>
        `;
    }
    
    const html = `
        <div class="card">
            <div class="card-header bg-primary text-white">
                <h5 class="mb-0"><i class="fas fa-table me-2"></i>Customer Account Reports</h5>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-striped table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th><i class="fas fa-user me-2"></i>Customer Name</th>
                                <th><i class="fas fa-credit-card me-2"></i>Account Number</th>
                                <th><i class="fas fa-dollar-sign me-2"></i>Balance</th>
                                <th><i class="fas fa-clock me-2"></i>Last Updated</th>
                                <th><i class="fas fa-exchange-alt me-2"></i>Last Transaction</th>
                            </tr>
                        </thead>
                        <tbody>
                            ${tableRows}
                        </tbody>
                    </table>
                </div>
                <div class="mt-3">
                    <small class="text-muted">
                        <i class="fas fa-info-circle me-1"></i>
                        Showing ${data ? data.length : 0} customer account(s) | Updated in real-time
                    </small>
                </div>
            </div>
        </div>
    `;
    document.getElementById('reportResult').innerHTML = html;
}

function displaySummary(data) {
    // Display real summary data
    const html = `
        <div class="card">
            <div class="card-header bg-info text-white">
                <h5 class="mb-0"><i class="fas fa-chart-pie me-2"></i>Banking Summary Report</h5>
            </div>
            <div class="card-body">
                <div class="row text-center g-3">
                    <div class="col-md-3">
                        <div class="card bg-primary text-white h-100">
                            <div class="card-body">
                                <div class="display-6 fw-bold">${data.totalCustomers}</div>
                                <p class="mb-0"><i class="fas fa-users me-2"></i>Total Customers</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card bg-success text-white h-100">
                            <div class="card-body">
                                <div class="display-6 fw-bold">${data.totalCustomers}</div>
                                <p class="mb-0"><i class="fas fa-credit-card me-2"></i>Active Accounts</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card bg-info text-white h-100">
                            <div class="card-body">
                                <div class="display-6 fw-bold">$${data.totalBalance.toLocaleString()}</div>
                                <p class="mb-0"><i class="fas fa-dollar-sign me-2"></i>Total Balance</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card bg-warning text-white h-100">
                            <div class="card-body">
                                <div class="display-6 fw-bold">${data.totalTransactions}</div>
                                <p class="mb-0"><i class="fas fa-exchange-alt me-2"></i>Transactions</p>
                            </div>
                        </div>
                    </div>
                </div>
                <hr>
                <div class="row mt-4">
                    <div class="col-md-6">
                        <div class="text-center p-3 border rounded">
                            <h5 class="text-primary">Average Balance</h5>
                            <div class="display-6 text-success fw-bold">$${data.averageBalance.toLocaleString()}</div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="text-center p-3 border rounded">
                            <h5 class="text-primary">System Status</h5>
                            <span class="badge bg-success fs-6">
                                <i class="fas fa-check-circle me-2"></i>Online & Active
                            </span>
                        </div>
                    </div>
                </div>
                <div class="mt-3">
                    <small class="text-muted">
                        <i class="fas fa-clock me-1"></i>
                        Last updated: ${new Date().toLocaleString()} | Real-time data
                    </small>
                </div>
            </div>
        </div>
    `;
    document.getElementById('reportResult').innerHTML = html;
}
