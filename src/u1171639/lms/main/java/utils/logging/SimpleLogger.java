package u1171639.lms.main.java.utils.logging;

public class SimpleLogger {
	
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
		SimpleLogger.log(level.toString() + ": " + message);
	}
}
