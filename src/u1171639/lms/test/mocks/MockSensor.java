package u1171639.lms.test.mocks;

import u1171639.lms.main.java.client.Sensor;

public class MockSensor implements Sensor {
	private boolean alarmRaised;
	private boolean isActive = true;
	
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
		this.isActive = true;
	}

	@Override
	public void deactivate() {
		this.isActive = false;
	}

	@Override
	public boolean isActive() {
		return this.isActive;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
