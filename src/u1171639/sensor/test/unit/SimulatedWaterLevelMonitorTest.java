package u1171639.sensor.test.unit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import u1171639.sensor.main.java.monitor.SimulatedWaterLevelMonitor;
import u1171639.sensor.main.java.utils.SensorConfig;
import u1171639.sensor.main.java.controller.SensorController;
import u1171639.sensor.test.mocks.MockLogger;

public class SimulatedWaterLevelMonitorTest {

	private SimulatedWaterLevelMonitor monitor;
	private Object lock = new Object();
	private boolean alarmRaised;
	
	@Before
	public void setUp() throws Exception {
		this.monitor = new SimulatedWaterLevelMonitor();
		this.monitor.setController(new SensorController(null, null, new MockLogger()) {
			@Override
			public void raiseAlarm() {
				SimulatedWaterLevelMonitorTest.this.alarmRaised = true;
				synchronized (SimulatedWaterLevelMonitorTest.this.lock) {
					SimulatedWaterLevelMonitorTest.this.lock.notify();
				}
			}
		});
		
		SensorConfig.setMonitoringInterval(50);
		SensorConfig.setWarningThreshold(70);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMonitor() throws InterruptedException {
		this.monitor.monitorWaterLevel();
		
		this.monitor.setWaterLevel(69);
		synchronized (this.lock) {
			this.lock.wait(100);
		}
		
		assertFalse(this.alarmRaised);
		
		this.monitor.setWaterLevel(70);
		synchronized (this.lock) {
			this.lock.wait(100);
		}
		
		assertTrue(this.alarmRaised);
		
	}
	
	@Test
	public void testGetWaterLevel() {
		this.monitor.setWaterLevel(65);
		assertTrue(this.monitor.getWaterLevel() == 65);
		
		this.monitor.setWaterLevel(-1);
		assertTrue(this.monitor.getWaterLevel() == 65);
	}
	
	@Test
	public void testPauseMonitoring() throws InterruptedException {
		this.monitor.monitorWaterLevel();
		
		this.monitor.setWaterLevel(70);
		synchronized (this.lock) {
			this.lock.wait(100);
		}
		
		assertTrue(this.alarmRaised);
		
		this.monitor.pauseMonitoring();
		this.alarmRaised = false;
		synchronized (this.lock) {
			this.lock.wait(100);
		}
		
		assertFalse(this.alarmRaised);
		
		this.monitor.resumeMonitoring();
		synchronized (this.lock) {
			this.lock.wait(100);
		}
		
		assertTrue(this.alarmRaised);
		
	}

}
