package u1171639.rmc.main.java.client;

import java.util.List;

import u1171639.rmc.main.java.model.Zone;
import u1171639.shared.main.java.logging.LogItem;

public interface LMS {
	public void connect();
	public void disconnect();
	public List<Zone> getZoneUpdates();
	public void handleRaisedAlarm(String zone);
	public List<LogItem> getLog();
}
