package htwk.smartcard.traincard.model;

public class Stage extends IModel {

	private byte day;
	private char deviceID;
	private Set[] sets;
	private char musclegroupID;
	private char stageID;
	public Stage(byte day, char deviceID, Set[] sets, char musclegroupID, char stageID) {
		super();
		// TODO Auto-generated constructor stub
		this.day = day;
		this.deviceID = deviceID;
		this.sets = sets;
		this.musclegroupID = musclegroupID;
		this.stageID = stageID;
	}
	public byte getDay() {
		return day;
	}
	public void setDay(byte day) {
		this.day = day;
	}
	public char getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(char deviceID) {
		this.deviceID = deviceID;
	}
	public char getMusclegroupID() {
		return musclegroupID;
	}
	public void setMusclegroupID(char musclegroupID) {
		this.musclegroupID = musclegroupID;
	}
	public Set[] getSets() {
		return sets;
	}
	public void setSets(Set[] sets) {
		this.sets = sets;
	}
	public char getStageID() {
		return stageID;
	}
	public void setStageID(char stageID) {
		this.stageID = stageID;
	}
	
	
}
