package geoview.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class PredictivePerformanceController {
	
	@FXML
	private HBox buttonBox;
	
	@FXML
	private ToggleGroup predictiveToggle;
	
	@FXML
	private Button dataViewButton;
	
	@FXML
	private Button printPreviewButton;
	
	@FXML
	private Button exportTableButton;
	
	@FXML
	private Button printButton;
	
	@FXML
	private Button exportGraphicsButton;
	
	@FXML
	private Button backButton;
	
	@FXML
	private Button findButton;
	
	@FXML
	public void initialize() {
		assert(buttonBox != null);
		assert(dataViewButton != null);
		assert(printPreviewButton != null);
		assert(exportTableButton != null);
		assert(printButton != null);
		assert(exportGraphicsButton != null);
		assert(backButton != null);
		assert(findButton != null);
		dataViewButton.setDisable(true);
		printPreviewButton.setDisable(true);
		exportTableButton.setDisable(true);
		printButton.setDisable(true);
		exportGraphicsButton.setDisable(true);
	}
	
	@FXML
	public void findEvent(ActionEvent event) {
		if(predictiveToggle.getSelectedToggle() != null) {
			dataViewButton.setDisable(false);
			printPreviewButton.setDisable(false);
			exportTableButton.setDisable(false);
			printButton.setDisable(false);
			exportGraphicsButton.setDisable(false);
		}
	}
	
	@FXML
	public void backEvent(ActionEvent event) throws IOException {
		FXMLLoader load = new FXMLLoader(getClass().getClassLoader().getResource("main_menu.fxml"));
		backButton.getScene().setRoot((Parent)load.load());
	}
	
	

}
