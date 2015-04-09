package u1171639.shared.main.java.corba.lms_rmc;


/**
* u1171639/shared/main/java/corba/lms_rmc/CorbaZoneHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from LMS_RMC.idl
* Thursday, 9 April 2015 19:47:15 o'clock BST
*/

abstract public class CorbaZoneHelper
{
  private static String  _id = "IDL:lms_rmc/CorbaZone:1.0";

  public static void insert (org.omg.CORBA.Any a, u1171639.shared.main.java.corba.lms_rmc.CorbaZone that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static u1171639.shared.main.java.corba.lms_rmc.CorbaZone extract (org.omg.CORBA.Any a)
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
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_boolean);
          _members0[1] = new org.omg.CORBA.StructMember (
            "alarmRaised",
            _tcOf_members0,
            null);
          _tcOf_members0 = u1171639.shared.main.java.corba.lms_rmc.CorbaSensorHelper.type ();
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_sequence_tc (0, _tcOf_members0);
          _members0[2] = new org.omg.CORBA.StructMember (
            "sensors",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (u1171639.shared.main.java.corba.lms_rmc.CorbaZoneHelper.id (), "CorbaZone", _members0);
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

  public static u1171639.shared.main.java.corba.lms_rmc.CorbaZone read (org.omg.CORBA.portable.InputStream istream)
  {
    u1171639.shared.main.java.corba.lms_rmc.CorbaZone value = new u1171639.shared.main.java.corba.lms_rmc.CorbaZone ();
    value.name = istream.read_string ();
    value.alarmRaised = istream.read_boolean ();
    int _len0 = istream.read_long ();
    value.sensors = new u1171639.shared.main.java.corba.lms_rmc.CorbaSensor[_len0];
    for (int _o1 = 0;_o1 < value.sensors.length; ++_o1)
      value.sensors[_o1] = u1171639.shared.main.java.corba.lms_rmc.CorbaSensorHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, u1171639.shared.main.java.corba.lms_rmc.CorbaZone value)
  {
    ostream.write_string (value.name);
    ostream.write_boolean (value.alarmRaised);
    ostream.write_long (value.sensors.length);
    for (int _i0 = 0;_i0 < value.sensors.length; ++_i0)
      u1171639.shared.main.java.corba.lms_rmc.CorbaSensorHelper.write (ostream, value.sensors[_i0]);
  }

}
