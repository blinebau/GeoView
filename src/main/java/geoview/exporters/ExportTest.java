//package geoview.exporters;
//
//import java.awt.Desktop;
//import java.io.File;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Cell;
//import com.itextpdf.layout.element.Paragraph;
//import com.itextpdf.layout.element.Table;
//
//public class ExportTest {
//	
//	private static LocalDateTime currentTime = LocalDateTime.now();
//	
//	private final static String pdfPath = "./src/main/resources/export-" + currentTime.toLocalDate() + "@" + currentTime.getHour() + "-" + 
//			currentTime.getMinute() + ".pdf";
//	
//	public static void main(String [] args) throws Exception {
//		File file = new File(pdfPath);
//		file.getParentFile().mkdirs();
//		new ExportTest().manipulatePdf(pdfPath);
//		Desktop.getDesktop().open(file);
//	}
//	
//	public void manipulatePdf(String dest) throws Exception {
//		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
//		Document doc = new Document(pdfDoc);
//		
//		float[] colWidths = { 1, 3, 3, 3 };
//		Table table = new Table(colWidths);
//		table.setWidthPercent(100);
//		ArrayList<Map<String, Object>> attrColl = new ArrayList<>();
//		for(int i = 0; i < 5; i++) {
//			Map<String, Object> map = new HashMap<>();
//			map.put("ID", "1");
//			map.put("SCI", "34");
//			map.put("AGE", "54");
//			map.put("LENGTH", "54");
//			attrColl.add(map);
//		}
//		String[] headers = { "ID", "SCI", "AGE", "LENGTH" };
//		for(int i = 0; i < headers.length; i++) {
//			table.addCell(new Cell().add(new Paragraph(headers[i])));
//		}
//		for(Map<String, Object> attr : attrColl) {
//			for(int i = 0; i < headers.length; i++) {
//				table.addCell(new Cell().add(new Paragraph((String) attr.get(headers[i]))));
//			}
//		}
//		
//		doc.add(table);
//		doc.close();
//	}
//}
