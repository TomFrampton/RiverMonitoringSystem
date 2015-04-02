package u1171639.sensor.main.java.client;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import u1171639.sensor.main.java.corba.lms_sensor.LMS;
import u1171639.sensor.main.java.corba.lms_sensor.LMSHelper;

public class CorbaLMSClient implements LMSClient {
	private LMS lms = null;
	
	public CorbaLMSClient() {
	}
	
	public void connect(String[] args, String servantName) {
		try {
			// Initialise the ORB
			ORB orb = ORB.init(args, null);
			
			// Get a reference to the naming service
			org.omg.CORBA.Object nameServiceObj = orb.resolve_initial_references("NameService");
			if(nameServiceObj == null) {
				System.err.println("Name Service object == null");
				return;
			}
			
			// Use NamingContextExt which is part of the Interoperable Naming Service (INS) specification
			NamingContextExt nameService = NamingContextExtHelper.narrow(nameServiceObj);
			if(nameService == null) {
				System.err.println("Name service == null");
				return;
			}
			
			// Resolve the servant object reference in the naming service
			String name = servantName;
			this.lms = LMSHelper.narrow(nameService.resolve_str(name));
			
		} catch (NotFound | CannotProceed | InvalidName | org.omg.CORBA.ORBPackage.InvalidName e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void raiseAlarm() {
		lms.raiseAlarm();
	}

}
