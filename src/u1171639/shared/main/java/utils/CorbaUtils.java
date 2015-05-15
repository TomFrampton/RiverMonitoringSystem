package u1171639.shared.main.java.utils;

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

import u1171639.shared.main.java.exception.ServerNotFoundException;

public class CorbaUtils {
	private static ORB orb;
	private static POA rootPoa;
	private static NamingContextExt nameService;
	
	public static void initOrb(String[] args) {
		if(orb == null) {
			orb = ORB.init(args, null);
		}
	}
	
	public static void initRootPOA() {
		if(rootPoa == null) {
			// Get reference to rootPOA and activate POAManager
			try {
				rootPoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
				rootPoa.the_POAManager().activate();
			} catch (InvalidName | AdapterInactive e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void initNameService() throws ServerNotFoundException {
		if(nameService == null) {
			try {
				// Get a reference to the naming service
				org.omg.CORBA.Object nameServiceObj;
				nameServiceObj = orb.resolve_initial_references("NameService");
			
				if(nameServiceObj == null) {
					System.err.println("Name Service object == null");
					return;
				}
				
				// Use NamingContextExt which is part of the Interoperable Naming Service (INS) specification
				nameService = NamingContextExtHelper.narrow(nameServiceObj);
				if(nameService == null) {
					System.err.println("Name service == null");
					return;
				}
			} catch (org.omg.CORBA.SystemException | InvalidName e) {
				throw new ServerNotFoundException("Name Service not found.");
			}
		}
	}
	
	public static void registerWithNameService(String name, org.omg.CORBA.Object ref) {
		NameComponent[] nameCom;
		try {
			nameCom = nameService.to_name(name);
			// Use rebind so we overwrite the old servant each time
			nameService.rebind(nameCom, ref);
		} catch (org.omg.CosNaming.NamingContextPackage.InvalidName | NotFound | CannotProceed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static org.omg.CORBA.Object resolveService(String name) throws ServerNotFoundException {
		try {
			return nameService.resolve_str(name);
		} catch (CannotProceed | org.omg.CosNaming.NamingContextPackage.InvalidName | NotFound e) {
			throw new ServerNotFoundException("Server with name: " + name + " not found.");
		}
	}
	
	public static void runOrb() {
		orb.run();
	}
	
	public NamingContextExt getNameService() {
		return nameService;	
	}
	
	public static POA getRootPOA() {
		return rootPoa;
	}
	
	public static ORB getOrb() {
		return orb;
	}
}
