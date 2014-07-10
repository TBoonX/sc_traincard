package htwk.smartcard.traincard.test;

import junit.framework.TestCase;
import htwk.smartcard.traincard.model.MyDate;

public class TestDate extends TestCase {
	
	MyDate that;

	protected void setUp() throws Exception {
		super.setUp();
		
		that = new MyDate((byte)0x0e, (byte)0x06, (byte)0x02);
	}

	/*
	 * Test method for 'htwk.smartcard.traincard.model.Date.toBytes()'
	 */
	public void testToBytes() {
		byte[] dateAsBytes = that.toBytes();
		
		assertSame(dateAsBytes[0], MyDate.IDENTIFICATOR);
		
		short length = (short)((dateAsBytes[1]<<8) | (dateAsBytes[2]));
		
		assertSame(length, (short)3);
	}

	/*
	 * Test method for 'htwk.smartcard.traincard.model.Date.fromBytes(byte[])'
	 */
	public void testFromBytes() {
		byte[] thatbytes = that.toBytes();
		
		MyDate nweone = MyDate.fromBytes(thatbytes);
		
		assertEquals(nweone.getYear(), that.getYear());
		assertEquals(nweone.getMonth(), that.getMonth());
		assertEquals(nweone.getDay(), that.getDay());
	}

}
