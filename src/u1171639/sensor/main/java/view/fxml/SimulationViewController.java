package u1171639.sensor.main.java.view.fxml;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import u1171639.sensor.main.java.monitor.SimulatedWaterLevelMonitor;
import u1171639.sensor.main.java.utils.FXMLViewLoader;
import u1171639.sensor.main.java.utils.SensorConfig;

public class SimulationViewController extends ViewController implements Observer {
	
	@FXML private Slider waterLevelSlider;
	
	@FXML private Label sensorNameLabel;
	@FXML private Label zoneNameLabel;
	@FXML private Label localityNameLabel;
	
	@FXML private Label activationLabel;
	@FXML private Label thresholdValueLabel;
	
	private SimulatedWaterLevelMonitor monitor;
	
	public SimulationViewController(SimulatedWaterLevelMonitor monitor) {
		this.monitor = monitor;
		FXMLViewLoader.loadView(this, "simulation.fxml");
		this.monitor.setViewController(this);
		init();
	}
	
	private void init() {
		this.sensorNameLabel.setText(SensorConfig.getSensorName());
		this.zoneNameLabel.setText(SensorConfig.getZone());
		this.localityNameLabel.setText(SensorConfig.getLocality());
		this.thresholdValueLabel.setText(SensorConfig.getWarningThreshold() + "");
		waterLevelsUpdated();
		
		this.waterLevelSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				SimulationViewController.this.monitor.setWaterLevel(newValue.floatValue());
				waterLevelsUpdated();
			}
		});
		
		SensorConfig.observeWarningThreshold(this);
		
		sensorActivated();
	}
	
	public void sensorActivated() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				SimulationViewController.this.activationLabel.setText("Active");
				SimulationViewController.this.activationLabel.setStyle("-fx-text-fill: #22b90f");
			}
		});
		
	}

	public void sensorDeactivated() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				SimulationViewController.this.activationLabel.setText("Deactivated");
				SimulationViewController.this.activationLabel.setStyle("-fx-text-fill: #ff0000e8");
			}
		});
	}
	
	private void waterLevelsUpdated() {
		if(this.waterLevelSlider.getValue() >= SensorConfig.getWarningThreshold()) {
			this.thresholdValueLabel.setStyle("-fx-text-fill: #ff0000e8");
		} else {
			this.thresholdValueLabel.setStyle("-fx-text-fill: #22b90f");
		}
	}

	@Override
	public void update(Observable observable, Object warningThreshold) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				double threshold = (double) warningThreshold;
				SimulationViewController.this.thresholdValueLabel.setText(threshold + "");
				waterLevelsUpdated();
			}
		});
	}
}
