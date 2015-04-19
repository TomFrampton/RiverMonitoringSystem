package u1171639.lms.main.java.client;

import org.omg.CosNaming.NamingContextPackage.NotFound;

import u1171639.lms.main.java.utils.LMSConfig;
import u1171639.shared.main.java.corba.rmc.RMCHelper;
import u1171639.shared.main.java.exception.ConnectionException;
import u1171639.shared.main.java.exception.ServerNotFoundException;
import u1171639.shared.main.java.utils.CorbaUtils;

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
	public void connect() throws ServerNotFoundException, ConnectionException {
		try {
			if(rmcIOR == null) {
				this.rmcIOR = CorbaUtils.resolveService(this.servantName);
			}
			
			this.rmc = RMCHelper.narrow(this.rmcIOR);
			this.rmc.register(this.serviceIOR, LMSConfig.getLocality());
		
		} catch (org.omg.CORBA.COMM_FAILURE e) {
			throw new ConnectionException("Could not connect to server with name: " + this.servantName + ".");
		}
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

	@Override
	public void sensorAdded() {
		this.rmc.sensorAdded();
	}

}
