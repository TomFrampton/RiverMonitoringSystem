package u1171639.lms.main.java.corba;


/**
* u1171639/lms/main/java/corba/LMS_SensorOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Workspaces/Eclipse/RiverMonitoringSystem/src/u1171639/lms/main/resources/idl/LMS_Sensor.idl
* Sunday, 5 April 2015 01:16:34 o'clock BST
*/

public interface LMS_SensorOperations 
{
  void raiseAlarm (String zone);
  void register (String ior, String zone);
} // interface LMS_SensorOperations