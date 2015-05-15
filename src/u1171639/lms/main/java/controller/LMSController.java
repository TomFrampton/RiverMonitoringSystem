package u1171639.lms.main.java.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import u1171639.lms.main.java.client.CorbaRMC;
import u1171639.lms.main.java.client.RMC;
import u1171639.lms.main.java.client.Sensor;
import u1171639.lms.main.java.model.LMSZone;
import u1171639.lms.main.java.service.LMS_RMCService;
import u1171639.lms.main.java.service.LMS_SensorService;
import u1171639.lms.main.java.utils.LMSConfig;
import u1171639.shared.main.java.exception.ConnectionException;
import u1171639.shared.main.java.exception.ServerNotFoundException;
import u1171639.shared.main.java.logging.LogItem;
import u1171639.shared.main.java.logging.Logger;
import u1171639.shared.main.java.logging.TransientLogger;
import u1171639.shared.main.java.utils.CorbaUtils;

public class LMSController {
	private List<LMSZone> zones = new ArrayList<LMSZone>();
	private RMC rmc;
	private Logger logger;
	
	public LMSController(RMC rmc, Logger logger) {
		this.rmc = rmc;
		this.logger = logger;
	}
	
	public String registerSensor(String zoneName, Sensor sensor) {
		LMSZone zone = this.getZoneByName(zoneName);
		if(zone == null) {
			zone = new LMSZone();
			zone.setName(zoneName);
			this.zones.add(zone);
		}
		
		String sensorName = zoneName.toUpperCase() + "_SENSOR" + (zone.getSensors().size() + 1);
		sensor.setName(sensorName);
		zone.getSensors().add(sensor);
		
		// Inform RMC that a sensor has been added
		rmc.sensorAdded();
		
		logger.logEvent(new LogItem("Sensor registered in " + LMSConfig.getLocality() + " - " + zoneName, LogItem.Event.CONNECTION));
		
		return sensorName;
	}
	
	public List<Sensor> getSensorsByZone(String zoneName) {
		LMSZone zone = this.getZoneByName(zoneName);
		if(zone != null) {
			return zone.getSensors();
		} else {
			return null;
		}
	}
	
	public LMSZone getZoneByName(String zoneName) {
		Iterator<LMSZone> it = this.zones.iterator();
		while(it.hasNext()) {
			LMSZone next = it.next();
			if(next.getName().equals(zoneName)) {
				return next;
			}
		}
		
		return null;
	}
	
	public List<LMSZone> getAllZones() {
		return this.zones;
	}
	
	public void alarmRaised(String zoneName) {
		LMSZone zone = this.getZoneByName(zoneName);
		// Don't raise another alarm in this zone if the alarm is already raised
		if(zone != null && !zone.isAlarmRaised()) {
			logger.logEvent(new LogItem("Alarm raised in " + zoneName, LogItem.Event.ALARM_RAISED));
			
			if(zone.confirmAlarm()) {
				// Alarm condition confirmed by all sensors in zone
				logger.logEvent(new LogItem("ALARM CONDITION CONFIRMED IN " + zoneName.toUpperCase(), LogItem.Event.ALARM_CONFIRMED));
				rmc.raiseAlarm(zoneName);
			} else {
				// Write log about unconfirmed alarm condition
				logger.logEvent(new LogItem("Alarm in " + zoneName + " unconfirmed. Standing down.", LogItem.Event.ALARM_IGNORED));
			}
		}
	}
	
	public List<LogItem> getAllLogs() {
		return this.logger.getAllLogs();
	}
	
	public boolean activateSensor(String zoneName, String sensorName) {
		LMSZone zone = this.getZoneByName(zoneName);
		Sensor sensor = zone.getSensorByName(sensorName);
		logger.logEvent(new LogItem(sensorName + " in " + zoneName + " activated.", LogItem.Event.SENSOR_ACTIVATION));
		return sensor.activate();
	}
	
	public boolean deactivateSensor(String zoneName, String sensorName) {
		LMSZone zone = this.getZoneByName(zoneName);
		Sensor sensor = zone.getSensorByName(sensorName);
		logger.logEvent(new LogItem(sensorName + " in " + zoneName + " deactivated.", LogItem.Event.SENSOR_ACTIVATION));
		return sensor.deactivate();
	}
	
	public boolean setWarningThreshold(String zoneName, String sensorName, double threshold) {
		LMSZone zone = this.getZoneByName(zoneName);
		Sensor sensor = zone.getSensorByName(sensorName);
		logger.logEvent(new LogItem(sensorName + " in " + zoneName + " threshold changed to "  + threshold + ".", LogItem.Event.SENSOR_THRESHOLD));
		return sensor.setThreshold(threshold);
	}
	
	public double getSensorReading(String zoneName, String sensorName) {
		LMSZone zone = this.getZoneByName(zoneName);
		Sensor sensor = zone.getSensorByName(sensorName);
		return sensor.getReading();
	}
	
	public boolean resetAlarm(String zoneName) {
		LMSZone zone = this.getZoneByName(zoneName);
		logger.logEvent(new LogItem("Alarm reset in " + zoneName, LogItem.Event.ALARM_RESET));	
		return zone.resetAlarms();
	}
	
	public static void main(String[] args) {
		Options options = new Options();
		options.addOption("locality", true, "The name of the Locality this LMS resides in.");
		options.addOption("ORBInitialPort", true, "Port number of the Name Service.");
		CommandLineParser parser = new GnuParser();
		List<String> errors = new ArrayList<String>();
		
		try {
			CommandLine cmd = parser.parse(options, args);
			
			if(!cmd.hasOption("locality")) {
				errors.add("-locality option required for LMS.");
			} else {
				LMSConfig.setLocality(cmd.getOptionValue("locality"));
			}
			
			if(!cmd.hasOption("ORBInitialPort")) {
				errors.add("-ORBInitialPort option required.");
			}
			
		} catch (ParseException e) {
			errors.add("Could not parse command line arguments.");
		}
		
		if(!errors.isEmpty()) {
			for(String error : errors) {
				System.err.println(error);
			}
			System.exit(1);
		}
				
		CorbaUtils.initOrb(args);
		CorbaUtils.initRootPOA();
		try {
			CorbaUtils.initNameService();
		} catch (ServerNotFoundException e) {
			System.err.println("Name service not found");
			System.exit(1);
		}
		
		CorbaRMC rmc = new CorbaRMC("RMCServer", null);
		Logger logger = new TransientLogger();
		LMSController controller = new LMSController(rmc, logger);
		
		// Start service that listens to the sensors
		String lmsSensorServiceName = LMSConfig.getLocality() + "_LMSServer";
		
		LMS_SensorService lmsSensorService = new LMS_SensorService(controller);
		lmsSensorService.listen(lmsSensorServiceName);
		
		//SimpleLogger.log(LogLevel.INFO, "LMS in " + LMSConfig.getLocality() + " listening...");
	
		// Start service that listens to the RMC
		LMS_RMCService lmsRmcService = new LMS_RMCService(controller);
		String ior = lmsRmcService.listen();
		
		rmc.setServiceIOR(ior);
		try {
			rmc.connect();
		} catch (ServerNotFoundException | ConnectionException e) {
			System.err.println("RMC for region not found.");
			System.exit(1);
		}
		
		System.out.println("LMS running in " + LMSConfig.getLocality() + "...");
	}
}
