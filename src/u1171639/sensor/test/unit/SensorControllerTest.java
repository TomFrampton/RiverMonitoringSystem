package u1171639.sensor.test.unit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import u1171639.sensor.main.java.controller.SensorController;
import u1171639.sensor.main.java.monitor.SimulatedWaterLevelMonitor;
import u1171639.sensor.test.mocks.MockLogger;

public class SensorControllerTest {
	private SensorController controller;
	
	@Before
	public void setUp() throws Exception {
		this.controller = new SensorController(null, new SimulatedWaterLevelMonitor(), new MockLogger());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testActivate() {
		assertTrue(this.controller.isActive());
		
		this.controller.deactivate();
		assertFalse(this.controller.isActive());
		
		this.controller.activate();
		assertTrue(this.controller.isActive());
	}

}
