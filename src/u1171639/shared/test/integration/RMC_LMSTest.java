package u1171639.shared.test.integration;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import u1171639.lms.main.java.client.CorbaRMC;
import u1171639.lms.main.java.controller.LMSController;
import u1171639.lms.main.java.service.LMS_RMCService;
import u1171639.lms.main.java.service.LMS_SensorService;
import u1171639.lms.main.java.utils.LMSConfig;
import u1171639.rmc.main.java.controller.RMCController;
import u1171639.rmc.main.java.service.RMCService;
import u1171639.rmc.test.mocks.MockHomeUserManager;
import u1171639.rmc.test.mocks.MockRMCView;
import u1171639.sensor.main.java.monitor.SimulatedWaterLevelMonitor;
import u1171639.sensor.main.java.utils.SensorConfig;
import u1171639.sensor.test.mocks.MockLogger;
import u1171639.shared.main.java.logging.LogItem;
import u1171639.shared.main.java.logging.Logger;
import u1171639.shared.main.java.logging.TransientLogger;
import u1171639.shared.main.java.utils.CorbaUtils;

public class RMC_LMSTest {
	// RMC objects
	private RMCController rmcController;
	private CorbaRMC rmc;
	
	// LMS objects
	private LMSController lmsController;
	private Logger lmsLogger;
	
	@Before
	public void setUp() throws Exception {
		String[] args = { "-ORBInitialPort", "1050" };
		
		// Start up the RMC
		CorbaUtils.initOrb(args);
		CorbaUtils.initRootPOA();
		CorbaUtils.initNameService();
		
		this.rmcController = new RMCController(new MockRMCView(), new MockHomeUserManager());
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
		LMSConfig.setLocality("Locality1");
		
		this.rmc = new CorbaRMC("RMCServer", null);
		this.lmsLogger = new TransientLogger();
		this.lmsController = new LMSController(this.rmc, this.lmsLogger);
		
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
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetLogs() {
		// Log an item with the Logger used by the LMS
		LogItem logItem1 = new LogItem();
		logItem1.setEvent(LogItem.Event.ALARM_RAISED);
		logItem1.setMessage("Alarm raised in Zone 1");
		this.lmsLogger.logEvent(logItem1);
		
		List<LogItem> logs = this.rmcController.getLocalityByName("Locality1").getUpdatedLogs();
		assertTrue(logs.size() == 1);
		assertTrue(logs.get(0).getEvent() == LogItem.Event.ALARM_RAISED);
		assertTrue(logs.get(0).getMessage().equals("Alarm raised in Zone 1"));
	}

}
