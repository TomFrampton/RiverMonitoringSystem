package u1171639.rmc.main.java.service;

import java.io.Closeable;
import java.io.IOException;

import com.sun.jersey.api.container.filter.GZIPContentEncodingFilter;
import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.simple.container.SimpleServerFactory;

import u1171639.rmc.main.java.controller.RMCController;
import u1171639.rmc.main.java.web_api.CORSFilter;
import u1171639.rmc.main.java.web_api.HomeUserWebApi;

public class ExternalRMCService {
	private Object lock = new Object();
	
	public ExternalRMCService(RMCController rmcController) {
		HomeUserWebApi.setRMCController(rmcController);
	}
	
	public void listen() {
		
		try {
			DefaultResourceConfig resourceConfig = new DefaultResourceConfig(HomeUserWebApi.class);
		    resourceConfig.getContainerResponseFilters().add(new CORSFilter());
		    // The following line is to enable GZIP when client accepts it
		    resourceConfig.getContainerResponseFilters().add(new GZIPContentEncodingFilter());
		    Closeable server = SimpleServerFactory.create("http://0.0.0.0:5555", resourceConfig);
		    
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
