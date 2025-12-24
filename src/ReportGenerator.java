import java.util.List;

/**
 * Initial implementation with code smells that needs refactoring
 * Code Smells present:
 * - Long Method (generateReport)
 * - Magic Strings ("PDF", "CSV")
 * - Switch Statements / Conditional Logic
 * - Dead Code (printStatus)
 * - Not encapsulated fields
 */
public class ReportGenerator {
    public String reportType;  // Should be encapsulated
    private List<String> rawData;  // Not self-encapsulated
    
    public ReportGenerator(String reportType, List<String> rawData) {
        this.reportType = reportType;
        this.rawData = rawData;
    }
    
    /**
     * Long Method that does too much - needs Extract Method refactoring
     * Contains conditional logic based on reportType - needs Replace Conditional with Polymorphism
     */
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        
        // Process data
        for (String data : rawData) {
            String processed = data.trim().toUpperCase();
            
            // Format based on report type - violates OCP, needs polymorphism
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
    
    /**
     * Dead Code - unused method that should be removed
     */
    public void printStatus() {
        System.out.println("Status: Active");
    }
    
    public String getReportType() {
        return reportType;
    }
}
