package u1171639.sensor.main.java.view;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import u1171639.sensor.main.java.monitor.SimulatedWaterLevelMonitor;

public class JavaFXSimulationView extends Application implements SimulationView {

	@Override
	public void start(SimulatedWaterLevelMonitor monitor) {
		JavaFXSimulationView.launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Scene scene = new Scene(new VBox(), 1200, 800);
		
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
