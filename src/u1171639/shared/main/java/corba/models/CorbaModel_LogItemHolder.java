package u1171639.shared.main.java.corba.models;

/**
* u1171639/shared/main/java/corba/models/CorbaModel_LogItemHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Models.idl
* Thursday, 30 April 2015 10:50:00 o'clock BST
*/

public final class CorbaModel_LogItemHolder implements org.omg.CORBA.portable.Streamable
{
  public u1171639.shared.main.java.corba.models.CorbaModel_LogItem value = null;

  public CorbaModel_LogItemHolder ()
  {
  }

  public CorbaModel_LogItemHolder (u1171639.shared.main.java.corba.models.CorbaModel_LogItem initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = u1171639.shared.main.java.corba.models.CorbaModel_LogItemHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    u1171639.shared.main.java.corba.models.CorbaModel_LogItemHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return u1171639.shared.main.java.corba.models.CorbaModel_LogItemHelper.type ();
  }

}
