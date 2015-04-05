package u1171639.lms.main.java.model;

import java.util.ArrayList;
import java.util.List;

public class Zone {
	private String name;
	private List<Sensor> sensors = new ArrayList<Sensor>();
	
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
}
