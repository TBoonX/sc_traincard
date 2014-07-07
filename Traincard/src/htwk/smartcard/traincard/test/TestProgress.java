package htwk.smartcard.traincard.test;

import static org.junit.Assert.*;
import htwk.smartcard.traincard.model.Date;
import htwk.smartcard.traincard.model.Progress;
import htwk.smartcard.traincard.model.ProgressElement;

import org.junit.Before;
import org.junit.Test;

public class TestProgress {
	Progress that;

	@Before
	public void setUp() throws Exception {
		Date date = new Date((byte)0x0b, (byte)0x07, (byte)0x05);
		ProgressElement last = new ProgressElement((byte)0x24, date);
		ProgressElement best = new ProgressElement((byte)0x26, date);
		ProgressElement worst = new ProgressElement((byte)0x2f, date);
		
		that = new Progress((byte)0x01, last, best, worst);
	}

	@Test
	public void testFromBytesByteArray() {
		byte[] bytes = that.toBytes();
		
		Progress newthat = Progress.fromBytes(bytes);
		
		assertEquals(that.getStageID(), newthat.getStageID());
	}

}
