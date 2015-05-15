package u1171639.sensor.main.java.client;

import u1171639.sensor.main.java.utils.SensorConfig;
import u1171639.shared.main.java.corba.lms_sensor.LMS_Sensor;
import u1171639.shared.main.java.corba.lms_sensor.LMS_SensorHelper;
import u1171639.shared.main.java.exception.ConnectionException;
import u1171639.shared.main.java.exception.ServerNotFoundException;
import u1171639.shared.main.java.utils.CorbaUtils;

/**
 * CORBA Specific implementation of the LMS client. Encapsulates the complexity
 * of connecting to and communicating with an LMS.
 * @author Tom
 *
 */
public class CorbaLMS implements LMS {
	// The servant
	private LMS_Sensor lms = null;
	// IOR of the servant
	private org.omg.CORBA.Object lmsIOR = null;
	// IOR of the service of this sensor. Will be passed to the LMS when we register this sensor.
	private String serviceIOR;
	// Name that the LMS is registered with in the naming service
	private String servantName;
	
	/**
	 * Constructor.
	 * @param servantName Name that the LMS is registered with in the naming service
	 * @param serviceIOR The IOR of this sensors service
	 */
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
			// If not already connected
			if(this.lmsIOR == null) {
				// Find the IOR of the LMS using the naming service
				this.lmsIOR = CorbaUtils.resolveService(this.servantName);
			}
		
			// Narrow the IOR of the LMS which will open a connection
			this.lms = LMS_SensorHelper.narrow(this.lmsIOR);
			// Call the register method of the LMS to register this sensor
			String sensorName = this.lms.register(this.serviceIOR, SensorConfig.getZone());
			// LMS will return a sensor name that we will use to identify ourself
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
		// The zone of this sensor was set in the config file at application startup
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
