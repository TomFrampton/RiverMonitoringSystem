package u1171639.shared.main.java.corba.lms_rmc;


/**
* u1171639/shared/main/java/corba/lms_rmc/CorbaLogItem.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from LMS_RMC.idl
* Thursday, 9 April 2015 17:54:36 o'clock BST
*/

public final class CorbaLogItem implements org.omg.CORBA.portable.IDLEntity
{
  public String event = null;
  public String message = null;

  public CorbaLogItem ()
  {
  } // ctor

  public CorbaLogItem (String _event, String _message)
  {
    event = _event;
    message = _message;
  } // ctor

} // class CorbaLogItem