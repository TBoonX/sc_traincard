package htwk.smartcard.traincard.test;

import static org.junit.Assert.*;
import htwk.smartcard.traincard.model.Date;
import htwk.smartcard.traincard.model.ProgressElement;

import org.junit.Before;
import org.junit.Test;

public class TestProgressElement {
	
	ProgressElement that;

	@Before
	public void setUp() throws Exception {
		Date date = new Date((byte)0x0b, (byte)0x07, (byte)0x05);
		that = new ProgressElement((byte)0x24,(byte)0x0a, date);
	}

	@Test
	public void testFromBytesByteArray() {
		byte[] bytes = that.toBytes();
		
		ProgressElement newthat = ProgressElement.fromBytes(bytes);
		
		assertEquals(that.getReplicates(), newthat.getReplicates());
	}

}
