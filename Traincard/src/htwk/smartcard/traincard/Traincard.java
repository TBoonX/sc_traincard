package htwk.smartcard.traincard;

import javacard.framework.APDU;
import javacard.framework.Applet;
import javacard.framework.ISO7816;
import javacard.framework.ISOException;
import javacard.framework.Util;
import javacard.framework.JCSystem;

/**
 * 
 * @author Kurt
 * packagename: smartcard
 * appletname: Traincard
 * http://de.wikipedia.org/wiki/Application_Protocol_Data_Unit
 */
public class Traincard extends Applet {
	
//	offsets
	final byte NOA = 0x02;	//Number of APDUs
	final byte LENGTH = 0x03;	//count of bytes beeing send with the request
	final byte DATA = 0x04;	//Start data byte
	

//	instructions
	final byte SAVEWORKOUTPLAN = (byte)0x01;
	final byte GETWORKOUTPLAN = (byte)0x02;
	final byte SAVEWEIGHT = (byte)0x03;
	final byte GETWEIGHTS = (byte)0x04;
	final byte SAVECHANGES = (byte)0x05;
	final byte GETCHANGES = (byte)0x06;
	final byte REGISTER = (byte)0x07;
	final byte LOGIN = (byte)0x08;
	final byte LOGOUT = (byte)0x09;
	final byte TEST = (byte)0x11;
	final byte TEST2 = (byte)0x12;
	
	short ret_length = 0;
	
//	data
	byte[] password_trainer;
	byte[] password_sportsman;
	final byte TRAINER = (byte)0x01;
	final byte SPORTSMAN = (byte)0x02;
	boolean trainer_loggedin = false;
	boolean sportsman_loggedin = false;
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

	    byte[] output = null;
	    
		switch (instruction) {
		case REGISTER:
			output = register(buf);
			ret_length = (short)(output.length);
			break;
		case GETWORKOUTPLAN:
			output = getWorkoutplan(buf);
			ret_length = (short)(output.length+1);
			break;
		case SAVEWORKOUTPLAN:
			output = saveWorkoutplan(buf);
			ret_length = (short)(output.length+1);
			break;
		case SAVEWEIGHT:
			output = saveWeight(buf);
			ret_length = (short)(output.length+1);
			break;
		case GETWEIGHTS:
			output = getWeightHistory(buf);
			ret_length = (short)(output.length+1);
			break;
		case SAVECHANGES:
			output = saveChanges(buf);
			ret_length = (short)(output.length+1);
			break;
		case GETCHANGES:
			output = getChanges(buf);
			ret_length = (short)(output.length+1);
			break;
		case TEST:
			byte[] ret7 = null;
			ret7 = test(buf);
			override(buf, ret7);
			
			apdu.setOutgoingAndSend((short)0, (short)106);
			
            ISOException.throwIt(ISO7816.SW_NO_ERROR);
			break;
		case TEST2:
			short l = 256;
			byte[] big = new byte[l];
			override(buf, big);
			apdu.setOutgoingAndSend((short)0, l);
			return;
			//break;
		
		default:
			// good practice: If you don't know the INStruction, say so:
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
		
		override(buf, output);
		apdu.setOutgoingAndSend((short)0, ret_length);
		ISOException.throwIt(ISO7816.SW_NO_ERROR);
	}
	
	private void override(byte[] buffer, byte[] data) {
		Util.arrayCopy(data, (short)0, buffer, (short)0, (short)data.length);
	}
	
	private byte[] test(byte[] buffer) {
		Set[] sets = new Set[2];
		sets[0] = new Set((byte)0x01, (byte)0x1d, (byte)0x0f);
		sets[1] = new Set((byte)0x02, (byte)0x28, (byte)0x08);
		Stage stage1 = new Stage((byte)0x01, (byte)0x0f, sets, (byte)0x33, (byte)0x01);//19 byte
		
		Set[] sets2 = new Set[3];
		sets2[0] = new Set((byte)0x01, (byte)0x1d, (byte)0x0e);
		sets2[1] = new Set((byte)0x02, (byte)0x28, (byte)0x08);
		sets2[2] = new Set((byte)0x03, (byte)0x0a, (byte)0x0f);
		Stage stage2 = new Stage((byte)0x01, (byte)0x10, sets2, (byte)0x2b, (byte)0x01);//25 bytes
		
		Stage[] allStages = new Stage[2];	//44 bytes
		allStages[0] = stage1;
		allStages[1] = stage2;
		Stage[] fs = new Stage[1];
		fs[0] = stage1;
		Stage[] ss = new Stage[1];
		ss[0] = stage2;
		
		Date now = new Date((byte)0x0e, (byte)0x07, (byte)0x03);	//6 bytes
		Date othernow = new Date((byte)0x0e, (byte)0x08, (byte)0x01);//6 bytes
		
		Workoutplan that = new Workoutplan(fs, allStages, ss, now, othernow);	//3+12+3+19+25+44 bytes
		
		return that.toBytes();
	}
	
	/**
	 * Register a person
	 * 
	 * data bytes should include kind byte and password bytes
	 * example: /send 80 07 01 04 01 0a 0b 0c
	 * 
	 * returned bytes include kind byte and success byte
	 * 
	 * @param buffer
	 * @return
	 */
	private byte[] register(byte[] buffer) {
		byte length = buffer[LENGTH];
		byte kind = buffer[DATA];
		
		byte[] errorbytes = JCSystem.makeTransientByteArray((short)4, JCSystem.CLEAR_ON_DESELECT );
		errorbytes[0] = 0x01;
		errorbytes[1] = 0x02;
		errorbytes[2] = kind;
		errorbytes[3] = 0x00;
		
		
		switch (kind) {
		case TRAINER:
			if (null != password_trainer || length < 2) {
				return errorbytes;
			}
			
			password_trainer = new byte[length];
			Util.arrayCopy(buffer, (short)(DATA+1), password_trainer, (short)0, (short)(length-1));
			break;
		case SPORTSMAN:
			if (null != password_sportsman || length < 2) {
				return errorbytes;
			}
			
			password_sportsman = new byte[length];
			Util.arrayCopy(buffer, (short)(DATA+1), password_sportsman, (short)0, (short)(length-1));
			break;
		default:
			return errorbytes;
		}
		
		return new byte[]{0x01, 0x02, kind, (byte)0x01};
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
