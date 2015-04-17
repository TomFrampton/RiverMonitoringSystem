package u1171639.sensor.main.java.client;

import u1171639.shared.main.java.exception.ConnectionException;
import u1171639.shared.main.java.exception.ServerNotFoundException;

public interface LMS {
	public void connect() throws ServerNotFoundException, ConnectionException;
	public void disconnect();
	public void raiseAlarm();
}
