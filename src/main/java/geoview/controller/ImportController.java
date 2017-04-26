package geoview.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import com.esri.arcgisruntime.mapping.view.MapView;

import geoview.importers.ImportFileDirectory;
import geoview.importers.ImportPortalMap;
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
import javafx.scene.image.Image;
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
		
		private FXMLLoader main_load;
		
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
			main_load = new FXMLLoader(getClass().getClassLoader().getResource("main_menu.fxml"));
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
		
		public void browseEvent() throws IOException {
				pathField.setText(ImportFileDirectory.browseEvent());
		}
		
		
		@FXML
		public void backEvent(ActionEvent event) throws IOException {
			backButton.getScene().setRoot((Parent)main_load.load());
		}
		
		@FXML 
		public void importEvent(ActionEvent event) throws IOException {
			boolean fsImport = searchToggle.getSelectedToggle().equals(fsRadio);
			if(fsImport) {
				//get path text
				//pass to importmapcontroller for local map file
			} else {
				MapView view = ImportPortalMap.importMap(idField.getText().toString());
				Stage mapStage = new Stage();
				StackPane mapStack = new StackPane();
				mapStack.getChildren().add(view);
				Scene scene = new Scene(mapStack, 600, 400);
				mapStage.setScene(scene);
				mapStage.getIcons().add(new Image("/geoview_logo_temp.png"));
				mapStage.setTitle("GEOVIEW - MAP");
				mapStage.show();
				importButton.getScene().setRoot((Parent)main_load.load());
			}
		}
		
		//Add Map Destructor on Map Window Close
}
