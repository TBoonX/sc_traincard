package model;

public class ProgressElement extends IModel {

	public static final byte IDENTIFICATOR = 0x05;
	
	private byte weight;
	private Date date;
	public ProgressElement(byte weight, Date date) {
		super();
		// TODO Auto-generated constructor stub
		this.weight = weight;
		this.date = date;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public byte getWeight() {
		return weight;
	}
	public void setWeight(byte weight) {
		this.weight = weight;
	}

	public byte[] toBytes(){
		byte[] ret = new byte[6];
		
		//ID
		ret[0] = IDENTIFICATOR;
		//length
		ret[1] = 0x00;
		ret[2] = 0x07;
		//data
		ret[3] = weight;
		byte[] datebytes = date.toBytes();
		for (short j = 4; j < 10; j++) {
			ret[j] = datebytes[j-3];
		}
		
		return ret;
	}
	public static ProgressElement fromBytes(byte[] bytes) {
		if (IDENTIFICATOR != bytes[0])
			return null;
		
		short length = (short)((bytes[1]<<8) | (bytes[2]));
		
		if (length != 7)
			return null;
		
		byte[] datebytes = new byte[6];
		datebytes[0] = bytes[4];
		datebytes[1] = bytes[5];
		datebytes[2] = bytes[6];
		datebytes[3] = bytes[7];
		datebytes[4] = bytes[8];
		datebytes[5] = bytes[9];
		Date date = Date.fromBytes(datebytes);
		
		return new ProgressElement(bytes[3], date);
	}
	
}
