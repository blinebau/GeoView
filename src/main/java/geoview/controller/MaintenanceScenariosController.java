package geoview.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

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
	private TextField budget;
	
	@FXML
	private Button calculate;
	
	@FXML
	private Button back;
	
	@FXML
	public void initialize() {
		assert(cipYear != null);
		assert(pipeReplacementRadio != null);
		assert(pipeReplacementChoiceBox != null);
		assert(trenchlessRehabRadio != null);
		assert(trenchlessRehabChoiceBox != null);
		assert(budget != null);
		assert(calculate != null);
		assert(back != null);
		
/*		pipeReplacementRadio.setDisable(true);
		pipeReplacementChoiceBox.setDisable(true);
		trenchlessRehabRadio.setDisable(true);
		trenchlessRehabChoiceBox.setDisable(true);
		calculate.setDisable(true);
		budget.setDisable(true);*/
		
		//configureRadioToggleListener();
		configureChoiceBoxListeners();
	} 
	
	@FXML
	private void backEvent(ActionEvent event) throws IOException {
		FXMLLoader load = new FXMLLoader(getClass().getClassLoader().getResource("main_menu.fxml"));
		back.getScene().setRoot((Parent)load.load());
	}
	
/*	@FXML
	private void configureRadioToggleListener() {
		rehabToggle.selectedToggleProperty().addListener((observed, oldVal, newVal) -> {
			if(newVal.equals(pipeReplacementRadio)) {
				
			} else {
				
			}
		});
	}*/
	
	@FXML
	private void configureChoiceBoxListeners() {
		
	}
}
