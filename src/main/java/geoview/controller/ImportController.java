package geoview.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import com.esri.arcgisruntime.mapping.view.MapView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ImportController {
		private String fileName;
		
		@FXML
		private RadioButton fsRadio;
		
		@FXML
		private RadioButton webRadio;
		
		@FXML
		private ToggleGroup searchToggle;

		@FXML
		private Button importButton;
		
		@FXML
		private Button browseButton;
		
		@FXML
		private Button backButton;
		
		@FXML
		private TextField pathField;
		
		@FXML
		private TextField idField;
		
		@FXML
		public void initialize() {
			assert(fsRadio != null);
			assert(webRadio != null);
			assert(backButton != null);
			assert(importButton != null);
			assert(pathField != null);
			assert(browseButton != null);
			assert(idField != null);
			configureSearchToggleListener();
		}
		
		@FXML
		private void configureSearchToggleListener() {
			searchToggle.selectedToggleProperty().addListener((observed, oldval, newval) -> {
				if(newval.equals(fsRadio)) {
					idField.setText("");
				} else {
					pathField.setText("");
				}
			});
		}
		
		@FXML
		public void browseEvent() throws IOException {
			Desktop desktop = Desktop.getDesktop();
			FileChooser fileChooser = new FileChooser();
			File file = fileChooser.showOpenDialog(null);
			if (file != null) {
				//Load file
				fileName = file.getName();
				pathField.setText(fileName);
				//System.out.println(fileName);
			}
		}
		
		
		@FXML
		public void backEvent(ActionEvent event) throws IOException {
			FXMLLoader load = new FXMLLoader(getClass().getClassLoader().getResource("main_menu.fxml"));
			backButton.getScene().setRoot((Parent)load.load());
		}
		
		@FXML public void importEvent(ActionEvent event) {
			boolean fsImport = searchToggle.getSelectedToggle().equals(fsRadio);
			if(fsImport) {
				//get path text
				//pass to importmapcontroller for local map file
			} else {
				ImportedMapController controller = new ImportedMapController();
				MapView view = controller.importMap(idField.getText().toString());
				Stage stage = new Stage();
				StackPane stack = new StackPane();
				stack.getChildren().add(view);
				Scene scene = new Scene(stack, 600, 400);
				stage.setScene(scene);
				stage.show();
			}
		}
}
