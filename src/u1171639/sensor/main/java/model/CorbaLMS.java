package u1171639.sensor.main.java.model;

import u1171639.lms.main.java.corba.LMS_SensorHelper;
import u1171639.sensor.main.java.utils.CorbaUtils;
import u1171639.sensor.main.java.utils.SensorConfig;

public class CorbaLMS implements LMS {
	private u1171639.lms.main.java.corba.LMS_Sensor lms = null;
	private org.omg.CORBA.Object lmsIOR = null;
	
	private String serviceIOR;
	
	private String servantName;
	
	public CorbaLMS(String servantName, String serviceIOR) {
		this.servantName = servantName;
		this.serviceIOR = serviceIOR;
	}
	
	@Override
	public void connect() {
		if(lmsIOR == null) {
			this.lmsIOR = CorbaUtils.resolveService(this.servantName);
		}
		
		this.lms = LMS_SensorHelper.narrow(this.lmsIOR);
		this.lms.register(this.serviceIOR, SensorConfig.getZone());
	}
	
	@Override
	public void disconnect() {
		this.lms = null;
	}
	
	@Override
	public void raiseAlarm() {
		lms.raiseAlarm(SensorConfig.getZone());
	}
	
	public void setServiceIOR(String serviceIOR) {
		this.serviceIOR = serviceIOR;
	}
}
