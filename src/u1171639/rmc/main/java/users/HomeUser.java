package u1171639.rmc.main.java.users;

import java.util.ArrayList;
import java.util.List;

import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import u1171639.rmc.main.java.model.RMCSensor;

public class HomeUser {
	private int id;
	
	@NotNull
	@NotEmpty
	private String forename;
	
	@NotNull
	@NotEmpty
	private String surname;
	
	@NotNull
	@NotEmpty
	private String username;
	
	@NotNull
	@NotEmpty
	private String password;
	
	private List<RMCSensor> registeredSensors = new ArrayList<RMCSensor>();
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getForename() {
		return forename;
	}
	
	public void setForename(String forename) {
		this.forename = forename;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public List<RMCSensor> getRegisteredSensors() {
		return registeredSensors;
	}

	public void setRegisteredSensors(List<RMCSensor> registeredSensors) {
		this.registeredSensors = registeredSensors;
	}
}
