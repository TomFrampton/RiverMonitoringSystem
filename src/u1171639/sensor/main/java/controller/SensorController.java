package u1171639.sensor.main.java.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import u1171639.lms.main.java.utils.LMSConfig;
import u1171639.rmc.main.java.view.JavaFXRMCView;
import u1171639.sensor.main.java.client.CorbaLMS;
import u1171639.sensor.main.java.client.LMS;
import u1171639.sensor.main.java.monitor.SimulatedWaterLevelMonitor;
import u1171639.sensor.main.java.monitor.WaterLevelMonitor;
import u1171639.sensor.main.java.service.SensorService;
import u1171639.sensor.main.java.utils.SensorConfig;
import u1171639.sensor.main.java.view.JavaFXSimulationView;
import u1171639.sensor.main.java.view.SimulationView;
import u1171639.shared.main.java.exception.ConnectionException;
import u1171639.shared.main.java.exception.ServerNotFoundException;
import u1171639.shared.main.java.logging.Logger;
import u1171639.shared.main.java.logging.TransientLogger;
import u1171639.shared.main.java.utils.CorbaUtils;

public class SensorController {
	private LMS lms;
	private WaterLevelMonitor monitor;
	private boolean isActive = true;
	private Logger logger;
	
	public SensorController(LMS lms, WaterLevelMonitor monitor, Logger logger) {
		this.lms = lms;
		this.monitor = monitor;
		this.logger = logger;
	}
	
	public void raiseAlarm() {
		lms.raiseAlarm();
	}
	
	public boolean isAlarmRaised() {
		return monitor.isAlarmRaised();
	}
	
	public void activate() {
		this.monitor.resumeMonitoring();
		this.isActive = true;
	}
	
	public void deactivate() {
		this.monitor.pauseMonitoring();
		this.isActive = false;
	}
	
	public boolean isActive() {
		return this.isActive;
	}
	
	public WaterLevelMonitor getMonitor() {
		return this.monitor;
	}
	
	public double getWaterLevelReading() {
		return this.monitor.getWaterLevel();
	}
	
	public static void main(String[] args) {
		Options options = new Options();
		options.addOption("locality", true, "The name of the Locality this Sensor resides in.");
		options.addOption("zone", true, "The name of the Zone this Sensor resides in.");
		options.addOption("ORBInitialPort", true, "Port number of the Name Service.");
		CommandLineParser parser = new GnuParser();
		
		CommandLine cmd = null;
		List<String> errors = new ArrayList<String>();
		
		try {
			cmd = parser.parse(options, args);
			
			if(!cmd.hasOption("locality")) {
				errors.add("-locality option required for Sensor.");
			} else {
				SensorConfig.setLocality(cmd.getOptionValue("locality"));
			}
			
			if(!cmd.hasOption("zone")) {
				errors.add("-zone option required for Sensor.");
			} else {
				SensorConfig.setZone(cmd.getOptionValue("zone"));
			}
			
			if(!cmd.hasOption("ORBInitialPort")) {
				errors.add("-ORBInitialPort option required.");
			}
			
		} catch (ParseException e) {
			errors.add("Could not parse command line arguments.");
		}
		
		if(!errors.isEmpty()) {
			JavaFXSimulationView.startUpErrors("Invalid Command Line Arguments", errors);
			System.exit(1);
		}
		
		/** Set monitoring configuration */
		// Read value every second and raise alarm if waterLevel >= 70
		SensorConfig.setMonitoringInterval(5000);
		SensorConfig.setWarningThreshold(70);
		
		CorbaUtils.initOrb(args);
		CorbaUtils.initRootPOA();
		try {
			CorbaUtils.initNameService();
		} catch (ServerNotFoundException e) {
			JavaFXSimulationView.startUpError("Name Service Error", "Name service not found");
			System.exit(1);
		}
		
		CorbaLMS lms = new CorbaLMS(SensorConfig.getLocality() + "_LMSServer", null);
		SimulatedWaterLevelMonitor monitor = new SimulatedWaterLevelMonitor();
		
		Logger logger = new TransientLogger();
		
		SensorController controller = new SensorController(lms, monitor, logger);
		SensorService service = new SensorService(controller);
		
		String ior = service.listen();
		lms.setServiceIOR(ior);
		try {
			lms.connect();
		} catch (ServerNotFoundException | ConnectionException e) {
			JavaFXSimulationView.startUpError("LMS Not Found", "LMS for specified locality not found.");
			System.exit(1);
		}
		
		monitor.setController(controller);
		monitor.monitorWaterLevel();
		
		SimulationView view = new JavaFXSimulationView();
		view.start(monitor);
	}
	
	
}
