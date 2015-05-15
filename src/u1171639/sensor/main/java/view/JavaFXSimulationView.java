package u1171639.sensor.main.java.view;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import u1171639.rmc.main.java.view.JavaFXRMCView;
import u1171639.sensor.main.java.monitor.SimulatedWaterLevelMonitor;
import u1171639.sensor.main.java.view.fxml.SimulationViewController;

public class JavaFXSimulationView extends Application implements SimulationView {
	private static SimulatedWaterLevelMonitor monitor;
	
	private static boolean startUpError;
	private static String startUpErrorHeader;
	private static String startUpErrorMessage;
	
	@Override
	public void start(SimulatedWaterLevelMonitor monitor) {
		JavaFXSimulationView.monitor = monitor;
		JavaFXSimulationView.launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		if(!startUpError) {
			SimulationViewController simulationView = new SimulationViewController(monitor);
			Scene scene = new Scene(simulationView.getView(), 320, 400);
			
			stage.setScene(scene);
			stage.show();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle(startUpErrorHeader);
			alert.setHeaderText(startUpErrorHeader);
			alert.setContentText(startUpErrorMessage);
			alert.show();
		}
	}
	
	public static void startUpError(String errorHeader, String errorMessage) {
		JavaFXSimulationView.startUpError = true;
		JavaFXSimulationView.startUpErrorHeader = errorHeader;
		JavaFXSimulationView.startUpErrorMessage = errorMessage;
		Application.launch();
	}
}
