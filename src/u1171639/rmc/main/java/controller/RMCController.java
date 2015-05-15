package u1171639.rmc.main.java.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import u1171639.rmc.main.java.client.LMS;
import u1171639.rmc.main.java.model.Alarm;
import u1171639.rmc.main.java.model.Locality;
import u1171639.rmc.main.java.model.RMCSensor;
import u1171639.rmc.main.java.service.HomeUserRMCService;
import u1171639.rmc.main.java.service.RMCService;
import u1171639.rmc.main.java.users.HomeUser;
import u1171639.rmc.main.java.users.HomeUserManager;
import u1171639.rmc.main.java.users.TransientHomeUserManager;
import u1171639.rmc.main.java.view.JavaFXRMCView;
import u1171639.rmc.main.java.view.RMCView;
import u1171639.shared.main.java.exception.AuthenticationException;
import u1171639.shared.main.java.exception.ServerNotFoundException;
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
		if(this.view != null) {
			this.view.alarmRaised(alarm);
		}
	}
	
	public void sensorAdded() {
		if(this.view != null) {
			this.view.updateView();
		}
	}
	
	public void registerLMS(String localityName, LMS lms) {
		Locality locality = getLocalityByName(localityName);
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
	
	public HomeUser getHomeUser(String username, String password) throws AuthenticationException {
		return this.homeUserManager.authenticateUser(username, password);
	}
	
	public HomeUserManager getHomeUserManager() {
		return this.homeUserManager;
	}
	
	public List<RMCSensor> getHomeUserSensorReadings(int userId) {
		HomeUser user = this.homeUserManager.getUserById(userId);
		List<RMCSensor> sensors = user.getRegisteredSensors();
		List<RMCSensor> sensorsCopy = new ArrayList<RMCSensor>();
		
		for(RMCSensor sensor : sensors) {
			Locality locality = getLocalityByName(sensor.getLocalityName());
			
			RMCSensor sensorCopy = new RMCSensor();
			sensorCopy.setLocalityName(sensor.getLocalityName());
			sensorCopy.setZoneName(sensor.getZoneName());
			sensorCopy.setName(sensor.getName());
			sensorCopy.setReading(locality.getSensorReading(sensor.getZoneName(), sensor.getName()));
			
			sensorsCopy.add(sensorCopy);
		}
		
		return sensorsCopy;
	}
	
	public static void main(String[] args) {
		Options options = new Options();
		options.addOption("ORBInitialPort", true, "Port number of the Name Service.");
		CommandLineParser parser = new GnuParser();
		
		try {
			CommandLine cmd = parser.parse(options, args);
			
			if(!cmd.hasOption("ORBInitialPort")) {
				JavaFXRMCView.startUpError("Invalid Arguments", "ORBInitialPort argument required.");
				System.exit(1);
			}
		} catch (ParseException e) {
			JavaFXRMCView.startUpError("Invalid Arguments", "Could not parse command line arguments.");
			System.exit(1);
		}
		
		CorbaUtils.initOrb(args);
		CorbaUtils.initRootPOA();
		try {
			CorbaUtils.initNameService();
		} catch (ServerNotFoundException e) {
			JavaFXRMCView.startUpError("Name Service", "Name Service not found.");
			System.exit(1);
		}
		
		HomeUserManager homeUserManager = new TransientHomeUserManager();
		RMCView view = new JavaFXRMCView();
		
		RMCController controller = new RMCController(view, homeUserManager);
		
		// Start RMC-LMS Service
		RMCService service = new RMCService(controller);
		
		// Start RMC External Service
		HomeUserRMCService homeUserService = new HomeUserRMCService(controller);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				service.listen();
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				homeUserService.listen();
			}
		}).start();
		
		view.start(controller);
		
		// Terminate all threads when view is closed
		System.exit(0);
	}
}
