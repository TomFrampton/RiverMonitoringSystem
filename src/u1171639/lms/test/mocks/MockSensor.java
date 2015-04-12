package u1171639.lms.test.mocks;

import u1171639.lms.main.java.client.Sensor;

public class MockSensor implements Sensor {
	private boolean alarmRaised;
	private boolean active = true;
	private String id;
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
	public void activate() {
		this.active = true;
	}

	@Override
	public void deactivate() {
		this.active = false;
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
		return this.id;
	}
	
	public void setName(String id) {
		this.id = id;
	}

	@Override
	public double getThreshold() {
		return this.threshold;
	}
	
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

}
