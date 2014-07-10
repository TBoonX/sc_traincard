package htwk.smartcard.traincard.model;

public class Workoutplan extends IModel {

	public static final byte IDENTIFICATOR = 0x04;
	
	private Stage[] warmupstage ;
	private Stage[] trainingstage ;
	private Stage[] cooldownstage;
	private MyDate startdate;
	private MyDate enddate;
	public Workoutplan(Stage[] warmupstage, Stage[] trainingstage, Stage[] cooldownstage, MyDate startdate, MyDate enddate) {
		super();
		// TODO Auto-generated constructor stub
		this.warmupstage = warmupstage;
		this.trainingstage = trainingstage;
		this.cooldownstage = cooldownstage;
		this.startdate = startdate;
		this.enddate = enddate;
	}
	public Stage[] getCooldownstage() {
		return cooldownstage;
	}
	public void setCooldownstage(Stage[] cooldownstage) {
		this.cooldownstage = cooldownstage;
	}
	public MyDate getEnddate() {
		return enddate;
	}
	public void setEnddate(MyDate enddate) {
		this.enddate = enddate;
	}
	public MyDate getStartdate() {
		return startdate;
	}
	public void setStartdate(MyDate startdate) {
		this.startdate = startdate;
	}
	public Stage[] getTrainingstage() {
		return trainingstage;
	}
	public void setTrainingstage(Stage[] trainingstage) {
		this.trainingstage = trainingstage;
	}
	public Stage[] getWarmupstage() {
		return warmupstage;
	}
	public void setWarmupstage(Stage[] warmupstage) {
		this.warmupstage = warmupstage;
	}

	public byte[] toBytes(){
		short overalllength = 	3		//id and length
								+2*6	//two Dates
								+3;		//lengths of 3 stage arrays
		//count stages and their sets
		for (short i = 0; i < warmupstage.length; i++) {
			overalllength += 6*warmupstage[i].getSets().length + 7;	//static stage length and lengths of sets
		}
		for (short i = 0; i < trainingstage.length; i++) {
			overalllength += 6*trainingstage[i].getSets().length + 7;	//static stage length and lengths of sets
		}
		for (short i = 0; i < cooldownstage.length; i++) {
			overalllength += 6*cooldownstage[i].getSets().length + 7;	//static stage length and lengths of sets
		}
		
		byte[] ret = new byte[overalllength];
		
		//ID
		ret[0] = IDENTIFICATOR;
		//length
		ret[1] = (byte)((overalllength-3) >> 8);
		ret[2] = (byte)((overalllength-3) & 0xff);
		//data
		//set bytes from dates
		byte[] datebytes = startdate.toBytes();
		for (short j = 3; j < 9; j++) {
			ret[j] = datebytes[j-3];
		}
		datebytes = enddate.toBytes();
		for (short j = 9; j < 15; j++) {
			ret[j] = datebytes[j-9];
		}
		//set lengths for stages
		ret[15] = (byte)warmupstage.length;
		ret[16] = (byte)trainingstage.length;
		ret[17] = (byte)cooldownstage.length;
		//set data from stages
		short index = 18;
		for (short i = 0; i < warmupstage.length; i++) {
			Stage stage = warmupstage[i];
			byte[] stagebytes = stage.toBytes();
			for (short j = 0; j < stagebytes.length; j++) {
				ret[index+j] = stagebytes[j];
			}
			index += stagebytes.length;
		}
		for (short i = 0; i < trainingstage.length; i++) {
			Stage stage = trainingstage[i];
			byte[] stagebytes = stage.toBytes();
			for (short j = 0; j < stagebytes.length; j++) {
				ret[index+j] = stagebytes[j];
			}
			index += stagebytes.length;
		}
		for (short i = 0; i < cooldownstage.length; i++) {
			Stage stage = cooldownstage[i];
			byte[] stagebytes = stage.toBytes();
			for (short j = 0; j < stagebytes.length; j++) {
				ret[index+j] = stagebytes[j];
			}
			index += stagebytes.length;
		}
		
		return ret;
	}
	public static Workoutplan fromBytes(byte[] bytes) {
		if (IDENTIFICATOR != bytes[0])
			return null;
		
		short datalength = (short)((bytes[1]<<8) | (bytes[2]));
		
		byte[] datebytes = new byte[6];
		datebytes[0] = bytes[3];
		datebytes[1] = bytes[4];
		datebytes[2] = bytes[5];
		datebytes[3] = bytes[6];
		datebytes[4] = bytes[7];
		datebytes[5] = bytes[8];
		MyDate startdate = MyDate.fromBytes(datebytes);
		datebytes[0] = bytes[9];
		datebytes[1] = bytes[10];
		datebytes[2] = bytes[11];
		datebytes[3] = bytes[12];
		datebytes[4] = bytes[13];
		datebytes[5] = bytes[14];
		MyDate enddate = MyDate.fromBytes(datebytes);
		
		short countWarmup = bytes[15];
		short countTraining = bytes[16];
		short countCooldown = bytes[17];
		
		Stage[] warmupStages = new Stage[countWarmup];
		Stage[] trainingStages = new Stage[countTraining];
		Stage[] cooldownStages = new Stage[countCooldown];
		
		
		short index = 18;
		for (short i = 0; i < countWarmup; i++) {
			byte[] stagebytes = getStage(bytes, index);
			short stagelength = (short)((bytes[index+1]<<8) | (bytes[index+2]));
			warmupStages[i] = Stage.fromBytes(stagebytes);
			index += stagelength+3;
		}
		for (short i = 0; i < countTraining; i++) {
			byte[] stagebytes = getStage(bytes, index);
			short stagelength = (short)((bytes[index+1]<<8) | (bytes[index+2]));
			trainingStages[i] = Stage.fromBytes(stagebytes);
			index += stagelength+3;
		}
		for (short i = 0; i < countCooldown; i++) {
			byte[] stagebytes = getStage(bytes, index);
			short stagelength = (short)((bytes[index+1]<<8) | (bytes[index+2]));
			cooldownStages[i] = Stage.fromBytes(stagebytes);
			index += stagelength+3;
		}
		
		return new Workoutplan(warmupStages, trainingStages, cooldownStages, startdate, enddate);
	}
	
	private static byte[] getStage(byte[] workoutplan, short startindex) {
		short length = (short) ((workoutplan[startindex+1]<<8) | (workoutplan[startindex+2]));
		
		byte[] ret = new byte[length+3];
		
		for (short i = 0; i < ret.length; i++) {
			ret[i] = workoutplan[startindex+i];
		}
		
		return ret;
	}
	
}
