package u1171639.rmc.main.java.users;

import u1171639.rmc.main.java.model.RMCSensor;


public interface HomeUserManager {

	public int addHomeUser(HomeUser newUser);
	public HomeUser getUserById(int id);
	public HomeUser getUserByUsername(String username);
	
}
