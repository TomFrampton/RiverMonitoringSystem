package u1171639.shared.main.java.corba.models;


/**
* u1171639/shared/main/java/corba/models/CorbaModel_SensorHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Models.idl
* Friday, 15 May 2015 00:04:27 o'clock BST
*/

abstract public class CorbaModel_SensorHelper
{
  private static String  _id = "IDL:models/CorbaModel_Sensor:1.0";

  public static void insert (org.omg.CORBA.Any a, u1171639.shared.main.java.corba.models.CorbaModel_Sensor that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static u1171639.shared.main.java.corba.models.CorbaModel_Sensor extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [3];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "name",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_double);
          _members0[1] = new org.omg.CORBA.StructMember (
            "threshold",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_boolean);
          _members0[2] = new org.omg.CORBA.StructMember (
            "active",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (u1171639.shared.main.java.corba.models.CorbaModel_SensorHelper.id (), "CorbaModel_Sensor", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static u1171639.shared.main.java.corba.models.CorbaModel_Sensor read (org.omg.CORBA.portable.InputStream istream)
  {
    u1171639.shared.main.java.corba.models.CorbaModel_Sensor value = new u1171639.shared.main.java.corba.models.CorbaModel_Sensor ();
    value.name = istream.read_string ();
    value.threshold = istream.read_double ();
    value.active = istream.read_boolean ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, u1171639.shared.main.java.corba.models.CorbaModel_Sensor value)
  {
    ostream.write_string (value.name);
    ostream.write_double (value.threshold);
    ostream.write_boolean (value.active);
  }

}
