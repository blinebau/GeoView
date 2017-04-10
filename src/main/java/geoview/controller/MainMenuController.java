package geoview.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainMenuController {
	
	@FXML
	private Button importMap_Data;
	
	@FXML
	private Button sci;
	
	@FXML
	private Button inventory;
	
	@FXML
	private Button predictivePerformance;
	
	@FXML
	private Button maintenanceScenarios;
	
	@FXML
	public void initialize() {
		assert(importMap_Data != null);
		assert(sci != null);
		assert(inventory != null);
		assert(predictivePerformance != null);
		assert(maintenanceScenarios != null);
		//sci.setDisable(true);
		inventory.setDisable(true);
		//predictivePerformance.setDisable(true);
		//maintenanceScenarios.setDisable(true);
		//Enable by default, in future
		//importMap_Data.setDisable(true);
	}
	
	@FXML
	public void importEvent(ActionEvent event) throws IOException {
		FXMLLoader load = new FXMLLoader(getClass().getClassLoader().getResource("import.fxml"));
		importMap_Data.getScene().setRoot((Parent)load.load());
	}
	
	@FXML
	public void sciEvent(ActionEvent event) throws IOException {
		FXMLLoader load = new FXMLLoader(getClass().getClassLoader().getResource("sci.fxml"));
		sci.getScene().setRoot((Parent)load.load());
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
	

}
