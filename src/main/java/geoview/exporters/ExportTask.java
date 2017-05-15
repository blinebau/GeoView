package geoview.exporters;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
//import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import javafx.concurrent.Task;

public class ExportTask extends Task<Void> {
	
	private ArrayList<Map<String, Object>> attrColl;
	
	//private LocalDateTime currentTime = LocalDateTime.now();
	
	private final String pdfPath = "./src/main/resources/export1.pdf";
/*	private final String pdfPath = "./src/main/resources/export-" + currentTime.toLocalDate() + "@" + currentTime.getHour() + "-" + 
			currentTime.getMinute() + ".pdf";*/
	
	public ExportTask(ArrayList<Map<String, Object>> newColl) {
		attrColl = newColl;
	}

	@Override
	protected Void call() throws IOException {
		File exportFile = new File(pdfPath);
		exportFile.getParentFile().mkdirs();
		manipulatePDF(pdfPath);
		Desktop.getDesktop().open(exportFile);
		return null;
	}
	
	private void manipulatePDF(String dest) throws FileNotFoundException {
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
		Document doc = new Document(pdfDoc);
		
		String[] fields = { "SEWER_ID", "SCI", "AGE", "LENGTH", "PIPE_MATERIAL" };
		float[] fieldWidth = { 1, 2, 2, 2, 2 };
		
		Table table = new Table(fieldWidth);
		table.setWidthPercent(100);
		
		for(int i = 0; i < fields.length; i++) {
			table.addCell(new Cell().add(new Paragraph(fields[i])));
		}
		for(Map<String, Object> attr : attrColl) {
			for(int i = 0; i < fields.length; i++) {
				table.addCell(new Cell().add(new Paragraph(attr.get(fields[i]).toString())));
			}
		}
		doc.add(table);
		doc.close();
	}

}
