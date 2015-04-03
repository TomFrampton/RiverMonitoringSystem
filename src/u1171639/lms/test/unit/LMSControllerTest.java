package u1171639.lms.test.unit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import u1171639.lms.main.java.controller.LMSController;
import u1171639.lms.test.mocks.MockSensor;

public class LMSControllerTest {
	private LMSController controller;
	
	@Before
	public void setUp() throws Exception {
		this.controller = new LMSController();
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
		// Build mock RMC and wait for reply
	}

}
