package geoview.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import geoview.alerts.DataAlert;
import geoview.data.FeatureData;
import geoview.data.PlanTask;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class MaintenanceScenariosController {

	@FXML
	private ChoiceBox<String> cipYear;
	
	@FXML
	private RadioButton pipeReplacementRadio;
	
	@FXML
	private ChoiceBox<String> pipeReplacementChoiceBox;
	
	@FXML
	private RadioButton trenchlessRehabRadio;
	
	@FXML
	private ChoiceBox<String> trenchlessRehabChoiceBox;
	
	@FXML
	private ToggleGroup rehabToggle;
	
	@FXML
	private TextField budgetField;
	
	@FXML
	private Button generate;
	
	@FXML
	private Button back;

	private Stage mapStage;

	private FeatureData mapData;
	
	@FXML
	public void initialize() {
		assert(cipYear != null);
		assert(pipeReplacementRadio != null);
		assert(pipeReplacementChoiceBox != null);
		assert(trenchlessRehabRadio != null);
		assert(trenchlessRehabChoiceBox != null);
		assert(budgetField != null);
		assert(generate != null);
		assert(back != null);
		assert(rehabToggle != null);
		pipeReplacementRadio.setSelected(true);
		configureSelectionListeners();
	} 
	
	@FXML
	private void backEvent(ActionEvent event) throws IOException {
		FXMLLoader mainLoad = new FXMLLoader(getClass().getClassLoader().getResource("main_menu.fxml"));
		back.getScene().setRoot((Parent)mainLoad.load());
		MainMenuController cntrl = mainLoad.<MainMenuController>getController();
		cntrl.initMapData(mapStage, mapData);
	}
	
	@FXML
	private void generatePlanEvent(ActionEvent event) {
		if(mapData != null) {
			RadioButton selected = (RadioButton) rehabToggle.selectedToggleProperty().getValue();
			generatePlan(selected);
		} else {
			String[] alertText = { "GeoView - Invalid Data", "No valid data has been imported.", "Please import a valid data set before searching." };
			Alert emptyData = DataAlert.setAlert(generate.getScene().getWindow(), alertText);
			emptyData.showAndWait();
		}
	}
	
	private void generatePlan(RadioButton selected) {
		String year = cipYear.getSelectionModel().getSelectedItem();
		String method = "";
		if(selected.equals(pipeReplacementRadio)) {
			method = pipeReplacementChoiceBox.getSelectionModel().getSelectedItem();
		} else {
			method = trenchlessRehabChoiceBox.getSelectionModel().getSelectedItem();
		}
		String budget = budgetField.getText();
		Task<List<Map<String, Object>>> planTask = new PlanTask(Integer.parseInt(year), method, Double.parseDouble(budget), mapData.getAttrCollection());
		List<String> taskDetails = new ArrayList<>(Arrays.asList("Maintenance Plan", year, method, budget));
		mapData.prepareTask(planTask, taskDetails);
	}
	
	private void configureSelectionListeners() {
		rehabToggle.selectedToggleProperty().addListener((observed, oldval, newval) -> {
			if(newval.equals(pipeReplacementRadio)) {
				trenchlessRehabChoiceBox.getSelectionModel().clearSelection();
			} else {
				pipeReplacementChoiceBox.getSelectionModel().clearSelection();
			}
		});
	}

	public void initMapData(Stage newMapStage, FeatureData newMapData) {
		mapStage = newMapStage;
		mapData = newMapData;
	}
}
