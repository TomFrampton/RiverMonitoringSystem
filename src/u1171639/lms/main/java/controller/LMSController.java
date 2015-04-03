package u1171639.lms.main.java.controller;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import u1171639.lms.main.java.model.RMC;
import u1171639.lms.main.java.model.Sensor;
import u1171639.lms.main.java.service.LMSService;
import u1171639.lms.main.java.utils.CorbaUtils;

public class LMSController {
	private Hashtable<String, List<Sensor>> sensors = new Hashtable<String, List<Sensor>>();
	private RMC rmc;
	
	public void registerSensor(String zone, Sensor sensor) {
		if(!this.sensors.containsKey(zone)) {
			this.sensors.put(zone, new ArrayList<Sensor>());
		}
		this.sensors.get(zone).add(sensor);
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
		
		while(it.hasNext()) {
			Sensor sensor = it.next();
			if(!sensor.isAlarmRaised()) {
				// Write log about unconfirmed alarm condition
				System.out.println("Unconfirmed Alarm");
				return;
			}
		}
		// Alarm condition confirmed by all sensors in zone
		System.out.println("ALARM");
		
		// Call RMC
	}
	
	public static void main(String[] args) {
		CorbaUtils.initOrb(args);
		CorbaUtils.initRootPOA();
		CorbaUtils.initNameService();
		
		LMSController controller = new LMSController();
		LMSService service = new LMSService(controller);
		service.listen();
	}
}
