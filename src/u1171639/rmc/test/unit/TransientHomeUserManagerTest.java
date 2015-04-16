package u1171639.rmc.test.unit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

}
