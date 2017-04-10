package geoview.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

public class ImportController {
		private String fileName;

		@FXML
		private Button nextButton;
		
		@FXML
		private Button backButton;
		
		@FXML
		private TextArea name;
		
		@FXML
		public void initialize() {
			assert(backButton != null);
		}
		
		@FXML
		public void nextEvent(ActionEvent event) throws IOException {
			Desktop desktop = Desktop.getDesktop();
			FileChooser fileChooser = new FileChooser();
			File file = fileChooser.showOpenDialog(null);
			if (file != null) {
				//Load file
				fileName = file.getName();
				name.setText(fileName);
				System.out.println(fileName);
			}
		}
		
		@FXML
		public void backEvent(ActionEvent event) throws IOException {
			FXMLLoader load = new FXMLLoader(getClass().getClassLoader().getResource("main_menu.fxml"));
			backButton.getScene().setRoot((Parent)load.load());
		}
}
