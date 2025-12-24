import java.util.ArrayList;
import java.util.List;

/**
 * Simple data processor class
 * Works with ReportGenerator to process and format data
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
     * Demonstrates usage of ReportGenerator with different report types
     */
    public static void main(String[] args) {
        DataProcessor processor = new DataProcessor();
        processor.addData("Sales Data: Q1");
        processor.addData("Revenue: $50000");
        processor.addData("Expenses: $30000");
        
        // Generate PDF report
        ReportGenerator pdfGenerator = new ReportGenerator("PDF", processor.getData());
        System.out.println("PDF Report:\n" + pdfGenerator.generateReport());
        System.out.println("\n---\n");
        
        // Generate CSV report
        ReportGenerator csvGenerator = new ReportGenerator("CSV", processor.getData());
        System.out.println("CSV Report:\n" + csvGenerator.generateReport());
    }
}
