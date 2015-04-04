package u1171639.rmc.main.java.utils;

public class Logger {
	
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
		Logger.log(level.toString() + ": " + message);
	}
}
