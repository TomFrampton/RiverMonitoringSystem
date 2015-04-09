package u1171639.lms.main.java.service;

import java.util.ArrayList;
import java.util.List;

import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import u1171639.lms.main.java.client.Sensor;
import u1171639.lms.main.java.controller.LMSController;
import u1171639.lms.main.java.utils.CorbaUtils;
import u1171639.lms.main.java.utils.LMSConfig;
import u1171639.shared.main.java.corba.lms_rmc.CorbaLMSLog;
import u1171639.shared.main.java.corba.lms_rmc.CorbaLocality;
import u1171639.shared.main.java.corba.lms_rmc.CorbaLogItem;
import u1171639.shared.main.java.corba.lms_rmc.CorbaSensor;
import u1171639.shared.main.java.corba.lms_rmc.CorbaZone;
import u1171639.shared.main.java.corba.lms_rmc.LMS_RMC;
import u1171639.shared.main.java.corba.lms_rmc.LMS_RMCHelper;
import u1171639.shared.main.java.corba.lms_rmc.LMS_RMCPOA;
import u1171639.shared.main.java.logging.LogItem;

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
	public CorbaLocality getLocalityInfo() {
		List<u1171639.lms.main.java.model.Zone> zones = this.controller.getAllZones();
		
		// Copy data from Zone models to CORBA Zone objects
		CorbaZone[] corbaZones = new CorbaZone[zones.size()];
		for(int i = 0; i < zones.size(); ++i) {
			CorbaZone corbaZone = new CorbaZone();
			corbaZone.name = zones.get(i).getName();
			corbaZone.alarmRaised = zones.get(i).isAlarmRaised();
			corbaZones[i] = corbaZone;
			
			List<Sensor> sensors = zones.get(i).getSensors();
			CorbaSensor[] corbaSensors = new CorbaSensor[sensors.size()];
			for(int ii = 0; ii < sensors.size(); ++ii) {
				CorbaSensor corbaSensor = new CorbaSensor();
				corbaSensor.id = sensors.get(ii).getId();
				corbaSensors[ii] = corbaSensor;
			}
			
			corbaZones[i].sensors = corbaSensors;
		}
		
		CorbaLocality corbaLocality = new CorbaLocality();
		corbaLocality.name = LMSConfig.getLocality();
		corbaLocality.zones = corbaZones;
		
		return corbaLocality;
	}

	@Override
	public CorbaLMSLog getLogs() {
		List<LogItem> logs = this.controller.getAllLogs();
		
		// Convert from generic to CORBA LogItem model
		CorbaLogItem[] corbaLogItems = new CorbaLogItem[logs.size()];
		
		for(int i = 0; i < logs.size(); ++i) {
			LogItem item = logs.get(i);
			CorbaLogItem corbaItem = new CorbaLogItem();
			corbaItem.message = item.getMessage();
			corbaItem.event = item.getEvent().toString();
			corbaLogItems[i] = corbaItem;
		}
		
		CorbaLMSLog corbaLog = new CorbaLMSLog();
		corbaLog.logItems = corbaLogItems;
		
		return corbaLog;
	}
}
