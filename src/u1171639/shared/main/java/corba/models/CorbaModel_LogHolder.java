package u1171639.shared.main.java.corba.models;

/**
* u1171639/shared/main/java/corba/models/CorbaModel_LogHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Models.idl
* Monday, 20 April 2015 23:09:46 o'clock BST
*/

public final class CorbaModel_LogHolder implements org.omg.CORBA.portable.Streamable
{
  public u1171639.shared.main.java.corba.models.CorbaModel_Log value = null;

  public CorbaModel_LogHolder ()
  {
  }

  public CorbaModel_LogHolder (u1171639.shared.main.java.corba.models.CorbaModel_Log initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = u1171639.shared.main.java.corba.models.CorbaModel_LogHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    u1171639.shared.main.java.corba.models.CorbaModel_LogHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return u1171639.shared.main.java.corba.models.CorbaModel_LogHelper.type ();
  }

}
