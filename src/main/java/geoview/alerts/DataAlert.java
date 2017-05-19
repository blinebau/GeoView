package geoview.alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Window;

public class DataAlert {
	
	public static Alert setAlert(Window owner, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.initOwner(owner);
		alert.setResizable(false);
		alert.initModality(Modality.WINDOW_MODAL);
		alert.setTitle("GEOVIEW - Invalid Data");
		alert.setHeaderText("An error occurred while importing data.");
		alert.setContentText(message);
		return alert;
	}

}
