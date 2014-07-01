package htwk.smartcard.traincard.model;

public class ProgressElement extends IModel {

	private short weight;
	private Date date;
	public ProgressElement(short weight, Date date) {
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
	public short getWeight() {
		return weight;
	}
	public void setWeight(short weight) {
		this.weight = weight;
	}
	
	
}
