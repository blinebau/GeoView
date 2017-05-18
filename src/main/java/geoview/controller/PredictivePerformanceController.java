package geoview.controller;

import java.io.IOException;

import geoview.data.FeatureData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
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
	private Button backButton;
	
	@FXML
	private Button findButton;

	private Stage mapStage;

	private FeatureData mapData;
	
	@FXML
	public void initialize() {
		assert(backButton != null);
		assert(findButton != null);
		assert(predictiveToggle != null);
	}
	
	@FXML
	public void findEvent(ActionEvent event) {
		RadioButton selected = (RadioButton) predictiveToggle.getSelectedToggle();
		if(predictiveToggle.getSelectedToggle() != null) {
			search(selected);
		}
	}
	
	@FXML
	public void backEvent(ActionEvent event) throws IOException {
		FXMLLoader load = new FXMLLoader(getClass().getClassLoader().getResource("main_menu.fxml"));
		backButton.getScene().setRoot((Parent)load.load());
	}
	
	private void search(RadioButton selected) {
		String selectName = selected.getId();
		int lowFailurePeriod = 0;
		int highFailurePeriod = 0;
		switch(selectName) {
			case "veryHighFailure": {
				lowFailurePeriod = 75;
				highFailurePeriod = 100;
				break;
			}
			case "highFailure": {
				lowFailurePeriod = 50;
				highFailurePeriod = 75;
				break;
			}
			case "mediumFailure": {
				lowFailurePeriod = 25;
				highFailurePeriod = 50;
			}
			case "lowFailure": {
				lowFailurePeriod = 1;
				highFailurePeriod = 25;
			}
		}
		mapData.initiateRangeQueryTask(lowFailurePeriod, highFailurePeriod, "LOF");
	}
	
	public void initMapData(Stage newMapStage, FeatureData newMapData) {
		mapStage = newMapStage;
		mapData = newMapData;
	}
}
