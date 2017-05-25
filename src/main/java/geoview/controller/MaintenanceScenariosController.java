package geoview.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import geoview.data.FeatureData;
import geoview.data.PlanTask;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
	private Button calculate;
	
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
		assert(calculate != null);
		assert(back != null);
		pipeReplacementRadio.setSelected(true);
	} 
	
	@FXML
	private void backEvent(ActionEvent event) throws IOException {
		FXMLLoader load = new FXMLLoader(getClass().getClassLoader().getResource("main_menu.fxml"));
		back.getScene().setRoot((Parent)load.load());
	}
	
	@FXML
	private void generatePlanEvent(ActionEvent event) {
		String year = cipYear.getSelectionModel().getSelectedItem();
		RadioButton selected = (RadioButton) rehabToggle.selectedToggleProperty().getValue();
		String method = "";
		if(selected.equals(pipeReplacementRadio)) {
			method = pipeReplacementChoiceBox.getSelectionModel().getSelectedItem();
		} else {
			method = trenchlessRehabChoiceBox.getSelectionModel().getSelectedItem();
		}
		String budget = budgetField.getText();
		Task<List<Map<String, Object>>> planTask = new PlanTask(Integer.parseInt(year), method, Double.parseDouble(budget));
		String[] taskDetails = { "Maintenance Plan", year, method, budget };
		mapData.initiateTask(planTask, taskDetails);
	}

	public void initMapData(Stage newMapStage, FeatureData newMapData) {
		mapStage = newMapStage;
		mapData = newMapData;
	}
}
