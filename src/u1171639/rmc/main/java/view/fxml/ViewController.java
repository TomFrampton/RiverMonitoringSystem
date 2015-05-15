package u1171639.rmc.main.java.view.fxml;

import java.util.List;

import net.sf.oval.ConstraintViolation;
import u1171639.rmc.main.java.controller.RMCController;
import u1171639.rmc.main.java.view.ViewManager;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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
		this.viewManager.showInLeftPanel(this);
	}
	
	public void showInRightPanel() {
		this.viewManager.showInRightPanel(this);
	}
	
	public void clearLeftPanel() {
		this.viewManager.clearLeftPanel();
	}
	
	public void clearRightPanel() {
		this.viewManager.clearRightPanel();
	}
	
	public RMCController getRMCController() {
		return this.viewManager.getRmcController();
	}
	
	public void showUnspecifiedErrorAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Unspecified Error");
		alert.setHeaderText("Unspecified Error");
		alert.setContentText("An unspecified error has occured.");
		alert.show();
	}
	
	public void showErrorAlert(String header, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(header);
		alert.setHeaderText(header);
		alert.setContentText(message);
		alert.show();
	}
	
	public void showValidationAlert(List<ConstraintViolation> violations) {
		String errors = "";
		
		for(ConstraintViolation violation : violations) {
			String valMsg = violation.getMessage();
			int i = valMsg.indexOf(' ');
			
			String fullFieldName = valMsg.substring(0, i);
			String shortFieldName = fullFieldName.substring(fullFieldName.lastIndexOf('.') + 1);
			
			shortFieldName = shortFieldName.substring(0,1).toUpperCase() + shortFieldName.substring(1);
			
			errors += shortFieldName + valMsg.substring(i) + "\n";
		}
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Validation Errors");
		alert.setHeaderText("Validation Errors");
		alert.setContentText(errors);
		
		alert.show();
		alert.setHeight(175 + ( 25 * violations.size()));
	} 
}
