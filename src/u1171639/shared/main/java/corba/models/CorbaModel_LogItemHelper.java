package u1171639.shared.main.java.corba.models;


/**
* u1171639/shared/main/java/corba/models/CorbaModel_LogItemHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Models.idl
* Friday, 1 May 2015 16:46:24 o'clock BST
*/

abstract public class CorbaModel_LogItemHelper
{
  private static String  _id = "IDL:models/CorbaModel_LogItem:1.0";

  public static void insert (org.omg.CORBA.Any a, u1171639.shared.main.java.corba.models.CorbaModel_LogItem that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static u1171639.shared.main.java.corba.models.CorbaModel_LogItem extract (org.omg.CORBA.Any a)
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
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [2];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "event",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[1] = new org.omg.CORBA.StructMember (
            "message",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (u1171639.shared.main.java.corba.models.CorbaModel_LogItemHelper.id (), "CorbaModel_LogItem", _members0);
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

  public static u1171639.shared.main.java.corba.models.CorbaModel_LogItem read (org.omg.CORBA.portable.InputStream istream)
  {
    u1171639.shared.main.java.corba.models.CorbaModel_LogItem value = new u1171639.shared.main.java.corba.models.CorbaModel_LogItem ();
    value.event = istream.read_string ();
    value.message = istream.read_string ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, u1171639.shared.main.java.corba.models.CorbaModel_LogItem value)
  {
    ostream.write_string (value.event);
    ostream.write_string (value.message);
  }

}