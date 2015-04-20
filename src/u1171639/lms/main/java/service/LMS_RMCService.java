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
import u1171639.shared.main.java.utils.ModelConverter;

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
		return ModelConverter.convertLocality(LMSConfig.getLocality(), this.controller.getAllZones());
	}

	@Override
	public CorbaModel_Log getLog() {
		return ModelConverter.convertLog(this.controller.getAllLogs());
	}

	@Override
	public boolean activateSensor(String zoneName, String sensorName) {
		return this.controller.activateSensor(zoneName, sensorName);
	}

	@Override
	public boolean deactivateSensor(String zoneName, String sensorName) {
		return this.controller.deactivateSensor(zoneName, sensorName);
	}

	@Override
	public boolean setWarningThreshold(String zoneName, String sensorName, double threshold) {
		return this.controller.setWarningThreshold(zoneName, sensorName, threshold);
	}

	@Override
	public double getSensorReading(String zoneName, String sensorName) {
		return this.controller.getSensorReading(zoneName, sensorName);
	}

	@Override
	public boolean resetAlarm(String zoneName) {
		return this.controller.resetAlarm(zoneName);
	}
}
