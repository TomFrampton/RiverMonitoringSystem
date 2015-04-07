package u1171639.lms.main.java.controller;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import u1171639.lms.main.java.model.CorbaRMC;
import u1171639.lms.main.java.model.RMC;
import u1171639.lms.main.java.model.Sensor;
import u1171639.lms.main.java.model.Zone;
import u1171639.lms.main.java.service.LMS_RMCService;
import u1171639.lms.main.java.service.LMS_SensorService;
import u1171639.lms.main.java.utils.CorbaUtils;
import u1171639.lms.main.java.utils.LMSConfig;
import u1171639.lms.main.java.utils.Logger;
import u1171639.lms.main.java.utils.Logger.LogLevel;

public class LMSController {
	private List<Zone> zones = new ArrayList<Zone>();
	private RMC rmc;
	
	public LMSController(RMC rmc) {
		this.rmc = rmc;
	}
	
	public void registerSensor(String zoneName, Sensor sensor) {
		Zone zone = this.getZoneByName(zoneName);
		if(zone == null) {
			zone = new Zone();
			zone.setName(zoneName);
			this.zones.add(zone);
		}
		
		zone.getSensors().add(sensor);	
		
		Logger.log(LogLevel.INFO, "Sensor registered in " + LMSConfig.getLocality() + " - " + zoneName);
	}
	
	public List<Sensor> getSensorsByZone(String zoneName) {
		Zone zone = this.getZoneByName(zoneName);
		if(zone != null) {
			return zone.getSensors();
		} else {
			return null;
		}
	}
	
	public Zone getZoneByName(String zoneName) {
		Iterator<Zone> it = this.zones.iterator();
		while(it.hasNext()) {
			Zone next = it.next();
			if(next.getName().equals(zoneName)) {
				return next;
			}
		}
		
		return null;
	}
	
	public List<Zone> getAllZones() {
		return this.zones;
	}
	
	public void alarmRaised(String zoneName) {
		Zone zone = this.getZoneByName(zoneName);
		if(zone != null) {
			Logger.log(LogLevel.WARNING, "Alarm raised in " + zoneName);
			
			if(zone.confirmAlarm()) {
				// Alarm condition confirmed by all sensors in zone
				Logger.log(LogLevel.WARNING, "ALARM CONDITION CONFIRMED IN " + zoneName.toUpperCase() + ". INFORMING RMC");
				rmc.raiseAlarm(zoneName);
			} else {
				// Write log about unconfirmed alarm condition
				Logger.log(LogLevel.INFO, "Alarm in " + zoneName + " unconfirmed. Standing down.");
			}
		}
		
	}
	
	public static void main(String[] args) {
		Options options = new Options();
		options.addOption("locality", true, "The name of the Locality this LMS resides in.");
		options.addOption("ORBInitialPort", true, "Port number of the Name Service.");
		CommandLineParser parser = new GnuParser();
		
		try {
			CommandLine cmd = parser.parse(options, args);
			
			if(!cmd.hasOption("locality")) {
				System.err.println("-locality option required for LMS.");
				System.exit(1);
			} else {
				LMSConfig.setLocality(cmd.getOptionValue("locality"));
			}
			
			if(!cmd.hasOption("ORBInitialPort")) {
				System.err.println("-ORBInitialPort option required.");
				System.exit(1);
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		CorbaUtils.initOrb(args);
		CorbaUtils.initRootPOA();
		CorbaUtils.initNameService();
		
		CorbaRMC rmc = new CorbaRMC("RMCServer", null);
		LMSController controller = new LMSController(rmc);
		
		// Start service that listens to the sensors
		String lmsSensorServiceName = LMSConfig.getLocality() + "_LMSServer";
		
		LMS_SensorService lmsSensorService = new LMS_SensorService(controller);
		lmsSensorService.listen(lmsSensorServiceName);
		
		Logger.log(LogLevel.INFO, "LMS in " + LMSConfig.getLocality() + " listening...");
	
		// Start service that listens to the RMC
		LMS_RMCService lmsRmcService = new LMS_RMCService(controller);
		String ior = lmsRmcService.listen();
		
		rmc.setServiceIOR(ior);
		rmc.connect();
	}
}
