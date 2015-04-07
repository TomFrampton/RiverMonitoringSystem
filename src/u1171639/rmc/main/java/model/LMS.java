package u1171639.rmc.main.java.model;

import java.util.List;

public interface LMS {
	public void connect();
	public void disconnect();
	public List<Zone> getLocalityInfo();
	public void handleRaisedAlarm(String zone);
}
