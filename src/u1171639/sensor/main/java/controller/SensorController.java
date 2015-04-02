package u1171639.sensor.main.java.controller;

import u1171639.sensor.main.java.client.CorbaLMSClient;
import u1171639.sensor.main.java.client.LMSClient;
import u1171639.sensor.main.java.monitor.SimulatedWaterLevelMonitor;
import u1171639.sensor.main.java.monitor.WaterLevelMonitor;
import u1171639.sensor.main.java.service.CorbaSensorService;
import u1171639.sensor.main.java.service.SensorService;
import u1171639.sensor.main.java.utils.SensorConfig;
import u1171639.sensor.main.java.view.JavaFXSimulationView;
import u1171639.sensor.main.java.view.SimulationView;

public class SensorController {
	public static void main(String[] args) {
		/** Set monitoring configuration */
		// Read value every second and raise alarm if waterLevel >= 70
		SensorConfig.setMonitoringInterval(5000);
		SensorConfig.setWarningWaterLevel(70);
		
		CorbaLMSClient lms = new CorbaLMSClient();
		lms.connect(args, "LMSSensorServer");
		SimulatedWaterLevelMonitor monitor = new SimulatedWaterLevelMonitor(lms);
		
		//CorbaSensorService service = new CorbaSensorService(monitor);
		//service.connect(args);
		
		SimulationView view = new JavaFXSimulationView();
		
		monitor.monitorWaterLevel();
		view.start(monitor);
		// Start server thread
		
	}
}
