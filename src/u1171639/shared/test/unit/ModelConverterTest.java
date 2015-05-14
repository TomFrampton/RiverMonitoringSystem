package u1171639.shared.test.unit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import u1171639.lms.main.java.client.Sensor;
import u1171639.lms.main.java.model.LMSZone;
import u1171639.lms.test.mocks.MockSensor;
import u1171639.rmc.main.java.model.Locality;
import u1171639.rmc.main.java.model.RMCSensor;
import u1171639.rmc.main.java.model.RMCZone;
import u1171639.shared.main.java.corba.models.CorbaModel_Locality;
import u1171639.shared.main.java.corba.models.CorbaModel_Log;
import u1171639.shared.main.java.corba.models.CorbaModel_LogItem;
import u1171639.shared.main.java.corba.models.CorbaModel_Sensor;
import u1171639.shared.main.java.corba.models.CorbaModel_Zone;
import u1171639.shared.main.java.logging.LogItem;
import u1171639.shared.main.java.utils.ModelConverter;

public class ModelConverterTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCorbaLocalityToGenericLocality() {
		CorbaModel_Locality corbaLocality = new CorbaModel_Locality();
		corbaLocality.name = "Locality1";
		corbaLocality.zones = new CorbaModel_Zone[0];
		
		Locality genericLocality = ModelConverter.convertLocality(corbaLocality);
		
		assertEquals(genericLocality.getName(), "Locality1");
		assertTrue(genericLocality.getZones().size() == 0);
		
		CorbaModel_Zone zone1 = new CorbaModel_Zone();
		zone1.name = "Zone1";
		zone1.alarmRaised = true;
		zone1.sensors = new CorbaModel_Sensor[0];
		corbaLocality.zones = new CorbaModel_Zone[1];
		corbaLocality.zones[0] = zone1;
		
		genericLocality = ModelConverter.convertLocality(corbaLocality);
		
		assertEquals(genericLocality.getName(), "Locality1");
		assertTrue(genericLocality.getZones().size() == 1);
		assertEquals(genericLocality.getZones().get(0).getName(), "Zone1");
		assertTrue(genericLocality.getZones().get(0).getSensors().size() == 0);
	}
	
	@Test
	public void testGenericLocalityToCorbaLocality() {
		List<LMSZone> genericZones = new ArrayList<LMSZone>();
		
		CorbaModel_Locality corbaLocality = ModelConverter.convertLocality("Locality1", genericZones);
		
		assertEquals(corbaLocality.name, "Locality1");
		assertTrue(corbaLocality.zones.length == 0);
		
		LMSZone genericZone1 = new LMSZone();
		genericZone1.setName("Zone1");
		genericZone1.setSensors(new ArrayList<Sensor>());
		genericZones.add(genericZone1);
		
		corbaLocality = ModelConverter.convertLocality("Locality1", genericZones);
		
		assertEquals(corbaLocality.name, "Locality1");
		assertTrue(corbaLocality.zones.length == 1);
		assertEquals(corbaLocality.zones[0].name, "Zone1");
		assertTrue(corbaLocality.zones[0].sensors.length == 0);
		
		LMSZone genericZone2 = new LMSZone();
		genericZone2.setName("Zone2");
		
		List<Sensor> sensors = new ArrayList<Sensor>();
		MockSensor sensor = new MockSensor();
		sensor.setName("Sensor1");
		sensor.setAlarmRaised(true);
		sensors.add(sensor);
		genericZone2.setSensors(sensors);
		genericZones.add(genericZone2);
		
		corbaLocality = ModelConverter.convertLocality("Locality1", genericZones);
		
		assertEquals(corbaLocality.name, "Locality1");
		assertTrue(corbaLocality.zones.length == 2);
		
		assertEquals(corbaLocality.zones[0].name, "Zone1");
		assertTrue(corbaLocality.zones[0].sensors.length == 0);
		
		assertEquals(corbaLocality.zones[1].name, "Zone2");
		assertTrue(corbaLocality.zones[1].sensors.length == 1);
		assertEquals(corbaLocality.zones[1].sensors[0].name, "Sensor1");
	}

	@Test
	public void testCorbaZoneToRMCZone() {
		CorbaModel_Zone corbaZone = new CorbaModel_Zone();
		corbaZone.name = "Zone1";
		corbaZone.alarmRaised = true;
		corbaZone.sensors = new CorbaModel_Sensor[0];
		
		RMCZone rmcZone = ModelConverter.convertRMCZone("Locality1", corbaZone);
		
		assertEquals(rmcZone.getName(), "Zone1");
		assertEquals(rmcZone.getLocalityName(), "Locality1");
		assertTrue(rmcZone.isAlarmRaised());
		assertTrue(rmcZone.getSensors().isEmpty());
		
		corbaZone.sensors = new CorbaModel_Sensor[1];
		CorbaModel_Sensor corbaSensor1 = new CorbaModel_Sensor();
		corbaSensor1.name = "ID0001";
		corbaZone.sensors[0] = corbaSensor1;
		
		rmcZone = ModelConverter.convertRMCZone("Locality2", corbaZone);
		
		assertEquals(rmcZone.getName(), "Zone1");
		assertEquals(rmcZone.getLocalityName(), "Locality2");
		assertTrue(rmcZone.isAlarmRaised());
		assertTrue(rmcZone.getSensors().size() == 1);
		assertEquals(rmcZone.getSensors().get(0).getName(), "ID0001");		
	}
	
	@Test
	public void testLMSZoneToCorbaZone() {
		LMSZone lmsZone = new LMSZone();
		lmsZone.setName("TestZone");
		List<Sensor> sensors = new ArrayList<Sensor>();
		lmsZone.setSensors(sensors);
		
		CorbaModel_Zone corbaZone = ModelConverter.convertLMSZone(lmsZone);
		assertEquals(corbaZone.name, "TestZone");
		assertTrue(corbaZone.sensors.length == 0);
		
		MockSensor lmsSensor1 = new MockSensor();
		lmsSensor1.setName("ID0001");
		lmsSensor1.setAlarmRaised(false);
		sensors.add(lmsSensor1);
		
		corbaZone = ModelConverter.convertLMSZone(lmsZone);
		
		assertEquals(corbaZone.name, "TestZone");
		assertTrue(corbaZone.sensors.length == 1);
		assertEquals(corbaZone.sensors[0].name, "ID0001");
		
		MockSensor lmsSensor2 = new MockSensor();
		lmsSensor2.setName("ID0002");
		lmsSensor2.setAlarmRaised(true);
		sensors.add(lmsSensor2);
		
		corbaZone = ModelConverter.convertLMSZone(lmsZone);
		
		assertEquals(corbaZone.name, "TestZone");
		assertTrue(corbaZone.sensors.length == 2);
		assertEquals(corbaZone.sensors[0].name, "ID0001");
		assertEquals(corbaZone.sensors[1].name, "ID0002");
	}
	
	@Test
	public void testLMSSensorToCorbaSensor() {
		MockSensor lmsSensor = new MockSensor();
		lmsSensor.setName("Sensor1");
		lmsSensor.setThreshold(70);
		lmsSensor.setActive(true);
		
		CorbaModel_Sensor corbaSensor = ModelConverter.convertLMSSensor(lmsSensor);
		
		assertEquals(corbaSensor.name, "Sensor1");
		assertTrue(corbaSensor.threshold == 70);
		assertTrue(corbaSensor.active);
	}
	
	@Test
	public void testCorbaSensorToRMCSensor() {
		CorbaModel_Sensor corbaSensor = new CorbaModel_Sensor();
		corbaSensor.name = "CorbaSensor1";
		corbaSensor.threshold = 75.6;
		corbaSensor.active = false;
		
		RMCSensor rmcSensor = ModelConverter.convertRMCSensor(corbaSensor);
		
		assertEquals(rmcSensor.getName(), "CorbaSensor1");
		assertTrue(rmcSensor.getThreshold() == 75.6);
		assertFalse(rmcSensor.isActive());
	}
	
	@Test
	public void testCorbaLogToGenericLog() {
		CorbaModel_Log corbaLog = new CorbaModel_Log();
		corbaLog.logItems = new CorbaModel_LogItem[0];
		List<LogItem> genericLog = ModelConverter.convertLog(corbaLog);
		assertTrue(genericLog.size() == 0);
		
		corbaLog.logItems = new CorbaModel_LogItem[1];
		CorbaModel_LogItem corbaLogItem1 = new CorbaModel_LogItem();
		corbaLogItem1.event = "ALARM_RAISED";
		corbaLogItem1.message = "This is the first item in the log.";
		corbaLog.logItems[0] = corbaLogItem1;
		
		genericLog = ModelConverter.convertLog(corbaLog);
		
		assertTrue(genericLog.size() == 1);
		assertTrue(genericLog.get(0).getEvent() == LogItem.Event.ALARM_RAISED);
		assertEquals(genericLog.get(0).getMessage(), "This is the first item in the log.");
		
		corbaLog.logItems = new CorbaModel_LogItem[2];
		corbaLog.logItems[0] = corbaLogItem1;
		
		CorbaModel_LogItem corbaLogItem2 = new CorbaModel_LogItem();
		corbaLogItem2.event = "CONNECTION";
		corbaLogItem2.message = "This is the second item in the log.";
		corbaLog.logItems[1] = corbaLogItem2;
		
		genericLog = ModelConverter.convertLog(corbaLog);
		
		assertTrue(genericLog.size() == 2);
		assertTrue(genericLog.get(0).getEvent() == LogItem.Event.ALARM_RAISED);
		assertEquals(genericLog.get(0).getMessage(), "This is the first item in the log.");
		assertTrue(genericLog.get(1).getEvent() == LogItem.Event.CONNECTION);
		assertEquals(genericLog.get(1).getMessage(), "This is the second item in the log.");
	}
	
	@Test
	public void testGenericLogToCorbaLog() {
		List<LogItem> genericLog = new ArrayList<LogItem>();
		CorbaModel_Log corbaLog = ModelConverter.convertLog(genericLog);
		assertTrue(corbaLog.logItems.length == 0);
		
		LogItem genericLogItem1 = new LogItem();
		Date date1 = new Date();
		genericLogItem1.setTime(date1);
		genericLogItem1.setEvent(LogItem.Event.ALARM_RAISED);
		genericLogItem1.setMessage("This is the first log item.");
		genericLog.add(genericLogItem1);
		
		corbaLog = ModelConverter.convertLog(genericLog);
		
		assertTrue(corbaLog.logItems.length == 1);
		assertEquals(corbaLog.logItems[0].timestamp, date1.getTime());
		assertEquals(corbaLog.logItems[0].event, "ALARM_RAISED");
		assertEquals(corbaLog.logItems[0].message, "This is the first log item.");
		
		LogItem genericLogItem2 = new LogItem();
		Date date2 = new Date();
		genericLogItem2.setTime(date1);
		genericLogItem2.setEvent(LogItem.Event.CONNECTION);
		genericLogItem2.setMessage("This is the second log item.");
		genericLog.add(genericLogItem2);
		
		corbaLog = ModelConverter.convertLog(genericLog);
		
		assertTrue(corbaLog.logItems.length == 2);
		assertEquals(corbaLog.logItems[0].timestamp, date1.getTime());
		assertEquals(corbaLog.logItems[0].event, "ALARM_RAISED");
		assertEquals(corbaLog.logItems[0].message, "This is the first log item.");
		assertEquals(corbaLog.logItems[1].timestamp, date2.getTime());
		assertEquals(corbaLog.logItems[1].event, "CONNECTION");
		assertEquals(corbaLog.logItems[1].message, "This is the second log item.");
	}
	
	@Test
	public void testCorbaLogItemToGenericLogItem() {
		CorbaModel_LogItem corbaLogItem = new CorbaModel_LogItem();
		Date date = new Date();
		corbaLogItem.timestamp = date.getTime();
		corbaLogItem.event = "ALARM_RAISED";
		corbaLogItem.message = "Sample message.";
		
		LogItem genericLogItem = ModelConverter.convertLogItem(corbaLogItem);
		
		assertEquals(genericLogItem.getTime(), date);
		assertTrue(genericLogItem.getEvent() == LogItem.Event.ALARM_RAISED);
		assertEquals(genericLogItem.getMessage(), "Sample message.");
	}
	
	@Test
	public void testGenericLogItemToCorbaLogItem() {
		LogItem genericLogItem = new LogItem();
		Date date = new Date();
		genericLogItem.setTime(date);
		genericLogItem.setEvent(LogItem.Event.ALARM_RAISED);
		genericLogItem.setMessage("Sample message.");
		
		CorbaModel_LogItem corbaLogItem = ModelConverter.convertLogItem(genericLogItem);
		
		assertEquals(corbaLogItem.timestamp, date.getTime());
		assertEquals(corbaLogItem.event, "ALARM_RAISED");
		assertEquals(corbaLogItem.message, "Sample message.");		
	}

}
