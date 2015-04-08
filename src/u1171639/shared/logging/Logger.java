package u1171639.shared.logging;

import java.util.List;

public interface Logger {
	
	public void logEvent(LogItem item);
	public List<LogItem> getByEvent(LogItem.Event event);
	
}
