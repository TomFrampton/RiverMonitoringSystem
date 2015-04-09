package u1171639.rmc.main.java.model;

import java.util.ArrayList;
import java.util.List;

import u1171639.rmc.main.java.client.LMS;

public class Locality {
	private List<Zone> zones = new ArrayList<Zone>();
	private String name;
	private LMS lms;
	
	public Locality() {
		
	}
	
	public Locality(String name, LMS lms) {
		this.name = name;
		this.lms = lms;
	}
	
	public void updateLocalityInfo() {
		this.zones = this.lms.getLocalityInfo();
	}
	
	public List<Zone> getZones() {
		return zones;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public LMS getLms() {
		return lms;
	}

	public void setLms(LMS lms) {
		this.lms = lms;
	}
}
