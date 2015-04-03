package u1171639.sensor.main.java.monitor;

import java.util.Random;
import java.util.Timer;

import u1171639.sensor.main.java.model.LMS;
import u1171639.sensor.main.java.utils.SensorConfig;
import u1171639.sensor.main.java.utils.SensorLogger;
import u1171639.sensor.main.java.utils.SensorLogger.LogLevel;

public class SimulatedWaterLevelMonitor implements WaterLevelMonitor, Runnable {

	private float waterLevel;
	private LMS lms;
	
	public SimulatedWaterLevelMonitor(LMS lms) {
		this.lms = lms;
	}
	
	@Override
	public void monitorWaterLevel() {
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public float getWaterLevel() {
		return this.waterLevel;
	}
	
	public void setWaterLevel(float waterLevel) {
		if(waterLevel >= 0) {
			this.waterLevel = waterLevel;
		}
	}

	@Override
	public void run() {
		try {
			for(;;Thread.sleep(SensorConfig.getMonitoringInterval())) {
				
				if(this.waterLevel >= SensorConfig.getWarningWaterLevel()) {
					SensorLogger.log(LogLevel.WARNING, "Water Level Reading Exceeded Warning Level - " + this.waterLevel);
					SensorLogger.log(LogLevel.WARNING, "Raising Alarm At LMS");
					
					lms.raiseAlarm();
				} else {
					SensorLogger.log(LogLevel.INFO, "Water Level Reading - " + this.waterLevel);
				}
				
				
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
