package u1171639.shared.test.integration;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import u1171639.lms.main.java.client.CorbaRMC;
import u1171639.lms.main.java.client.RMC;
import u1171639.lms.main.java.client.Sensor;
import u1171639.lms.main.java.controller.LMSController;
import u1171639.lms.main.java.service.LMS_SensorService;
import u1171639.rmc.test.mocks.MockRMC;
import u1171639.sensor.main.java.client.CorbaLMS;
import u1171639.sensor.main.java.controller.SensorController;
import u1171639.sensor.main.java.monitor.SimulatedWaterLevelMonitor;
import u1171639.sensor.main.java.service.SensorService;
import u1171639.sensor.main.java.utils.SensorConfig;
import u1171639.sensor.test.mocks.MockLogger;
import u1171639.shared.main.java.utils.CorbaUtils;

public class LMS_SensorTest {
	// RMC stub
	private MockRMC rmc;
	
	// LMS objects
	private LMSController lmsController;
	
	// Sensor objects
	private SensorController sensor1;
	private SensorController sensor2;
	
	// Lock for each sensor
	private Object lock1 = new Object();
	private Object lock2 = new Object();
	
	@Before
	public void setUp() throws Exception {
		String[] args = { "-ORBInitialPort", "1050" };
		
		// Start up LMS with RMC stubbed
		CorbaUtils.initOrb(args);
		CorbaUtils.initRootPOA();
		CorbaUtils.initNameService();
		
		this.rmc = new MockRMC();
		this.lmsController = new LMSController(this.rmc, new MockLogger());
		final LMS_SensorService lmsService = new LMS_SensorService(lmsController);
		new Thread(new Runnable() {
			@Override
			public void run() {
				lmsService.listen("Locality1_LMSServer");
			}
		}).start();
		// Brief wait while lmsService sets up
		Thread.sleep(100);
				
		// Mock up two sensors
		SensorConfig.setMonitoringInterval(50);
		SensorConfig.setWarningWaterLevel(70);
		SensorConfig.setLocality("Locality1");
		SensorConfig.setZone("Zone1");
		
		// Sensor 1
		this.sensor1 = mockSensor(this.lock1);
		this.sensor2 = mockSensor(this.lock2);
		
		SimulatedWaterLevelMonitor monitor1 = (SimulatedWaterLevelMonitor) this.sensor1.getMonitor();
		SimulatedWaterLevelMonitor monitor2 = (SimulatedWaterLevelMonitor) this.sensor2.getMonitor();
		
		monitor1.monitorWaterLevel();
		monitor2.monitorWaterLevel();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testRaiseAlarm() throws InterruptedException {
		SimulatedWaterLevelMonitor monitor1 = (SimulatedWaterLevelMonitor) this.sensor1.getMonitor();
		SimulatedWaterLevelMonitor monitor2 = (SimulatedWaterLevelMonitor) this.sensor2.getMonitor();
		
		monitor1.setWaterLevel(70);
		synchronized(lock1) { lock1.wait(); }
		assertFalse(this.rmc.isAlarmRaised());
		
		monitor1.setWaterLevel(69);
		monitor2.setWaterLevel(70);
		synchronized(lock2) { lock2.wait(); }
		assertFalse(this.rmc.isAlarmRaised());
		
		monitor1.setWaterLevel(70);
		synchronized(lock1) { lock1.wait(); }
		synchronized(lock2) { lock2.wait(); }
		assertTrue(this.rmc.isAlarmRaised());
		
		this.rmc.resetAlarm();
		monitor2.setWaterLevel(69);
		synchronized(lock1) { lock1.wait(); }
		assertFalse(this.rmc.isAlarmRaised());
		
		this.lmsController.getSensorsByZone("Zone1").get(1).deactivate();
		synchronized(lock1) { lock1.wait(); }
		assertTrue(this.rmc.isAlarmRaised());
		
		
	}
	
	@Test
	public void testRemoteSensorActivation() throws InterruptedException {
		List<Sensor> sensors = this.lmsController.getSensorsByZone("Zone1");
		
		sensors.get(0).deactivate();
		assertFalse(this.sensor1.isActive());
		assertFalse(sensors.get(0).isActive());
		
		sensors.get(0).activate();
		assertTrue(this.sensor1.isActive());
		assertTrue(sensors.get(0).isActive());
	}
	
	private SensorController mockSensor(final Object lock) {
		CorbaLMS lms = new CorbaLMS(SensorConfig.getLocality() + "_LMSServer", null);
		SimulatedWaterLevelMonitor monitor = new SimulatedWaterLevelMonitor();

		// Modify sensor controller to notify us when the alarm has been raised as raising an
		// alarm is an asynchronous action due to the water level monitoring running on
		// a separate thread
		SensorController sensorController = new SensorController(lms, monitor, new MockLogger()) {
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
		
		String ior = sensorService.listen();
		lms.setServiceIOR(ior);
		lms.connect();
		
		return sensorController;
	}
}
