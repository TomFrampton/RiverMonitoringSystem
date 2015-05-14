package u1171639.rmc.main.java.users;

import java.util.List;

import u1171639.rmc.main.java.model.RMCSensor;
import u1171639.shared.main.java.exception.AuthenticationException;
import u1171639.shared.main.java.exception.RegistrationException;


public interface HomeUserManager {

	public int addHomeUser(HomeUser newUser);
	public HomeUser getUserById(int id);
	public HomeUser getUserByUsername(String username);
	public HomeUser authenticateUser(String username, String password) throws AuthenticationException;
	public void registerUserWithSensor(int userId, RMCSensor sensor) throws RegistrationException;
	public void registerUserWithSensor(String username, RMCSensor sensor) throws RegistrationException;
	public List<HomeUser> getAllUsersRegistered(String sensorName);
	
}
