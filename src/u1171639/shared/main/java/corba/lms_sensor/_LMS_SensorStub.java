package u1171639.shared.main.java.corba.lms_sensor;


/**
* u1171639/shared/main/java/corba/lms_sensor/_LMS_SensorStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from LMS_Sensor.idl
* Friday, 15 May 2015 00:04:27 o'clock BST
*/

public class _LMS_SensorStub extends org.omg.CORBA.portable.ObjectImpl implements u1171639.shared.main.java.corba.lms_sensor.LMS_Sensor
{

  @Override
public void raiseAlarm (String zone)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("raiseAlarm", true);
                $out.write_string (zone);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                raiseAlarm (zone        );
            } finally {
                _releaseReply ($in);
            }
  } // raiseAlarm

  @Override
public String register (String ior, String zone)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("register", true);
                $out.write_string (ior);
                $out.write_string (zone);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return register (ior, zone        );
            } finally {
                _releaseReply ($in);
            }
  } // register

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:lms_sensor/LMS_Sensor:1.0"};

  @Override
public String[] _ids ()
  {
    return __ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _LMS_SensorStub
