package u1171639.lms.main.java.model;

public class CorbaSensor implements Sensor {
	
	private org.omg.CORBA.Object ior;
	
	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAlarmRaised() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void setIOR(org.omg.CORBA.Object ior) {
		this.ior = ior;
	}

}
