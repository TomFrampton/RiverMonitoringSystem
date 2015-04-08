package u1171639.shared.logging;

import java.util.ArrayList;
import java.util.List;

import u1171639.shared.logging.LogItem.Event;

public class TransientLogger implements Logger {

	private List<LogItem> log = new ArrayList<LogItem>();
	
	@Override
	public void logEvent(LogItem item) {
		this.log.add(item);
	}

	@Override
	public List<LogItem> getByEvent(Event event) {
		List<LogItem> result = new ArrayList<LogItem>();
		
		for(LogItem item : this.log) {
			if(item.getEvent().equals(event)) {
				result.add(item);
			}
		}
		
		return result;
	}
	
	

}
