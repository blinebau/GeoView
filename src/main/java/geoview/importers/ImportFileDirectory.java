package geoview.importers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.stage.FileChooser;

public class ImportFileDirectory {
	
	public static String browseEvent() throws IOException {
		Desktop desktop = Desktop.getDesktop();
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(null);
		if (file != null) {
			//Load file
			return file.getName();
		}
		return "";
	}
	

}
