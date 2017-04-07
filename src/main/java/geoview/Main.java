package geoview;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static void main(String [] args) {
		Application.launch(Main.class, args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main_menu.fxml"));
			Scene menuScene = new Scene(root);
			primaryStage.setScene(menuScene);
			primaryStage.getIcons().add(new Image("/geoview_logo_temp.png"));
			primaryStage.setTitle("GEOVIEW");
			primaryStage.show();
		} catch (IOException e) {
			System.err.println(e);
		}
	}

}
