package u1171639.lms.main.java.client;

import java.util.concurrent.Callable;

import u1171639.shared.main.java.corba.sensor.SensorHelper;
import u1171639.shared.main.java.utils.CorbaUtils;

public class CorbaSensor implements Sensor {
	private org.omg.CORBA.Object ior;
	private u1171639.shared.main.java.corba.sensor.Sensor sensor;
	
	private String name;
	
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
	public boolean activate() {
		return this.communicate(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return sensor.activate();
			}
		});
	}

	@Override
	public boolean deactivate() {
		return this.communicate(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return sensor.deactivate();
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
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public double getThreshold() {
		return this.communicate(new Callable<Double>() {
			@Override
			public Double call() throws Exception {
				return sensor.getThreshold();
			}
		});
	}
	
	@Override
	public boolean setThreshold(double threshold) {
		return this.communicate(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return sensor.setThreshold(threshold);
			}
		});
	}
	
	@Override
	public double getReading() {
		return this.communicate(new Callable<Double>() {
			@Override
			public Double call() throws Exception {
				return sensor.getReading();
			}
		});
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
