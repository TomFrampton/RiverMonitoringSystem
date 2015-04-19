package u1171639.shared.main.java.corba.sensor;


/**
* u1171639/shared/main/java/corba/sensor/SensorOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Sensor.idl
* Saturday, 18 April 2015 18:41:22 o'clock BST
*/

public interface SensorOperations 
{
  boolean isAlarmRaised ();
  boolean activate ();
  boolean deactivate ();
  boolean isActive ();
  double getThreshold ();
  boolean setThreshold (double threshold);
  double getReading ();
} // interface SensorOperations
