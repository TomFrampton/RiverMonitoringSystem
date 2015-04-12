package u1171639.lms.main.java.client;

public interface Sensor {
	public void connect();
	public void disconnect();
	public boolean isAlarmRaised();
	public void activate();
	public void deactivate();
	public boolean isActive();
	public String getName();
	public double getThreshold();
}
