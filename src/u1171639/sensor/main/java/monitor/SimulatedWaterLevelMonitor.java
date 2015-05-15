package u1171639.sensor.main.java.monitor;

import java.util.Random;
import java.util.Timer;

import u1171639.sensor.main.java.client.LMS;
import u1171639.sensor.main.java.controller.SensorController;
import u1171639.sensor.main.java.utils.SensorConfig;
import u1171639.sensor.main.java.view.fxml.SimulationViewController;

public class SimulatedWaterLevelMonitor implements WaterLevelMonitor, Runnable {
	private double waterLevel;
	private SensorController controller;
	// Used for pausing the monitoring thread
	private Object lock = new Object();
	private boolean paused;
	
	private SimulationViewController viewController;

	@Override
	public void monitorWaterLevel() {
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public double getWaterLevel() {
		return this.waterLevel;
	}
	
	public void setWaterLevel(double waterLevel) {
		if(waterLevel >= 0) {
			this.waterLevel = waterLevel;
		}
	}
	
	@Override
	public boolean isAlarmRaised() {
		return this.waterLevel >= SensorConfig.getWarningThreshold();
	}
	
	@Override
	public void resumeMonitoring() {
		this.paused = false;
		synchronized(lock) {
			lock.notifyAll();
		}
		
		if(this.viewController != null) {
			this.viewController.sensorActivated();
		}
	}

	@Override
	public void pauseMonitoring() {
		this.paused = true;
		
		if(this.viewController != null) {
			this.viewController.sensorDeactivated();
		}
	}
	
	public void setController(SensorController controller) {
		this.controller = controller;
	}
	
	public void setViewController(SimulationViewController viewController) {
		this.viewController = viewController;
	}

	@Override
	public void run() {
		try {
			for(;;Thread.sleep(SensorConfig.getMonitoringInterval())) {
				if(!paused) {
					if(this.waterLevel >= SensorConfig.getWarningThreshold()) {
						controller.raiseAlarm();
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
	
	
}
