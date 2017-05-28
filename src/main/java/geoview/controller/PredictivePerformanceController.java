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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class PredictivePerformanceController {
	
	@FXML
	private ToggleGroup predictiveToggle;
	
	@FXML
	private RadioButton veryHighFailure;
	
	@FXML
	private RadioButton highFailure;
	
	@FXML
	private RadioButton mediumFailure;
	
	@FXML
	private RadioButton lowFailure;
	
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
		assert(predictiveToggle != null);
	}
	
	@FXML
	public void searchEvent(ActionEvent event) {
		if(mapData != null) {
			RadioButton selected = (RadioButton) predictiveToggle.getSelectedToggle();
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
	
	private void search(RadioButton selected) {
		String selectName = selected.getId();
		int lowFailurePeriod = 0;
		int highFailurePeriod = 0;
		String periodContext = "";
		switch(selectName) {
			case "veryHighFailure": {
				lowFailurePeriod = 75;
				highFailurePeriod = 100;
				periodContext = "Failure within 0 - 12 Months";
				break;
			}
			case "highFailure": {
				lowFailurePeriod = 50;
				highFailurePeriod = 75;
				periodContext = "Failure within 1 - 5 Years";
				break;
			}
			case "mediumFailure": {
				lowFailurePeriod = 25;
				highFailurePeriod = 50;
				periodContext = "Failure within 5 - 10 years";
				break;
			}
			case "lowFailure": {
				lowFailurePeriod = 1;
				highFailurePeriod = 25;
				periodContext = "Failure after 10 years";
				break;
			}
		}
		Task<List<Map<String, Object>>> rangeTask = new QueryTask(lowFailurePeriod, highFailurePeriod, "LOF", mapData.getAttrCollection());
		String[] taskDetails = { "Predictive Performance", "\n" + periodContext };
		mapData.prepareTask(rangeTask, taskDetails);
	}
	
	public void initMapData(Stage newMapStage, FeatureData newMapData) {
		mapStage = newMapStage;
		mapData = newMapData;
	}
}
