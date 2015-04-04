package u1171639.lms.main.java.service;

import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import u1171639.lms.main.java.controller.LMSController;
import u1171639.lms.main.java.corba.LMS;
import u1171639.lms.main.java.corba.LMSHelper;
import u1171639.lms.main.java.corba.LMSPOA;
import u1171639.lms.main.java.model.CorbaSensor;
import u1171639.lms.main.java.utils.CorbaUtils;
import u1171639.lms.main.java.utils.Logger;
import u1171639.lms.main.java.utils.Logger.LogLevel;

public class LMSService extends LMSPOA {
	private LMSController controller;
	
	public LMSService(LMSController controller) {
		this.controller = controller;
	}
	
	public void listen() {
		try {
			// Get the reference of the servant
			org.omg.CORBA.Object servantRef; servantRef = CorbaUtils.getRootPOA().servant_to_reference(this);
			LMS ref = LMSHelper.narrow(servantRef);
			CorbaUtils.registerWithNameService("LMSServer", ref);
			
			Logger.log(LogLevel.INFO, "LMS listening...");
			
			CorbaUtils.runOrb();
		} catch (ServantNotActive | WrongPolicy e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void raiseAlarm(String zone) {
		controller.alarmRaised(zone);
	}

	@Override
	public void register(String ior, String zone) {
		CorbaSensor sensor = new CorbaSensor(CorbaUtils.getOrb().string_to_object(ior));
		controller.registerSensor(zone, sensor);
	}
}
