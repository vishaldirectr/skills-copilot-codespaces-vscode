package com.wipro.bank.util;

import java.util.regex.Pattern;

/**
 * String Utility Class for String Validations
 */
public class StringUtil {
    
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^\\d{10,15}$");
    
    private static final Pattern ACCOUNT_NUMBER_PATTERN = 
        Pattern.compile("^\\d{17}$");
    
    /**
     * Check if string is null or empty
     * @param str String to check
     * @return true if null or empty, false otherwise
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * Check if string length is within limit
     * @param str String to check
     * @param maxLength Maximum allowed length
     * @return true if within limit, false otherwise
     */
    public static boolean isWithinLength(String str, int maxLength) {
        return str != null && str.length() <= maxLength;
    }
    
    /**
     * Validate email format
     * @param email Email to validate
     * @return true if valid email format, false otherwise
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * Validate phone number format (10-15 digits)
     * @param phone Phone number to validate
     * @return true if valid phone format, false otherwise
     */
    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }
    
    /**
     * Validate account number format (17 digits)
     * @param accountNo Account number to validate
     * @return true if valid account number format, false otherwise
     */
    public static boolean isValidAccountNumber(String accountNo) {
        return accountNo != null && ACCOUNT_NUMBER_PATTERN.matcher(accountNo).matches();
    }
    
    /**
     * Check if string contains only alphabets and spaces
     * @param str String to check
     * @return true if contains only alphabets and spaces, false otherwise
     */
    public static boolean isAlphabetic(String str) {
        return str != null && str.matches("^[a-zA-Z\\s]+$");
    }
    
    /**
     * Check if string is numeric
     * @param str String to check
     * @return true if numeric, false otherwise
     */
    public static boolean isNumeric(String str) {
        if (isNullOrEmpty(str)) {
            return false;
        }
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Clean and trim string
     * @param str String to clean
     * @return Cleaned string or null if input is null
     */
    public static String clean(String str) {
        return str != null ? str.trim() : null;
    }
}
