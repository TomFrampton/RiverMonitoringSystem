package u1171639.lms.main.java.model;

import u1171639.lms.main.java.utils.LMSConfig;
import u1171639.rmc.main.java.corba.RMCHelper;
import u1171639.lms.main.java.utils.CorbaUtils;

public class CorbaRMC implements RMC {
	private u1171639.rmc.main.java.corba.RMC rmc = null;
	private org.omg.CORBA.Object rmcIOR = null;
	
	private String serviceIOR;
	
	private String servantName;
	
	public CorbaRMC(String servantName, String serviceIOR) {
		this.servantName = servantName;
		this.serviceIOR = serviceIOR;
	}
	
	@Override
	public void connect() {
		if(rmcIOR == null) {
			this.rmcIOR = CorbaUtils.resolveService(this.servantName);
		}
		
		this.rmc = RMCHelper.narrow(this.rmcIOR);
		rmc.register(this.serviceIOR, LMSConfig.getLocality());
	}
	
	@Override
	public void disconnect() {
		this.rmc = null;
	}
	
	@Override
	public void raiseAlarm() {
		this.rmc.raiseAlarm();
	}
	
	public void setServiceIOR(String serviceIOR) {
		this.serviceIOR = serviceIOR;
	}

}
