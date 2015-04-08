package u1171639.rmc.main.java.view;

import u1171639.rmc.main.java.controller.RMCController;
import u1171639.rmc.main.java.view.fxml.MonitoringViewController;
import u1171639.rmc.main.java.view.fxml.ViewController;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewManager {
	private Stage stage;
	private RMCController rmcController;

	public void showScreen(ViewController screen) {
		this.stage.setScene(new Scene(screen.getView(), 1200, 600));
		this.stage.show();
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public RMCController getRmcController() {
		return rmcController;
	}

	public void setRmcController(RMCController rmcController) {
		this.rmcController = rmcController;
	}
}
