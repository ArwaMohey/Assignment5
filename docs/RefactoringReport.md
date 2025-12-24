# Refactoring Report: ReportGenerator and DataProcessor

**Student:** Assignment 5 Submission  
**Course:** CSE352  
**Word Count:** ~720 words

## Introduction

This report documents the systematic refactoring of the ReportGenerator and DataProcessor classes following the principles and techniques from Lecture 8 on Object-Oriented Refactoring. The initial code exhibited multiple code smells that violated fundamental object-oriented design principles, particularly the Single Responsibility Principle (SRP) and Open/Closed Principle (OCP). Through careful application of established refactoring techniques, the code was transformed into a clean, maintainable, and extensible solution.

## Code Smells Identified and Refactorings Applied

### 1. Long Method (generateReport)

**Code Smell:** The original `generateReport()` method was approximately 35 lines long and performed multiple distinct responsibilities: data processing, format-specific logic handling, and report assembly. This violated SRP and made the method difficult to understand, test, and maintain.

**Refactoring Applied:** Extract Method

Three focused methods were extracted from the original long method:
- `processData()`: Handles data transformation (trimming and uppercasing)
- `formatReport()`: Delegates formatting to the appropriate formatter
- `createFormatter()`: Factory method for creating formatter instances

**Benefits:** Each method now has a single, clear purpose. The main `generateReport()` method reads like a high-level algorithm, improving code readability and maintainability. Individual methods can be tested independently, and changes to one aspect (e.g., data processing) don't affect formatting logic.

### 2. Magic Strings ("PDF", "CSV")

**Code Smell:** String literals "PDF" and "CSV" were hardcoded throughout the code, creating opportunities for typos, making refactoring error-prone, and reducing type safety.

**Refactoring Applied:** Replace Magic Strings with Symbolic Constants (using Enum)

Created a `ReportType` enum with values PDF, CSV, and TEXT. This provides compile-time type checking and eliminates the possibility of runtime errors from typo strings like "pdf" or "Pdf".

**Benefits:** Type safety prevents errors at compile time rather than runtime. The enum makes it clear what values are valid, and IDEs can provide better autocomplete support. Adding new report types now requires adding an enum value, making the extension point explicit.

### 3. Switch Statements / Conditional Logic

**Code Smell:** Multiple if-else statements based on `reportType` cluttered the code and violated OCP. Every new report format would require modifying the `generateReport()` method, increasing the risk of introducing bugs into tested code.

**Refactoring Applied:** Replace Conditional with Polymorphism

Created a `ReportFormatter` interface and three concrete implementations: `PDFReportFormatter`, `CSVReportFormatter`, and `TextReportFormatter`. Each formatter encapsulates format-specific logic.

**Benefits:** This eliminates conditional statements and adheres to OCP. New report formats can be added by creating new formatter classes without modifying existing, tested code. Each formatter class focuses solely on one format type, following SRP. The design now uses the Strategy Pattern, making the code more flexible and testable.

### 4. Feature Envy / Lack of Abstraction

**Code Smell:** Report formatting logic was embedded directly in `ReportGenerator`, even though formatting is a distinct responsibility that varies by report type.

**Refactoring Applied:** Extract Interface and Extract Class

Created the `ReportFormatter` interface and moved format-specific logic into separate classes (PDFReportFormatter, CSVReportFormatter, TextReportFormatter).

**Benefits:** This separation of concerns follows SRP by giving each class a single responsibility. The ReportGenerator now coordinates the report generation process while delegating formatting to specialists. This makes the code easier to understand, test, and extend.

### 5. Dead Code (printStatus method)

**Code Smell:** The `printStatus()` method was never called anywhere in the codebase, representing unnecessary code that increases maintenance burden and confusion.

**Refactoring Applied:** Remove Dead Code

Deleted the `printStatus()` method entirely from the refactored version.

**Benefits:** Removes unnecessary code that developers might waste time trying to understand or maintain. Simplifies the class interface and reduces cognitive load.

### 6. Lack of Encapsulation

**Code Smell:** The `reportType` field was public, allowing direct manipulation from outside the class. The `rawData` field lacked getters and setters, preventing future flexibility in how the field is accessed or modified.

**Refactoring Applied:** Encapsulate Field and Self-Encapsulate Field

Made `reportType` private and added getter/setter methods. Added getter/setter methods for `rawData` field. The setter for `reportType` also updates the formatter, maintaining object consistency.

**Benefits:** Encapsulation protects object integrity by controlling how fields are accessed and modified. Future requirements (e.g., validation, logging, or format conversion) can be implemented in getters/setters without changing client code. This follows the principle of information hiding.

## Design Principle Improvements

**Single Responsibility Principle:** Each class now has one clear responsibility. ReportGenerator manages report generation coordination, while each formatter handles only its specific format.

**Open/Closed Principle:** The system is now open for extension (new formatters can be added) but closed for modification (existing classes don't need changes). Demonstrated by adding TextReportFormatter without modifying any existing code.

**Maintainability:** Code is easier to understand with shorter methods, clear class responsibilities, and eliminated duplication. Each formatter can be tested independently.

**Extensibility:** Adding new report formats requires only creating a new formatter class and adding an enum value. No existing code needs modification, reducing regression risk.

## Conclusion

Through systematic application of refactoring techniques from Lecture 8, the code was transformed from a monolithic, conditional-heavy design into a clean, polymorphic solution. All identified code smells were eliminated, and the design now adheres to SOLID principles, particularly SRP and OCP. The refactored code is more maintainable, testable, and extensible while preserving the original behavior.
