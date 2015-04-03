package u1171639.sensor.main.java.model;

import u1171639.lms.main.java.corba.LMSHelper;
import u1171639.sensor.main.java.utils.CorbaUtils;
import u1171639.sensor.main.java.utils.SensorConfig;

public class CorbaLMS implements LMS {
	private u1171639.lms.main.java.corba.LMS lms = null;
	private org.omg.CORBA.Object lmsIOR = null;
	
	private String servantName;
	
	public CorbaLMS(String servantName) {
		this.servantName = servantName;
	}
	
	@Override
	public void connect() {
		if(lmsIOR == null) {
			this.lmsIOR = CorbaUtils.resolveService(this.servantName);
		}
		
		this.lms = LMSHelper.narrow(this.lmsIOR);
	}
	
	@Override
	public void disconnect() {
		this.lms = null;
	}
	
	@Override
	public void raiseAlarm() {
		lms.raiseAlarm(SensorConfig.getZone());
	}
}
