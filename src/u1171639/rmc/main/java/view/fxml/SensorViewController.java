package u1171639.rmc.main.java.view.fxml;

import com.sun.prism.paint.Color;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import u1171639.rmc.main.java.model.Locality;
import u1171639.rmc.main.java.model.RMCSensor;
import u1171639.rmc.main.java.utils.FXMLViewLoader;
import u1171639.rmc.main.java.view.ViewManager;

public class SensorViewController extends ViewController {

	@FXML private Label localityLabel;
	@FXML private Label zoneLabel;
	
	@FXML private Button sensorThresholdUpdateButton;
	@FXML private Button activationButton;
	
	@FXML private Slider thresholdSlider;
	
	@FXML private Label currentStateLabel;
	
	private RMCSensor sensor;
	
	public SensorViewController(ViewManager viewManager) {
		super(viewManager);
		FXMLViewLoader.loadView(this, "sensor.fxml");
		init();
	}
	
	private void init() {
		this.thresholdSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				sensorThresholdUpdateButton.setDisable(false);
			}
		});
	}
	
	@FXML protected void handleUpdateSensorThresholdClicked(MouseEvent event) {
		Locality locality = this.getRMCController().getLocalityByName(this.sensor.getLocalityName());
		
		if(locality.setWarningThreshold(this.sensor.getZoneName(), this.sensor.getName(), Math.round(this.thresholdSlider.getValue()))) {
			this.sensorThresholdUpdateButton.setDisable(true);
		}
	}
	
	@FXML protected void handleActivationButtonClicked(MouseEvent event) {
		Locality locality = this.getRMCController().getLocalityByName(this.sensor.getLocalityName());
		
		if(this.sensor.isActive()) {
			if(locality.deactivateSensor(this.sensor.getZoneName(), this.sensor.getName())) {
				this.sensorDeactivated();
			} else {
				
			}
		} else {
			if(locality.activateSensor(this.sensor.getZoneName(), this.sensor.getName())) {
				this.sensorActivated();
			} else {
				
			}
		}
	}
	
	public void setSensor(RMCSensor sensor) {
		this.sensor = sensor;
		
		this.localityLabel.setText(sensor.getLocalityName());
		this.zoneLabel.setText(sensor.getZoneName());
		
		this.thresholdSlider.valueProperty().set(sensor.getThreshold());
		this.sensorThresholdUpdateButton.setDisable(true);
		
		if(sensor.isActive()) {
			this.sensorActivated();
		} else {
			this.sensorDeactivated();
		}
	}
	
	private void sensorActivated() {
		this.currentStateLabel.setText("Active");
		this.currentStateLabel.setStyle("-fx-text-fill: #22b90f");
		this.activationButton.setText("Deactivate");
		this.sensor.setActive(true);
	}

	private void sensorDeactivated() {
		this.currentStateLabel.setText("Deactivated");
		this.currentStateLabel.setStyle("-fx-text-fill: #ff0000e8");
		this.activationButton.setText("Activate");
		this.sensor.setActive(false);
	}

}
