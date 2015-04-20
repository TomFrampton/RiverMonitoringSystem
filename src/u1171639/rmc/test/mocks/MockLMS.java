package u1171639.rmc.test.mocks;

import java.util.List;

import u1171639.rmc.main.java.client.LMS;
import u1171639.rmc.main.java.model.Locality;
import u1171639.rmc.main.java.model.RMCZone;
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
	public List<RMCZone> getZoneUpdates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LogItem> getLog() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean activateSensor(String zoneName, String sensorName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deactivateSensor(String zoneName, String sensorName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setWarningThreshold(String zoneName, String sensorName,
			double threshold) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getSensorReading(String zoneName, String sensorName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean resetAlarm(String zoneName) {
		// TODO Auto-generated method stub
		return false;
	}

}
