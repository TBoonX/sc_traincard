package htwk.smartcard.traincard;

import javacard.framework.APDU;
import javacard.framework.Applet;
import javacard.framework.ISO7816;
import javacard.framework.ISOException;

public class Traincard extends Applet {
	
	//instructions
	final byte SAVEWORKOUTPLAN = (byte)0x01;
	final byte GETWORKOUTPLAN = (byte)0x02;
	final byte SAVEWEIGHT = (byte)0x03;
	final byte GETWEIGHTS = (byte)0x04;
	final byte SAVECHANGES = (byte)0x05;
	final byte GETCHANGES = (byte)0x06;
	
	short ret_length = 0;
	
	//data
	byte[] password_trainer;
	byte[] password_sportsman;
	final byte TRAINER = (byte)0x01;
	final byte SPORTSMAN = (byte)0x01;
	byte[] workoutplans;
	byte[] weigth_history;
	byte[] changed_exercies;
	

	public static void install(byte[] bArray, short bOffset, byte bLength) {
		// GP-compliant JavaCard applet registration
		new Traincard()
				.register(bArray, (short) (bOffset + 1), bArray[bOffset]);
	}

	public void process(APDU apdu) {
		// Good practice: Return 9000 on SELECT
		if (selectingApplet()) {
			return;
		}
		
		byte[] buf = apdu.getBuffer();
	    byte instruction = buf[ISO7816.OFFSET_INS];

	    apdu.setOutgoing();
		switch (instruction) {
		case GETWORKOUTPLAN:
			byte[] ret = getWorkoutplan(buf);
			
            apdu.setOutgoingLength(ret_length);
            apdu.sendBytes((short)0,ret_length);
			break;
		case SAVEWORKOUTPLAN:
			byte[] ret2 = saveWorkoutplan(buf);
			
            apdu.setOutgoingLength(ret_length);
            apdu.sendBytes((short)0,ret_length);
			break;
		case SAVEWEIGHT:
			byte[] ret3 = saveWeight(buf);
			
            apdu.setOutgoingLength(ret_length);
            apdu.sendBytes((short)0,ret_length);
			break;
		case GETWEIGHTS:
			byte[] ret4 = getWeightHistory(buf);
			
            apdu.setOutgoingLength(ret_length);
            apdu.sendBytes((short)0,ret_length);
			break;
		case SAVECHANGES:
			byte[] ret5 = saveChanges(buf);
			
            apdu.setOutgoingLength(ret_length);
            apdu.sendBytes((short)0,ret_length);
			break;
		case GETCHANGES:
			byte[] ret6 = getChanges(buf);
			
            apdu.setOutgoingLength(ret_length);
            apdu.sendBytes((short)0,ret_length);
			break;
		
		default:
			// good practice: If you don't know the INStruction, say so:
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
	}
	
	private byte[] getWorkoutplan(byte[] buffer) {
		return buffer;
	}
	
	private byte[] saveWorkoutplan(byte[] buffer) {
		return buffer;
	}
	
	private byte[] saveWeight(byte[] buffer) {
		return buffer;
	}
	
	private byte[] getWeightHistory(byte[] buffer) {
		return buffer;
	}
	
	private byte[] saveChanges(byte[] buffer) {
		return buffer;
	}
	
	private byte[] getChanges(byte[] buffer) {
		return buffer;
	}

	public void deselect() {
		super.deselect();
		
		//TODO: save data persistent
	}
}
