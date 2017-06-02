package geoview.exporters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceGray;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import javafx.concurrent.Task;

public class ExportTask extends Task<File> {
    
    private List<Map<String, Object>> attrColl;
    
    private List<String> exportDetails;
    
    private String exportDest;
    
    private final String[] searchFields = { "SEWER_ID", "SCI", "AGE", "LENGTH", "PIPE_MATERIAL" };
    private final String[] planFields = { "SEWER_ID", "SCI", "AGE", "LENGTH", "PIPE_MATERIAL", "BUDGET" };
    //private final String TITLE_FONT = "/JuliusSansOne-Regular.ttf";
    //private final String DESC_FONT = "/CrimsonText-Regular.ttf";
    
    public ExportTask(List<Map<String, Object>> newColl, List<String> newDetails, String dest) {
        attrColl = newColl;
        exportDetails = newDetails;
        exportDest = dest;
    }
    
    @Override
    protected File call() throws IOException {
        File exportFile = new File(exportDest);
        manipulatePDF();
        return exportFile;
    }
    
    private void manipulatePDF() throws FileNotFoundException, IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(exportDest));
        //PdfFont titleFont = PdfFontFactory.createFont(TITLE_FONT, PdfEncodings.CP1250, true);
        Document doc = new Document(pdfDoc);
        
        Paragraph titleHeading = new Paragraph();
        titleHeading.setTextAlignment(TextAlignment.LEFT);
        titleHeading.add("GeoView")
        			//.setFont(titleFont)
        			.setFontSize(24.0f);
        doc.add(titleHeading);
        doc.add(generateSubHeader());
        
        if(exportDetails.get(0).equals("Maintenance Plan")) {
        	populateTable(doc, planFields);
        } else {
        	populateTable(doc, searchFields);
        }
        doc.close();
    }
    
    private void populateTable(Document doc, String[] fields) {
        float[] fieldWidth = new float[fields.length];
        for(int i = 0; i < fieldWidth.length; i++) {
        	fieldWidth[i] = 1;
        }
        
        Table table = new Table(fieldWidth);
        table.setWidthPercent(100);
        
        for(int i = 0; i < fields.length; i++) {
            table.addCell(new Cell()
            		.setTextAlignment(TextAlignment.CENTER)
            		.setBackgroundColor(new DeviceGray(0.5f))
            		.setFontColor(Color.WHITE)
            		.add(new Paragraph(fields[i])));
        }
        for(Map<String, Object> attr : attrColl) {
            for(int i = 0; i < fields.length; i++) {
               table.addCell(new Cell()
            		   .setTextAlignment(TextAlignment.CENTER)
            		   .add(new Paragraph(attr.get(fields[i]).toString())));
            }
        }
        doc.add(table);
    }
    
    private Paragraph generateSubHeader() throws IOException {
    	//PdfFont descFont = PdfFontFactory.createFont(DESC_FONT, PdfEncodings.CP1250, true);
    	Paragraph subTitleHeading = new Paragraph();
        subTitleHeading.setTextAlignment(TextAlignment.LEFT);
        StringBuilder builder = new StringBuilder();
        subTitleHeading.add(buildDetailsMessage(builder))
        				//.setFont(descFont)
        				.setFontSize(14.0f);
        return subTitleHeading;
    }
    
    private String buildDetailsMessage(StringBuilder builder) {
    	String exportType = exportDetails.get(0);
        builder.append(exportType);
        if(exportType.equals("Maintenance Plan")) {
        	builder.append("\nYear: ");
        	builder.append(exportDetails.get(1));
        	builder.append("\nMethod: ");
        	builder.append(exportDetails.get(2));
        	builder.append("\nBudget: $");
        	builder.append(exportDetails.get(3));
        } else if(exportType.equals("Sewer Condition Index")){
        	builder.append(exportDetails.get(1));
        	builder.append(exportDetails.get(2));
        	builder.append(" - ");
        	builder.append(exportDetails.get(3));
        } else {
        	builder.append(exportDetails.get(1));
        }
        return builder.toString();
    }
}