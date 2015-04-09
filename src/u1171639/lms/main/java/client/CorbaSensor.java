package u1171639.lms.main.java.client;

import java.util.concurrent.Callable;

import u1171639.lms.main.java.utils.CorbaUtils;
import u1171639.shared.main.java.corba.sensor.SensorHelper;

public class CorbaSensor implements Sensor {
	private org.omg.CORBA.Object ior;
	private u1171639.shared.main.java.corba.sensor.Sensor sensor;
	
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
		return this.communicate(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return sensor.isAlarmRaised();
			}
		});
	}

	@Override
	public void activate() {
		this.communicate(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				sensor.activate();
				return null;
			}
		});
	}

	@Override
	public void deactivate() {
		this.communicate(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				sensor.deactivate();
				return null;
			}
		});
	}
	
	@Override
	public boolean isActive() {
		return this.communicate(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return sensor.isActive();
			}
		});
	}
	
	@Override
	public String getId() {
		return CorbaUtils.getOrb().object_to_string(this.ior);
	}
	
	private <T> T communicate(Callable<T> action) {
		try {
			connect();
			return action.call();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			disconnect();
		}
	}
}
