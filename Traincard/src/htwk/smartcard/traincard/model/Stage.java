package htwk.smartcard.traincard.model;

public class Stage extends IModel {

	private byte day;
	private byte deviceID;
	private Set[] sets;
	private byte musclegroupID;
	private byte stageID;
	public Stage(byte day, byte deviceID, Set[] sets, byte musclegroupID, byte stageID) {
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
	public byte getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(byte deviceID) {
		this.deviceID = deviceID;
	}
	public byte getMusclegroupID() {
		return musclegroupID;
	}
	public void setMusclegroupID(byte musclegroupID) {
		this.musclegroupID = musclegroupID;
	}
	public Set[] getSets() {
		return sets;
	}
	public void setSets(Set[] sets) {
		this.sets = sets;
	}
	public byte getStageID() {
		return stageID;
	}
	public void setStageID(byte stageID) {
		this.stageID = stageID;
	}
}
