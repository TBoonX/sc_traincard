package model;

public class Progress extends IModel {

	public static final byte IDENTIFICATOR = 0x06;
	
	private byte stageID;
	private ProgressElement last;
	private ProgressElement best;
	private ProgressElement worst;
	public Progress(byte stageID, ProgressElement last, ProgressElement best, ProgressElement worst) {
		super();
		// TODO Auto-generated constructor stub
		this.stageID = stageID;
		this.last = last;
		this.best = best;
		this.worst = worst;
	}
	public ProgressElement getBest() {
		return best;
	}
	public void setBest(ProgressElement best) {
		this.best = best;
	}
	public ProgressElement getLast() {
		return last;
	}
	public void setLast(ProgressElement last) {
		this.last = last;
	}
	public byte getStageID() {
		return stageID;
	}
	public void setStageID(byte stageID) {
		this.stageID = stageID;
	}
	public ProgressElement getWorst() {
		return worst;
	}
	public void setWorst(ProgressElement worst) {
		this.worst = worst;
	}
	
	
}
