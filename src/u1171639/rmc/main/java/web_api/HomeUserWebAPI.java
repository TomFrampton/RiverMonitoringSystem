package u1171639.rmc.main.java.web_api;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONObject;

import u1171639.rmc.main.java.controller.RMCController;
import u1171639.rmc.main.java.model.RMCSensor;
import u1171639.rmc.main.java.users.HomeUser;
import u1171639.rmc.main.java.users.HomeUserManager;
import u1171639.shared.main.java.exception.AuthenticationException;

import com.sun.jersey.server.wadl.WadlApplicationContext;

@Path("/home")
@Produces({ MediaType.APPLICATION_JSON })
public class HomeUserWebAPI {

	private static RMCController rmcController;
	
	public HomeUserWebAPI(@Context WadlApplicationContext wadlApplicationContext) {
		 wadlApplicationContext.setWadlGenerationEnabled(false);
	}
	
	public static void setRMCController(RMCController rmcController) {
		HomeUserWebAPI.rmcController = rmcController;
	}
	
	@POST
	@Path("/login")
	public String login(@FormParam("username") String username, @FormParam("password") String password) {
		HomeUserManager homeUserManager = rmcController.getHomeUserManager();
		boolean authenticated = true;
		HomeUser user = null;
		try {
			user = homeUserManager.authenticateUser(username, password);
		} catch (AuthenticationException e) {
			authenticated = false;
		}

		JSONObject root = new JSONObject();
		root.put("authenticated", authenticated);
		root.put("userId", user != null ? user.getId() : -1);
		
		return root.toString();
	}
	
	@GET
	@Path("/sensorReadings")
	public String sensorReadings(@QueryParam("userId") int userId) {
		List<RMCSensor> sensors = rmcController.getHomeUserSensorReadings(userId);
		JSONObject root = new JSONObject();
		JSONArray jsonSensors = new JSONArray();
		
		for(RMCSensor sensor : sensors) {
			JSONObject jsonSensor = new JSONObject();
			jsonSensor.put("time", System.currentTimeMillis());
			jsonSensor.put("locality", sensor.getLocalityName());
			jsonSensor.put("zone", sensor.getZoneName());
			jsonSensor.put("sensorName", sensor.getName());
			jsonSensor.put("reading", sensor.getReading());
			jsonSensors.put(jsonSensor);
		}
		
		root.put("sensors", jsonSensors);
		return root.toString();
	}
}
