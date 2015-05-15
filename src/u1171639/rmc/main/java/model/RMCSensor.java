package u1171639.rmc.main.java.model;

public class RMCSensor {
	private String name;
	private double threshold;
	private boolean active;
	private String localityName;
	private String zoneName;
	private double reading;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	public double getThreshold() {
		return this.threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getLocalityName() {
		return this.localityName;
	}

	public void setLocalityName(String localityName) {
		this.localityName = localityName;
	}

	public String getZoneName() {
		return this.zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public double getReading() {
		return this.reading;
	}

	public void setReading(double reading) {
		this.reading = reading;
	}
}
