package u1171639.rmc.test.mocks;

import java.util.List;

import u1171639.rmc.main.java.client.LMS;
import u1171639.rmc.main.java.model.Locality;
import u1171639.rmc.main.java.model.Zone;
import u1171639.shared.main.java.logging.LogItem;

public class MockLMS implements LMS {

	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleRaisedAlarm(String zone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Zone> getZoneUpdates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LogItem> getLog() {
		// TODO Auto-generated method stub
		return null;
	}

}
