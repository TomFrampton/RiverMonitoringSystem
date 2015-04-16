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
		this.modalStage.setScene(new Scene(this.getView()));
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
		HomeUser user = getRMCController().getHomeUserManager().getUserByUsername(this.usernameField.getText());
		
		if(user != null) {
			this.sensor.registerHomeUser(user);
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
			System.out.println("User not found");
		}
	}
	
	@FXML protected void handleCancelButtonClicked(MouseEvent event) {
		this.clearTextBoxes();
		this.modalStage.close();
	}
}
