package u1171639.shared.main.java.corba.models;

/**
* u1171639/shared/main/java/corba/models/CorbaModel_SensorHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Models.idl
* Monday, 20 April 2015 23:09:46 o'clock BST
*/

public final class CorbaModel_SensorHolder implements org.omg.CORBA.portable.Streamable
{
  public u1171639.shared.main.java.corba.models.CorbaModel_Sensor value = null;

  public CorbaModel_SensorHolder ()
  {
  }

  public CorbaModel_SensorHolder (u1171639.shared.main.java.corba.models.CorbaModel_Sensor initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = u1171639.shared.main.java.corba.models.CorbaModel_SensorHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    u1171639.shared.main.java.corba.models.CorbaModel_SensorHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return u1171639.shared.main.java.corba.models.CorbaModel_SensorHelper.type ();
  }

}
