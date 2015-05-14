package u1171639.shared.main.java.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import u1171639.lms.main.java.client.Sensor;
import u1171639.lms.main.java.model.LMSZone;
import u1171639.rmc.main.java.model.Locality;
import u1171639.rmc.main.java.model.RMCSensor;
import u1171639.rmc.main.java.model.RMCZone;
import u1171639.shared.main.java.corba.models.CorbaModel_Locality;
import u1171639.shared.main.java.corba.models.CorbaModel_Log;
import u1171639.shared.main.java.corba.models.CorbaModel_LogItem;
import u1171639.shared.main.java.corba.models.CorbaModel_Sensor;
import u1171639.shared.main.java.corba.models.CorbaModel_Zone;
import u1171639.shared.main.java.logging.LogItem;

public final class ModelConverter {

	public static Locality convertLocality(CorbaModel_Locality corbaLocality) {
		Locality genericLocality = new Locality();
		genericLocality.setName(corbaLocality.name);
		
		for(CorbaModel_Zone corbaZone : corbaLocality.zones) {
			genericLocality.getZones().add(ModelConverter.convertRMCZone(corbaLocality.name, corbaZone));
		}
		
		return genericLocality;
	}
	
	public static CorbaModel_Locality convertLocality(String localityName, List<LMSZone> genericZones) {
		CorbaModel_Locality corbaLocality = new CorbaModel_Locality();
		corbaLocality.name = localityName;
		corbaLocality.zones = new CorbaModel_Zone[genericZones.size()];
		
		for(int i = 0; i < corbaLocality.zones.length; ++i) {
			corbaLocality.zones[i] = convertLMSZone(genericZones.get(i));
		}
		
		return corbaLocality;
	}
	
	public static RMCZone convertRMCZone(String localityName, CorbaModel_Zone corbaZone) {
		RMCZone zone = new RMCZone();
		zone.setName(corbaZone.name);
		zone.setLocalityName(localityName);
		zone.setAlarmRaised(corbaZone.alarmRaised);
		
		List<RMCSensor> sensors = new ArrayList<RMCSensor>();
		
		for(int i = 0; i < corbaZone.sensors.length; ++i) {
			sensors.add(convertRMCSensor(corbaZone.sensors[i]));
		}
		
		zone.setSensors(sensors);
		return zone;	
	}
	
	public static CorbaModel_Zone convertLMSZone(LMSZone genericLmsZone) {
		CorbaModel_Zone corbaZone = new CorbaModel_Zone();
		corbaZone.name = genericLmsZone.getName();
		corbaZone.alarmRaised = genericLmsZone.isAlarmRaised();
		
		List<Sensor> sensors = genericLmsZone.getSensors();
		CorbaModel_Sensor[] corbaSensors = new CorbaModel_Sensor[sensors.size()];
		
		for(int i = 0; i < sensors.size(); ++i) {
			corbaSensors[i] = convertLMSSensor(sensors.get(i));
		}
		
		corbaZone.sensors = corbaSensors;
		
		return corbaZone;
	}
	
	public static CorbaModel_Sensor convertLMSSensor(Sensor lmsSensor) {
		CorbaModel_Sensor corbaSensor = new CorbaModel_Sensor();
		corbaSensor.name = lmsSensor.getName();
		corbaSensor.threshold = lmsSensor.getThreshold();
		corbaSensor.active = lmsSensor.isActive();
		
		return corbaSensor;
	}
	
	public static RMCSensor convertRMCSensor(CorbaModel_Sensor corbaSensor) {
		RMCSensor rmcSensor = new RMCSensor();
		rmcSensor.setName(corbaSensor.name);
		rmcSensor.setThreshold(corbaSensor.threshold);
		rmcSensor.setActive(corbaSensor.active);
		
		return rmcSensor;
		
	}
	
	public static List<LogItem> convertLog(CorbaModel_Log corbaLog) {
		List<LogItem> log = new ArrayList<LogItem>();
		
		for(CorbaModel_LogItem corbaLogItem : corbaLog.logItems) {
			log.add(convertLogItem(corbaLogItem));
		}
		
		return log;
	}
	
	public static CorbaModel_Log convertLog(List<LogItem> genericLog) {
		CorbaModel_LogItem[] corbaLogItems = new CorbaModel_LogItem[genericLog.size()];
		
		for(int i = 0; i < genericLog.size(); ++i) {
			LogItem genericLogItem = genericLog.get(i);
			corbaLogItems[i] = convertLogItem(genericLogItem);
		}
		
		CorbaModel_Log corbaLog = new CorbaModel_Log();
		corbaLog.logItems = corbaLogItems;
		return corbaLog;
	}
	
	public static LogItem convertLogItem(CorbaModel_LogItem corbaLogItem) {
		Date time = new Date(corbaLogItem.timestamp);
		return new LogItem(time, corbaLogItem.message, getEventFromString(corbaLogItem.event));
	}
	
	public static CorbaModel_LogItem convertLogItem(LogItem genericLogItem) {
		CorbaModel_LogItem corbaLogItem = new CorbaModel_LogItem();
		corbaLogItem.timestamp = genericLogItem.getTime().getTime();
		corbaLogItem.message = genericLogItem.getMessage();
		corbaLogItem.event = genericLogItem.getEvent().toString();
		return corbaLogItem;
	}
	
	private static LogItem.Event getEventFromString(String event) {
		return EnumParser.stringToEnum(LogItem.Event.class, event);
	}
}
