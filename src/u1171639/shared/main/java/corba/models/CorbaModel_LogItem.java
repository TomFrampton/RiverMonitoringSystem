package u1171639.shared.main.java.corba.models;


/**
* u1171639/shared/main/java/corba/models/CorbaModel_LogItem.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Models.idl
* Friday, 15 May 2015 00:04:27 o'clock BST
*/

public final class CorbaModel_LogItem implements org.omg.CORBA.portable.IDLEntity
{
  public long timestamp = (long)0;
  public String event = null;
  public String message = null;

  public CorbaModel_LogItem ()
  {
  } // ctor

  public CorbaModel_LogItem (long _timestamp, String _event, String _message)
  {
    timestamp = _timestamp;
    event = _event;
    message = _message;
  } // ctor

} // class CorbaModel_LogItem
