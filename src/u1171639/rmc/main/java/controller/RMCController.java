package u1171639.rmc.main.java.controller;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import u1171639.lms.main.java.model.Sensor;
import u1171639.lms.main.java.model.Zone;
import u1171639.lms.main.java.utils.logging.SimpleLogger;
import u1171639.lms.main.java.utils.logging.SimpleLogger.LogLevel;
import u1171639.rmc.main.java.model.CorbaLMS;
import u1171639.rmc.main.java.model.LMS;
import u1171639.rmc.main.java.model.Locality;
import u1171639.rmc.main.java.service.RMCService;
import u1171639.rmc.main.java.utils.CorbaUtils;

public class RMCController {
	private List<Locality> localities = new ArrayList<Locality>();
	
	public RMCController() {

	}
	
	public void raiseAlarm(String locality, String zone) {
		SimpleLogger.log(LogLevel.INFO, "ALARM RAISED AT RMC IN " + locality.toUpperCase() + " - " + zone.toUpperCase());

		
	}
	
	public void registerLMS(String localityName, LMS lms) {
		Locality locality = this.getLocalityByName(localityName);
		if(locality == null) {
			locality = new Locality();
			this.localities.add(locality);
		}
		
		locality.setName(localityName);
		locality.setLms(lms);
		
		SimpleLogger.log(LogLevel.INFO, "LMS registered for " + locality);
	}
	
	public Locality getLocalityByName(String localityName) {
		Iterator<Locality> it = this.localities.iterator();
		while(it.hasNext()) {
			Locality next = it.next();
			if(next.getName().equals(localityName)) {
				return next;
			}
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		CorbaUtils.initOrb(args);
		CorbaUtils.initRootPOA();
		CorbaUtils.initNameService();
		
		RMCController controller = new RMCController();
		RMCService service = new RMCService(controller);
		service.listen();
	}
}
