package u1171639.sensor.main.java.utils;

public class SensorLogger {
	
	public enum LogLevel {
		DEBUG,
		INFO,
		WARNING,
		ERROR
	}
	
	public static void log(String message) {
		System.out.println(message);
	}
	
	public static void log(LogLevel level, String message) {
		SensorLogger.log(level.toString() + ": " + message);
	}
}
