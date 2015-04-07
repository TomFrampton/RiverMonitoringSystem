package u1171639.rmc.main.java.model;

public interface LMS {
	public void connect();
	public void disconnect();
	public Locality getLocalityInfo();
	public void handleRaisedAlarm(String zone);
}
