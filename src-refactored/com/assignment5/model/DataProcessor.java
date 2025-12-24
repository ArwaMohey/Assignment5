package com.assignment5.model;

import java.util.ArrayList;
import java.util.List;

/**
 * DataProcessor - Refactored version
 * Maintains the same external behavior but works with refactored ReportGenerator
 */
public class DataProcessor {
    private List<String> data;
    
    public DataProcessor() {
        this.data = new ArrayList<>();
    }
    
    public void addData(String item) {
        data.add(item);
    }
    
    public List<String> getData() {
        return data;
    }
    
    public void clearData() {
        data.clear();
    }
    
    /**
     * Demonstrates usage of refactored ReportGenerator
     * Shows that behavior is preserved while code quality improved
     */
    public static void main(String[] args) {
        DataProcessor processor = new DataProcessor();
        processor.addData("Sales Data: Q1");
        processor.addData("Revenue: $50000");
        processor.addData("Expenses: $30000");
        
        // Generate PDF report using enum instead of string
        ReportGenerator pdfGenerator = new ReportGenerator(ReportType.PDF, processor.getData());
        System.out.println("PDF Report:\n" + pdfGenerator.generateReport());
        System.out.println("\n---\n");
        
        // Generate CSV report using enum instead of string
        ReportGenerator csvGenerator = new ReportGenerator(ReportType.CSV, processor.getData());
        System.out.println("CSV Report:\n" + csvGenerator.generateReport());
        System.out.println("\n---\n");
        
        // Demonstrate extensibility - new TEXT report type
        ReportGenerator textGenerator = new ReportGenerator(ReportType.TEXT, processor.getData());
        System.out.println("TEXT Report:\n" + textGenerator.generateReport());
    }
}
