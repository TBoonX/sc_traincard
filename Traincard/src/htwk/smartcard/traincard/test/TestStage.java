package htwk.smartcard.traincard.test;

import static org.junit.Assert.*;
import htwk.smartcard.traincard.model.Set;
import htwk.smartcard.traincard.model.Stage;

import org.junit.Before;
import org.junit.Test;

public class TestStage {

	Stage that;
	Stage that2;
	
	@Before
	public void setUp() throws Exception {
		Set[] sets = new Set[2];
		sets[0] = new Set((byte)0x01, (byte)0x1d, (byte)0x0f);
		sets[1] = new Set((byte)0x02, (byte)0x28, (byte)0x08);
		that = new Stage((byte)0x01, (byte)0x0f, sets, (byte)0x33, (byte)0x01);
		
		Set[] sets2 = new Set[3];
		sets2[0] = new Set((byte)0x01, (byte)0x1d, (byte)0x0f);
		sets2[1] = new Set((byte)0x02, (byte)0x28, (byte)0x08);
		sets2[2] = new Set((byte)0x03, (byte)0x0a, (byte)0x0f);
		that2 = new Stage((byte)0x01, (byte)0x10, sets2, (byte)0x2b, (byte)0x01);
	}

	@Test
	public void testToBytes() {
		byte[] bytes = that.toBytes();
		
		short length = (short)((bytes[1]<<8) | (bytes[2]));
		
		assertEquals((short)16, length);
		
		byte[] bytes2 = that2.toBytes();
		
		short datalength2 = (short)((bytes2[1]<<8) | (bytes2[2]));
		
		assertEquals((short)22, datalength2);
		
		///////////////
		assertEquals(4, datalength2%6);
		short countSets = (short) ( (datalength2-4)/6 );
		short index = 7;
		for (short i = 0; i < countSets; i++) {
			byte[] setbytes = new byte[6];
			for (short j = 0; j < 6; j++) {
				setbytes[j] = bytes2[index+j];
			}
			index += 6;
			Set set = Set.fromBytes(setbytes);
			
			assertTrue(set.getNumber() > 0);
			assertTrue(set.getReplicates() > 0);
			assertTrue(set.getWeight() > 0);
			
		}
	}

	@Test
	public void testFromBytesByteArray() {
		byte[] bytes = that.toBytes();
		Stage newThat = Stage.fromBytes(bytes);
		
		assertEquals(that.getDay(), newThat.getDay());
		assertEquals(that.getDeviceID(), newThat.getDeviceID());
		assertEquals(that.getMusclegroupID(), newThat.getMusclegroupID());
		assertEquals(that.getStageID(), newThat.getStageID());
		
		Set[] sets = newThat.getSets();
		
		assertEquals(that.getSets().length, sets.length);
	}

}
