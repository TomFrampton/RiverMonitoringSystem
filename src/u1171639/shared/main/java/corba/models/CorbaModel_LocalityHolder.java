package u1171639.shared.main.java.corba.models;

/**
* u1171639/shared/main/java/corba/models/CorbaModel_LocalityHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Models.idl
* Friday, 1 May 2015 16:46:24 o'clock BST
*/

public final class CorbaModel_LocalityHolder implements org.omg.CORBA.portable.Streamable
{
  public u1171639.shared.main.java.corba.models.CorbaModel_Locality value = null;

  public CorbaModel_LocalityHolder ()
  {
  }

  public CorbaModel_LocalityHolder (u1171639.shared.main.java.corba.models.CorbaModel_Locality initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = u1171639.shared.main.java.corba.models.CorbaModel_LocalityHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    u1171639.shared.main.java.corba.models.CorbaModel_LocalityHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return u1171639.shared.main.java.corba.models.CorbaModel_LocalityHelper.type ();
  }

}