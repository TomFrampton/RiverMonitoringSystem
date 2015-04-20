package u1171639.rmc.main.java.model;

import java.util.Iterator;
import java.util.List;

public class RMCZone {
	private String name;
	private String localityName;
	private boolean alarmRaised;
	private List<RMCSensor> sensors;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLocalityName() {
		return localityName;
	}

	public void setLocalityName(String localityName) {
		this.localityName = localityName;
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
	
	public RMCSensor getSensorByName(String sensorName) {
		Iterator<RMCSensor> it = this.sensors.iterator();
		while(it.hasNext()) {
			RMCSensor next = it.next();
			if(next.getName().equals(sensorName)) {
				return next;
			}
		}
		
		return null;
	}
}
