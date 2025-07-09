package com.wipro.bank.dao;

import com.wipro.bank.model.Customer;
import com.wipro.bank.util.H2DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Customer Data Access Object for H2 Database
 */
public class CustomerDAOH2 {
    
    /**
     * Get next customer ID (starts at 1001, increments by 1001)
     */
    public int getNextCustomerId() throws SQLException {
        String query = "SELECT COALESCE(MAX(ID), 0) + 1001 as NEXT_ID FROM CUSTOMER";
        
        try (Connection conn = H2DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("NEXT_ID");
            }
            return 1001; // First customer ID
        }
    }
    
    /**
     * Create a new customer
     */
    public boolean createCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO CUSTOMER (ID, NAME, DOB, EMAIL, PHONE) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = H2DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, customer.getId());
            stmt.setString(2, customer.getName());
            stmt.setDate(3, Date.valueOf(customer.getDateOfBirth()));
            stmt.setString(4, customer.getEmail());
            stmt.setString(5, customer.getPhone());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Get customer by ID
     */
    public Customer getCustomerById(int customerId) throws SQLException {
        String query = "SELECT * FROM CUSTOMER WHERE ID = ?";
        
        try (Connection conn = H2DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, customerId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Customer customer = new Customer();
                    customer.setId(rs.getInt("ID"));
                    customer.setName(rs.getString("NAME"));
                    customer.setDateOfBirth(rs.getDate("DOB").toLocalDate());
                    customer.setEmail(rs.getString("EMAIL"));
                    customer.setPhone(rs.getString("PHONE"));
                    return customer;
                }
            }
        }
        return null;
    }
    
    /**
     * Check if email exists
     */
    public boolean emailExists(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM CUSTOMER WHERE EMAIL = ?";
        
        try (Connection conn = H2DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    /**
     * Check if phone exists
     */
    public boolean phoneExists(String phone) throws SQLException {
        String query = "SELECT COUNT(*) FROM CUSTOMER WHERE PHONE = ?";
        
        try (Connection conn = H2DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, phone);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    /**
     * Get all customers
     */
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM CUSTOMER ORDER BY ID";
        
        try (Connection conn = H2DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("ID"));
                customer.setName(rs.getString("NAME"));
                customer.setDateOfBirth(rs.getDate("DOB").toLocalDate());
                customer.setEmail(rs.getString("EMAIL"));
                customer.setPhone(rs.getString("PHONE"));
                customers.add(customer);
            }
        }
        return customers;
    }
    
    /**
     * Update customer
     */
    public boolean updateCustomer(Customer customer) throws SQLException {
        String query = "UPDATE CUSTOMER SET NAME = ?, DOB = ?, EMAIL = ?, PHONE = ? WHERE ID = ?";
        
        try (Connection conn = H2DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, customer.getName());
            stmt.setDate(2, Date.valueOf(customer.getDateOfBirth()));
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPhone());
            stmt.setInt(5, customer.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Delete customer
     */
    public boolean deleteCustomer(int customerId) throws SQLException {
        String query = "DELETE FROM CUSTOMER WHERE ID = ?";
        
        try (Connection conn = H2DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, customerId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
