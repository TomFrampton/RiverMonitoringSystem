package u1171639.lms.main.java.service;

import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import u1171639.lms.main.java.controller.LMSController;
import u1171639.lms.main.java.model.CorbaSensor;
import u1171639.lms.main.java.utils.CorbaUtils;
import u1171639.lms.main.java.utils.LMSConfig;
import u1171639.shared.main.java.corba.lms_sensor.LMS_Sensor;
import u1171639.shared.main.java.corba.lms_sensor.LMS_SensorHelper;
import u1171639.shared.main.java.corba.lms_sensor.LMS_SensorPOA;

public class LMS_SensorService extends LMS_SensorPOA {
	private LMSController controller;
	
	public LMS_SensorService(LMSController controller) {
		this.controller = controller;
	}
	
	public void listen(String serviceName) {
		// Start service on separate thread
		Thread serviceThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					// Get the reference of the servant
					org.omg.CORBA.Object servantRef; servantRef = CorbaUtils.getRootPOA().servant_to_reference(LMS_SensorService.this);
					LMS_Sensor ref = LMS_SensorHelper.narrow(servantRef);
					CorbaUtils.registerWithNameService(serviceName, ref);
					
					//SimpleLogger.log(LogLevel.INFO, "LMS listening in " + LMSConfig.getLocality() + "...");
					
					CorbaUtils.runOrb();
				} catch (ServantNotActive | WrongPolicy e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		serviceThread.start();
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
