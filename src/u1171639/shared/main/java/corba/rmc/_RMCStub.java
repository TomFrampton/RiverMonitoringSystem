package u1171639.shared.main.java.corba.rmc;


/**
* u1171639/shared/main/java/corba/rmc/_RMCStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from RMC.idl
* Friday, 10 April 2015 14:13:12 o'clock BST
*/

public class _RMCStub extends org.omg.CORBA.portable.ObjectImpl implements u1171639.shared.main.java.corba.rmc.RMC
{

  public void raiseAlarm (String locality, String zone)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("raiseAlarm", true);
                $out.write_string (locality);
                $out.write_string (zone);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                raiseAlarm (locality, zone        );
            } finally {
                _releaseReply ($in);
            }
  } // raiseAlarm

  public void register (String ior, String locality)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("register", true);
                $out.write_string (ior);
                $out.write_string (locality);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                register (ior, locality        );
            } finally {
                _releaseReply ($in);
            }
  } // register

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:rmc/RMC:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
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
} // class _RMCStub
