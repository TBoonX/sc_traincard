package htwk.smartcard.traincard.test;

import static org.junit.Assert.*;
import htwk.smartcard.traincard.model.Set;

import org.junit.Before;
import org.junit.Test;

public class TestSet {
	
	Set that;

	@Before
	public void setUp() throws Exception {
		that = new Set((byte)0x01, (byte)0x1d, (byte)0x0f);
	}

	@Test
	public void testToBytes() {
		byte[] dateAsBytes = that.toBytes();
		
		assertSame(dateAsBytes[0], Set.IDENTIFICATOR);
		
		short length = (short)((dateAsBytes[1]<<8) | (dateAsBytes[2]));
		
		assertSame(length, (short)3);
	}

	@Test
	public void testFromBytesByteArray() {
		byte[] thatbytes = that.toBytes();
		
		Set nweone = Set.fromBytes(thatbytes);
		
		assertEquals(nweone.getNumber(), that.getNumber());
		assertEquals(nweone.getWeight(), that.getWeight());
		assertEquals(nweone.getReplicates(), that.getReplicates());
	}

}
