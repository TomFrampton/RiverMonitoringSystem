package u1171639.lms.main.java.model;

import u1171639.lms.main.java.utils.LMSConfig;
import u1171639.lms.main.java.utils.CorbaUtils;
import u1171639.shared.main.java.corba.rmc.RMCHelper;

public class CorbaRMC implements RMC {
	private u1171639.shared.main.java.corba.rmc.RMC rmc = null;
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
		this.rmc.register(this.serviceIOR, LMSConfig.getLocality());
	}
	
	@Override
	public void disconnect() {
		this.rmc = null;
	}
	
	@Override
	public void raiseAlarm(String zone) {
		this.rmc.raiseAlarm(LMSConfig.getLocality(), zone);
	}
	
	public void setServiceIOR(String serviceIOR) {
		this.serviceIOR = serviceIOR;
	}

}
