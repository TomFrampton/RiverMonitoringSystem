package u1171639.rmc.main.java.controller;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import u1171639.lms.main.java.model.Sensor;
import u1171639.lms.main.java.utils.Logger;
import u1171639.lms.main.java.utils.Logger.LogLevel;
import u1171639.rmc.main.java.model.CorbaLMS;
import u1171639.rmc.main.java.model.LMS;
import u1171639.rmc.main.java.service.RMCService;
import u1171639.rmc.main.java.utils.CorbaUtils;

public class RMCController {
	private Hashtable<String, LMS> lmses = new Hashtable<String, LMS>();
	
	public RMCController() {

	}
	
	public void raiseAlarm() {
		
	}
	
	public void registerLMS(String locality, LMS lms) {
		this.lmses.put(locality, lms);
		
		Logger.log(LogLevel.INFO, "LMS registered for " + locality);
	}
	
	public LMS getLMSByLocality(String locality) {
		if(this.lmses.containsKey(locality)) {
			return this.lmses.get(locality);
		} else {
			return null;
		}
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
