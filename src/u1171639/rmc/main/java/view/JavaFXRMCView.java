package u1171639.rmc.main.java.view;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import u1171639.rmc.main.java.controller.RMCController;
import u1171639.rmc.main.java.view.fxml.MonitoringViewController;
import u1171639.sensor.main.java.monitor.SimulatedWaterLevelMonitor;
import u1171639.sensor.main.java.view.fxml.SimulationViewController;

public class JavaFXRMCView extends Application implements RMCView {

	private static RMCController controller;
	
	@Override
	public void start(RMCController controller) {
		JavaFXRMCView.controller = controller;
		JavaFXRMCView.launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		ViewManager viewManager = new ViewManager();
		viewManager.setStage(stage);
		viewManager.setRmcController(controller);
		
		MonitoringViewController monitoringView = new MonitoringViewController(viewManager);
		monitoringView.show();
		
		// Load CSS
		// Load all CSS files
//		File cssDirectory = new File("./src/u1171639/main/resources/styles/");
//		for(File cssFile : cssDirectory.listFiles()) {
//			String css = this.getClass().getResource("/u1171639/main/resources/styles/" + cssFile.getName()).toExternalForm();
//			scene.getStylesheets().add(css);
//		}
		
	}

}
