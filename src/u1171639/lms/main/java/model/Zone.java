package u1171639.lms.main.java.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Zone {
	private String name;
	private List<Sensor> sensors = new ArrayList<Sensor>();
	private boolean alarmRaised;
	
	public boolean confirmAlarm() {
		// Check alarm status on all sensors in the zone
		Iterator<Sensor> it = sensors.iterator();
		
		while(it.hasNext()) {
			Sensor sensor = it.next();
			if(sensor.isActive() && !sensor.isAlarmRaised()) {
				return false;
			}
		}
		
		this.alarmRaised = true;
		return true;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Sensor> getSensors() {
		return sensors;
	}
	
	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}
	
	public boolean isAlarmRaised() {
		return this.alarmRaised;
	}
	
	public void resetAlarms() {
		this.alarmRaised = false;
	}
}
