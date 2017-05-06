package geoview.controller;


import geoview.data.FeatureData;
import geoview.importers.ImportFileMap;
import geoview.importers.ImportPortalMap;
import geoview.importers.ImportedMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.portal.Portal;
import com.esri.arcgisruntime.portal.PortalItem;

public class ImportController {
	
		private Stage mapStage;
	
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
		
		private FeatureData map_data;
		
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
				pathField.setText(getFilePath());
		}
		
		
		@FXML
		public void backEvent(ActionEvent event) throws IOException {
			backButton.getScene().setRoot((Parent)main_load.load());
			MainMenuController cntrl = main_load.<MainMenuController>getController();
			cntrl.initMapStage(mapStage, map_data);
		}
		
		@FXML 
		public void importEvent(ActionEvent event) throws IOException {
			boolean fsImport = searchToggle.getSelectedToggle().equals(fsRadio);
			ImportedMap map;
			map_data = new FeatureData();
			
			if(fsImport) {
				map = new ImportFileMap(pathField.getText());
			} else {
		        Portal portal = new Portal("http://www.arcgis.com");
		        PortalItem portalItem = new PortalItem(portal, idField.getText());
				map = new ImportPortalMap(portalItem);
			}
			map.getView().getMap().addDoneLoadingListener(() -> {
				map_data.retrieveMapData(map);
			});
			mapStage = generateMapStage(map.getView());
			mapStage.show();
		}
		
		private String getFilePath() throws IOException {
			FileChooser fileChooser = new FileChooser();
			File file = fileChooser.showOpenDialog(null);
			if (file != null) {
				return file.getName();
			}
			return "";
		}
		
		private Stage generateMapStage(MapView view) {
				Stage mapStage = new Stage();
				mapStage.setResizable(false);
				mapStage.setOnCloseRequest((event) -> {
					view.dispose();
				});
				Scene scene = new Scene(view, 600, 400);
				mapStage.setScene(scene);
				mapStage.getIcons().add(new Image("/geoview_logo_temp.png"));
				mapStage.setTitle("GEOVIEW - MAP");
				return mapStage;
		}
}
