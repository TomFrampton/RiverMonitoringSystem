package u1171639.shared.main.java.corba.lms_rmc;

/**
* u1171639/shared/main/java/corba/lms_rmc/LMS_RMCHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from LMS_RMC.idl
* Friday, 15 May 2015 00:04:28 o'clock BST
*/

public final class LMS_RMCHolder implements org.omg.CORBA.portable.Streamable
{
  public u1171639.shared.main.java.corba.lms_rmc.LMS_RMC value = null;

  public LMS_RMCHolder ()
  {
  }

  public LMS_RMCHolder (u1171639.shared.main.java.corba.lms_rmc.LMS_RMC initialValue)
  {
    this.value = initialValue;
  }

  @Override
public void _read (org.omg.CORBA.portable.InputStream i)
  {
    this.value = u1171639.shared.main.java.corba.lms_rmc.LMS_RMCHelper.read (i);
  }

  @Override
public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    u1171639.shared.main.java.corba.lms_rmc.LMS_RMCHelper.write (o, this.value);
  }

  @Override
public org.omg.CORBA.TypeCode _type ()
  {
    return u1171639.shared.main.java.corba.lms_rmc.LMS_RMCHelper.type ();
  }

}
