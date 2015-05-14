package u1171639.shared.main.java.corba.lms_rmc;


/**
* u1171639/shared/main/java/corba/lms_rmc/LMS_RMCOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from LMS_RMC.idl
* Friday, 15 May 2015 00:04:28 o'clock BST
*/

public interface LMS_RMCOperations 
{
  u1171639.shared.main.java.corba.models.CorbaModel_Locality getZoneUpdates ();
  u1171639.shared.main.java.corba.models.CorbaModel_Log getLog ();
  boolean activateSensor (String zoneName, String sensorName);
  boolean deactivateSensor (String zoneName, String sensorName);
  boolean setWarningThreshold (String zoneName, String sensorName, double threshold);
  double getSensorReading (String zoneName, String sensorName);
  boolean resetAlarm (String zoneName);
} // interface LMS_RMCOperations
