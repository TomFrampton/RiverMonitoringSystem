package u1171639.lms.main.java.corba;

/**
* u1171639/lms/main/java/corba/LocalityHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Workspaces/Eclipse/RiverMonitoringSystem/src/u1171639/lms/main/resources/idl/LMS_RMC.idl
* Wednesday, 22 April 2015 16:43:07 o'clock BST
*/

public final class LocalityHolder implements org.omg.CORBA.portable.Streamable
{
  public u1171639.lms.main.java.corba.Locality value = null;

  public LocalityHolder ()
  {
  }

  public LocalityHolder (u1171639.lms.main.java.corba.Locality initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = u1171639.lms.main.java.corba.LocalityHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    u1171639.lms.main.java.corba.LocalityHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return u1171639.lms.main.java.corba.LocalityHelper.type ();
  }

}