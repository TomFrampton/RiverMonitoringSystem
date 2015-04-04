package u1171639.rmc.main.java.controller;

import u1171639.rmc.main.java.model.CorbaLMS;
import u1171639.rmc.main.java.model.LMS;
import u1171639.rmc.main.java.service.RMCService;
import u1171639.rmc.main.java.utils.CorbaUtils;

public class RMCController {
	private LMS lms;
	
	public RMCController(LMS lms) {
		this.lms = lms;
	}
	
	public void raiseAlarm() {
		
	}
	
	public static void main(String[] args) {
		CorbaUtils.initOrb(args);
		CorbaUtils.initRootPOA();
		CorbaUtils.initNameService();
		
		LMS lms = new CorbaLMS();
		RMCController controller = new RMCController(lms);
		RMCService service = new RMCService(controller);
		service.listen();
	}
}
