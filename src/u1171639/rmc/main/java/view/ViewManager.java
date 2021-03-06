package u1171639.rmc.main.java.view;

import u1171639.rmc.main.java.controller.RMCController;
import u1171639.rmc.main.java.view.fxml.ViewController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ViewManager {
	private Stage stage;
	private RMCController rmcController;
	
	private AnchorPane leftPanel;
	private AnchorPane rightPanel;
	
	public void showInLeftPanel(ViewController screen) {
		showInPanel(this.leftPanel, screen.getView());
	}
	
	public void showInRightPanel(ViewController screen) {
		showInPanel(this.rightPanel, screen.getView());
	}
	
	public void clearLeftPanel() {
		this.leftPanel.getChildren().clear();
	}
	
	public void clearRightPanel() {
		this.rightPanel.getChildren().clear();
	}
	
	private void showInPanel(AnchorPane panel, Parent toDisplay) {
		AnchorPane.setTopAnchor(toDisplay, 0.0);
		AnchorPane.setBottomAnchor(toDisplay, 0.0);
		AnchorPane.setLeftAnchor(toDisplay, 0.0);
		AnchorPane.setRightAnchor(toDisplay, 0.0);
		
		panel.getChildren().clear();
		panel.getChildren().add(toDisplay);
	}
	
	public void initStage(Stage stage, int width, int height) {
		this.rightPanel = new AnchorPane();
		this.rightPanel.setMinWidth(width * 0.75);
		this.rightPanel.setMinHeight(height);
		
		this.leftPanel = new AnchorPane();
		this.leftPanel.setMinWidth(width * 0.25);
		this.leftPanel.setMinHeight(height);
		
		this.stage = stage;
		HBox layout = new HBox();
		layout.getChildren().addAll(this.leftPanel, this.rightPanel);
		this.stage.setScene(new Scene(layout, width, height));
		
		this.stage.show();
	}

	public RMCController getRmcController() {
		return this.rmcController;
	}

	public void setRmcController(RMCController rmcController) {
		this.rmcController = rmcController;
	}
}
