package u1171639.sensor.main.java.view.fxml;

import javafx.scene.Parent;

public abstract class ViewController {
	private Parent view;

	public Parent getView() {
		return this.view;
	}
	
	public void setView(Parent view) {
		this.view = view;
	}
}
