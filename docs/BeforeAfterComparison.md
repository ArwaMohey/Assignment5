# Refactoring: Before & After Comparison

## 1. Extract Method Refactoring

### BEFORE: Long Method (35+ lines)
```java
public String generateReport() {
    StringBuilder report = new StringBuilder();
    
    // Process data
    for (String data : rawData) {
        String processed = data.trim().toUpperCase();
        
        // Format based on report type
        if (reportType.equals("PDF")) {
            report.append("PDF-FORMAT: ");
            report.append(processed);
            report.append("\n");
            report.append("---PDF-PAGE-BREAK---\n");
        } else if (reportType.equals("CSV")) {
            report.append(processed);
            report.append(",");
        } else {
            report.append(processed);
            report.append("\n");
        }
    }
    
    // Add footer based on type
    if (reportType.equals("PDF")) {
        report.append("===PDF-END===");
    } else if (reportType.equals("CSV")) {
        if (report.length() > 0 && report.charAt(report.length() - 1) == ',') {
            report.deleteCharAt(report.length() - 1);
        }
    }
    
    return report.toString();
}
```

### AFTER: Extracted into Multiple Focused Methods
```java
public String generateReport() {
    List<String> processedData = processData();
    return formatReport(processedData);
}

private List<String> processData() {
    List<String> processed = new ArrayList<>();
    for (String data : getRawData()) {
        processed.add(data.trim().toUpperCase());
    }
    return processed;
}

private String formatReport(List<String> processedData) {
    return formatter.format(processedData);
}
```

**Benefits:**
- Each method has single responsibility
- Easy to understand and test
- Easy to modify individual aspects

---

## 2. Replace Magic Strings with Symbolic Constants

### BEFORE: Magic Strings
```java
if (reportType.equals("PDF")) {
    // PDF logic
} else if (reportType.equals("CSV")) {
    // CSV logic
}

// Usage
ReportGenerator pdfGen = new ReportGenerator("PDF", data);  // Typo risk!
ReportGenerator csvGen = new ReportGenerator("csv", data);  // Wrong case!
```

### AFTER: Type-Safe Enum
```java
public enum ReportType {
    PDF,
    CSV,
    TEXT
}

// Usage
ReportGenerator pdfGen = new ReportGenerator(ReportType.PDF, data);  // Type safe!
ReportGenerator csvGen = new ReportGenerator(ReportType.CSV, data);  // No typos!
```

**Benefits:**
- Compile-time type checking
- IDE autocomplete support
- Impossible to have typos
- Clear what values are valid

---

## 3. Replace Conditional with Polymorphism

### BEFORE: Conditional Logic
```java
if (reportType.equals("PDF")) {
    report.append("PDF-FORMAT: ");
    report.append(processed);
    report.append("\n");
    report.append("---PDF-PAGE-BREAK---\n");
} else if (reportType.equals("CSV")) {
    report.append(processed);
    report.append(",");
} else {
    report.append(processed);
    report.append("\n");
}
```

### AFTER: Polymorphic Design
```java
// Interface
public interface ReportFormatter {
    String format(List<String> processedData);
}

// PDF Implementation
public class PDFReportFormatter implements ReportFormatter {
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

// CSV Implementation
public class CSVReportFormatter implements ReportFormatter {
    public String format(List<String> processedData) {
        StringBuilder report = new StringBuilder();
        for (String data : processedData) {
            report.append(data);
            report.append(",");
        }
        if (report.length() > 0 && report.charAt(report.length() - 1) == ',') {
            report.deleteCharAt(report.length() - 1);
        }
        return report.toString();
    }
}
```

**Benefits:**
- No conditional statements
- Each formatter has single responsibility
- Easy to add new formatters
- Follows Open/Closed Principle

---

## 4. Encapsulate Field

### BEFORE: Public Field
```java
public String reportType;  // Anyone can modify!

// Problematic usage:
generator.reportType = "XML";  // No validation, no side effects handling
```

### AFTER: Proper Encapsulation
```java
private ReportType reportType;  // Protected

public ReportType getReportType() {
    return reportType;
}

public void setReportType(ReportType reportType) {
    this.reportType = reportType;
    this.formatter = createFormatter(reportType);  // Maintains consistency
}
```

**Benefits:**
- Control over access
- Can add validation
- Can perform side effects (updating formatter)
- Protects object integrity

---

## 5. Self-Encapsulate Field

### BEFORE: Direct Field Access
```java
private List<String> rawData;

public String generateReport() {
    for (String data : rawData) {  // Direct access
        // ...
    }
}
```

### AFTER: Access Through Getters/Setters
```java
private List<String> rawData;

public List<String> getRawData() {
    return rawData;
}

public void setRawData(List<String> rawData) {
    this.rawData = rawData;
}

private List<String> processData() {
    for (String data : getRawData()) {  // Through getter
        // ...
    }
}
```

**Benefits:**
- Future flexibility (can add validation, logging)
- Consistent access pattern
- Easier to refactor later

---

## 6. Remove Dead Code

### BEFORE: Unused Method
```java
public void printStatus() {
    System.out.println("Status: Active");
}
// Never called anywhere!
```

### AFTER: Deleted
```java
// Method completely removed
```

**Benefits:**
- Less code to maintain
- Reduces confusion
- Cleaner interface

---

## 7. Extract Interface & Extract Class

### BEFORE: Everything in One Class
```java
public class ReportGenerator {
    public String reportType;
    private List<String> rawData;
    
    public String generateReport() {
        // Data processing code
        // PDF formatting code
        // CSV formatting code
        // Footer logic
        // All mixed together!
    }
}
```

### AFTER: Separated Responsibilities
```java
// Interface
public interface ReportFormatter {
    String format(List<String> processedData);
}

// Coordinator
public class ReportGenerator {
    private ReportType reportType;
    private List<String> rawData;
    private ReportFormatter formatter;
    
    public String generateReport() {
        List<String> processed = processData();
        return formatReport(processed);
    }
}

// Specialized Classes
public class PDFReportFormatter implements ReportFormatter { ... }
public class CSVReportFormatter implements ReportFormatter { ... }
public class TextReportFormatter implements ReportFormatter { ... }
```

**Benefits:**
- Single Responsibility Principle
- Each class is focused and testable
- Easy to understand and maintain
- Easy to extend

---

## Class Structure Comparison

### BEFORE
```
ReportGenerator
├── reportType: String (public!)
├── rawData: List<String>
├── generateReport(): String (long, complex)
├── printStatus(): void (dead code)
└── getReportType(): String

DataProcessor
└── (unchanged)
```

### AFTER
```
ReportType (enum)
├── PDF
├── CSV
└── TEXT

ReportFormatter (interface)
└── format(List<String>): String

PDFReportFormatter (implements ReportFormatter)
CSVReportFormatter (implements ReportFormatter)
TextReportFormatter (implements ReportFormatter)

ReportGenerator
├── reportType: ReportType (private)
├── rawData: List<String> (private)
├── formatter: ReportFormatter
├── generateReport(): String (simple)
├── processData(): List<String> (focused)
├── formatReport(List<String>): String (focused)
├── createFormatter(ReportType): ReportFormatter (factory)
├── getReportType(): ReportType (encapsulated)
├── setReportType(ReportType): void (encapsulated)
├── getRawData(): List<String> (self-encapsulated)
└── setRawData(List<String>): void (self-encapsulated)

DataProcessor
└── (updated to use ReportType enum)
```

---

## Adding New Report Type: Extensibility Comparison

### BEFORE: Must Modify Existing Code (Violates OCP)
```java
// Must modify ReportGenerator.generateReport()
if (reportType.equals("PDF")) {
    // PDF logic
} else if (reportType.equals("CSV")) {
    // CSV logic
} else if (reportType.equals("XML")) {  // NEW: Must add here
    // XML logic
}
// Risk of breaking existing code!
```

### AFTER: Just Add New Class (Follows OCP)
```java
// Create new file: XMLReportFormatter.java
public class XMLReportFormatter implements ReportFormatter {
    public String format(List<String> processedData) {
        // XML formatting logic
    }
}

// Add enum value
public enum ReportType {
    PDF,
    CSV,
    TEXT,
    XML  // Just add here
}

// Update factory (only place to change)
private ReportFormatter createFormatter(ReportType type) {
    switch (type) {
        case PDF: return new PDFReportFormatter();
        case CSV: return new CSVReportFormatter();
        case TEXT: return new TextReportFormatter();
        case XML: return new XMLReportFormatter();  // NEW
    }
}
```

**Benefits:**
- Existing formatters are untouched
- No risk of breaking tested code
- Clear extension point
- Open for extension, closed for modification

---

## Summary

| Refactoring | Lines Changed | Complexity Reduced | Maintainability | Extensibility |
|-------------|---------------|-------------------|-----------------|---------------|
| Extract Method | -20 | High | High | Medium |
| Magic Strings → Enum | -10 | Medium | High | High |
| Conditional → Polymorphism | -15 | Very High | Very High | Very High |
| Encapsulate Field | +8 | Low | High | High |
| Self-Encapsulate Field | +8 | Low | High | High |
| Remove Dead Code | -4 | Low | Medium | Low |
| Extract Interface/Class | +30 | High | Very High | Very High |

**Overall Impact:**
- Code Smells: 7 → 0
- Maintainability: Poor → Excellent
- Extensibility: Hard → Easy
- SOLID Compliance: Violated → Achieved
