package u1171639.shared.main.java.corba.lms_sensor;

/**
* u1171639/shared/main/java/corba/lms_sensor/LMS_SensorHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from LMS_Sensor.idl
* Friday, 15 May 2015 00:04:27 o'clock BST
*/

public final class LMS_SensorHolder implements org.omg.CORBA.portable.Streamable
{
  public u1171639.shared.main.java.corba.lms_sensor.LMS_Sensor value = null;

  public LMS_SensorHolder ()
  {
  }

  public LMS_SensorHolder (u1171639.shared.main.java.corba.lms_sensor.LMS_Sensor initialValue)
  {
    this.value = initialValue;
  }

  @Override
public void _read (org.omg.CORBA.portable.InputStream i)
  {
    this.value = u1171639.shared.main.java.corba.lms_sensor.LMS_SensorHelper.read (i);
  }

  @Override
public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    u1171639.shared.main.java.corba.lms_sensor.LMS_SensorHelper.write (o, this.value);
  }

  @Override
public org.omg.CORBA.TypeCode _type ()
  {
    return u1171639.shared.main.java.corba.lms_sensor.LMS_SensorHelper.type ();
  }

}
