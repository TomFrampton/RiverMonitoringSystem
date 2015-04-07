package u1171639.lms.main.java.corba;


/**
* u1171639/lms/main/java/corba/LMS_SensorPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Workspaces/Eclipse/RiverMonitoringSystem/src/u1171639/lms/main/resources/idl/LMS_Sensor.idl
* Wednesday, 22 April 2015 16:43:07 o'clock BST
*/

public abstract class LMS_SensorPOA extends org.omg.PortableServer.Servant
 implements u1171639.lms.main.java.corba.LMS_SensorOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("raiseAlarm", new java.lang.Integer (0));
    _methods.put ("register", new java.lang.Integer (1));
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
       case 0:  // lms_sensor/LMS_Sensor/raiseAlarm
       {
         String zone = in.read_string ();
         this.raiseAlarm (zone);
         out = $rh.createReply();
         break;
       }

       case 1:  // lms_sensor/LMS_Sensor/register
       {
         String ior = in.read_string ();
         String zone = in.read_string ();
         this.register (ior, zone);
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
    "IDL:lms_sensor/LMS_Sensor:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public LMS_Sensor _this() 
  {
    return LMS_SensorHelper.narrow(
    super._this_object());
  }

  public LMS_Sensor _this(org.omg.CORBA.ORB orb) 
  {
    return LMS_SensorHelper.narrow(
    super._this_object(orb));
  }


} // class LMS_SensorPOA
