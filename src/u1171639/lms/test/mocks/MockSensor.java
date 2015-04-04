package u1171639.lms.test.mocks;

import u1171639.lms.main.java.model.Sensor;

public class MockSensor implements Sensor {
	private boolean alarmRaised;
	
	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAlarmRaised() {
		return this.alarmRaised;
	}
	
	public void setAlarmRaised(boolean alarmRaised) {
		this.alarmRaised = alarmRaised;
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

}
