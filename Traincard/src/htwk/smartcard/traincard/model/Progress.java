package htwk.smartcard.traincard.model;

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

	public byte[] toBytes(){
		byte[] ret = new byte[37];
		if (last == null)
			ret = new byte[4];
		
		//ID
		ret[0] = IDENTIFICATOR;
		//length
		ret[1] = 0x00;
		ret[2] = (byte)((last == null) ? 0x01 : 0x22);
		//data
		ret[3] = stageID;
		
		if (last == null)
			return ret;
		
		byte[] pebytes = last.toBytes();
		for (short j = 4; j < 15; j++) {
			ret[j] = pebytes[j-4];
		}
		pebytes = best.toBytes();
		for (short j = 15; j < 26; j++) {
			ret[j] = pebytes[j-15];
		}
		pebytes = worst.toBytes();
		for (short j = 26; j < 37; j++) {
			ret[j] = pebytes[j-26];
		}
		
		return ret;
	}
	public static Progress fromBytes(byte[] bytes) {
		if (IDENTIFICATOR != bytes[0])
			return null;
		
		short length = (short)((bytes[1]<<8) | (bytes[2]));
		
		if (length == 1) {
			return new Progress(bytes[3], null, null, null);
		}
		
		if (length != 34)
			return null;
		
		byte[] pebytes = new byte[11];
		for (short j = 4; j < 15; j++) {
			pebytes[j-4] = bytes[j];
		}
		ProgressElement last = ProgressElement.fromBytes(pebytes);
		for (short j = 15; j < 26; j++) {
			pebytes[j-15] = bytes[j];
		}
		ProgressElement best = ProgressElement.fromBytes(pebytes);
		for (short j = 26; j < 37; j++) {
			pebytes[j-26] = bytes[j];
		}
		ProgressElement worst = ProgressElement.fromBytes(pebytes);
		
		return new Progress(bytes[3], last, best, worst);
	}
}
