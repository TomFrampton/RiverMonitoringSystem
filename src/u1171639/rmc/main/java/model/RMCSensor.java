package u1171639.rmc.main.java.model;

import java.util.ArrayList;
import java.util.List;

import u1171639.rmc.main.java.users.HomeUser;

public class RMCSensor {
	private String name;
	private double threshold;
	private boolean active;
	private String localityName;
	private String zoneName;
	
	private List<HomeUser> registeredUsers = new ArrayList<HomeUser>();
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getLocalityName() {
		return localityName;
	}

	public void setLocalityName(String localityName) {
		this.localityName = localityName;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	
	public void registerHomeUser(HomeUser user) {
		if(this.getRegisteredUserById(user.getId()) == null) {
			this.registeredUsers.add(user);
		}
	}
	
	public List<HomeUser> getAllRegisteredUsers() {
		return this.registeredUsers;
	}
	
	public HomeUser getRegisteredUserById(int userId) {
		for(HomeUser user : this.registeredUsers) {
			if(user.getId() == userId) {
				return user;
			}
		}
		
		return null;
	}
}
