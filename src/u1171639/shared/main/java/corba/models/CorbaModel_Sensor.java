package u1171639.shared.main.java.corba.models;


/**
* u1171639/shared/main/java/corba/models/CorbaModel_Sensor.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Models.idl
* Monday, 20 April 2015 23:09:46 o'clock BST
*/

public final class CorbaModel_Sensor implements org.omg.CORBA.portable.IDLEntity
{
  public String name = null;
  public double threshold = (double)0;
  public boolean active = false;

  public CorbaModel_Sensor ()
  {
  } // ctor

  public CorbaModel_Sensor (String _name, double _threshold, boolean _active)
  {
    name = _name;
    threshold = _threshold;
    active = _active;
  } // ctor

} // class CorbaModel_Sensor
