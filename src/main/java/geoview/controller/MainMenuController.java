package geoview.controller;

import java.io.IOException;

import com.esri.arcgisruntime.mapping.view.MapView;

import geoview.data.FeatureData;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {
	
	@FXML
	private Button importMapData;
	
	@FXML
	private Button search;
	
	@FXML
	private Button maintenanceScenarios;
	
	@FXML
	private Button exit;
	
	private FXMLLoader loader;
	
	private Stage mapStage;
	
	private FeatureData mapData;
	
	@FXML
	public void initialize() {
		loader = new FXMLLoader();
		assert(importMapData != null);
		assert(search != null);
		assert(maintenanceScenarios != null);
		assert(exit != null);
	}
	
	@FXML
	public void importEvent(ActionEvent event) throws IOException {
		loader.setLocation(getClass().getClassLoader().getResource("import.fxml"));
		importMapData.getScene().setRoot((Parent)loader.load());
	}
	
	@FXML
	public void searchEvent(ActionEvent event) throws IOException {
		FXMLLoader load = new FXMLLoader(getClass().getClassLoader().getResource("Search.fxml"));
		search.getScene().setRoot((Parent)load.load());
		SearchController cntrl = load.<SearchController>getController();
		cntrl.initMapData(mapStage, mapData);
	}
	
	@FXML
	public void maintenanceScenariosEvent(ActionEvent event) throws IOException {
		FXMLLoader load = new FXMLLoader(getClass().getClassLoader().getResource("MaintenanceScenarios.fxml"));
		maintenanceScenarios.getScene().setRoot((Parent)load.load());
		MaintenanceScenariosController cntrl = load.<MaintenanceScenariosController>getController();
		cntrl.initMapData(mapStage, mapData);
	}
	
	@FXML
	public void exitEvent(ActionEvent event) throws IOException {
		if(mapStage != null && mapStage.isShowing()) {
			MapView view = (MapView) mapStage.getScene().getRoot();
			view.dispose();
			mapStage.close();
		}
		Platform.exit();
	}
	
	public void initMapData(Stage newMapStage, FeatureData newMapData) {
		mapStage = newMapStage;
		mapData = newMapData;
	}
	

}
