package u1171639.rmc.test.mocks;

import u1171639.lms.main.java.model.RMC;

public class MockRMC implements RMC {
	private boolean alarmRaised;
	
	@Override
	public void raiseAlarm() {
		this.alarmRaised = true;
	}
	
	public boolean isAlarmRaised() {
		return this.alarmRaised;
	}
	
	public void resetAlarm() {
		this.alarmRaised = false;
	}
}
