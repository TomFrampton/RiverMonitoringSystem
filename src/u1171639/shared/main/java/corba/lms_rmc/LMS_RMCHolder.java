package u1171639.shared.main.java.corba.lms_rmc;

/**
* u1171639/shared/main/java/corba/lms_rmc/LMS_RMCHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from LMS_RMC.idl
* Thursday, 30 April 2015 10:50:00 o'clock BST
*/

public final class LMS_RMCHolder implements org.omg.CORBA.portable.Streamable
{
  public u1171639.shared.main.java.corba.lms_rmc.LMS_RMC value = null;

  public LMS_RMCHolder ()
  {
  }

  public LMS_RMCHolder (u1171639.shared.main.java.corba.lms_rmc.LMS_RMC initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = u1171639.shared.main.java.corba.lms_rmc.LMS_RMCHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    u1171639.shared.main.java.corba.lms_rmc.LMS_RMCHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return u1171639.shared.main.java.corba.lms_rmc.LMS_RMCHelper.type ();
  }

}
