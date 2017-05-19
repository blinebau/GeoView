package geoview.alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Window;

public class ImportAlert {
	
	public static Alert setAlert(Window owner) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(owner);
		alert.setResizable(false);
		alert.initModality(Modality.WINDOW_MODAL);
		alert.setTitle("GEOVIEW - Import Confirmation");
		alert.setHeaderText("Confirm New Import");
		alert.setContentText("Importing new data now will begin a new session, closing the map window. Do you wish to import new data?");
		return alert;
	}

}
