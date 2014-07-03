package htwk.smartcard.traincard.test;

import static org.junit.Assert.*;
import htwk.smartcard.traincard.model.Date;
import htwk.smartcard.traincard.model.Set;
import htwk.smartcard.traincard.model.Stage;
import htwk.smartcard.traincard.model.Workoutplan;

import org.junit.Before;
import org.junit.Test;

public class TestWorkoutplan {
	
	Workoutplan that;

	@Before
	public void setUp() throws Exception {
		Set[] sets = new Set[2];
		sets[0] = new Set((byte)0x01, (byte)0x1d, (byte)0x0f);
		sets[1] = new Set((byte)0x02, (byte)0x28, (byte)0x08);
		Stage stage1 = new Stage((byte)0x01, (byte)0x0f, sets, (byte)0x33, (byte)0x01);//19 byte
		
		Set[] sets2 = new Set[3];
		sets2[0] = new Set((byte)0x01, (byte)0x1d, (byte)0x0e);
		sets2[1] = new Set((byte)0x02, (byte)0x28, (byte)0x08);
		sets2[2] = new Set((byte)0x03, (byte)0x0a, (byte)0x0f);
		Stage stage2 = new Stage((byte)0x01, (byte)0x10, sets2, (byte)0x2b, (byte)0x01);//25 bytes
		
		Stage[] allStages = new Stage[2];	//44 bytes
		allStages[0] = stage1;
		allStages[1] = stage2;
		Stage[] fs = new Stage[1];
		fs[0] = stage1;
		Stage[] ss = new Stage[1];
		ss[0] = stage2;
		
		Date now = new Date((byte)0x0e, (byte)0x07, (byte)0x03);	//6 bytes
		Date othernow = new Date((byte)0x0e, (byte)0x08, (byte)0x01);//6 bytes
		
		that = new Workoutplan(fs, allStages, ss, now, othernow);	//3+12+3+19+25+44 bytes
	}

	@Test
	public void testToBytes() {
		byte[] bytes = that.toBytes();
		
		assertEquals(Workoutplan.IDENTIFICATOR, bytes[0]);
		
		short length = (short)((bytes[1]<<8) | (bytes[2]));
		
		assertEquals(106-3, length);
		
		byte[] datebytes = new byte[6];
		datebytes[0] = bytes[9];
		datebytes[1] = bytes[10];
		datebytes[2] = bytes[11];
		datebytes[3] = bytes[12];
		datebytes[4] = bytes[13];
		datebytes[5] = bytes[14];
		Date othernow = Date.fromBytes(datebytes);
		
		assertEquals((byte)0x0e, othernow.getYear());
		assertEquals((byte)0x08, othernow.getMonth());
		assertEquals((byte)0x01, othernow.getDay());
		
		assertEquals((byte)0x0f, bytes[bytes.length-1]);
	}

	@Test
	public void testFromBytesByteArray() {
		byte[] bytes = that.toBytes();
		
		Workoutplan wp = Workoutplan.fromBytes(bytes);
		
		//assertEquals(that, wp);
		assertEquals(that.getEnddate().getMonth(), wp.getEnddate().getMonth());
		assertEquals(that.getTrainingstage()[0].getSets()[0].getWeight(), wp.getTrainingstage()[0].getSets()[0].getWeight());
	}

}
