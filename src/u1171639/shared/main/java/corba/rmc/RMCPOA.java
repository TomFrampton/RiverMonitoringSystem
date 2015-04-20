package u1171639.shared.main.java.corba.rmc;


/**
* u1171639/shared/main/java/corba/rmc/RMCPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from RMC.idl
* Monday, 20 April 2015 23:09:47 o'clock BST
*/

public abstract class RMCPOA extends org.omg.PortableServer.Servant
 implements u1171639.shared.main.java.corba.rmc.RMCOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("raiseAlarm", new java.lang.Integer (0));
    _methods.put ("register", new java.lang.Integer (1));
    _methods.put ("sensorAdded", new java.lang.Integer (2));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // rmc/RMC/raiseAlarm
       {
         String locality = in.read_string ();
         String zone = in.read_string ();
         this.raiseAlarm (locality, zone);
         out = $rh.createReply();
         break;
       }

       case 1:  // rmc/RMC/register
       {
         String ior = in.read_string ();
         String locality = in.read_string ();
         this.register (ior, locality);
         out = $rh.createReply();
         break;
       }

       case 2:  // rmc/RMC/sensorAdded
       {
         this.sensorAdded ();
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:rmc/RMC:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public RMC _this() 
  {
    return RMCHelper.narrow(
    super._this_object());
  }

  public RMC _this(org.omg.CORBA.ORB orb) 
  {
    return RMCHelper.narrow(
    super._this_object(orb));
  }


} // class RMCPOA
