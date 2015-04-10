rm -r C:\Workspaces\Eclipse\RiverMonitoringSystem\src\u1171639\shared\main\java\corba

idlj -fall -td ..\..\..\..\ -pkgPrefix lms_sensor u1171639.shared.main.java.corba LMS_Sensor.idl
idlj -fall -td ..\..\..\..\ -pkgPrefix lms_rmc u1171639.shared.main.java.corba LMS_RMC.idl
idlj -fall -td ..\..\..\..\ -pkgPrefix sensor u1171639.shared.main.java.corba Sensor.idl
idlj -fall -td ..\..\..\..\ -pkgPrefix rmc u1171639.shared.main.java.corba RMC.idl

PAUSE