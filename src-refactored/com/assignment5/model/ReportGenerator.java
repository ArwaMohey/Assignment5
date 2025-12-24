package com.assignment5.model;

import com.assignment5.formatter.ReportFormatter;
import com.assignment5.formatter.PDFReportFormatter;
import com.assignment5.formatter.CSVReportFormatter;
import com.assignment5.formatter.TextReportFormatter;
import java.util.List;
import java.util.ArrayList;

/**
 * Refactored ReportGenerator
 * 
 * Refactorings Applied:
 * 1. Extract Method - Broke down generateReport() into smaller methods
 * 2. Encapsulate Field - Made reportType private with getter/setter
 * 3. Self-Encapsulate Field - Added getters/setters for rawData
 * 4. Remove Dead Code - Removed unused printStatus() method
 * 5. Replace Magic Strings - Using ReportType enum instead of strings
 * 6. Replace Conditional with Polymorphism - Using ReportFormatter strategy
 * 
 * Benefits:
 * - Single Responsibility: Only manages data and delegates formatting
 * - Open/Closed: Can add new report types without modifying this class
 * - Better encapsulation and testability
 */
public class ReportGenerator {
    private ReportType reportType;  // Refactoring: Encapsulate Field
    private List<String> rawData;   // Refactoring: Self-Encapsulate Field
    private ReportFormatter formatter;
    
    public ReportGenerator(ReportType reportType, List<String> rawData) {
        this.reportType = reportType;
        this.rawData = rawData;
        this.formatter = createFormatter(reportType);
    }
    
    /**
     * Refactoring: Extract Method
     * Factory method to create appropriate formatter based on report type
     * Replaced conditional logic with polymorphism
     */
    private ReportFormatter createFormatter(ReportType type) {
        switch (type) {
            case PDF:
                return new PDFReportFormatter();
            case CSV:
                return new CSVReportFormatter();
            case TEXT:
            default:
                return new TextReportFormatter();
        }
    }
    
    /**
     * Refactoring: Extract Method
     * Main report generation method - now much simpler and clearer
     * Delegates formatting to the appropriate formatter
     */
    public String generateReport() {
        List<String> processedData = processData();
        return formatReport(processedData);
    }
    
    /**
     * Refactoring: Extract Method
     * Separated data processing logic into its own method
     */
    private List<String> processData() {
        List<String> processed = new ArrayList<>();
        for (String data : getRawData()) {
            processed.add(data.trim().toUpperCase());
        }
        return processed;
    }
    
    /**
     * Refactoring: Extract Method
     * Separated formatting logic and delegated to formatter
     */
    private String formatReport(List<String> processedData) {
        return formatter.format(processedData);
    }
    
    // Refactoring: Encapsulate Field - Getter for reportType
    public ReportType getReportType() {
        return reportType;
    }
    
    // Refactoring: Encapsulate Field - Setter for reportType
    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
        this.formatter = createFormatter(reportType);
    }
    
    // Refactoring: Self-Encapsulate Field - Getter for rawData
    public List<String> getRawData() {
        return rawData;
    }
    
    // Refactoring: Self-Encapsulate Field - Setter for rawData
    public void setRawData(List<String> rawData) {
        this.rawData = rawData;
    }
    
    // Note: printStatus() method removed - Refactoring: Remove Dead Code
}
