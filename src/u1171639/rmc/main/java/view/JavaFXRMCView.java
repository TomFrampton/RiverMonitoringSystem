package u1171639.rmc.main.java.view;

import java.io.File;

import org.controlsfx.control.Notifications;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import u1171639.rmc.main.java.controller.RMCController;
import u1171639.rmc.main.java.model.Alarm;
import u1171639.rmc.main.java.view.fxml.MonitoringViewController;
import u1171639.sensor.main.java.monitor.SimulatedWaterLevelMonitor;
import u1171639.sensor.main.java.view.fxml.SimulationViewController;

public class JavaFXRMCView extends Application implements RMCView {
	private static RMCController controller;
	private static MonitoringViewController homeController;
	
	private static boolean startUpError = false;
	private static String startUpErrorHeader;
	private static String startUpErrorMessage;
	
	@Override
	public void start(RMCController controller) {
		JavaFXRMCView.controller = controller;
		JavaFXRMCView.launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		if(!startUpError) {
			ViewManager viewManager = new ViewManager();
			viewManager.initStage(stage, 1350, 450);
			viewManager.setRmcController(controller);
			
			homeController = new MonitoringViewController(viewManager);
			
			homeController.showInLeftPanel();
			
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle(startUpErrorHeader);
			alert.setHeaderText(startUpErrorHeader);
			alert.setContentText(startUpErrorMessage);
			alert.show();
		}
	}

	@Override
	public void alarmRaised(Alarm alarm) {	
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Notifications.create()
		        .title("Alarm Raised")
		        .text("Alarm Raised in " + alarm.getLocality() + " - " + alarm.getZone())
		        .showError();
				
				updateView();
			}
		});		
	}

	@Override
	public void updateView() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				homeController.updateTreeView();
			}
		});
	}
	
	public static void startUpError(String errorHeader, String errorMessage) {
		JavaFXRMCView.startUpError = true;
		JavaFXRMCView.startUpErrorHeader = errorHeader;
		JavaFXRMCView.startUpErrorMessage = errorMessage;
		Application.launch();
	}
}
