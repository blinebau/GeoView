package geoview.exporters;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
//import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceGray;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import javafx.concurrent.Task;

public class ExportTask extends Task<Void> {
    
    private List<Map<String, Object>> attrColl;
    
    private String[] exportDetails;
    //private LocalDateTime currentTime = LocalDateTime.now();
    
    private final String[] searchFields = { "SEWER_ID", "SCI", "AGE", "LENGTH", "PIPE_MATERIAL" };
    private final String[] planFields = { "SEWER_ID", "SCI", "AGE", "LENGTH", "PIPE_MATERIAL", "BUDGET" };
    private final String PDF_DEST = "./src/main/resources/export1.pdf";
    private final String TITLE_FONT = "./src/main/resources/fonts/JuliusSansOne-Regular.ttf";
    private final String DESC_FONT = "./src/main/resources/fonts/CrimsonText-Regular.ttf";
    /*	private final String PDF_DEST = "./src/main/resources/export-" + currentTime.toLocalDate() + "@" + currentTime.getHour() + "-" +
     currentTime.getMinute() + ".pdf";*/
    
    public ExportTask(List<Map<String, Object>> newColl, String[] newDetails) {
        attrColl = newColl;
        exportDetails = newDetails;
    }
    
    @Override
    protected Void call() throws IOException {
        File exportFile = new File(PDF_DEST);
        exportFile.getParentFile().mkdirs();
        manipulatePDF(PDF_DEST);
        Desktop.getDesktop().open(exportFile);
        return null;
    }
    
    private void manipulatePDF(String dest) throws FileNotFoundException, IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        PdfFont titleFont = PdfFontFactory.createFont(TITLE_FONT, PdfEncodings.CP1250, true);
        Document doc = new Document(pdfDoc);
        
        Paragraph titleHeading = new Paragraph();
        titleHeading.setTextAlignment(TextAlignment.LEFT);
        titleHeading.add("GeoView")
        			.setFont(titleFont)
        			.setFontSize(24.0f);
        doc.add(titleHeading);
        doc.add(generateSubHeader());
        
        if(exportDetails[0].equals("Maintenance Plan")) {
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
    	PdfFont descFont = PdfFontFactory.createFont(DESC_FONT, PdfEncodings.CP1250, true);
    	Paragraph subTitleHeading = new Paragraph();
        subTitleHeading.setTextAlignment(TextAlignment.LEFT);
        StringBuilder builder = new StringBuilder();
        subTitleHeading.add(buildDetailsMessage(builder))
        				.setFont(descFont)
        				.setFontSize(14.0f);
        return subTitleHeading;
    }
    
    private String buildDetailsMessage(StringBuilder builder) {
    	String exportType = exportDetails[0];
        builder.append(exportType);
        if(exportType.equals("Maintenance Plan")) {
        	builder.append("\nYear: ");
        	builder.append(exportDetails[1]);
        	builder.append("\nMethod: ");
        	builder.append(exportDetails[2]);
        	builder.append("\nBudget: $");
        	builder.append(exportDetails[3]);
        } else if(exportType.equals("Sewer Condition Index")){
        	builder.append(exportDetails[1]);
        	builder.append(exportDetails[2]);
        	builder.append(" - ");
        	builder.append(exportDetails[3]);
        } else {
        	builder.append(exportDetails[1]);
        }
        return builder.toString();
    }
}