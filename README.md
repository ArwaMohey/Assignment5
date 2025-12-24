# CSE352 Assignment 5: Refactoring Exercise

## Overview

This repository contains a Java refactoring assignment demonstrating the application of object-oriented refactoring principles and techniques covered in Lecture 8.

## Repository Structure

```
Assignment5/
├── src/                          # Original "smelly" code
│   ├── ReportGenerator.java      # Original implementation with code smells
│   └── DataProcessor.java        # Original data processor
│
├── src-refactored/               # Refactored code following OO principles
│   └── com/assignment5/
│       ├── model/
│       │   ├── ReportType.java         # Enum replacing magic strings
│       │   ├── ReportGenerator.java    # Refactored generator
│       │   └── DataProcessor.java      # Updated processor
│       └── formatter/
│           ├── ReportFormatter.java     # Interface for formatters
│           ├── PDFReportFormatter.java  # PDF-specific formatter
│           ├── CSVReportFormatter.java  # CSV-specific formatter
│           └── TextReportFormatter.java # TEXT-specific formatter
│
└── docs/                         # Documentation
    ├── InitialUML.md             # UML before refactoring
    ├── RefactoredUML.md          # UML after refactoring
    └── RefactoringReport.md      # Detailed refactoring report
```

## How to Run

### Original Code
```bash
cd src
javac DataProcessor.java ReportGenerator.java
java DataProcessor
```

### Refactored Code
```bash
cd src-refactored
javac com/assignment5/model/*.java com/assignment5/formatter/*.java
java com.assignment5.model.DataProcessor
```

Both versions produce the same output for PDF and CSV reports, demonstrating that behavior is preserved.

## Code Smells in Original Code

1. **Long Method**: `generateReport()` was too long and did too much
2. **Magic Strings**: Hardcoded "PDF" and "CSV" strings
3. **Switch Statements**: Multiple if-else blocks based on report type
4. **Dead Code**: Unused `printStatus()` method
5. **Lack of Encapsulation**: Public `reportType` field
6. **Feature Envy**: Formatting logic embedded in wrong class

## Refactoring Techniques Applied

1. **Extract Method**: Broke down long `generateReport()` method
2. **Replace Magic Strings with Symbolic Constants**: Created `ReportType` enum
3. **Replace Conditional with Polymorphism**: Used Strategy pattern with formatters
4. **Extract Interface**: Created `ReportFormatter` interface
5. **Extract Class**: Separated formatters into dedicated classes
6. **Encapsulate Field**: Made `reportType` private with getters/setters
7. **Self-Encapsulate Field**: Added getters/setters for `rawData`
8. **Remove Dead Code**: Deleted unused `printStatus()` method

## Design Principles Achieved

### Single Responsibility Principle (SRP)
- ReportGenerator: Manages report generation coordination
- Each Formatter: Handles one specific format

### Open/Closed Principle (OCP)
- New report types can be added without modifying existing code
- Demonstrated by adding TEXT format without changing ReportGenerator

### Better Maintainability
- Shorter, focused methods
- Clear class responsibilities
- Eliminated code duplication

### Better Extensibility
- Easy to add new report formats
- Just create new formatter class and add enum value
- No modification to existing tested code

## Deliverables

1. ✅ **Initial UML Class Diagram** - See `docs/InitialUML.md`
2. ✅ **Refactored Java Code** - See `src-refactored/` directory
3. ✅ **Refactored UML Class Diagram** - See `docs/RefactoredUML.md`
4. ✅ **Refactoring Report (500-750 words)** - See `docs/RefactoringReport.md`

## Key Improvements

| Aspect | Before | After |
|--------|--------|-------|
| Number of Classes | 2 | 7 (more focused) |
| Interfaces | 0 | 1 |
| Public Fields | 1 | 0 |
| Long Methods | 1 (35+ lines) | 0 (max ~10 lines) |
| Conditional Logic | Multiple if-else | Polymorphism |
| Dead Code | 1 method | 0 |
| Magic Strings | 2 | 0 |
| Encapsulation | Poor | Strong |
| Extensibility | Hard | Easy |

## Verification

Both the original and refactored code produce identical output:

**PDF Report:**
```
PDF-FORMAT: SALES DATA: Q1
---PDF-PAGE-BREAK---
PDF-FORMAT: REVENUE: $50000
---PDF-PAGE-BREAK---
PDF-FORMAT: EXPENSES: $30000
---PDF-PAGE-BREAK---
===PDF-END===
```

**CSV Report:**
```
SALES DATA: Q1,REVENUE: $50000,EXPENSES: $30000
```

## References

- CSE352-Lecture8-OO-Refactoring.pdf - Course lecture on refactoring
- CSE352-Assign5-Refact.pdf - Assignment specification

## Author

CSE352 Student - Assignment 5 Submission
