package u1171639.sensor.main.java.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.oval.ConstraintViolation;
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
	private static List<String> startUpErrorMessages;
	
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
			String errorString = "";
			
			for(String errorMessage : startUpErrorMessages) {
				errorString += errorMessage + "\n";
			}
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle(startUpErrorHeader);
			alert.setHeaderText(startUpErrorHeader);
			alert.setContentText(errorString);
			
			alert.show();
			alert.setHeight(175 + ( 25 * startUpErrorMessages.size()));
		}
	}
	
	public static void startUpError(String errorHeader, String error) {
		List<String> errors = new ArrayList<String>();
		errors.add(error);
		
		JavaFXSimulationView.startUpErrors(errorHeader, errors);
	}
	
	public static void startUpErrors(String errorHeader, List<String> errors) {
		JavaFXSimulationView.startUpError = true;
		JavaFXSimulationView.startUpErrorHeader = errorHeader;
		JavaFXSimulationView.startUpErrorMessages = errors;
		Application.launch();
	} 
}
