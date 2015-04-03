package u1171639.sensor.main.java.utils;

public class SensorConfig {
	private static float warningWaterLevel;
	private static long monitoringInterval;
	
	public static float getWarningWaterLevel() {
		return warningWaterLevel;
	}
	
	public static void setWarningWaterLevel(float warningWaterLevel) {
		SensorConfig.warningWaterLevel = warningWaterLevel;
	}
	
	public static long getMonitoringInterval() {
		return monitoringInterval;
	}
	
	public static void setMonitoringInterval(long monitoringInterval) {
		SensorConfig.monitoringInterval = monitoringInterval;
	}	
}
