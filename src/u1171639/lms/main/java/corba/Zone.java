package u1171639.lms.main.java.corba;


/**
* u1171639/lms/main/java/corba/Zone.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Workspaces/Eclipse/RiverMonitoringSystem/src/u1171639/lms/main/resources/idl/LMS_RMC.idl
* Wednesday, 22 April 2015 16:43:07 o'clock BST
*/

public final class Zone implements org.omg.CORBA.portable.IDLEntity
{
  public String name = null;
  public boolean alarmRaised = false;

  public Zone ()
  {
  } // ctor

  public Zone (String _name, boolean _alarmRaised)
  {
    name = _name;
    alarmRaised = _alarmRaised;
  } // ctor

} // class Zone