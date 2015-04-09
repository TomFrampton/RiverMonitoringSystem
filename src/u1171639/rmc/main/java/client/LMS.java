package u1171639.rmc.main.java.client;

import java.util.List;

import u1171639.rmc.main.java.model.Zone;

public interface LMS {
	public void connect();
	public void disconnect();
	public List<Zone> getLocalityInfo();
	public void handleRaisedAlarm(String zone);
}
