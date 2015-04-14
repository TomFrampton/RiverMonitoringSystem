package u1171639.sensor.main.java.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class SensorConfig {
	private static double warningThreshold;
	private static long monitoringInterval;
	private static String locality;
	private static String zone;
	private static String name;
	
	private static List<Observer> warningThresholdObservers = new ArrayList<Observer>();
	
	public static double getWarningThreshold() {
		return warningThreshold;
	}
	
	public static void setWarningThreshold(double warningThreshold) {
		SensorConfig.warningThreshold = warningThreshold;
		for(Observer observer : warningThresholdObservers) {
			observer.update(null, SensorConfig.warningThreshold);
		}
	}
	
	public static long getMonitoringInterval() {
		return monitoringInterval;
	}
	
	public static void setMonitoringInterval(long monitoringInterval) {
		SensorConfig.monitoringInterval = monitoringInterval;
	}
	
	public static String getLocality() {
		return locality;
	}
	
	public static void setLocality(String locality) {
		SensorConfig.locality = locality;
	}
	
	public static String getZone() {
		return zone;
	}
	
	public static void setZone(String zone) {
		SensorConfig.zone = zone;
	}
	
	public static String getSensorName() {
		return SensorConfig.name;
	}
	
	public static void setSensorName(String name) {
		SensorConfig.name = name;
	}
	
	public static void observeWarningThreshold(Observer observer) {
		SensorConfig.warningThresholdObservers.add(observer);
	}
}
