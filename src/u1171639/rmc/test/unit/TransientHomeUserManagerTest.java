package u1171639.rmc.test.unit;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import u1171639.lms.main.java.client.Sensor;
import u1171639.rmc.main.java.model.RMCSensor;
import u1171639.rmc.main.java.users.HomeUser;
import u1171639.rmc.main.java.users.HomeUserManager;
import u1171639.rmc.main.java.users.TransientHomeUserManager;

public class TransientHomeUserManagerTest {

	private HomeUserManager homeUserManager;
	
	@Before
	public void setUp() throws Exception {
		this.homeUserManager = new TransientHomeUserManager();
		
		HomeUser user1 = new HomeUser();
		user1.setForename("Tom");
		user1.setSurname("Frampton");
		user1.setUsername("TomFrampton");
		user1.setPassword("Password");
		
		HomeUser user2 = new HomeUser();
		user2.setForename("John");
		user2.setSurname("Smith");
		user2.setUsername("Johnny");
		user2.setPassword("Qwerty");
		
		this.homeUserManager.addHomeUser(user1);
		this.homeUserManager.addHomeUser(user2);
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testGetUserById() {
		HomeUser user = this.homeUserManager.getUserById(1);
		
		assertTrue(user != null);
		assertTrue(user.getId() == 1);
		assertEquals(user.getForename(), "Tom");
		assertEquals(user.getSurname(), "Frampton");
		assertEquals(user.getUsername(), "TomFrampton");
		assertEquals(user.getPassword(), "Password");
		
		user = this.homeUserManager.getUserById(2);
		
		assertTrue(user != null);
		assertTrue(user.getId() == 2);
		assertEquals(user.getForename(), "John");
		assertEquals(user.getSurname(), "Smith");
		assertEquals(user.getUsername(), "Johnny");
		assertEquals(user.getPassword(), "Qwerty");
		
		user = this.homeUserManager.getUserById(3);
		
		assertTrue(user == null);
	}
	
	@Test
	public void testGetUserByUsername() {
		HomeUser user = this.homeUserManager.getUserByUsername("TomFrampton");
		
		assertTrue(user != null);
		assertTrue(user.getId() == 1);
		assertEquals(user.getForename(), "Tom");
		assertEquals(user.getSurname(), "Frampton");
		assertEquals(user.getUsername(), "TomFrampton");
		assertEquals(user.getPassword(), "Password");
		
		user = this.homeUserManager.getUserByUsername("Johnny");
		
		assertTrue(user != null);
		assertTrue(user.getId() == 2);
		assertEquals(user.getForename(), "John");
		assertEquals(user.getSurname(), "Smith");
		assertEquals(user.getUsername(), "Johnny");
		assertEquals(user.getPassword(), "Qwerty");
		
		user = this.homeUserManager.getUserByUsername("NotRegisteredUser");
		
		assertTrue(user == null);
	}
	
	@Test
	public void testRegisterUserWithSensor() {
		HomeUser user = this.homeUserManager.getUserByUsername("TomFrampton");
		List<RMCSensor> registeredSensors = user.getRegisteredSensors();
		
		assertTrue(registeredSensors.size() == 0);
		
		RMCSensor sensor1 = new RMCSensor();
		sensor1.setName("Sensor1");
		this.homeUserManager.registerUserWithSensor(user.getId(), sensor1);
		
		registeredSensors = user.getRegisteredSensors();
		assertTrue(registeredSensors.size() == 1);
		assertTrue(registeredSensors.get(0) == sensor1);
	}
	
	@Test
	public void testGetAllUsersRegistered() {
		HomeUser user1 = this.homeUserManager.getUserByUsername("TomFrampton");
		HomeUser user2 = this.homeUserManager.getUserByUsername("Johnny");
		
		RMCSensor sensor1 = new RMCSensor();
		sensor1.setName("Sensor1");
		
		this.homeUserManager.registerUserWithSensor(user1.getId(), sensor1);
		
		List<HomeUser> registeredUsers = this.homeUserManager.getAllUsersRegistered("Sensor1");
		assertTrue(registeredUsers.size() == 1);
		assertTrue(registeredUsers.get(0) == user1);
		
		this.homeUserManager.registerUserWithSensor(user2.getId(), sensor1);
		
		registeredUsers = this.homeUserManager.getAllUsersRegistered("Sensor1");
		assertTrue(registeredUsers.size() == 2);
		assertTrue(registeredUsers.get(1) == user2);
	}

}
