package geoview.alerts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProgressAlert {
	private final Stage progressStage;
	private final ProgressIndicator progressIndicator = new ProgressIndicator(0);
	private final FXMLLoader load = new FXMLLoader(getClass().getClassLoader().getResource("progress.fxml"));
	
	public ProgressAlert() {
		progressStage = new Stage();
		progressStage.initStyle(StageStyle.UTILITY);
		progressStage.initModality(Modality.APPLICATION_MODAL);
		progressStage.setResizable(false);
		setScene();
	}
	
	private void setScene() {
		try {
		Scene progressScene = new Scene((Parent)load.load());
		progressStage.setScene(progressScene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void bindProgress(Task<ArrayList<Map<String, Object>>> task) {
		progressIndicator.progressProperty().bind(task.progressProperty());
	}
	
	public Stage getProgressStage() {
		return progressStage;
	}
	
	public void show() {
		progressStage.show();
	}
	
	public void close() {
		progressStage.close();
	}
	
	public double getProgress() {
		return progressIndicator.getProgress();
	}

}
