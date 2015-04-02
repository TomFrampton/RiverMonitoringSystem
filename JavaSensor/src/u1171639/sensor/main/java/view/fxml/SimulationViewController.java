package u1171639.sensor.main.java.view.fxml;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import u1171639.sensor.main.java.monitor.SimulatedWaterLevelMonitor;
import u1171639.sensor.main.java.utils.FXMLViewLoader;

public class SimulationViewController extends ViewController {
	
	@FXML private Slider waterLevelSlider;
	
	private SimulatedWaterLevelMonitor monitor;
	
	public SimulationViewController(SimulatedWaterLevelMonitor monitor) {
		this.monitor = monitor;
		FXMLViewLoader.loadView(this, "simulation.fxml");
		init();
	}
	
	private void init() {
		this.waterLevelSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				monitor.setWaterLevel(newValue.floatValue());
			}
		});
	}

}
