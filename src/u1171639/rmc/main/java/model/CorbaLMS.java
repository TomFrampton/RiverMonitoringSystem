package u1171639.rmc.main.java.model;

import java.util.concurrent.Callable;

import u1171639.lms.main.java.corba.LMS_RMCHelper;

public class CorbaLMS implements LMS {
	private org.omg.CORBA.Object ior;
	private u1171639.lms.main.java.corba.LMS_RMC lms;
	
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
	public u1171639.rmc.main.java.model.Locality getLocalityInfo() {
		u1171639.lms.main.java.corba.Locality corbaLocality = this.communicate(new Callable<u1171639.lms.main.java.corba.Locality>() {
			@Override
			public u1171639.lms.main.java.corba.Locality call() throws Exception {
				return lms.getLocalityInfo();
			}
		});
		
		// Convert corbaLocality to Locality model
		u1171639.rmc.main.java.model.Locality locality = new u1171639.rmc.main.java.model.Locality();
		
		for(int i = 0; i < corbaLocality.zones.length; ++i) {
			u1171639.rmc.main.java.model.Zone zone = new u1171639.rmc.main.java.model.Zone();
			zone.setName(corbaLocality.zones[i].name);
			zone.setAlarmRaised(corbaLocality.zones[i].alarmRaised);
			locality.getZones().add(zone);
		}
		
		locality.setName(corbaLocality.name);
		
		return locality;
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
