package geoview.controller;

import java.io.IOException;
import java.util.Optional;

import com.esri.arcgisruntime.mapping.view.MapView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
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
	
	private Stage currentMapStage;
	
	@FXML
	public void initialize() {
		assert(importMap_Data != null);
		assert(sci != null);
		assert(inventory != null);
		assert(predictivePerformance != null);
		assert(maintenanceScenarios != null);
		inventory.setDisable(true);
	}
	
	@FXML
	public void importEvent(ActionEvent event) throws IOException {
		if(currentMapStage != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.initOwner(importMap_Data.getScene().getWindow());
			alert.setResizable(false);
			alert.initModality(Modality.WINDOW_MODAL);
			alert.setTitle("GEOVIEW - Import Confirmation");
			alert.setHeaderText("Confirm New Import");
			alert.setContentText("Importing new data now will begin a new session, closing the map window. Do you wish to import new data?");
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get() == ButtonType.OK) {
				Scene mapScene = currentMapStage.getScene();
				MapView view = (MapView) mapScene.getRoot();
				view.dispose();
				currentMapStage.close();
				FXMLLoader load = new FXMLLoader(getClass().getClassLoader().getResource("import.fxml"));
				importMap_Data.getScene().setRoot((Parent)load.load());
			}
		} else {
			FXMLLoader load = new FXMLLoader(getClass().getClassLoader().getResource("import.fxml"));
			importMap_Data.getScene().setRoot((Parent)load.load());
		}
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
	
	public void initMapStage(Stage currentMapStage) {
		this.currentMapStage = currentMapStage;
	}
	

}
