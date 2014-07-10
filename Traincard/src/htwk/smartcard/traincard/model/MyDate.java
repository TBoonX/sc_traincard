package htwk.smartcard.traincard.model;


public class MyDate extends IModel {

	public static final byte IDENTIFICATOR = 0x01;
	
	private byte year;
	private byte month;
	private byte day;
	public MyDate(byte year, byte month, byte day) {
		super();
		// TODO Auto-generated constructor stub
		this.year = year;
		this.month = month;
		this.day = day;
	}
	public byte getDay() {
		return day;
	}
	public void setDay(byte day) {
		this.day = day;
	}
	public byte getMonth() {
		return month;
	}
	public void setMonth(byte month) {
		this.month = month;
	}
	public byte getYear() {
		return year;
	}
	public void setYear(byte year) {
		this.year = year;
	}
	public byte[] toBytes(){
		byte[] ret = new byte[6];
		
		//ID
		ret[0] = IDENTIFICATOR;
		//length
		ret[1] = 0x00;
		ret[2] = 0x03;
		//data
		ret[3] = year;
		ret[4] = month;
		ret[5] = day;
		
		return ret;
	}
	public static MyDate fromBytes(byte[] bytes) {
		if (IDENTIFICATOR != bytes[0])
			return null;
		
		short length = (short)((bytes[1]<<8) | (bytes[2]));
		
		if (length != 3)
			return null;
		
		return new MyDate(bytes[3], bytes[4], bytes[5]);
	}
}
