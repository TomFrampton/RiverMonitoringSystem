package u1171639.lms.test.mocks;

import u1171639.lms.main.java.client.Sensor;

public class MockSensor implements Sensor {
	private boolean alarmRaised;
	private boolean active = true;
	private String name;
	private double threshold;
	
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
	public boolean activate() {
		this.active = true;
		return true;
	}

	@Override
	public boolean deactivate() {
		this.active = false;
		return true;
	}

	@Override
	public boolean isActive() {
		return this.active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public void setName(String id) {
		this.name = id;
	}

	@Override
	public double getThreshold() {
		return this.threshold;
	}
	
	@Override
	public boolean setThreshold(double threshold) {
		this.threshold = threshold;
		return true;
	}
}
