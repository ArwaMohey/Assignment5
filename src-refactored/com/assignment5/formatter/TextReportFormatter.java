package com.assignment5.formatter;

import java.util.List;

/**
 * Refactoring: Replace Conditional with Polymorphism
 * Extract Class - Moved TEXT formatting logic to dedicated class
 * Benefits:
 * - Single Responsibility: Only handles TEXT formatting
 * - Open/Closed: Can add new formatters without modifying this class
 * - Demonstrates extensibility
 */
public class TextReportFormatter implements ReportFormatter {
    
    @Override
    public String format(List<String> processedData) {
        StringBuilder report = new StringBuilder();
        
        for (String data : processedData) {
            report.append(data);
            report.append("\n");
        }
        
        return report.toString();
    }
}
