package u1171639.shared.main.java.corba.models;

/**
* u1171639/shared/main/java/corba/models/CorbaModel_LocalityHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Models.idl
* Friday, 15 May 2015 00:04:27 o'clock BST
*/

public final class CorbaModel_LocalityHolder implements org.omg.CORBA.portable.Streamable
{
  public u1171639.shared.main.java.corba.models.CorbaModel_Locality value = null;

  public CorbaModel_LocalityHolder ()
  {
  }

  public CorbaModel_LocalityHolder (u1171639.shared.main.java.corba.models.CorbaModel_Locality initialValue)
  {
    this.value = initialValue;
  }

  @Override
public void _read (org.omg.CORBA.portable.InputStream i)
  {
    this.value = u1171639.shared.main.java.corba.models.CorbaModel_LocalityHelper.read (i);
  }

  @Override
public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    u1171639.shared.main.java.corba.models.CorbaModel_LocalityHelper.write (o, this.value);
  }

  @Override
public org.omg.CORBA.TypeCode _type ()
  {
    return u1171639.shared.main.java.corba.models.CorbaModel_LocalityHelper.type ();
  }

}
