package u1171639.sensor.main.java.controller;

import u1171639.sensor.main.java.model.CorbaLMS;
import u1171639.sensor.main.java.model.LMS;
import u1171639.sensor.main.java.monitor.SimulatedWaterLevelMonitor;
import u1171639.sensor.main.java.monitor.WaterLevelMonitor;
import u1171639.sensor.main.java.service.SensorService;
import u1171639.sensor.main.java.utils.CorbaUtils;
import u1171639.sensor.main.java.utils.SensorConfig;
import u1171639.sensor.main.java.view.JavaFXSimulationView;
import u1171639.sensor.main.java.view.SimulationView;

public class SensorController {
	private LMS lms;
	private WaterLevelMonitor monitor;
	
	public SensorController(LMS lms, WaterLevelMonitor monitor) {
		this.lms = lms;
		this.monitor = monitor;
	}
	
	public void raiseAlarm() {
		lms.raiseAlarm();
	}
	
	public boolean isAlarmRaised() {
		return monitor.isAlarmRaised();
	}
	
	public static void main(String[] args) {
		/** Set monitoring configuration */
		// Read value every second and raise alarm if waterLevel >= 70
		SensorConfig.setMonitoringInterval(5000);
		SensorConfig.setWarningWaterLevel(70);
		SensorConfig.setZone("Zone1");
		
		CorbaUtils.initOrb(args);
		CorbaUtils.initRootPOA();
		CorbaUtils.initNameService();
		
		CorbaLMS lms = new CorbaLMS("LMSServer", null);
		SimulatedWaterLevelMonitor monitor = new SimulatedWaterLevelMonitor();
		
		SensorController controller = new SensorController(lms, monitor);
		SensorService service = new SensorService(controller);
		
		String ior = service.listen();
		lms.setServiceIOR(ior);
		lms.connect();
		
		monitor.setController(controller);
		monitor.monitorWaterLevel();
		
		SimulationView view = new JavaFXSimulationView();
		view.start(monitor);
	}
	
	
}
