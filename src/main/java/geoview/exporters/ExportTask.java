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
    
    private String exportMessage;
    //private LocalDateTime currentTime = LocalDateTime.now();
    
    private final String PDF_DEST = "./src/main/resources/export1.pdf";
    private final String TITLE_FONT = "./src/main/resources/fonts/JuliusSansOne-Regular.ttf";
    private final String DESC_FONT = "./src/main/resources/fonts/CrimsonText-Regular.ttf";
    /*	private final String PDF_DEST = "./src/main/resources/export-" + currentTime.toLocalDate() + "@" + currentTime.getHour() + "-" +
     currentTime.getMinute() + ".pdf";*/
    
    public ExportTask(List<Map<String, Object>> newColl, String exportType) {
        attrColl = newColl;
        exportMessage = determineExportMessage(exportType);
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
        PdfFont descFont = PdfFontFactory.createFont(DESC_FONT, PdfEncodings.CP1250, true);
        Document doc = new Document(pdfDoc);
        
        Paragraph titleHeading = new Paragraph();
        titleHeading.setTextAlignment(TextAlignment.LEFT);
        titleHeading.add("GeoView")
        			.setFont(titleFont)
        			.setFontSize(24.0f);
        doc.add(titleHeading);
        
        Paragraph subTitleHeading = new Paragraph();
        subTitleHeading.setTextAlignment(TextAlignment.LEFT);
        subTitleHeading.add(exportMessage)
        				.setFont(descFont)
        				.setFontSize(18.0f);
        doc.add(subTitleHeading);
        
        populateTable(doc);
        doc.close();
    }
    
    private void populateTable(Document doc) {
        //Omitted PIPE_MATERIAL- 2
        String[] fields = { "SEWER_ID", "SCI", "AGE", "LENGTH" };
        float[] fieldWidth = { 2, 2, 2, 2 };
        
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
    
    private String determineExportMessage(String exportType) {
    	switch(exportType) {
	    	case "SCI":
	    		return "Sewer Condition Index";
	    	default:
	    		return "Exported Result";
    	}
    }
}