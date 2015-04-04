package u1171639.sensor.test.unit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import u1171639.sensor.main.java.model.LMS;
import u1171639.sensor.main.java.model.MockLMS;
import u1171639.sensor.main.java.monitor.SimulatedWaterLevelMonitor;
import u1171639.sensor.main.java.utils.SensorConfig;
import u1171639.sensor.main.java.controller.SensorController;

public class SimulatedWaterLevelMonitorTest {

	private SimulatedWaterLevelMonitor monitor;
	private Object lock = new Object();
	private boolean alarmRaised;
	
	@Before
	public void setUp() throws Exception {
		this.monitor = new SimulatedWaterLevelMonitor();
		this.monitor.setController(new SensorController(null, null) {
			@Override
			public void raiseAlarm() {
				alarmRaised = true;
				synchronized (lock) {
					lock.notify();
				}
			}
		});
		
		SensorConfig.setMonitoringInterval(50);
		SensorConfig.setWarningWaterLevel(70);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMonitor() throws InterruptedException {
		monitor.monitorWaterLevel();
		
		monitor.setWaterLevel(69);
		synchronized (lock) {
			lock.wait(100);
		}
		
		assertFalse(alarmRaised);
		
		monitor.setWaterLevel(70);
		synchronized (lock) {
			lock.wait(100);
		}
		
		assertTrue(alarmRaised);
		
	}
	
	@Test
	public void testGetWaterLevel() {
		monitor.setWaterLevel(65);
		assertTrue(monitor.getWaterLevel() == 65);
		
		monitor.setWaterLevel(-1);
		assertTrue(monitor.getWaterLevel() == 65);
	}
	
	@Test
	public void testPauseMonitoring() throws InterruptedException {
		monitor.monitorWaterLevel();
		
		monitor.setWaterLevel(70);
		synchronized (lock) {
			lock.wait(100);
		}
		
		assertTrue(alarmRaised);
		
		monitor.pauseMonitoring();
		alarmRaised = false;
		synchronized (lock) {
			lock.wait(100);
		}
		
		assertFalse(alarmRaised);
		
		monitor.resumeMonitoring();
		synchronized (lock) {
			lock.wait(100);
		}
		
		assertTrue(alarmRaised);
		
	}

}
