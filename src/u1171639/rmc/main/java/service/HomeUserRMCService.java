package u1171639.rmc.main.java.service;

import java.io.Closeable;
import java.io.IOException;

import com.sun.jersey.api.container.filter.GZIPContentEncodingFilter;
import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.simple.container.SimpleServerFactory;

import u1171639.rmc.main.java.controller.RMCController;
import u1171639.rmc.main.java.web_api.CORSFilter;
import u1171639.rmc.main.java.web_api.HomeUserWebAPI;

public class HomeUserRMCService {
	private Object lock = new Object();
	
	public HomeUserRMCService(RMCController rmcController) {
		HomeUserWebAPI.setRMCController(rmcController);
	}
	
	public void listen() {
		
		try {
			DefaultResourceConfig resourceConfig = new DefaultResourceConfig(HomeUserWebAPI.class);
		    resourceConfig.getContainerResponseFilters().add(new CORSFilter());
		    // The following line is to enable GZIP when client accepts it
		    resourceConfig.getContainerResponseFilters().add(new GZIPContentEncodingFilter());
		    Closeable server = SimpleServerFactory.create("http://127.0.0.1:5555", resourceConfig);
		    
			try {
				System.out.println("Web Server running...");
				synchronized(lock) {
					lock.wait();
				}
			} finally {
		        server.close();
		    }
		} catch(InterruptedException | IllegalArgumentException | IOException e) {
			
		}
	}
	
	public void stopServer() {
		synchronized(lock) {
			lock.notify();
		}
	}
}
