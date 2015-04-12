package u1171639.rmc.main.java.model;

import java.util.List;

public class RMCZone {
	private String name;
	private boolean alarmRaised;
	private List<RMCSensor> sensors;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isAlarmRaised() {
		return alarmRaised;
	}
	
	public void setAlarmRaised(boolean alarmRaised) {
		this.alarmRaised = alarmRaised;
	}

	public List<RMCSensor> getSensors() {
		return sensors;
	}

	public void setSensors(List<RMCSensor> sensors) {
		this.sensors = sensors;
	}
}
