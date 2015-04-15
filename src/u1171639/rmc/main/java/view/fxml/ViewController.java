package u1171639.rmc.main.java.view.fxml;

import u1171639.rmc.main.java.controller.RMCController;
import u1171639.rmc.main.java.view.ViewManager;
import javafx.scene.Parent;
import javafx.stage.Stage;

public abstract class ViewController {
	private Parent view;
	private ViewManager viewManager;
	
	public ViewController(ViewManager viewManager) {
		this.viewManager = viewManager;
	}
	
	public Parent getView() {
		return this.view;
	}
	
	public void setView(Parent view) {
		this.view = view;
	}
	
	public void showInLeftPanel() {
		viewManager.showInLeftPanel(this);
	}
	
	public void showInCentrePanel() {
		viewManager.showInCentrePanel(this);
	}
	
	public void showInRightPanel() {
		viewManager.showInRightPanel(this);
	}
	
	public void clearLeftPanel() {
		viewManager.clearLeftPanel();
	}
	
	public void clearCentrePanel() {
		viewManager.clearCentrePanel();
	}
	
	public void clearRightPanel() {
		viewManager.clearRightPanel();
	}
	
	public RMCController getRMCController() {
		return this.viewManager.getRmcController();
	}
}
