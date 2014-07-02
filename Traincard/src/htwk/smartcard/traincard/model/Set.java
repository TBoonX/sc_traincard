package htwk.smartcard.traincard.model;

public class Set extends IModel {

	public static final byte IDENTIFICATOR = 0x02;
	
	private byte number;
	private byte weight;
	private byte replicates;
	
	
	
	public Set(byte number, byte weight, byte replicates) {
		super();
		// TODO Auto-generated constructor stub
		this.number = number;
		this.weight = weight;
		this.replicates = replicates;
	}
	public byte getNumber() {
		return number;
	}
	public void setNumber(byte number) {
		this.number = number;
	}
	public byte getReplicates() {
		return replicates;
	}
	public void setReplicates(byte replicates) {
		this.replicates = replicates;
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
		ret[2] = 0x03;
		//data
		ret[3] = number;
		ret[4] = weight;
		ret[5] = replicates;
		
		return ret;
	}
	public static Set fromBytes(byte[] bytes) {
		if (IDENTIFICATOR != bytes[0])
			return null;
		
		short length = (short)((bytes[1]<<8) | (bytes[2]));
		
		if (length != 3)
			return null;
		
		return new Set(bytes[3], bytes[4], bytes[5]);
	}
}
