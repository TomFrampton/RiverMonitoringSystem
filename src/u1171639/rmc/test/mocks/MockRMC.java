package u1171639.rmc.test.mocks;

import u1171639.lms.main.java.model.RMC;

public class MockRMC implements RMC {
	private boolean alarmRaised;
	
	@Override
	public void raiseAlarm(String zone) {
		this.alarmRaised = true;
	}
	
	public boolean isAlarmRaised() {
		return this.alarmRaised;
	}
	
	public void resetAlarm() {
		this.alarmRaised = false;
	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}
}
