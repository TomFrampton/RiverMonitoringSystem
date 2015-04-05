package u1171639.lms.main.java.controller;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import u1171639.lms.main.java.model.CorbaRMC;
import u1171639.lms.main.java.model.RMC;
import u1171639.lms.main.java.model.Sensor;
import u1171639.lms.main.java.model.Zone;
import u1171639.lms.main.java.service.LMS_RMCService;
import u1171639.lms.main.java.service.LMS_SensorService;
import u1171639.lms.main.java.utils.CorbaUtils;
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
		
		Logger.log(LogLevel.INFO, "Sensor registered in " + zone);
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
	
	public void alarmRaised(String zoneName) {
		// Check alarm status on all sensors in the zone
		List<Sensor> zoneSensors = this.getSensorsByZone(zoneName);
		Iterator<Sensor> it = zoneSensors.iterator();
		
		Logger.log(LogLevel.WARNING, "Alarm raised in " + zoneName);
		
		while(it.hasNext()) {
			Sensor sensor = it.next();
			if(sensor.isActive() && !sensor.isAlarmRaised()) {
				// Write log about unconfirmed alarm condition
				Logger.log(LogLevel.INFO, "Alarm in " + zoneName + " unconfirmed. Standing down.");
				return;
			}
		}
		// Alarm condition confirmed by all sensors in zone
		Logger.log(LogLevel.WARNING, "ALARM CONDITION CONFIRMED IN " + zoneName.toUpperCase() + ". INFORMING RMC");
		rmc.raiseAlarm();
	}
	
	public static void main(String[] args) {
		CorbaUtils.initOrb(args);
		CorbaUtils.initRootPOA();
		CorbaUtils.initNameService();
		
		CorbaRMC rmc = new CorbaRMC("RMCServer", null);
		LMSController controller = new LMSController(rmc);
		
		// Start service that listens to the sensors
		LMS_SensorService lmsSensorService = new LMS_SensorService(controller);
		lmsSensorService.listen();
	
		// Start service that listens to the RMC
		LMS_RMCService lmsRmcService = new LMS_RMCService(controller);
		String ior = lmsRmcService.listen();
		
		rmc.setServiceIOR(ior);
		rmc.connect();
	}
}
