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

public class SCIController {
	
	@FXML
	private TextField sciFrom;
	
	@FXML
	private TextField sciTo;
	
	@FXML
	private TextField icgFrom;
	
	@FXML
	private TextField icgTo;
	
	@FXML
	private RadioButton sci;
	
	@FXML
	private RadioButton icg;
	
	@FXML 
	private ToggleGroup searchToggle;
	
	@FXML
	private RadioButton defect;
	
	@FXML
	private ChoiceBox<String> defectChoice;
	
	@FXML
	private Button search;
	
	@FXML
	private ChoiceBox<String> detailChoice;
	
	@FXML
	private Button exportGraphics;
	
	@FXML
	private Button printPreview;
	
	@FXML
	private Button exportTable;
	
	@FXML
	private Button dataView;
	
	@FXML
	private Button print;
	
	@FXML
	private Button back;
	
	@FXML
	public void initialize() {
		assert(sciFrom != null);
		assert(sciTo != null);
		assert(icgTo != null);
		assert(icgFrom != null);
		assert(sci != null);
		assert(icg != null);
		assert(defect != null);
		assert(defectChoice != null);
		assert(search != null);
		assert(detailChoice != null);
		assert(exportGraphics != null);
		assert(printPreview != null);
		assert(exportTable != null);
		assert(dataView != null);
		assert(print != null);
		assert(back != null);
		search.setDisable(true);
		exportGraphics.setDisable(true);
		exportTable.setDisable(true);
		printPreview.setDisable(true);
		dataView.setDisable(true);
		print.setDisable(true);
		defectChoice.setDisable(true);
		configureSearchToggleListener();
		configureFieldEntryListeners();
	}
	
	@FXML
	private void backEvent(ActionEvent event) throws IOException {
		FXMLLoader load = new FXMLLoader(getClass().getClassLoader().getResource("main_menu.fxml"));
		back.getScene().setRoot((Parent)load.load());
	}
	
	private void configureSearchToggleListener() {
		searchToggle.selectedToggleProperty().addListener((observed, oldVal, newVal) -> {
			if(newVal.equals(sci) || newVal.equals(icg)) {
				defectChoice.getSelectionModel().clearSelection();
				defectChoice.setDisable(true);
				search.setDisable(true);
				if(newVal.equals(sci)) {
					icgFrom.textProperty().setValue("");
					icgTo.textProperty().setValue("");
				} else {
					sciFrom.textProperty().setValue("");
					sciTo.textProperty().setValue("");
				}
			} else {
				sciFrom.textProperty().setValue("");
				sciTo.textProperty().setValue("");
				icgFrom.textProperty().setValue("");
				icgTo.textProperty().setValue("");
				defectChoice.setDisable(false);
				search.setDisable(true);
			}
		});
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
		icgFrom.textProperty().addListener((observed, oldVal, newVal) -> {
			if(icg.isSelected()) {
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
}
