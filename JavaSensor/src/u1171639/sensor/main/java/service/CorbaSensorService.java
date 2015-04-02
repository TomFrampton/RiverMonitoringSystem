package u1171639.sensor.main.java.service;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import u1171639.sensor.main.java.corba.sensor.Sensor;
import u1171639.sensor.main.java.corba.sensor.SensorHelper;
import u1171639.sensor.main.java.corba.sensor.SensorImpl;
import u1171639.sensor.main.java.corba.sensor.SensorPOA;
import u1171639.sensor.main.java.monitor.WaterLevelMonitor;

public class CorbaSensorService implements SensorService {

	private WaterLevelMonitor monitor;
	
	public CorbaSensorService(WaterLevelMonitor monitor) {
		this.monitor = monitor;
	}
	
	public void connect(String[] args) {
		try {
			// Initialise ORB
			ORB orb = ORB.init(args, null);
			
			// Get reference to rootPOA and activate POAManager
			POA rootPoa;
			
			rootPoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootPoa.the_POAManager().activate();
			
			// Create the servant object
			SensorPOA servant = new SensorImpl();
			
			// Get the reference of the servant
			org.omg.CORBA.Object servantRef = rootPoa.servant_to_reference(servant);
			Sensor ref = SensorHelper.narrow(servantRef);
			
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
			
			// Bind the servant object in the naming service
			String name = "SensorService";
			NameComponent[] sensorName = nameService.to_name(name);
			// Use rebind so we overwrite the old servant each time
			nameService.rebind(sensorName, ref);
			
			// Wait for clients to connect
			System.out.println("Listening for clients...");
			orb.run();
		} catch (InvalidName | NotFound | CannotProceed | org.omg.CosNaming.NamingContextPackage.InvalidName | ServantNotActive | WrongPolicy | AdapterInactive e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public float getWaterLevel() {
		return this.monitor.getWaterLevel();
	}
}
