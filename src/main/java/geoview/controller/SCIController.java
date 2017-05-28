package geoview.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import geoview.alerts.DataAlert;
import geoview.data.FeatureData;
import geoview.data.QueryTask;
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

public class SCIController {
	
	@FXML
	private TextField sciFrom;
	
	@FXML
	private TextField sciTo;
	
	@FXML
	private RadioButton sci;
	
	@FXML 
	private ToggleGroup searchToggle;
	
	@FXML
	private RadioButton defect;
	
	@FXML
	private ChoiceBox<String> defectChoice;
	
	@FXML
	private Button search;
	
	@FXML
	private Button back;
	
	private FeatureData mapData;

	private Stage mapStage;
	
	@FXML
	public void initialize() {
		assert(sciFrom != null);
		assert(sciTo != null);
		assert(sci != null);
		assert(defect != null);
		assert(defectChoice != null);
		assert(search != null);
		assert(back != null);
		search.setDisable(true);
		defectChoice.setDisable(true);
		configureFieldEntryListeners();
	}
	
	@FXML
	private void backEvent(ActionEvent event) throws IOException {
		FXMLLoader mainLoad = new FXMLLoader(getClass().getClassLoader().getResource("main_menu.fxml"));
		back.getScene().setRoot((Parent)mainLoad.load());
		MainMenuController cntrl = mainLoad.<MainMenuController>getController();
		cntrl.initMapData(mapStage, mapData);
	}
	
	@FXML
	private void searchEvent(ActionEvent event) {
		if(mapData != null) {
			RadioButton selected = (RadioButton) searchToggle.selectedToggleProperty().getValue();
			search(selected);
		} else {
			String[] alertText = { "GeoView - Invalid Data", "No valid data has been imported.", "Please import a valid data set before searching." };
			Alert emptyData = DataAlert.setAlert(search.getScene().getWindow(), alertText);
			emptyData.showAndWait();
		}
	}
	
	private void search(RadioButton selected) {
		Task<List<Map<String, Object>>> rangeTask;
		if(selected.equals(sci)) {
			String from = sciFrom.getText();
			String to = sciTo.getText();
			rangeTask = new QueryTask(Integer.parseInt(from),  Integer.parseInt(to), "SCI", mapData.getAttrCollection());
			String[] taskDetails = { "Sewer Condition Index", "\nRange: ", from, to };
			mapData.prepareTask(rangeTask, taskDetails);
		} else {
			//defect
		}
	}
	
	private void configureFieldEntryListeners() {
		sciFrom.textProperty().addListener((observed, oldVal, newVal) -> {
			if(sci.isSelected()) {
				if(newVal.equals("")) {
					search.setDisable(true);
				} else {
					search.setDisable(false);
				}
			}
		});
		defectChoice.getSelectionModel().selectedItemProperty().addListener((observer, oldVal, newVal) -> {
			if(defect.isSelected()) {
				if(!newVal.isEmpty()) {
					search.setDisable(false);
				}
			}
		});
	}
	
	public void initMapData(Stage newMapStage, FeatureData newMapData) {
		mapStage = newMapStage;
		mapData = newMapData;
	}
}
