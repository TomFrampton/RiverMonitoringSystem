package u1171639.shared.main.java.logging;

import java.util.Date;

public class LogItem {

	private Date time;
	private String message;
	private Event event;
	
	public LogItem() {
	}
	
	public LogItem(String message, Event event) {
		this(new Date(), message, event);
	}
	
	public LogItem(Date time, String message, Event event) {
		this.time = time;
		this.message = message;
		this.event = event;
	}
	
	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Event getEvent() {
		return this.event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public enum Event {
		ALARM_RAISED,
		ALARM_CONFIRMED,
		ALARM_IGNORED,
		ALARM_RESET,
		CONNECTION,
		SENSOR_ACTIVATION,
		SENSOR_THRESHOLD
	}
}
