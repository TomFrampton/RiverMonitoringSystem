package u1171639.shared.main.java.corba.sensor;

/**
* u1171639/shared/main/java/corba/sensor/SensorHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Sensor.idl
* Thursday, 30 April 2015 13:41:00 o'clock BST
*/

public final class SensorHolder implements org.omg.CORBA.portable.Streamable
{
  public u1171639.shared.main.java.corba.sensor.Sensor value = null;

  public SensorHolder ()
  {
  }

  public SensorHolder (u1171639.shared.main.java.corba.sensor.Sensor initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = u1171639.shared.main.java.corba.sensor.SensorHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    u1171639.shared.main.java.corba.sensor.SensorHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return u1171639.shared.main.java.corba.sensor.SensorHelper.type ();
  }

}
