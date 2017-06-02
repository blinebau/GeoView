package geoview.controller;


import geoview.alerts.DataAlert;
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
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Optional;

import com.esri.arcgisruntime.mapping.Viewpoint;
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
		
		@FXML
		private TextField urlField;
		
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
			webRadio.setSelected(true);
			idField.setText("98f54307ba5e4586b49772b73a16d245");
			urlField.setText("http://services7.arcgis.com/DQPcd87LglSVJI8c/arcgis/rest/services/Sewer_Test_Map/FeatureServer/1");
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
		
		
		@FXML
		public void backEvent(ActionEvent event) throws IOException {
			backButton.getScene().setRoot((Parent)mainLoad.load());
			MainMenuController cntrl = mainLoad.<MainMenuController>getController();
			cntrl.initMapData(mapStage, mapData);
		}
		
		@FXML 
		public void importEvent(ActionEvent event) throws IOException {
			if(mapStage == null) {
				importButton.setText("Importing...");
				importButton.setDisable(true);
				importFeatureData();
			} else {
				String[] alertText = { "GeoView - Import Confirmation", "Confirm New Import", "Importing new data now will begin a new session, closing the map window. Do you wish to import new data?"};
				Alert importConfirm = DataAlert.setAlert(importButton.getScene().getWindow(), alertText);
				Optional<ButtonType> result = importConfirm.showAndWait();
				if(result.get() == ButtonType.OK) {
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
			importButton.setText("Import");
			importButton.setDisable(false);
		}
		
		private void importFeatureData() {
			mapData = new FeatureData();
			DataTask dataTask = new DataTask();
			dataTask.setOnSucceeded(t -> {
				mapData.setAttrCollection(dataTask.getValue());
				importMapData();
			});
			dataTask.setOnFailed(t -> {
				Alert invalidData;
				String[] alertText = { "GeoView - Invalid Data", "An error occurred while importing data.", "Error related to invalid schema or field value."};
				Window alertWindow = importButton.getScene().getWindow();
				if(dataTask.getException() != null) {
					alertText[2] = dataTask.getException().getMessage();
					invalidData = DataAlert.setAlert(alertWindow, alertText);
				} else {
					invalidData = DataAlert.setAlert(alertWindow, alertText);
				}
				invalidData.showAndWait();
			});
			mapData.retrieveMapData(dataTask, urlField.getText());
		}
		
		private Stage generateMapStage(MapView view) {
				Viewpoint viewPoint = new Viewpoint(40.278042, -75.312331, 10E3);
				view.setViewpoint(viewPoint);
				Stage mapStage = new Stage();
				mapStage.setResizable(false);
				mapStage.setOnCloseRequest((event) -> {
					view.dispose();
					ImportController.mapStage = null;
				});
				Scene scene = new Scene(view, 900, 600);
				mapStage.setScene(scene);
				mapStage.getIcons().add(new Image("/window_icon.png"));
				mapStage.setTitle("GeoView - Map");
				return mapStage;
		}
}
