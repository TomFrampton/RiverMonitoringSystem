package u1171639.rmc.main.java.view.fxml;

import u1171639.rmc.main.java.utils.FXMLViewLoader;
import u1171639.rmc.main.java.view.ViewManager;

public class SensorRegisterViewController extends ViewController {

	public SensorRegisterViewController(ViewManager viewManager) {
		super(viewManager);
		FXMLViewLoader.loadView(this, "sensor_register.fxml");
	}

}
