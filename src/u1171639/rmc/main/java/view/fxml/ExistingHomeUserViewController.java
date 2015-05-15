package u1171639.rmc.main.java.view.fxml;

import java.util.concurrent.Callable;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import u1171639.rmc.main.java.model.RMCSensor;
import u1171639.rmc.main.java.users.HomeUser;
import u1171639.rmc.main.java.users.HomeUserManager;
import u1171639.rmc.main.java.utils.FXMLViewLoader;
import u1171639.rmc.main.java.view.ViewManager;
import u1171639.shared.main.java.exception.RegistrationException;

public class ExistingHomeUserViewController extends ViewController {
	@FXML private TextField usernameField;
	@FXML private Stage modalStage = new Stage();
	
	private Callable<Void> modalCallback;
	
	private RMCSensor sensor;
	
	public ExistingHomeUserViewController(ViewManager viewManager) {
		super(viewManager);
		FXMLViewLoader.loadView(this, "existing_home_user.fxml");
		init();
	}
	
	private void init() {
		this.modalStage.setScene(new Scene(getView()));
		this.modalStage.setTitle("Register Existing Home User");
		this.modalStage.initModality(Modality.WINDOW_MODAL);
	}
	
	public void setSensor(RMCSensor sensor) {
		this.sensor = sensor;
	}
	
	public void showInModal(Callable<Void> callback) {
		this.modalCallback = callback;
		this.modalStage.show();
	}
	
	private void clearTextBoxes() {
		this.usernameField.setText("");
	}
	
	@FXML protected void handleRegisterButtonClicked(MouseEvent event) {
		HomeUserManager manager = getRMCController().getHomeUserManager();
		HomeUser user = manager.getUserByUsername(this.usernameField.getText());
		
		if(user != null) {
			try {
				manager.registerUserWithSensor(user.getId(), this.sensor);
			} catch (RegistrationException e) {
				showErrorAlert("User Already Registered", "The specified user is already registered with that sensor.");
			}
			
			try {
				this.modalCallback.call();
				this.modalCallback = null;
			} catch (Exception e) {
				showUnspecifiedErrorAlert();
			}
			
			clearTextBoxes();
			this.modalStage.close();
			
		} else {
			showErrorAlert("User Not Found", "The user with that username was not found in the system.");
		}
	}
	
	@FXML protected void handleCancelButtonClicked(MouseEvent event) {
		clearTextBoxes();
		this.modalStage.close();
	}
}
