package u1171639.rmc.main.java.client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import u1171639.rmc.main.java.model.Sensor;
import u1171639.rmc.main.java.model.Zone;
import u1171639.shared.main.java.corba.lms_rmc.CorbaLocality;
import u1171639.shared.main.java.corba.lms_rmc.CorbaLog;
import u1171639.shared.main.java.corba.lms_rmc.CorbaLogItem;
import u1171639.shared.main.java.corba.lms_rmc.CorbaSensor;
import u1171639.shared.main.java.corba.lms_rmc.LMS_RMC;
import u1171639.shared.main.java.corba.lms_rmc.LMS_RMCHelper;
import u1171639.shared.main.java.logging.LogItem;
import u1171639.shared.main.java.utils.EnumParser;

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
	public List<Zone> getZoneUpdates() {
		CorbaLocality corbaLocality = this.communicate(new Callable<CorbaLocality>() {
			@Override
			public CorbaLocality call() throws Exception {
				return lms.getZoneUpdates();
			}
		});
		
		List<Zone> zones = new ArrayList<Zone>();
		
		for(int i = 0; i < corbaLocality.zones.length; ++i) {
			Zone zone = new Zone();
			zone.setName(corbaLocality.zones[i].name);
			zone.setAlarmRaised(corbaLocality.zones[i].alarmRaised);
			zones.add(zone);
			
			List<Sensor> sensors = new ArrayList<Sensor>();
			
			for(int ii = 0; ii < corbaLocality.zones[i].sensors.length; ++ii) {
				CorbaSensor corbaSensor = corbaLocality.zones[i].sensors[ii];
				Sensor sensor = new Sensor(corbaSensor.id, "");
				sensors.add(sensor);
			}
			
			zone.setSensors(sensors);
		}
		
		return zones;
	}
	
	@Override
	public void handleRaisedAlarm(String zone) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<LogItem> getLog() {
		CorbaLog corbaLog = this.communicate(new Callable<CorbaLog>() {
			@Override
			public CorbaLog call() throws Exception {
				return lms.getLog();
			}
		});
		
		List<LogItem> log = new ArrayList<LogItem>();
		
		for(CorbaLogItem corbaLogItem : corbaLog.logItems) {
			LogItem item = new LogItem(corbaLogItem.message, EnumParser.stringToEnum(LogItem.Event.class, corbaLogItem.event));
			log.add(item);
		}
		
		return log;
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
