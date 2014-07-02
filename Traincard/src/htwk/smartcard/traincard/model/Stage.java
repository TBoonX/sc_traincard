package htwk.smartcard.traincard.model;

import static org.junit.Assert.assertTrue;

public class Stage extends IModel {

	public static final byte IDENTIFICATOR = 0x03;
	
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
	public byte[] toBytes(){
		byte[] ret = new byte[3+4+sets.length*6];
		
		//ID
		ret[0] = IDENTIFICATOR;
		//length
		ret[1] = (byte)((4+sets.length*6) >> 8);
		ret[2] = (byte)((4+sets.length*6) & 0xff);
		//data
		ret[3] = day;
		ret[4] = deviceID;
		ret[5] = musclegroupID;
		ret[6] = stageID;
		//set bytes from all stages
		short index = 7;
		for (short i = 0; i < sets.length; i++) {
			byte[] setbytes = sets[i].toBytes();
			
			for (short j = 0; j < 6; j++) {
				ret[index+j] = setbytes[j];
			}
			index += 6;
		}
		
		return ret;
	}
	public static Stage fromBytes(byte[] bytes) {
		if (IDENTIFICATOR != bytes[0])
			return null;
		
		short datalength = (short)((bytes[1]<<8) | (bytes[2]));
		
		if ((datalength-4)%6 > 0)
			return null;
		
		short countSets = (short) ( (datalength-4)/6 );
		Set[] sets = new Set[countSets];
		short index = 7;
		for (short i = 0; i < countSets; i++) {
			byte[] setbytes = new byte[6];
			for (short j = 0; j < 6; j++) {
				setbytes[j] = bytes[index+j];
			}
			index += 6;
			sets[i] = Set.fromBytes(setbytes);
			
		}
		
		return new Stage(bytes[3], bytes[4], sets, bytes[5], bytes[6]);
	}
}
