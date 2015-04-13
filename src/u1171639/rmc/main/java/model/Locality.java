package u1171639.rmc.main.java.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import u1171639.rmc.main.java.client.LMS;
import u1171639.shared.main.java.logging.LogItem;

public class Locality {
	private List<RMCZone> zones = new ArrayList<RMCZone>();
	private List<LogItem> logs = new ArrayList<LogItem>();
	private String name;
	private LMS lms;
	
	public Locality() {
		
	}
	
	public Locality(String name, LMS lms) {
		this.name = name;
		this.lms = lms;
	}
	
	public List<RMCZone> getUpdatedZones() {
		this.zones = this.lms.getZoneUpdates();
		return this.getZones();
	}
	
	public List<LogItem> getUpdatedLogs() {
		this.logs = this.lms.getLog();
		return this.getLogs();
	}
	
	public List<RMCZone> getZones() {
		return this.zones;
	}
	
	public RMCZone getZoneByName(String zoneName) {
		Iterator<RMCZone> it = this.zones.iterator();
		while(it.hasNext()) {
			RMCZone next = it.next();
			if(next.getName().equals(zoneName)) {
				return next;
			}
		}
		
		return null;
	}
	
	public List<LogItem> getLogs() {
		return this.logs;
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
