package htwk.smartcard.traincard.model;

public class Set extends IModel {

	private byte number;
	private short weight;
	private byte replicates;
	
	
	
	public Set(byte number, short weight, byte replicates) {
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
	public short getWeight() {
		return weight;
	}
	public void setWeight(short weight) {
		this.weight = weight;
	}
}
