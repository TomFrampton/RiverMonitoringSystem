package u1171639.rmc.main.java.model;

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

}
