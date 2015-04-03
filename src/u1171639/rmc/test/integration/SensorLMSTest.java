package u1171639.rmc.test.integration;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import u1171639.lms.main.java.controller.LMSController;
import u1171639.lms.main.java.model.CorbaRMC;
import u1171639.lms.main.java.model.RMC;
import u1171639.lms.main.java.service.LMSService;
import u1171639.rmc.test.mocks.MockRMC;
import u1171639.sensor.main.java.controller.SensorController;
import u1171639.sensor.main.java.model.CorbaLMS;
import u1171639.sensor.main.java.monitor.SimulatedWaterLevelMonitor;
import u1171639.sensor.main.java.service.SensorService;
import u1171639.sensor.main.java.utils.SensorConfig;

public class SensorLMSTest {
	// RMC stub
	private MockRMC rmc;
	
	// LMS objects
	
	// Sensor objects
	private SimulatedWaterLevelMonitor monitor1;
	private SimulatedWaterLevelMonitor monitor2;
	
	// Lock for each sensor
	private Object lock1 = new Object();
	private Object lock2 = new Object();
	
	@Before
	public void setUp() throws Exception {
		String[] args = { "-ORBInitialPort", "1050" };
		
		// Start up LMS with RMC stubbed
		u1171639.lms.main.java.utils.CorbaUtils.initOrb(args);
		u1171639.lms.main.java.utils.CorbaUtils.initRootPOA();
		u1171639.lms.main.java.utils.CorbaUtils.initNameService();
		
		this.rmc = new MockRMC();
		LMSController controller = new LMSController(this.rmc);
		final LMSService lmsService = new LMSService(controller);
		new Thread(new Runnable() {
			@Override
			public void run() {
				lmsService.listen();
			}
		}).start();
				
		// Mock up two sensors
		u1171639.sensor.main.java.utils.CorbaUtils.initOrb(args);
		u1171639.sensor.main.java.utils.CorbaUtils.initRootPOA();
		u1171639.sensor.main.java.utils.CorbaUtils.initNameService();
		
		SensorConfig.setMonitoringInterval(50);
		SensorConfig.setWarningWaterLevel(70);
		SensorConfig.setZone("Zone1");
		
		// Sensor 1
		this.monitor1 = mockSensor(this.lock1);
		this.monitor2 = mockSensor(this.lock2);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testRaiseAlarm() throws InterruptedException {
		this.monitor1.setWaterLevel(70);
		synchronized(lock1) { lock1.wait(); }
		assertFalse(this.rmc.isAlarmRaised());
		
		this.monitor1.setWaterLevel(69);
		this.monitor2.setWaterLevel(70);
		synchronized(lock2) { lock2.wait(); }
		assertFalse(this.rmc.isAlarmRaised());
		
		this.monitor1.setWaterLevel(70);
		synchronized(lock1) { lock1.wait(); }
		synchronized(lock2) { lock2.wait(); }
		assertTrue(this.rmc.isAlarmRaised());
	}
	
	private SimulatedWaterLevelMonitor mockSensor(final Object lock) {
		CorbaLMS lms = new CorbaLMS("LMSServer", null);
		SimulatedWaterLevelMonitor monitor = new SimulatedWaterLevelMonitor();

		// Modify sensor controller to notify us when the alarm has been raised as raising an
		// alarm is an asynchronous action due to the water level monitoring running on
		// a separate thread
		SensorController sensorController = new SensorController(lms, monitor) {
			@Override
			public void raiseAlarm() {
				super.raiseAlarm();
				synchronized (lock) {
					lock.notify();
				}
			}
		};
		SensorService sensorService = new SensorService(sensorController);
		
		monitor.setController(sensorController);
		monitor.monitorWaterLevel();
		
		String ior = sensorService.listen();
		lms.setServiceIOR(ior);
		lms.connect();
		
		return monitor;
	}
}
