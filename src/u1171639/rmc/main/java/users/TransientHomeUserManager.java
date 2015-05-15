package u1171639.rmc.main.java.users;

import java.util.ArrayList;
import java.util.List;

import u1171639.rmc.main.java.model.RMCSensor;
import u1171639.shared.main.java.exception.AuthenticationException;
import u1171639.shared.main.java.exception.RegistrationException;


public class TransientHomeUserManager implements HomeUserManager {

	private List<HomeUser> homeUsers = new ArrayList<HomeUser>();
	
	private Object lock = new Object();
	
	@Override
	public int addHomeUser(HomeUser newUser) {
		synchronized(this.lock) {
			if(getUserByUsername(newUser.getUsername()) == null) {
				newUser.setId(this.homeUsers.size() + 1);
				this.homeUsers.add(newUser);
				return newUser.getId();
			} else {
				return -1;
			}
		}
	}

	@Override
	public HomeUser getUserById(int id) {
		for(HomeUser user : this.homeUsers) {
			if(user.getId() == id) {
				return user;
			}
		}
		
		return null;
	}
	
	@Override
	public HomeUser getUserByUsername(String username) {
		for(HomeUser user : this.homeUsers) {
			if(user.getUsername().equals(username)) {
				return user;
			}
		}
		
		return null;
	}

	@Override
	public HomeUser authenticateUser(String username, String password) throws AuthenticationException {
		HomeUser user = getUserByUsername(username);
		if(user == null) {
			throw new AuthenticationException("User not found");
		}
		
		if(!user.getPassword().equals(password)) {
			throw new AuthenticationException("Incorrect password");
		}
		
		return user;
		
	}

	@Override
	public void registerUserWithSensor(int userId, RMCSensor sensor) throws RegistrationException {
		HomeUser user = getUserById(userId);
		if(user != null && !isUserRegisteredWithSensor(user, sensor)) {
			user.getRegisteredSensors().add(sensor);
		} else {
			throw new RegistrationException("User already registered with sensor.");
		}
	}
	
	@Override
	public void registerUserWithSensor(String username, RMCSensor sensor) throws RegistrationException {
		HomeUser user = getUserByUsername(username);
		this.registerUserWithSensor(user.getId(), sensor);
	}
	
	private boolean isUserRegisteredWithSensor(HomeUser user, RMCSensor sensor) {
		List<RMCSensor> userSensors = user.getRegisteredSensors();
		for(RMCSensor userSensor : userSensors) {
			if(userSensor.getName().equals(sensor.getName())) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isUserRegisteredWithSensor(HomeUser user, String sensorName) {
		RMCSensor sensor = new RMCSensor();
		sensor.setName(sensorName);
		return this.isUserRegisteredWithSensor(user, sensor);
	}

	@Override
	public List<HomeUser> getAllUsersRegistered(String sensorName) {
		List<HomeUser> users = new ArrayList<HomeUser>();
		
		for(HomeUser user : this.homeUsers) {
			if(this.isUserRegisteredWithSensor(user, sensorName)) {
				users.add(user);
			}
		}
		
		return users;
	}
}
