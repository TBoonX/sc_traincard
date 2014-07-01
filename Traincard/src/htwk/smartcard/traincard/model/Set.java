package htwk.smartcard.traincard.model;

public class Set extends IModel {

	private char number;
	private short weight;
	private char replicates;
	
	
	
	public Set(char number, short weight, char replicates) {
		super();
		// TODO Auto-generated constructor stub
		this.number = number;
		this.weight = weight;
		this.replicates = replicates;
	}
	public char getNumber() {
		return number;
	}
	public void setNumber(char number) {
		this.number = number;
	}
	public char getReplicates() {
		return replicates;
	}
	public void setReplicates(char replicates) {
		this.replicates = replicates;
	}
	public short getWeight() {
		return weight;
	}
	public void setWeight(short weight) {
		this.weight = weight;
	}
	
	
}
