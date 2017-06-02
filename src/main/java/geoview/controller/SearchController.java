package geoview.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

public class SearchController {

	@FXML
	private TextField sciFrom;
	
	@FXML
	private TextField sciTo;
	
	@FXML
	private RadioButton sci;
	
	@FXML 
	private ToggleGroup searchToggle;
	
	@FXML
	private RadioButton predictivePerformance;
	
	@FXML
	private ChoiceBox<String> failureChoice;
	
	@FXML
	private Button back;
	
	@FXML
	private Button search;

	private Stage mapStage;

	private FeatureData mapData;
	
	@FXML
	public void initialize() {
		assert(back != null);
		assert(search != null);
		assert(searchToggle != null);
		sci.setSelected(true);
		configureSelectionListeners();
	}
	
	@FXML
	public void searchEvent(ActionEvent event) {
		if(mapData != null) {
			RadioButton selected = (RadioButton) searchToggle.getSelectedToggle();
			search(selected);
		} else {
			String[] alertText = { "GeoView - Invalid Data", "No valid data has been imported.", "Please import a valid data set before searching." };
			Alert emptyData = DataAlert.setAlert(search.getScene().getWindow(), alertText);
			emptyData.showAndWait();
		} 
	}
	
	@FXML
	public void backEvent(ActionEvent event) throws IOException {
		FXMLLoader mainLoad = new FXMLLoader(getClass().getClassLoader().getResource("main_menu.fxml"));
		back.getScene().setRoot((Parent)mainLoad.load());
		MainMenuController cntrl = mainLoad.<MainMenuController>getController();
		cntrl.initMapData(mapStage, mapData);
	}
	
	private void configureSelectionListeners() {
		searchToggle.selectedToggleProperty().addListener((observed, oldval, newval) -> {
			if(newval.equals(sci)) {
				failureChoice.getSelectionModel().clearSelection();
			} else {
				sciFrom.setText("");
				sciTo.setText("");
			}
		});
	}
	
	private void search(RadioButton selected) {
		Task<List<Map<String, Object>>> searchTask;
		List<String> taskDetails;
		if(selected.equals(sci)) {
			String from = sciFrom.getText();
			String to = sciTo.getText();
			searchTask = new QueryTask(Integer.parseInt(from),  Integer.parseInt(to), "SCI", mapData.getAttrCollection());
			taskDetails = new ArrayList<String>(Arrays.asList("Sewer Condition Index", "\nRange: ", from, to));
		} else {
			String failurePeriod = failureChoice.getSelectionModel().getSelectedItem();
			int choiceIndex = failureChoice.getSelectionModel().getSelectedIndex();
			int lowFailurePeriod = 0;
			int highFailurePeriod = 0;
			switch(choiceIndex) {
				case 0: {
					lowFailurePeriod = 75;
					highFailurePeriod = 100;
					break;
				}
				case 1: {
					lowFailurePeriod = 50;
					highFailurePeriod = 75;
					break;
				}
				case 2: {
					lowFailurePeriod = 25;
					highFailurePeriod = 50;
					break;
				}
				case 3: {
					lowFailurePeriod = 1;
					highFailurePeriod = 25;
					break;
				}
			}
			System.out.println(failurePeriod + " : " + choiceIndex); 
			searchTask = new QueryTask(lowFailurePeriod, highFailurePeriod, "LOF", mapData.getAttrCollection());
			taskDetails = new ArrayList<String>(Arrays.asList("Predictive Performance", "\n" + failurePeriod));
		}
		mapData.prepareTask(searchTask, taskDetails);
	}
	
	public void initMapData(Stage newMapStage, FeatureData newMapData) {
		mapStage = newMapStage;
		mapData = newMapData;
	}
}
