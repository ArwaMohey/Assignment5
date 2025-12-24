package com.assignment5.formatter;

import java.util.List;

/**
 * Refactoring: Extract Interface
 * Created interface to define contract for report formatters
 * Benefits: 
 * - Follows Interface Segregation Principle
 * - Allows multiple implementations without changing client code
 * - Supports Open/Closed Principle
 */
public interface ReportFormatter {
    /**
     * Format a list of processed data items into a report
     * @param processedData List of processed data strings
     * @return Formatted report as a string
     */
    String format(List<String> processedData);
}
