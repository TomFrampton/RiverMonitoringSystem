package u1171639.shared.main.java.corba.lms_sensor;


/**
* u1171639/shared/main/java/corba/lms_sensor/LMS_SensorHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from LMS_Sensor.idl
* Thursday, 30 April 2015 13:41:00 o'clock BST
*/

abstract public class LMS_SensorHelper
{
  private static String  _id = "IDL:lms_sensor/LMS_Sensor:1.0";

  public static void insert (org.omg.CORBA.Any a, u1171639.shared.main.java.corba.lms_sensor.LMS_Sensor that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static u1171639.shared.main.java.corba.lms_sensor.LMS_Sensor extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (u1171639.shared.main.java.corba.lms_sensor.LMS_SensorHelper.id (), "LMS_Sensor");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static u1171639.shared.main.java.corba.lms_sensor.LMS_Sensor read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_LMS_SensorStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, u1171639.shared.main.java.corba.lms_sensor.LMS_Sensor value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static u1171639.shared.main.java.corba.lms_sensor.LMS_Sensor narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof u1171639.shared.main.java.corba.lms_sensor.LMS_Sensor)
      return (u1171639.shared.main.java.corba.lms_sensor.LMS_Sensor)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      u1171639.shared.main.java.corba.lms_sensor._LMS_SensorStub stub = new u1171639.shared.main.java.corba.lms_sensor._LMS_SensorStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static u1171639.shared.main.java.corba.lms_sensor.LMS_Sensor unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof u1171639.shared.main.java.corba.lms_sensor.LMS_Sensor)
      return (u1171639.shared.main.java.corba.lms_sensor.LMS_Sensor)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      u1171639.shared.main.java.corba.lms_sensor._LMS_SensorStub stub = new u1171639.shared.main.java.corba.lms_sensor._LMS_SensorStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
