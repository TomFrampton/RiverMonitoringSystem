package u1171639.shared.main.java.corba.models;


/**
* u1171639/shared/main/java/corba/models/CorbaModel_Locality.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Models.idl
* Friday, 15 May 2015 00:04:27 o'clock BST
*/

public final class CorbaModel_Locality implements org.omg.CORBA.portable.IDLEntity
{
  public String name = null;
  public u1171639.shared.main.java.corba.models.CorbaModel_Zone zones[] = null;

  public CorbaModel_Locality ()
  {
  } // ctor

  public CorbaModel_Locality (String _name, u1171639.shared.main.java.corba.models.CorbaModel_Zone[] _zones)
  {
    this.name = _name;
    this.zones = _zones;
  } // ctor

} // class CorbaModel_Locality
