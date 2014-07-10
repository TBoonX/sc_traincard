package model;

public class ProgressElement extends IModel {

	public static final byte IDENTIFICATOR = 0x05;
	
	private byte weight;
	private byte replicates;
	private MyDate date;
	
	public ProgressElement(byte weight, byte replicates, MyDate date) {
		super();
		this.weight = weight;
		this.replicates = replicates;
		this.date = date;
	}
	public MyDate getDate() {
		return date;
	}
	public void setDate(MyDate date) {
		this.date = date;
	}
	public byte getWeight() {
		return weight;
	}
	public void setWeight(byte weight) {
		this.weight = weight;
	}
	

	public byte getReplicates() {
		return replicates;
	}
	public void setReplicates(byte replicates) {
		this.replicates = replicates;
	}
	public byte[] toBytes(){
		byte[] ret = new byte[3+2+6];
		
		//ID
		ret[0] = IDENTIFICATOR;
		//length
		ret[1] = 0x00;
		ret[2] = 0x07;
		//data
		ret[3] = weight;
		ret[4] = replicates;
		byte[] datebytes = date.toBytes();
		for (short j = 5; j < 11; j++) {
			ret[j] = datebytes[j-5];
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
		datebytes[0] = bytes[5];
		datebytes[1] = bytes[6];
		datebytes[2] = bytes[7];
		datebytes[3] = bytes[8];
		datebytes[4] = bytes[9];
		datebytes[5] = bytes[10];
		MyDate date = MyDate.fromBytes(datebytes);
		
		return new ProgressElement(bytes[3], bytes[4], date);
	}
	
}
