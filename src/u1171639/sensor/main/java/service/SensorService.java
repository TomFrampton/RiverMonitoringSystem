package u1171639.sensor.main.java.service;

import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import u1171639.sensor.main.java.utils.CorbaUtils;
import u1171639.sensor.main.java.controller.SensorController;
import u1171639.sensor.main.java.corba.Sensor;
import u1171639.sensor.main.java.corba.SensorHelper;
import u1171639.sensor.main.java.corba.SensorPOA;

public class SensorService extends SensorPOA {
	private SensorController controller;
	
	public SensorService(SensorController controller) {
		this.controller = controller;
	}
	
	public void listen() {
		// Get the reference of the servant
		org.omg.CORBA.Object servantRef;
		try {
			servantRef = CorbaUtils.getRootPOA().servant_to_reference(this);
			Sensor ref = SensorHelper.narrow(servantRef);
			
		} catch (ServantNotActive | WrongPolicy e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String hello() {
		// TODO Auto-generated method stub
		return null;
	}
}
