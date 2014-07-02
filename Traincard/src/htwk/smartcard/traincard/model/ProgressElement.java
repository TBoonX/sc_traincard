package htwk.smartcard.traincard.model;

public class ProgressElement extends IModel {

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
	
	
}
