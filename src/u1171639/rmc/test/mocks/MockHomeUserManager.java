package u1171639.rmc.test.mocks;

import java.util.List;

import u1171639.rmc.main.java.model.RMCSensor;
import u1171639.rmc.main.java.users.HomeUser;
import u1171639.rmc.main.java.users.HomeUserManager;

public class MockHomeUserManager implements HomeUserManager {

	@Override
	public int addHomeUser(HomeUser newUser) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public HomeUser getUserById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HomeUser getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HomeUser authenticateUser(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerUserWithSensor(int userId, RMCSensor sensor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerUserWithSensor(String username, RMCSensor sensor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<HomeUser> getAllUsersRegistered(String sensorName) {
		// TODO Auto-generated method stub
		return null;
	}

}
