package htwk.smartcard.traincard.test;

import static org.junit.Assert.*;
import htwk.smartcard.traincard.model.MyDate;
import htwk.smartcard.traincard.model.Progress;
import htwk.smartcard.traincard.model.ProgressElement;

import org.junit.Before;
import org.junit.Test;

public class TestProgress {
	Progress that;
	Progress that2;

	@Before
	public void setUp() throws Exception {
		MyDate date = new MyDate((byte)0x0b, (byte)0x07, (byte)0x05);
		ProgressElement last = new ProgressElement((byte)0x24,(byte)0x0a, date);
		ProgressElement best = new ProgressElement((byte)0x26,(byte)0x0a, date);
		ProgressElement worst = new ProgressElement((byte)0x2f,(byte)0x08, date);
		
		that = new Progress((byte)0x01, last, best, worst);
		
		that2 = new Progress((byte)0x01, null, null, null);
	}

	@Test
	public void testFromBytesByteArray() {
		byte[] bytes = that.toBytes();
		
		Progress newthat = Progress.fromBytes(bytes);
		
		assertEquals(that.getStageID(), newthat.getStageID());
		
		
		byte[] bytes2 = that2.toBytes();
		
		Progress newthat2 = Progress.fromBytes(bytes2);
		
		assertEquals(that2.getStageID(), newthat2.getStageID());
		assertEquals(that2.getLast(), newthat2.getLast());
		
	}

}
