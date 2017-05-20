package geoview.controller;


import geoview.alerts.DataAlert;
import geoview.alerts.ImportAlert;
import geoview.data.DataTask;
import geoview.data.FeatureData;
import geoview.importers.ImportFileMap;
import geoview.importers.ImportPortalMap;
import geoview.importers.ImportedMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.portal.Portal;
import com.esri.arcgisruntime.portal.PortalItem;

public class ImportController {
	
		private static Stage mapStage;
	
		@FXML
		private RadioButton fsRadio;
		
		@FXML
		private RadioButton webRadio;
		
		@FXML
		private ToggleGroup searchToggle;

		@FXML
		private Button importButton;
		
		@FXML
		private Button backButton;
		
		@FXML
		private TextField pathField;
		
		@FXML
		private TextField idField;
		
		private FXMLLoader mainLoad;
		
		private FeatureData mapData;
		
		@FXML
		public void initialize() {
			assert(fsRadio != null);
			assert(webRadio != null);
			assert(backButton != null);
			assert(importButton != null);
			assert(pathField != null);
			assert(idField != null);
			configureSearchToggleListener();
			mainLoad = new FXMLLoader(getClass().getClassLoader().getResource("main_menu.fxml"));
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
			backButton.getScene().setRoot((Parent)mainLoad.load());
			MainMenuController cntrl = mainLoad.<MainMenuController>getController();
			cntrl.initMapData(mapStage, mapData);
		}
		
		@FXML 
		public void importEvent(ActionEvent event) throws IOException {
			if(mapStage == null) {
				importFeatureData();
			} else {
				Alert alert = ImportAlert.setAlert(importButton.getScene().getWindow());
				Optional<ButtonType> result = alert.showAndWait();
				if(result.get() == ButtonType.OK) {
					mapData.setAttrCollection(new ArrayList<Map<String, Object>>());
					Scene mapScene = mapStage.getScene();
					MapView view = (MapView) mapScene.getRoot();
					view.dispose();
					mapStage.close();
					importFeatureData();
				}
			}
		}
		
		private void importMapData() {
			boolean fsImport = searchToggle.getSelectedToggle().equals(fsRadio);
			ImportedMap map;
			
			if(fsImport) {
				map = new ImportFileMap(pathField.getText());
			} else {
		        Portal portal = new Portal("http://www.arcgis.com");
		        PortalItem portalItem = new PortalItem(portal, idField.getText());
				map = new ImportPortalMap(portalItem);
			}
			mapStage = generateMapStage(map.getView());
			mapStage.show();
		}
		
		private void importFeatureData() {
			DataTask dataTask = new DataTask();
			dataTask.setOnSucceeded(t -> {
				mapData.setAttrCollection(dataTask.getValue());
				importMapData();
			});
			dataTask.setOnFailed(t -> {
				Alert dataAlert;
				Window alertWindow = importButton.getScene().getWindow();
				if(dataTask.getException() != null) {
					dataAlert = DataAlert.setAlert(alertWindow, dataTask.getException().getMessage());
				} else {
					dataAlert = DataAlert.setAlert(alertWindow, "Error related to invalid schema or field value.");
				}
				dataAlert.showAndWait();
			});
			mapData = new FeatureData();
			mapData.retrieveMapData(dataTask);
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
					ImportController.mapStage = null;
				});
				Scene scene = new Scene(view, 600, 400);
				mapStage.setScene(scene);
				mapStage.getIcons().add(new Image("/window_icon.png"));
				mapStage.setTitle("GeoView - Map");
				return mapStage;
		}
}
