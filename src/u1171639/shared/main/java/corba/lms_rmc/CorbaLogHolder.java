package u1171639.shared.main.java.corba.lms_rmc;

/**
* u1171639/shared/main/java/corba/lms_rmc/CorbaLogHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from LMS_RMC.idl
* Friday, 10 April 2015 14:13:12 o'clock BST
*/

public final class CorbaLogHolder implements org.omg.CORBA.portable.Streamable
{
  public u1171639.shared.main.java.corba.lms_rmc.CorbaLog value = null;

  public CorbaLogHolder ()
  {
  }

  public CorbaLogHolder (u1171639.shared.main.java.corba.lms_rmc.CorbaLog initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = u1171639.shared.main.java.corba.lms_rmc.CorbaLogHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    u1171639.shared.main.java.corba.lms_rmc.CorbaLogHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return u1171639.shared.main.java.corba.lms_rmc.CorbaLogHelper.type ();
  }

}
