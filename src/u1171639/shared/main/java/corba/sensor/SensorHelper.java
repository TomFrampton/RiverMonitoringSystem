package u1171639.shared.main.java.corba.sensor;


/**
* u1171639/shared/main/java/corba/sensor/SensorHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Sensor.idl
* Saturday, 18 April 2015 18:41:22 o'clock BST
*/

abstract public class SensorHelper
{
  private static String  _id = "IDL:sensor/Sensor:1.0";

  public static void insert (org.omg.CORBA.Any a, u1171639.shared.main.java.corba.sensor.Sensor that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static u1171639.shared.main.java.corba.sensor.Sensor extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (u1171639.shared.main.java.corba.sensor.SensorHelper.id (), "Sensor");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static u1171639.shared.main.java.corba.sensor.Sensor read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_SensorStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, u1171639.shared.main.java.corba.sensor.Sensor value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static u1171639.shared.main.java.corba.sensor.Sensor narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof u1171639.shared.main.java.corba.sensor.Sensor)
      return (u1171639.shared.main.java.corba.sensor.Sensor)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      u1171639.shared.main.java.corba.sensor._SensorStub stub = new u1171639.shared.main.java.corba.sensor._SensorStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static u1171639.shared.main.java.corba.sensor.Sensor unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof u1171639.shared.main.java.corba.sensor.Sensor)
      return (u1171639.shared.main.java.corba.sensor.Sensor)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      u1171639.shared.main.java.corba.sensor._SensorStub stub = new u1171639.shared.main.java.corba.sensor._SensorStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
