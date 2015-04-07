package u1171639.rmc.main.java.model;

import java.util.ArrayList;
import java.util.List;

public class Locality {
	private List<Zone> zones = new ArrayList<Zone>();
	private String name;
	
	public List<Zone> getZones() {
		return zones;
	}
	
	public void setZones(List<Zone> zones) {
		this.zones = zones;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
