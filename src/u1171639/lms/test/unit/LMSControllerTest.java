package u1171639.lms.test.unit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import u1171639.lms.main.java.client.RMC;
import u1171639.lms.main.java.controller.LMSController;
import u1171639.lms.test.mocks.MockSensor;
import u1171639.sensor.test.mocks.MockLogger;

public class LMSControllerTest {
	private LMSController controller;
	private boolean alarmRaised;
	
	@Before
	public void setUp() throws Exception {
		this.controller = new LMSController(new RMC() {
			@Override
			public void raiseAlarm(String zone) {
				alarmRaised = true;
			}

			@Override
			public void connect() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void disconnect() {
				// TODO Auto-generated method stub
				
			}	
		}, new MockLogger());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRegisterSensor() {
		this.controller.registerSensor("Zone1", new MockSensor());
		assertTrue(this.controller.getSensorsByZone("Zone1").size() == 1);
		assertTrue(this.controller.getSensorsByZone("Zone2") == null);
		
		this.controller.registerSensor("Zone1", new MockSensor());
		assertTrue(this.controller.getSensorsByZone("Zone1").size() == 2);
		
		this.controller.registerSensor("Zone2", new MockSensor());
		assertTrue(this.controller.getSensorsByZone("Zone1").size() == 2);
		assertTrue(this.controller.getSensorsByZone("Zone2").size() == 1);
	}
	
	@Test
	public void testAlarmRaised() {
		MockSensor sensor1 = new MockSensor();
		MockSensor sensor2 = new MockSensor();
		MockSensor sensor3 = new MockSensor();
		
		this.controller.registerSensor("Zone1", sensor1);
		this.controller.registerSensor("Zone1", sensor2);
		this.controller.registerSensor("Zone2", sensor3);
		
		this.controller.alarmRaised("Zone1");
		assertFalse(this.alarmRaised);
		
		sensor1.setAlarmRaised(true);
		this.controller.alarmRaised("Zone1");
		assertFalse(this.alarmRaised);
		
		sensor1.setAlarmRaised(true);
		sensor2.setAlarmRaised(true);
		this.controller.alarmRaised("Zone1");
		assertTrue(this.alarmRaised);
	}

}
