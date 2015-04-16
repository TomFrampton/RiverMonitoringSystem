package u1171639.lms.main.java.client;

public interface Sensor {
	public void connect();
	public void disconnect();
	public boolean isAlarmRaised();
	public boolean activate();
	public boolean deactivate();
	public boolean isActive();
	public String getName();
	public void setName(String name);
	public double getThreshold();
	public boolean setThreshold(double threshold);
	public double getReading();
}
