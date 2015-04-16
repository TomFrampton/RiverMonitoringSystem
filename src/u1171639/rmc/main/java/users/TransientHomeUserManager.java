package u1171639.rmc.main.java.users;

import java.util.ArrayList;
import java.util.List;

import u1171639.rmc.main.java.model.RMCSensor;


public class TransientHomeUserManager implements HomeUserManager {

	private List<HomeUser> homeUsers = new ArrayList<HomeUser>();
	
	private Object lock = new Object();
	
	@Override
	public int addHomeUser(HomeUser newUser) {
		synchronized(lock) {
			if(this.getUserByUsername(newUser.getEmail()) == null) {
				newUser.setId(this.homeUsers.size());
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
			if(user.getEmail().equals(username)) {
				return user;
			}
		}
		
		return null;
	}
}
