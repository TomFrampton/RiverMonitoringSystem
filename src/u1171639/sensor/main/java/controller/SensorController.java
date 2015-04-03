package u1171639.sensor.main.java.controller;

import u1171639.sensor.main.java.model.CorbaLMS;
import u1171639.sensor.main.java.model.LMS;
import u1171639.sensor.main.java.monitor.SimulatedWaterLevelMonitor;
import u1171639.sensor.main.java.service.SensorService;
import u1171639.sensor.main.java.utils.CorbaUtils;
import u1171639.sensor.main.java.utils.SensorConfig;
import u1171639.sensor.main.java.view.JavaFXSimulationView;
import u1171639.sensor.main.java.view.SimulationView;

public class SensorController {
	
	public SensorController() {
		
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
		
		LMS lms = new CorbaLMS("LMSServer");
		lms.connect();
		SimulatedWaterLevelMonitor monitor = new SimulatedWaterLevelMonitor(lms);
		
		SensorController controller = new SensorController();
		SensorService service = new SensorService(controller);
		service.listen();
		
		SimulationView view = new JavaFXSimulationView();
		
		monitor.monitorWaterLevel();
		view.start(monitor);
		// Start server thread
		
	}
}
