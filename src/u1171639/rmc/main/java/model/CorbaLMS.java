package u1171639.rmc.main.java.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import u1171639.shared.main.java.corba.lms_rmc.CorbaLocality;
import u1171639.shared.main.java.corba.lms_rmc.LMS_RMC;
import u1171639.shared.main.java.corba.lms_rmc.LMS_RMCHelper;

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
	public List<Zone> getLocalityInfo() {
		CorbaLocality corbaLocality = this.communicate(new Callable<CorbaLocality>() {
			@Override
			public CorbaLocality call() throws Exception {
				return lms.getLocalityInfo();
			}
		});
		
		List<Zone> zones = new ArrayList<Zone>();
		
		for(int i = 0; i < corbaLocality.zones.length; ++i) {
			Zone zone = new Zone();
			zone.setName(corbaLocality.zones[i].name);
			zone.setAlarmRaised(corbaLocality.zones[i].alarmRaised);
			zones.add(zone);
		}
		
		return zones;
	}
	
	@Override
	public void handleRaisedAlarm(String zone) {
		// TODO Auto-generated method stub
		
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
