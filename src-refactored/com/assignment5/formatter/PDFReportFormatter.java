package com.assignment5.formatter;

import java.util.List;

/**
 * Refactoring: Replace Conditional with Polymorphism
 * Extract Class - Moved PDF formatting logic to dedicated class
 * Benefits:
 * - Single Responsibility: Only handles PDF formatting
 * - Open/Closed: Can add new formatters without modifying this class
 * - Eliminates conditional logic from ReportGenerator
 */
public class PDFReportFormatter implements ReportFormatter {
    
    @Override
    public String format(List<String> processedData) {
        StringBuilder report = new StringBuilder();
        
        for (String data : processedData) {
            report.append("PDF-FORMAT: ");
            report.append(data);
            report.append("\n");
            report.append("---PDF-PAGE-BREAK---\n");
        }
        
        report.append("===PDF-END===");
        
        return report.toString();
    }
}
