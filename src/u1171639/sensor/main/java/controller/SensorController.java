package u1171639.sensor.main.java.controller;

import u1171639.sensor.main.java.client.CorbaLMSClient;
import u1171639.sensor.main.java.client.LMSClient;
import u1171639.sensor.main.java.monitor.SimulatedWaterLevelMonitor;
import u1171639.sensor.main.java.monitor.WaterLevelMonitor;
import u1171639.sensor.main.java.service.CorbaSensorService;
import u1171639.sensor.main.java.service.SensorService;

public class SensorController {
	public static void main(String[] args) {
		
		LMSClient lms = new CorbaLMSClient();
		WaterLevelMonitor monitor = new SimulatedWaterLevelMonitor(lms);
		SensorService service = new CorbaSensorService();
		// Start server thread
		
	}
}
