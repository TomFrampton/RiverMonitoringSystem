package u1171639.rmc.main.java.controller;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import u1171639.lms.main.java.client.Sensor;
import u1171639.lms.main.java.model.LMSZone;
import u1171639.rmc.main.java.client.CorbaLMS;
import u1171639.rmc.main.java.client.LMS;
import u1171639.rmc.main.java.model.Alarm;
import u1171639.rmc.main.java.model.Locality;
import u1171639.rmc.main.java.service.RMCService;
import u1171639.rmc.main.java.users.HomeUserManager;
import u1171639.rmc.main.java.users.TransientHomeUserManager;
import u1171639.rmc.main.java.view.JavaFXRMCView;
import u1171639.rmc.main.java.view.RMCView;
import u1171639.shared.main.java.utils.CorbaUtils;

public class RMCController {
	private List<Locality> localities = new ArrayList<Locality>();
	private HomeUserManager homeUserManager;
	
	private RMCView view;
	
	public RMCController(RMCView view, HomeUserManager homeUserManager) {
		this.view = view;
		this.homeUserManager = homeUserManager;
	}
	
	public void alarmRaised(Alarm alarm) {
		if(view != null) {
			view.alarmRaised(alarm);
		}
	}
	
	public void registerLMS(String localityName, LMS lms) {
		Locality locality = this.getLocalityByName(localityName);
		if(locality == null) {
			locality = new Locality();
			this.localities.add(locality);
		}
		
		locality.setName(localityName);
		locality.setLms(lms);
		
		this.view.updateView();
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
	
	public List<Locality> getLocalities() {
		Iterator<Locality> it = this.localities.iterator();
		while(it.hasNext()) {
			it.next().getUpdatedZones();
		}
		
		return this.localities;
	}
	
	public HomeUserManager getHomeUserManager() {
		return this.homeUserManager;
	}
	
	public static void main(String[] args) {
		CorbaUtils.initOrb(args);
		CorbaUtils.initRootPOA();
		CorbaUtils.initNameService();
		
		HomeUserManager homeUserManager = new TransientHomeUserManager();
		RMCView view = new JavaFXRMCView();
		
		RMCController controller = new RMCController(view, homeUserManager);
		RMCService service = new RMCService(controller);
		
		Thread serviceThread = new Thread(new Runnable() {
			@Override
			public void run() {
				service.listen();
			}
		});
		
		serviceThread.start();
		view.start(controller);
		
		// Terminate all threads when view is closed
		System.exit(0);
	}
}
