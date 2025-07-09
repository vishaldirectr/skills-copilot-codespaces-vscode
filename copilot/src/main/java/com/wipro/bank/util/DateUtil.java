package com.wipro.bank.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Date Utility Class for Date Validations
 */
public class DateUtil {
    
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    /**
     * Validate and parse date in DD-MM-YYYY format
     * @param dateStr Date string to validate
     * @return LocalDate if valid, null if invalid
     */
    public static LocalDate parseDate(String dateStr) {
        if (StringUtil.isNullOrEmpty(dateStr)) {
            return null;
        }
        
        try {
            return LocalDate.parse(dateStr, DATE_FORMAT);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    
    /**
     * Check if date is in valid format DD-MM-YYYY
     * @param dateStr Date string to validate
     * @return true if valid format, false otherwise
     */
    public static boolean isValidDateFormat(String dateStr) {
        return parseDate(dateStr) != null;
    }
    
    /**
     * Check if person is at least 18 years old
     * @param birthDate Birth date
     * @return true if person is 18 or older, false otherwise
     */
    public static boolean isAdult(LocalDate birthDate) {
        if (birthDate == null) {
            return false;
        }
        return LocalDate.now().minusYears(18).isAfter(birthDate) || 
               LocalDate.now().minusYears(18).isEqual(birthDate);
    }
    
    /**
     * Format LocalDate to DD-MM-YYYY string
     * @param date LocalDate to format
     * @return Formatted date string
     */
    public static String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMAT) : null;
    }
}
