package com.assignment5.formatter;

import java.util.List;

/**
 * Refactoring: Replace Conditional with Polymorphism
 * Extract Class - Moved CSV formatting logic to dedicated class
 * Benefits:
 * - Single Responsibility: Only handles CSV formatting
 * - Open/Closed: Can add new formatters without modifying this class
 * - Eliminates conditional logic from ReportGenerator
 */
public class CSVReportFormatter implements ReportFormatter {
    
    @Override
    public String format(List<String> processedData) {
        StringBuilder report = new StringBuilder();
        
        for (String data : processedData) {
            report.append(data);
            report.append(",");
        }
        
        // Remove trailing comma
        if (report.length() > 0 && report.charAt(report.length() - 1) == ',') {
            report.deleteCharAt(report.length() - 1);
        }
        
        return report.toString();
    }
}
