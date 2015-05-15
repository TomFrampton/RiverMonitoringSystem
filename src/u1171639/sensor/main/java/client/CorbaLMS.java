package u1171639.sensor.main.java.client;

import u1171639.sensor.main.java.utils.SensorConfig;
import u1171639.shared.main.java.corba.lms_sensor.LMS_Sensor;
import u1171639.shared.main.java.corba.lms_sensor.LMS_SensorHelper;
import u1171639.shared.main.java.exception.ConnectionException;
import u1171639.shared.main.java.exception.ServerNotFoundException;
import u1171639.shared.main.java.utils.CorbaUtils;

/**
 * CORBA Specific implementation of the LMS client interface. Can connect to a CORBA LMS
 * and raise an alarm.
 * @author Tom
 *
 */
public class CorbaLMS implements LMS {
	private LMS_Sensor lms = null;
	private org.omg.CORBA.Object lmsIOR = null;
	
	private String serviceIOR;
	
	private String servantName;
	
	public CorbaLMS(String servantName, String serviceIOR) {
		this.servantName = servantName;
		this.serviceIOR = serviceIOR;
	}
	
	/**
	 * Opens a connection to the LMS.
	 * @throws ServerNotFoundException If the LMS was not found.
	 * @throws ConnectionException If there was a problem connecting to the LMS
	 */
	@Override
	public void connect() throws ServerNotFoundException, ConnectionException {
		try {	
			if(this.lmsIOR == null) {
				this.lmsIOR = CorbaUtils.resolveService(this.servantName);
			}
		
			this.lms = LMS_SensorHelper.narrow(this.lmsIOR);
			String sensorName = this.lms.register(this.serviceIOR, SensorConfig.getZone());
			SensorConfig.setSensorName(sensorName);
			
		} catch(org.omg.CORBA.COMM_FAILURE e) {
			throw new ConnectionException("Could not connect to server with name: " + this.servantName + ".");
		}
	}
	
	/**
	 * Close the connection to the LMS.
	 */
	@Override
	public void disconnect() {
		this.lms = null;
	}
	
	/**
	 * Raise an alarm at the LMS.
	 */
	@Override
	public void raiseAlarm() {
		this.lms.raiseAlarm(SensorConfig.getZone());
	}
	
	/**
	 * Sets the IOR that will be passed to the LMS when we register.
	 * @param serviceIOR The IOR of the sensor service.
	 */
	public void setServiceIOR(String serviceIOR) {
		this.serviceIOR = serviceIOR;
	}
}
