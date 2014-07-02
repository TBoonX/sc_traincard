package htwk.smartcard.traincard.model;


public class Date extends IModel {

	public static final byte IDENTIFICATOR = 0x01;
	
	private short year;
	private byte month;
	private byte day;
	public Date(short year, byte month, byte day) {
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
	public short getYear() {
		return year;
	}
	public void setYear(short year) {
		this.year = year;
	}
	public byte[] toBytes(){
		byte[] ret = new byte[7];
		
		//ID
		ret[0] = IDENTIFICATOR;
		//length
		ret[1] = 0x00;
		ret[2] = 0x04;
		//data
		ret[3] = (byte)(year & 0xff);;
		ret[4] = (byte)((year >> 8) & 0xff);
		ret[5] = month;
		ret[6] = day;
		
		return ret;
	}
	public IModel fromBytes(byte[] bytes) {
		return null;
	}
}
