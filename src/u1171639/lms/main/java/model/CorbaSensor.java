package u1171639.lms.main.java.model;

import u1171639.sensor.main.java.corba.SensorHelper;

public class CorbaSensor implements Sensor {
	private org.omg.CORBA.Object ior;
	private u1171639.sensor.main.java.corba.Sensor sensor;
	
	public CorbaSensor(org.omg.CORBA.Object ior) {
		this.ior = ior;
	}
	
	@Override
	public void connect() {
		if(this.sensor == null) {
			this.sensor = SensorHelper.narrow(this.ior);
		}
	}

	@Override
	public void disconnect() {
		this.sensor = null;	
	}

	@Override
	public boolean isAlarmRaised() {
		try {
			connect();
			return this.sensor.isAlarmRaised();
		} finally {
			disconnect();
		}
	}
}
