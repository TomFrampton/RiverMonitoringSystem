package u1171639.rmc.main.java.client;

import java.util.List;
import java.util.concurrent.Callable;

import u1171639.rmc.main.java.model.RMCZone;
import u1171639.shared.main.java.corba.lms_rmc.LMS_RMC;
import u1171639.shared.main.java.corba.lms_rmc.LMS_RMCHelper;
import u1171639.shared.main.java.corba.models.CorbaModel_Locality;
import u1171639.shared.main.java.corba.models.CorbaModel_Log;
import u1171639.shared.main.java.logging.LogItem;
import u1171639.shared.main.java.utils.ModelConverter;

public class CorbaLMS implements LMS {
	private org.omg.CORBA.Object ior;
	private LMS_RMC lms;
	
	public CorbaLMS(org.omg.CORBA.Object ior) {
		this.ior = ior;
	}
	
	@Override
	public void connect() {
		if(this.lms == null) {
			this.lms = LMS_RMCHelper.narrow(this.ior);
		}
	}

	@Override
	public void disconnect() {
		this.lms = null;	
	}
	
	@Override
	public List<RMCZone> getZoneUpdates() {
		CorbaModel_Locality corbaLocality = this.communicate(new Callable<CorbaModel_Locality>() {
			@Override
			public CorbaModel_Locality call() throws Exception {
				return CorbaLMS.this.lms.getZoneUpdates();
			}
		});
		
		return ModelConverter.convertLocality(corbaLocality).getZones();
	}
	
	@Override
	public void handleRaisedAlarm(String zone) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<LogItem> getLog() {
		CorbaModel_Log corbaLog = this.communicate(new Callable<CorbaModel_Log>() {
			@Override
			public CorbaModel_Log call() throws Exception {
				return CorbaLMS.this.lms.getLog();
			}
		});
		
		return ModelConverter.convertLog(corbaLog);
	}
	
	@Override
	public boolean activateSensor(String zoneName, String sensorName) {
		return this.communicate(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return CorbaLMS.this.lms.activateSensor(zoneName, sensorName);
			}
		});
	}
	
	@Override
	public boolean deactivateSensor(String zoneName, String sensorName) {
		return this.communicate(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return CorbaLMS.this.lms.deactivateSensor(zoneName, sensorName);
			}
		});
	}
	
	@Override
	public boolean setWarningThreshold(String zoneName, String sensorName, double threshold) {
		return this.communicate(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return CorbaLMS.this.lms.setWarningThreshold(zoneName, sensorName, threshold);
			}
		});
	}
	
	@Override
	public double getSensorReading(String zoneName, String sensorName) {
		return this.communicate(new Callable<Double>() {
			@Override
			public Double call() throws Exception {
				return CorbaLMS.this.lms.getSensorReading(zoneName, sensorName);
			}
		});
	}
	
	@Override
	public boolean resetAlarm(String zoneName) {
		return this.communicate(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return CorbaLMS.this.lms.resetAlarm(zoneName);
			}
		});
	}
	
	private <T> T communicate(Callable<T> action) {
		try {
			connect();
			return action.call();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			disconnect();
		}
	}
}
