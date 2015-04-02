package u1171639.sensor.main.java.view;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import u1171639.sensor.main.java.monitor.SimulatedWaterLevelMonitor;
import u1171639.sensor.main.java.view.fxml.SimulationViewController;

public class JavaFXSimulationView extends Application implements SimulationView {

	private static SimulatedWaterLevelMonitor monitor;
	
	@Override
	public void start(SimulatedWaterLevelMonitor monitor) {
		JavaFXSimulationView.monitor = monitor;
		JavaFXSimulationView.launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		SimulationViewController simulationView = new SimulationViewController(monitor);
		Scene scene = new Scene(simulationView.getView(), 1200, 800);
		
		// Load CSS
		// Load all CSS files
//		File cssDirectory = new File("./src/u1171639/main/resources/styles/");
//		for(File cssFile : cssDirectory.listFiles()) {
//			String css = this.getClass().getResource("/u1171639/main/resources/styles/" + cssFile.getName()).toExternalForm();
//			scene.getStylesheets().add(css);
//		}
		
		stage.setScene(scene);
		stage.show();
		
	}

}
