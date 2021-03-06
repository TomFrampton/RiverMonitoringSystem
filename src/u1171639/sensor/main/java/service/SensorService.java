package u1171639.sensor.main.java.service;

import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import u1171639.sensor.main.java.controller.SensorController;
import u1171639.sensor.main.java.utils.SensorConfig;
import u1171639.shared.main.java.corba.sensor.Sensor;
import u1171639.shared.main.java.corba.sensor.SensorHelper;
import u1171639.shared.main.java.corba.sensor.SensorPOA;
import u1171639.shared.main.java.utils.CorbaUtils;

public class SensorService extends SensorPOA {
	private SensorController controller;
	
	public SensorService(SensorController controller) {
		this.controller = controller;
	}
	
	public String listen() {
		try {
			// Get the reference of the servant
			org.omg.CORBA.Object servantRef = CorbaUtils.getRootPOA().servant_to_reference(this);
			Sensor ref = SensorHelper.narrow(servantRef);
			return CorbaUtils.getOrb().object_to_string(ref);
			
		} catch (ServantNotActive | WrongPolicy e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean isAlarmRaised() {
		return this.controller.isAlarmRaised();
	}

	@Override
	public boolean activate() {
		this.controller.activate();
		return true;
	}

	@Override
	public boolean deactivate() {
		this.controller.deactivate();
		return true;
	}

	@Override
	public boolean isActive() {
		return this.controller.isActive();
	}

	@Override
	public double getThreshold() {
		return SensorConfig.getWarningThreshold();
	}

	@Override
	public boolean setThreshold(double threshold) {
		SensorConfig.setWarningThreshold(threshold);
		return true;
		
	}

	@Override
	public double getReading() {
		return this.controller.getWaterLevelReading();
	}
}
