package u1171639.shared.main.java.corba.lms_rmc;


/**
* u1171639/shared/main/java/corba/lms_rmc/CorbaLocality.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from LMS_RMC.idl
* Thursday, 9 April 2015 17:54:36 o'clock BST
*/

public final class CorbaLocality implements org.omg.CORBA.portable.IDLEntity
{
  public String name = null;
  public u1171639.shared.main.java.corba.lms_rmc.CorbaZone zones[] = null;

  public CorbaLocality ()
  {
  } // ctor

  public CorbaLocality (String _name, u1171639.shared.main.java.corba.lms_rmc.CorbaZone[] _zones)
  {
    name = _name;
    zones = _zones;
  } // ctor

} // class CorbaLocality
