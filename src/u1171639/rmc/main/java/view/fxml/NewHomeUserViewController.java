package u1171639.rmc.main.java.view.fxml;

import java.util.concurrent.Callable;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
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
import u1171639.shared.main.java.exception.ValidationException;
import u1171639.shared.main.java.utils.ValidationUtils;

public class NewHomeUserViewController extends ViewController {
	@FXML private TextField forenameField;
	@FXML private TextField surnameField;
	@FXML private TextField usernameField;
	
	@FXML private PasswordField passwordField;
	
	@FXML private Stage modalStage = new Stage();
	private Callable<Void> modalCallback;
	
	private RMCSensor sensor;
	
	public NewHomeUserViewController(ViewManager viewManager) {
		super(viewManager);
		FXMLViewLoader.loadView(this, "new_home_user.fxml");
		init();
	}
	
	private void init() {
		this.modalStage.setScene(new Scene(getView()));
		this.modalStage.setTitle("Add Home User");
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
		this.forenameField.setText("");
		this.surnameField.setText("");
		this.usernameField.setText("");
		this.passwordField.setText("");
	}
	
	@FXML protected void handleAddButtonClicked(MouseEvent event) {
		HomeUser newUser = new HomeUser();
		newUser.setForename(this.forenameField.getText());
		newUser.setSurname(this.surnameField.getText());
		newUser.setUsername(this.usernameField.getText());
		newUser.setPassword(this.passwordField.getText());
		
		try {
			ValidationUtils.validate(newUser);
			
			HomeUserManager manager = getRMCController().getHomeUserManager();
			
			int id = manager.addHomeUser(newUser);
			
			if(id > -1) {
				manager.registerUserWithSensor(id, this.sensor);
				
				try {
					this.modalCallback.call();
					this.modalCallback = null;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				clearTextBoxes();
				this.modalStage.close();
				
			} else {
				System.out.println("User already exists");
			}
		} catch (ValidationException e) {
			showValidationAlert(e.getViolations());
		} catch (RegistrationException e) {
			showErrorAlert("User Already Registered", "The specified user is already registered with that sensor.");
		}
	}
	
	@FXML protected void handleCancelButtonClicked(MouseEvent event) {
		clearTextBoxes();
		this.modalStage.close();
	}
}
