package u1171639.rmc.main.java.web_api;

import java.io.Closeable;
import java.io.IOException;

import javax.annotation.Resources;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.Endpoint;

import org.json.JSONObject;

import u1171639.rmc.main.java.controller.RMCController;

import com.sun.jersey.server.wadl.WadlApplicationContext;
import com.sun.jersey.simple.container.SimpleServerFactory;
import com.sun.net.httpserver.HttpServer;

@Path("/home")
@Produces({ MediaType.APPLICATION_JSON })
public class HomeUserWebApi {

	private static RMCController rmcController;
	
	public HomeUserWebApi(@Context WadlApplicationContext wadlApplicationContext) {
		 wadlApplicationContext.setWadlGenerationEnabled(false);
	}
	
	public static void setRMCController(RMCController rmcController) {
		HomeUserWebApi.setRMCController(rmcController);
	}
	
	@GET
	@Path("/sensorReadings")
	public String sensorReadings() {

		JSONObject root = new JSONObject();
		root.put("name", "Tom");
		
		return root.toString();
	}
}
