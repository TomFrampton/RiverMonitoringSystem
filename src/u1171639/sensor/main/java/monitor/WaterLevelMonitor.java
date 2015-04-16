package u1171639.sensor.main.java.monitor;

public interface WaterLevelMonitor {
	public void monitorWaterLevel();
	public double getWaterLevel();
	public boolean isAlarmRaised();
	public void resumeMonitoring();
	public void pauseMonitoring();
}
