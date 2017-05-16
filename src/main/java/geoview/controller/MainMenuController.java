package geoview.controller;

import java.io.IOException;

import geoview.data.FeatureData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {
	
	@FXML
	private Button importMapData;
	
	@FXML
	private Button sci;
	
	@FXML
	private Button predictivePerformance;
	
	@FXML
	private Button maintenanceScenarios;
	
	private Stage mapStage;
	
	private FeatureData mapData;
	
	@FXML
	public void initialize() {
		assert(importMapData != null);
		assert(sci != null);
		assert(predictivePerformance != null);
		assert(maintenanceScenarios != null);
	}
	
	@FXML
	public void importEvent(ActionEvent event) throws IOException {
		FXMLLoader load = new FXMLLoader(getClass().getClassLoader().getResource("import.fxml"));
		importMapData.getScene().setRoot((Parent)load.load());
	}
	
	@FXML
	public void sciEvent(ActionEvent event) throws IOException {
		FXMLLoader load = new FXMLLoader(getClass().getClassLoader().getResource("sci.fxml"));
		sci.getScene().setRoot((Parent)load.load());
		SCIController cntrl = load.<SCIController>getController();
		cntrl.initMapData(mapStage, mapData);
	}
	
	
	@FXML
	public void predictivePerformanceEvent(ActionEvent event) throws IOException {
		FXMLLoader load = new FXMLLoader(getClass().getClassLoader().getResource("PredictivePerformance.fxml"));
		predictivePerformance.getScene().setRoot((Parent)load.load());
	}
	
	@FXML
	public void maintenanceScenariosEvent(ActionEvent event) throws IOException {
		FXMLLoader load = new FXMLLoader(getClass().getClassLoader().getResource("MaintenanceScenarios.fxml"));
		maintenanceScenarios.getScene().setRoot((Parent)load.load());
	}
	
	public void initMapData(Stage newMapStage, FeatureData newMapData) {
		mapStage = newMapStage;
		mapData = newMapData;
	}
	

}
