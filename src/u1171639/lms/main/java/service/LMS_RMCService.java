package u1171639.lms.main.java.service;

import java.util.ArrayList;
import java.util.List;

import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import u1171639.lms.main.java.client.Sensor;
import u1171639.lms.main.java.controller.LMSController;
import u1171639.lms.main.java.utils.LMSConfig;
import u1171639.shared.main.java.corba.lms_rmc.LMS_RMC;
import u1171639.shared.main.java.corba.lms_rmc.LMS_RMCHelper;
import u1171639.shared.main.java.corba.lms_rmc.LMS_RMCPOA;
import u1171639.shared.main.java.corba.models.CorbaModel_Locality;
import u1171639.shared.main.java.corba.models.CorbaModel_Log;
import u1171639.shared.main.java.corba.models.CorbaModel_LogItem;
import u1171639.shared.main.java.corba.models.CorbaModel_Sensor;
import u1171639.shared.main.java.corba.models.CorbaModel_Zone;
import u1171639.shared.main.java.logging.LogItem;
import u1171639.shared.main.java.utils.CorbaUtils;

public class LMS_RMCService extends LMS_RMCPOA {
	private LMSController controller;
	
	public LMS_RMCService(LMSController controller) {
		this.controller = controller;
	}
	
	public String listen() {
		try {
			// Get the reference of the servant
			org.omg.CORBA.Object servantRef = CorbaUtils.getRootPOA().servant_to_reference(this);
			LMS_RMC ref = LMS_RMCHelper.narrow(servantRef);
			return CorbaUtils.getOrb().object_to_string(ref);
			
		} catch (ServantNotActive | WrongPolicy e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public CorbaModel_Locality getZoneUpdates() {
		List<u1171639.lms.main.java.model.Zone> zones = this.controller.getAllZones();
		
		// Copy data from Zone models to CORBA Zone objects
		CorbaModel_Zone[] corbaZones = new CorbaModel_Zone[zones.size()];
		for(int i = 0; i < zones.size(); ++i) {
			CorbaModel_Zone corbaZone = new CorbaModel_Zone();
			corbaZone.name = zones.get(i).getName();
			corbaZone.alarmRaised = zones.get(i).isAlarmRaised();
			corbaZones[i] = corbaZone;
			
			List<Sensor> sensors = zones.get(i).getSensors();
			CorbaModel_Sensor[] corbaSensors = new CorbaModel_Sensor[sensors.size()];
			for(int ii = 0; ii < sensors.size(); ++ii) {
				CorbaModel_Sensor corbaSensor = new CorbaModel_Sensor();
				corbaSensor.id = sensors.get(ii).getId();
				corbaSensors[ii] = corbaSensor;
			}
			
			corbaZones[i].sensors = corbaSensors;
		}
		
		CorbaModel_Locality corbaLocality = new CorbaModel_Locality();
		corbaLocality.name = LMSConfig.getLocality();
		corbaLocality.zones = corbaZones;
		
		return corbaLocality;
	}

	@Override
	public CorbaModel_Log getLog() {
		List<LogItem> logs = this.controller.getAllLogs();
		
		// Convert from generic to CORBA LogItem model
		CorbaModel_LogItem[] corbaLogItems = new CorbaModel_LogItem[logs.size()];
		
		for(int i = 0; i < logs.size(); ++i) {
			LogItem item = logs.get(i);
			CorbaModel_LogItem corbaItem = new CorbaModel_LogItem();
			corbaItem.message = item.getMessage();
			corbaItem.event = item.getEvent().toString();
			corbaLogItems[i] = corbaItem;
		}
		
		CorbaModel_Log corbaLog = new CorbaModel_Log();
		corbaLog.logItems = corbaLogItems;
		
		return corbaLog;
	}
}
