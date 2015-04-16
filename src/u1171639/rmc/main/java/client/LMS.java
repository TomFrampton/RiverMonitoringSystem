package u1171639.rmc.main.java.client;

import java.util.List;

import u1171639.rmc.main.java.model.RMCZone;
import u1171639.shared.main.java.logging.LogItem;

public interface LMS {
	public void connect();
	public void disconnect();
	public List<RMCZone> getZoneUpdates();
	public void handleRaisedAlarm(String zone);
	public List<LogItem> getLog();
	public boolean activateSensor(String zoneName, String sensorName);
	public boolean deactivateSensor(String zoneName, String sensorName);
	public boolean setWarningThreshold(String zoneName, String sensorName, double threshold);
	public double getSensorReading(String zoneName, String sensorName);
}
