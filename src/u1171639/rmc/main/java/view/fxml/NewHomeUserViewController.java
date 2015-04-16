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
import u1171639.rmc.main.java.utils.FXMLViewLoader;
import u1171639.rmc.main.java.view.ViewManager;

public class NewHomeUserViewController extends ViewController {
	@FXML private TextField forenameField;
	@FXML private TextField surnameField;
	@FXML private TextField usernameField;
	
	@FXML private PasswordField passwordField;
	@FXML private PasswordField confirmPasswordField;
	
	@FXML private Stage modalStage = new Stage();
	private Callable<Void> modalCallback;
	
	private RMCSensor sensor;
	
	public NewHomeUserViewController(ViewManager viewManager) {
		super(viewManager);
		FXMLViewLoader.loadView(this, "new_home_user.fxml");
		init();
	}
	
	private void init() {
		this.modalStage.setScene(new Scene(this.getView()));
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
		this.confirmPasswordField.setText("");
	}
	
	@FXML protected void handleAddButtonClicked(MouseEvent event) {
		HomeUser newUser = new HomeUser();
		newUser.setForename(this.forenameField.getText());
		newUser.setSurname(this.surnameField.getText());
		newUser.setEmail(this.usernameField.getText());
		newUser.setPassword(this.passwordField.getText());
		
		int id = getRMCController().getHomeUserManager().addHomeUser(newUser);
		if(id > -1) {
			this.sensor.registerHomeUser(newUser);
			try {
				this.modalCallback.call();
				this.modalCallback = null;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.clearTextBoxes();
			this.modalStage.close();
			
		} else {
			System.out.println("User already exists");
		}
	}
	
	@FXML protected void handleCancelButtonClicked(MouseEvent event) {
		this.clearTextBoxes();
		this.modalStage.close();
	}
}
