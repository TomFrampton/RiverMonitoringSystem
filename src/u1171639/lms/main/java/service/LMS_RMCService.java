package u1171639.lms.main.java.service;

import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import u1171639.lms.main.java.controller.LMSController;
import u1171639.lms.main.java.corba.LMS_RMC;
import u1171639.lms.main.java.corba.LMS_RMCHelper;
import u1171639.lms.main.java.corba.LMS_RMCPOA;
import u1171639.lms.main.java.utils.CorbaUtils;

public class LMS_RMCService extends LMS_RMCPOA {
	private LMSController controller;
	
	public LMS_RMCService(LMSController controller) {
		this.controller = controller;
	}
	
	public String listen() {
		try {
			// Get the reference of the servant
			org.omg.CORBA.Object servantRef = CorbaUtils.getRootPOA().servant_to_reference(this);
			LMS_RMC ref = LMS_RMCHelper.narrow(servantRef);
			return CorbaUtils.getOrb().object_to_string(ref);
			
		} catch (ServantNotActive | WrongPolicy e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
