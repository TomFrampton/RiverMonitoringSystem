package u1171639.shared.main.java.corba.rmc;


/**
* u1171639/shared/main/java/corba/rmc/RMCOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from RMC.idl
* Saturday, 18 April 2015 18:41:22 o'clock BST
*/

public interface RMCOperations 
{
  void raiseAlarm (String locality, String zone);
  void register (String ior, String locality);
  void sensorAdded ();
} // interface RMCOperations
