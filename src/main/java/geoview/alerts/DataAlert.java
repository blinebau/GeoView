package geoview.alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Window;

public class DataAlert {
	
	public static Alert setAlert(Window owner, String[] alertText) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.initOwner(owner);
		alert.setResizable(false);
		alert.initModality(Modality.WINDOW_MODAL);
		alert.setTitle(alertText[0]);
		alert.setHeaderText(alertText[1]);
		alert.setContentText(alertText[2]);
		return alert;
	}

}
