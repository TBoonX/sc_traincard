package htwk.smartcard.traincard.model;

public class Progress extends IModel {

	private char stageID;
	private ProgressElement last;
	private ProgressElement best;
	private ProgressElement worst;
	public Progress(char stageID, ProgressElement last, ProgressElement best, ProgressElement worst) {
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
	public char getStageID() {
		return stageID;
	}
	public void setStageID(char stageID) {
		this.stageID = stageID;
	}
	public ProgressElement getWorst() {
		return worst;
	}
	public void setWorst(ProgressElement worst) {
		this.worst = worst;
	}
	
	
}
