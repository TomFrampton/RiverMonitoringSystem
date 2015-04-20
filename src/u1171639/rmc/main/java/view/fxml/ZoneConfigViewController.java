package u1171639.rmc.main.java.view.fxml;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import u1171639.rmc.main.java.model.Locality;
import u1171639.rmc.main.java.model.RMCZone;
import u1171639.rmc.main.java.utils.FXMLViewLoader;
import u1171639.rmc.main.java.view.ViewManager;

public class ZoneConfigViewController extends ViewController {

	@FXML private Label zoneNameLabel;
	@FXML private Label localityNameLabel;
	@FXML private Label alarmStatusLabel;
	@FXML private Button resetAlarmButton;
	
	private RMCZone zone;
	
	public ZoneConfigViewController(ViewManager viewManager) {
		super(viewManager);
		FXMLViewLoader.loadView(this, "zone_config.fxml");
		init();
	}
	
	private void init() {
		
	}
	
	@FXML protected void handleResetAlarmButtonClicked(MouseEvent event) {
		Locality locality = getRMCController().getLocalityByName(this.zone.getLocalityName());
		
		if(locality.resetAlarm(this.zone.getName())) {
			this.alarmReset();
		}
	}
	
	public void setZone(RMCZone zone) {
		this.zone = zone;
		
		this.zoneNameLabel.setText(zone.getName());
		this.localityNameLabel.setText(zone.getLocalityName());
		
		if(this.zone.isAlarmRaised()) {
			this.alarmRaised();
		} else {
			this.alarmReset();
		}
	}
	
	private void alarmRaised() {
		this.alarmStatusLabel.setText("Alarm Raised");
		this.alarmStatusLabel.setStyle("-fx-text-fill: #ff0000e8");
		this.resetAlarmButton.setDisable(false);
	}
	
	private void alarmReset() {
		this.alarmStatusLabel.setText("Not Raised");
		this.alarmStatusLabel.setStyle("-fx-text-fill: #22b90f");
		this.resetAlarmButton.setDisable(true);
	}

}
