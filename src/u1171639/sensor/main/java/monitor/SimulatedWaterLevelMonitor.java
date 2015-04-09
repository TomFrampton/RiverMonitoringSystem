package u1171639.sensor.main.java.monitor;

import java.util.Random;
import java.util.Timer;

import u1171639.sensor.main.java.client.LMS;
import u1171639.sensor.main.java.controller.SensorController;
import u1171639.sensor.main.java.utils.SensorConfig;

public class SimulatedWaterLevelMonitor implements WaterLevelMonitor, Runnable {
	private float waterLevel;
	private SensorController controller;
	// Used for pausing the monitoring thread
	private Object lock = new Object();
	private boolean paused;

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
	public boolean isAlarmRaised() {
		return this.waterLevel >= SensorConfig.getWarningWaterLevel();
	}
	
	@Override
	public void resumeMonitoring() {
		this.paused = false;
		synchronized(lock) {
			lock.notifyAll();
		}
	}

	@Override
	public void pauseMonitoring() {
		this.paused = true;
	}

	@Override
	public void run() {
		try {
			for(;;Thread.sleep(SensorConfig.getMonitoringInterval())) {
				if(!paused) {
					if(this.waterLevel >= SensorConfig.getWarningWaterLevel()) {
						//Logger.log(LogLevel.WARNING, "Water Level Reading Exceeded Warning Level - " + this.waterLevel);
						//Logger.log(LogLevel.WARNING, "Raising Alarm At LMS");
						
						controller.raiseAlarm();
					} else {
						//Logger.log(LogLevel.INFO, "Water Level Reading - " + this.waterLevel);
					}
				} else {
					synchronized(lock) {
						lock.wait();
					}
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setController(SensorController controller) {
		this.controller = controller;
	}
}
