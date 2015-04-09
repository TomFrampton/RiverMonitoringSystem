package u1171639.shared.main.java.logging;

import java.util.List;

public interface Logger {
	
	public void logEvent(LogItem item);
	public List<LogItem> getByEvent(LogItem.Event event);
	public List<LogItem> getAllLogs();
	
}
