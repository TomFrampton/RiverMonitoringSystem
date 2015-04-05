package u1171639.lms.main.java.controller;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import u1171639.lms.main.java.model.CorbaRMC;
import u1171639.lms.main.java.model.RMC;
import u1171639.lms.main.java.model.Sensor;
import u1171639.lms.main.java.service.LMS_RMCService;
import u1171639.lms.main.java.service.LMS_SensorService;
import u1171639.lms.main.java.utils.CorbaUtils;
import u1171639.lms.main.java.utils.Logger;
import u1171639.lms.main.java.utils.Logger.LogLevel;

public class LMSController {
	private Hashtable<String, List<Sensor>> sensors = new Hashtable<String, List<Sensor>>();
	private RMC rmc;
	
	public LMSController(RMC rmc) {
		this.rmc = rmc;
	}
	
	public void registerSensor(String zone, Sensor sensor) {
		if(!this.sensors.containsKey(zone)) {
			this.sensors.put(zone, new ArrayList<Sensor>());
		}
		this.sensors.get(zone).add(sensor);
		
		Logger.log(LogLevel.INFO, "Sensor registered in " + zone);
	}
	
	public List<Sensor> getSensorsByZone(String zone) {
		return this.sensors.get(zone);
	}
	
	public Hashtable<String, List<Sensor>> getSensors() {
		return this.sensors;
	}
	
	public void alarmRaised(String zone) {
		// Check alarm status on all sensors in the zone
		List<Sensor> zoneSensors = this.sensors.get(zone);
		Iterator<Sensor> it = zoneSensors.iterator();
		
		Logger.log(LogLevel.WARNING, "Alarm raised in " + zone);
		
		while(it.hasNext()) {
			Sensor sensor = it.next();
			if(sensor.isActive() && !sensor.isAlarmRaised()) {
				// Write log about unconfirmed alarm condition
				Logger.log(LogLevel.INFO, "Alarm in " + zone + " unconfirmed. Standing down.");
				return;
			}
		}
		// Alarm condition confirmed by all sensors in zone
		Logger.log(LogLevel.WARNING, "ALARM CONDITION CONFIRMED IN " + zone.toUpperCase() + ". INFORMING RMC");
		rmc.raiseAlarm();
	}
	
	public static void main(String[] args) {
		CorbaUtils.initOrb(args);
		CorbaUtils.initRootPOA();
		CorbaUtils.initNameService();
		
		RMC rmc = new CorbaRMC("RMCServer", null);
		LMSController controller = new LMSController(rmc);
		
		// Start service that listens to the sensors
		LMS_SensorService lmsSensorService = new LMS_SensorService(controller);
		lmsSensorService.listen();
	
		// Start service that listens to the RMC
//		LMS_RMCService lmsRmcService = new LMS_RMCService(controller);
//		String ior = lmsRmcService.listen();
	}
}
