package u1171639.lms.main.java.service;

import java.util.ArrayList;
import java.util.List;

import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import u1171639.lms.main.java.controller.LMSController;
import u1171639.lms.main.java.corba.LMS_RMC;
import u1171639.lms.main.java.corba.LMS_RMCHelper;
import u1171639.lms.main.java.corba.LMS_RMCPOA;
import u1171639.lms.main.java.corba.Locality;
import u1171639.lms.main.java.utils.CorbaUtils;
import u1171639.lms.main.java.utils.LMSConfig;

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
	public Locality getLocalityInfo() {
		List<u1171639.lms.main.java.model.Zone> zones = this.controller.getAllZones();
		
		// Copy data from Zone models to CORBA Zone objects
		u1171639.lms.main.java.corba.Zone[] corbaZones = new u1171639.lms.main.java.corba.Zone[zones.size()];
		for(int i = 0; i < zones.size(); ++i) {
			u1171639.lms.main.java.corba.Zone corbaZone = new u1171639.lms.main.java.corba.Zone();
			corbaZone.name = zones.get(i).getName();
			corbaZone.alarmRaised = zones.get(i).isAlarmRaised();
			corbaZones[i] = corbaZone;
		}
		
		u1171639.lms.main.java.corba.Locality corbaLocality = new u1171639.lms.main.java.corba.Locality();
		corbaLocality.name = LMSConfig.getLocality();
		corbaLocality.zones = corbaZones;
		
		return corbaLocality;
	}
}
