package u1171639.rmc.main.java.service;

import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import u1171639.lms.main.java.client.CorbaSensor;
import u1171639.rmc.main.java.client.CorbaLMS;
import u1171639.rmc.main.java.controller.RMCController;
import u1171639.shared.main.java.corba.rmc.RMC;
import u1171639.shared.main.java.corba.rmc.RMCHelper;
import u1171639.shared.main.java.corba.rmc.RMCPOA;
import u1171639.shared.main.java.logging.Logger;
import u1171639.shared.main.java.utils.CorbaUtils;

public class RMCService extends RMCPOA {
	private RMCController controller;
	
	public RMCService(RMCController controller) {
		this.controller = controller;
	}
	
	public void listen() {
		try {
			// Get the reference of the servant
			org.omg.CORBA.Object servantRef; servantRef = CorbaUtils.getRootPOA().servant_to_reference(this);
			RMC ref = RMCHelper.narrow(servantRef);
			CorbaUtils.registerWithNameService("RMCServer", ref);
			
			//Logger.log(LogLevel.INFO, "RMC listening...");
			
			CorbaUtils.runOrb();
		} catch (ServantNotActive | WrongPolicy e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void raiseAlarm(String locality, String zone) {
		this.controller.raiseAlarm(locality, zone);
	}

	@Override
	public void register(String ior, String locality) {
		CorbaLMS lms = new CorbaLMS(CorbaUtils.getOrb().string_to_object(ior));
		controller.registerLMS(locality, lms);
	}

}
