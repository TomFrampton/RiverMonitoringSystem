package u1171639.sensor.main.java.controller;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import u1171639.lms.main.java.utils.LMSConfig;
import u1171639.sensor.main.java.model.CorbaLMS;
import u1171639.sensor.main.java.model.LMS;
import u1171639.sensor.main.java.monitor.SimulatedWaterLevelMonitor;
import u1171639.sensor.main.java.monitor.WaterLevelMonitor;
import u1171639.sensor.main.java.service.SensorService;
import u1171639.sensor.main.java.utils.CorbaUtils;
import u1171639.sensor.main.java.utils.SensorConfig;
import u1171639.sensor.main.java.view.JavaFXSimulationView;
import u1171639.sensor.main.java.view.SimulationView;
import u1171639.shared.main.java.logging.Logger;
import u1171639.shared.main.java.logging.TransientLogger;

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
	
	public static void main(String[] args) {
		Options options = new Options();
		options.addOption("locality", true, "The name of the Locality this Sensor resides in.");
		options.addOption("zone", true, "The name of the Zone this Sensor resides in.");
		options.addOption("ORBInitialPort", true, "Port number of the Name Service.");
		CommandLineParser parser = new GnuParser();
		
		CommandLine cmd = null;
		
		try {
			cmd = parser.parse(options, args);
			
			if(!cmd.hasOption("locality")) {
				System.err.println("-locality option required for Sensor.");
				System.exit(1);
			} else {
				SensorConfig.setLocality(cmd.getOptionValue("locality"));
			}
			
			if(!cmd.hasOption("zone")) {
				System.err.println("-zone option required for Sensor.");
				System.exit(1);
			} else {
				SensorConfig.setZone(cmd.getOptionValue("zone"));
			}
			
			if(!cmd.hasOption("ORBInitialPort")) {
				System.err.println("-ORBInitialPort option required.");
				System.exit(1);
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/** Set monitoring configuration */
		// Read value every second and raise alarm if waterLevel >= 70
		SensorConfig.setMonitoringInterval(5000);
		SensorConfig.setWarningWaterLevel(70);
		SensorConfig.setZone(cmd.getOptionValue("zone"));
		
		CorbaUtils.initOrb(args);
		CorbaUtils.initRootPOA();
		CorbaUtils.initNameService();
		
		CorbaLMS lms = new CorbaLMS(SensorConfig.getLocality() + "_LMSServer", null);
		SimulatedWaterLevelMonitor monitor = new SimulatedWaterLevelMonitor();
		
		Logger logger = new TransientLogger();
		
		SensorController controller = new SensorController(lms, monitor, logger);
		SensorService service = new SensorService(controller);
		
		String ior = service.listen();
		lms.setServiceIOR(ior);
		lms.connect();
		
		monitor.setController(controller);
		monitor.monitorWaterLevel();
		
		SimulationView view = new JavaFXSimulationView();
		view.start(monitor);
	}
	
	
}
