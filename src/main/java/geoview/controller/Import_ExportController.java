package geoview.controller;


import java.io.File;
import java.io.IOException;
import java.util.Optional;

import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.portal.Portal;
import com.esri.arcgisruntime.portal.PortalItem;

import geoview.data.FeatureData;
import geoview.importers.ImportFileMap;
import geoview.importers.ImportPortalMap;
import geoview.importers.ImportedMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Import_ExportController {
	private static Stage mapStage;
	
	@FXML
	private RadioButton fsRadio;
	
	@FXML
	private RadioButton webRadio;
	
	@FXML
	private ToggleGroup searchToggle;
	
	@FXML
	private Button backButton;
	
	@FXML
	private Button nextButton;
	
	@FXML
	private Button cancelButton;
	
	private FXMLLoader mainLoad;
	
	private FeatureData mapData;
	
	@FXML
	public void initialize() {
		assert(fsRadio != null);
		assert(webRadio != null);
		assert(backButton != null);
		assert(nextButton != null);
		assert(cancelButton != null);
		configureSearchToggleListener();
		mainLoad = new FXMLLoader(getClass().getClassLoader().getResource("main_menu.fxml"));
	}
	
	@FXML
	private void configureSearchToggleListener() {
		searchToggle.selectedToggleProperty().addListener((observed, oldval, newval) -> {
			if(newval.equals(fsRadio)) {
				
			} else {
				
			}
		});
	}
	
	
	@FXML
	public void backEvent(ActionEvent event) throws IOException {
		backButton.getScene().setRoot((Parent)mainLoad.load());
		MainMenuController cntrl = mainLoad.<MainMenuController>getController();
		cntrl.initMapData(mapStage, mapData);
	}
	
	@FXML
	public void nextEvent(ActionEvent event) throws IOException {
		searchToggle.selectedToggleProperty().addListener((observed, oldval, newval) -> {
			FXMLLoader load;
			if(newval.equals(fsRadio)) {
				load = new FXMLLoader(getClass().getClassLoader().getResource("import_file.fxml"));
			} else {
				load = new FXMLLoader(getClass().getClassLoader().getResource("import_web.fxml"));
			}
			try {
				nextButton.getScene().setRoot((Parent)load.load());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	@FXML
	public void cancelEvent(ActionEvent event) throws IOException {
		cancelButton.getScene().setRoot((Parent)mainLoad.load());
		MainMenuController cntrl = mainLoad.<MainMenuController>getController();
		cntrl.initMapData(mapStage, mapData);
	}
	
	
}
