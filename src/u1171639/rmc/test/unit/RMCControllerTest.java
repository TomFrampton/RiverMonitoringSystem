package u1171639.rmc.test.unit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import u1171639.lms.main.java.client.RMC;
import u1171639.lms.main.java.controller.LMSController;
import u1171639.lms.test.mocks.MockSensor;
import u1171639.rmc.main.java.client.LMS;
import u1171639.rmc.main.java.controller.RMCController;
import u1171639.rmc.test.mocks.MockLMS;

public class RMCControllerTest {
	private RMCController controller;
	
	@Before
	public void setUp() throws Exception {
		this.controller = new RMCController();
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testRegisterSensor() {
		LMS lms1 = new MockLMS();
		this.controller.registerLMS("Locality1", lms1);
		assertTrue(this.controller.getLocalityByName("Locality1").getLms() == lms1);
		assertTrue(this.controller.getLocalityByName("Locality2") == null);
		
		LMS lms2 = new MockLMS();
		this.controller.registerLMS("Locality2", lms2);
		assertTrue(this.controller.getLocalityByName("Locality1").getLms() == lms1);
		assertTrue(this.controller.getLocalityByName("Locality2").getLms() == lms2);
		
		LMS lms3 = new MockLMS();
		this.controller.registerLMS("Locality2", lms3);
		assertTrue(this.controller.getLocalityByName("Locality1").getLms() == lms1);
		assertTrue(this.controller.getLocalityByName("Locality2").getLms() == lms3);
	}
}
