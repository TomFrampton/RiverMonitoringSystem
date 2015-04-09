package u1171639.shared.test.integration;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import u1171639.lms.main.java.controller.LMSController;
import u1171639.lms.main.java.model.CorbaRMC;
import u1171639.lms.main.java.model.RMC;
import u1171639.lms.main.java.model.Sensor;
import u1171639.lms.main.java.service.LMS_RMCService;
import u1171639.lms.main.java.service.LMS_SensorService;
import u1171639.lms.main.java.utils.LMSConfig;
import u1171639.rmc.main.java.controller.RMCController;
import u1171639.rmc.main.java.model.LMS;
import u1171639.rmc.main.java.model.Locality;
import u1171639.rmc.main.java.model.Zone;
import u1171639.rmc.main.java.service.RMCService;
import u1171639.rmc.test.mocks.MockRMC;
import u1171639.sensor.main.java.controller.SensorController;
import u1171639.sensor.main.java.model.CorbaLMS;
import u1171639.sensor.main.java.monitor.SimulatedWaterLevelMonitor;
import u1171639.sensor.main.java.service.SensorService;
import u1171639.sensor.main.java.utils.SensorConfig;
import u1171639.sensor.test.mocks.MockLogger;

public class RMC_LMS_SensorTest {
	// RMC objects
	private RMCController rmcController;
	private CorbaRMC rmc;
	private boolean rmcAlarmRaised = false;
	
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
		
		// Start up the RMC
		u1171639.rmc.main.java.utils.CorbaUtils.initOrb(args);
		u1171639.rmc.main.java.utils.CorbaUtils.initRootPOA();
		u1171639.rmc.main.java.utils.CorbaUtils.initNameService();
		
		this.rmcController = new RMCController() {
			@Override
			public void raiseAlarm(String locality, String zone) {
				super.raiseAlarm(locality, zone);
				rmcAlarmRaised = true;
			}
		};
		RMCService service = new RMCService(this.rmcController);
		new Thread(new Runnable() {
			@Override
			public void run() {
				service.listen();
			}
		}).start();
		// Brief wait while rmcService sets up
		Thread.sleep(100);
		
		
		// Start up LMS and connect to RMC
		u1171639.lms.main.java.utils.CorbaUtils.initOrb(args);
		u1171639.lms.main.java.utils.CorbaUtils.initRootPOA();
		u1171639.lms.main.java.utils.CorbaUtils.initNameService();
		
		LMSConfig.setLocality("Locality1");
		
		this.rmc = new CorbaRMC("RMCServer", null);
		this.lmsController = new LMSController(this.rmc, new MockLogger());
		
		// Start LMS service for listening for Sensors
		final LMS_SensorService lmsSensorService = new LMS_SensorService(lmsController);
		new Thread(new Runnable() {
			@Override
			public void run() {
				lmsSensorService.listen("Locality1_LMSServer");
				
			}
		}).start();
		// Brief wait while lmsService sets up
		Thread.sleep(100);
		
		// Start LMS service for listening to RMC
		final LMS_RMCService lmsRmcService = new LMS_RMCService(lmsController);
		// Use Array to get around requiring a variable that is being accessed from an anonymous class must be final
		final String[] lmsRmcServiceIor = new String[1];
		new Thread(new Runnable() {
			@Override
			public void run() {
				lmsRmcServiceIor[0] = lmsRmcService.listen();
			}
		}).start();
		// Brief wait while lmsService sets up
		Thread.sleep(100);
		
		this.rmc.setServiceIOR(lmsRmcServiceIor[0]);
		this.rmc.connect();
				
		// Mock up two sensors
		u1171639.sensor.main.java.utils.CorbaUtils.initOrb(args);
		u1171639.sensor.main.java.utils.CorbaUtils.initRootPOA();
		u1171639.sensor.main.java.utils.CorbaUtils.initNameService();
		
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
		assertFalse(this.rmcAlarmRaised);
		
		monitor1.setWaterLevel(69);
		monitor2.setWaterLevel(70);
		synchronized(lock2) { lock2.wait(); }
		assertFalse(this.rmcAlarmRaised);
		
		monitor1.setWaterLevel(70);
		synchronized(lock1) { lock1.wait(); }
		synchronized(lock2) { lock2.wait(); }
		assertTrue(this.rmcAlarmRaised);
		
		// Reset alarm
		this.rmcAlarmRaised = false;
		monitor2.setWaterLevel(69);
		synchronized(lock1) { lock1.wait(); }
		assertFalse(this.rmcAlarmRaised);
		
		this.lmsController.getSensorsByZone("Zone1").get(1).deactivate();
		synchronized(lock1) { lock1.wait(); }
		assertTrue(this.rmcAlarmRaised);	
	}
	
	@Test
	public void testGetLocalityInfo() throws InterruptedException {
		Locality locality = this.rmcController.getLocalityByName("Locality1");
		LMS lms = locality.getLms();
		
		assertTrue(lms != null);
		
		locality.updateLocalityInfo();
		List<Zone> zones = locality.getZones();
		assertTrue(zones.size() == 1);
		assertFalse(zones.get(0).isAlarmRaised());
		
		SimulatedWaterLevelMonitor monitor1 = (SimulatedWaterLevelMonitor) this.sensor1.getMonitor();
		SimulatedWaterLevelMonitor monitor2 = (SimulatedWaterLevelMonitor) this.sensor2.getMonitor();
		
		monitor1.setWaterLevel(70);
		monitor2.setWaterLevel(70);
		synchronized(lock2) { lock2.wait(); }
		
		locality.updateLocalityInfo();
		zones = locality.getZones();
		assertTrue(zones.get(0).isAlarmRaised());
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
