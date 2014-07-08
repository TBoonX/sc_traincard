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
	final byte SAVEPROGRESS = (byte)0x03;
	final byte GETPROGRESS = (byte)0x04;
	final byte REGISTER = (byte)0x05;
	final byte LOGIN = (byte)0x06;
	final byte LOGOUT = (byte)0x07;
	final byte TEST = (byte)0x11;
	final byte TEST2 = (byte)0x12;
	
	short ret_length = 0;
	
//	data
	//sc14Trainer
	byte[] password_trainer = new byte[]{(byte)0x04, (byte)0xB6, (byte)0x3D, (byte)0xD6, (byte)0x08, (byte)0xE2, (byte)0x3F, (byte)0x05, (byte)0x2E, (byte)0xFF, (byte)0xC1, (byte)0x8C, (byte)0x8A, (byte)0x3B, (byte)0x10, (byte)0x97, (byte)0x8B, (byte)0xC3, (byte)0x5A, (byte)0x27, (byte)0x9D, (byte)0xDB, (byte)0x9A, (byte)0xF6, (byte)0x5F, (byte)0x54, (byte)0xBF, (byte)0x54, (byte)0xC3, (byte)0xB4, (byte)0x60, (byte)0x44};
	//sc14Sportler
	byte[] password_sportsman = new byte[]{(byte)0x5C, (byte)0xA1, (byte)0xA3, (byte)0x4A, (byte)0x74, (byte)0xF9, (byte)0xE1, (byte)0xCC, (byte)0xF4, (byte)0xEE, (byte)0x2E, (byte)0x09, (byte)0xA9, (byte)0x33, (byte)0xC8, (byte)0x20, (byte)0xB4, (byte)0xF7, (byte)0xF5, (byte)0x32, (byte)0xDC, (byte)0x99, (byte)0x01, (byte)0x19, (byte)0x18, (byte)0x6F, (byte)0x16, (byte)0xF2, (byte)0x03, (byte)0xDF, (byte)0x5D, (byte)0x6B};
	final byte TRAINER = (byte)0x01;
	final byte SPORTSMAN = (byte)0x02;
	boolean trainer_loggedin = false;
	boolean sportsman_loggedin = false;
	byte[] workoutplan;
	final byte MAXRESPONSEDATALENGTH = (byte)0xfc; 
	byte[] progress;
	

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
		case LOGIN:
			output = login(buf, true);
			ret_length = (short)(output.length);
			break;
		case LOGOUT:
			output = login(buf, false);
			ret_length = (short)(output.length);
			break;
		case GETWORKOUTPLAN:
			output = getWorkoutplan(buf);
			ret_length = (short)(output.length);
			break;
		case SAVEWORKOUTPLAN:
			output = saveWorkoutplan(buf);
			ret_length = (short)(output.length);
			break;
		case SAVEPROGRESS:
			output = saveProgress(buf);
			ret_length = (short)(output.length+1);
			break;
		case GETPROGRESS:
			output = getProgress(buf);
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
		short length = (short)(buffer[LENGTH] & 0xff);
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
			
			password_trainer = new byte[length-1];
			Util.arrayCopy(buffer, (short)(DATA+1), password_trainer, (short)0, (short)(length-1));
			break;
		case SPORTSMAN:
			if (null != password_sportsman || length < 2) {
				return errorbytes;
			}
			
			password_sportsman = new byte[length-1];
			Util.arrayCopy(buffer, (short)(DATA+1), password_sportsman, (short)0, (short)(length-1));
			break;
		default:
			return errorbytes;
		}
		
		return new byte[]{0x01, 0x02, kind, (byte)0x01};
	}
	
	/**
	 * login or logout the given user with the given password
	 * 
	 * data bytes should include kind byte and password bytes
	 * example: /send 80 08 01 04 01 0a 0b 0c
	 * 
	 * returned bytes include kind byte and success byte
	 * 
	 * @param buffer
	 * @return
	 */
	private byte[] login(byte[] buffer, boolean login) {
		short length = (short)(buffer[LENGTH] & 0xff);
		byte kind = buffer[DATA];
		
		byte[] errorbytes = JCSystem.makeTransientByteArray((short)4, JCSystem.CLEAR_ON_DESELECT );
		errorbytes[0] = 0x01;
		errorbytes[1] = 0x02;
		errorbytes[2] = kind;
		errorbytes[3] = 0x00;
		
		switch (kind) {
		case TRAINER:
			if (null == password_trainer || length-1 != password_trainer.length) {
				return errorbytes;
			}
			
			if (!login && !trainer_loggedin)
				return errorbytes;
			
			if (0 != Util.arrayCompare(password_trainer, (short)0, buffer, (short)(DATA+1), (short)(length-1)))
				return errorbytes;
			
			trainer_loggedin = login;
			
			break;
		case SPORTSMAN:
			if (null == password_sportsman || length-1 != password_sportsman.length) {
				return errorbytes;
			}
			
			if (!login && !sportsman_loggedin)
				return errorbytes;
			
			if (0 != Util.arrayCompare(password_sportsman, (short)0, buffer, (short)(DATA+1), (short)(length-1)))
				return errorbytes;
			
			sportsman_loggedin = login;
			
			break;
		default:
			return errorbytes;
		}
		
		return new byte[]{0x01, 0x02, kind, (byte)0x01};
	}
	
	/**
	 * get workoutplan
	 * sometimes more than one apdus neccessary
	 * 
	 * data bytes should include the needed number of apdu
	 * 
	 * return count of all apdus, length of data, the current apdu number and the part of the workplan
	 * 
	 * example send:
	 * /send 80 02 01 01 01
	 * 
	 * @param buffer
	 * @return
	 */
	private byte[] getWorkoutplan(byte[] buffer) {
		byte apduNumber = buffer[DATA];
		
		byte[] errorbytes = JCSystem.makeTransientByteArray((short)3, JCSystem.CLEAR_ON_DESELECT );
		errorbytes[0] = 0x01;
		errorbytes[1] = 0x01;
		errorbytes[2] = 0x00;
		
		if (apduNumber < 1 || workoutplan == null || workoutplan.length < 3)
			return errorbytes;
		
		//calculate needed amount of apdus and the range which should returned
		byte mod = (byte)(workoutplan.length % (MAXRESPONSEDATALENGTH & 0xff));
		byte mod_ = (byte)((mod & 0xff) == 0 ? 0 : 1);
		byte apduAmount = (byte)1;
		if (workoutplan.length-(mod & 0xff) > 0)//no division with zero
			apduAmount = (byte)(((workoutplan.length-(mod & 0xff))/(MAXRESPONSEDATALENGTH & 0xff))+mod_);
		
		//create ret array which contains the part of the workoutplan
		byte retLength = (byte)(MAXRESPONSEDATALENGTH & 0xff);
		if (apduAmount == 1)
			retLength = (byte)workoutplan.length;
		else if (apduAmount == apduNumber)
			retLength = mod;
		retLength += (byte)3;	//for NoA, LEN und apduNumber
		byte[] ret = JCSystem.makeTransientByteArray(retLength, JCSystem.CLEAR_ON_DESELECT );
		
		//copy the part
		short startIndex = (short)((apduNumber-1)*(MAXRESPONSEDATALENGTH & 0xff));
		if (startIndex > workoutplan.length)
			return errorbytes;
		Util.arrayCopy(workoutplan, startIndex, ret, (short)3, (short)(retLength-3));
		
		//set header
		ret[0] = apduAmount;
		ret[1] = (byte)(ret.length-2);
		ret[2] = apduNumber;
		
		
		return ret;
	}
	
	/**
	 * Save the given workoutplan
	 * 
	 * data bytes should include the number of the apdu and after that byte all bytes of the workoutplan
	 * 
	 * return just the success byte
	 * 
	 * example of workpoutplan
	 * 0400670100030E07030100030E0801010201030010010F3301020003011D0F020003022808030010010F3301020003011D0F02000302280803001601102B01020003011D0E020003022808020003030A0F03001601102B01020003011D0E020003022808020003030A0F
	 * 
	 * example send:
	 * /send 80 01 01 6b 010400670100030E07030100030E0801010201030010010F3301020003011D0F020003022808030010010F3301020003011D0F02000302280803001601102B01020003011D0E020003022808020003030A0F03001601102B01020003011D0E020003022808020003030A0F
	 * @param buffer
	 * @return
	 */
	private byte[] saveWorkoutplan(byte[] buffer) {
		byte countOfApdus = buffer[NOA]; 	//01
		short length = (short)(buffer[LENGTH] & 0xff);		//6b
		byte apduNumber = buffer[DATA];		//01
		boolean firstApdu = apduNumber == (byte)0x01 ? true : false;
		
		byte[] errorbytes = JCSystem.makeTransientByteArray((short)3, JCSystem.CLEAR_ON_DESELECT );
		errorbytes[0] = 0x01;
		errorbytes[1] = 0x01;
		errorbytes[2] = 0x00;
		
//		if (!trainer_loggedin)
//			return errorbytes;
		
		if (firstApdu) {
			//create bytearray which holds the workoutplan
			workoutplan = new byte[length-1];
			Util.arrayCopy(buffer, (short)(DATA+1), workoutplan, (short)0, (short)(length-1));
			
			return new byte[]{0x01, 0x01, 0x01};
		}
		
		//!firstApdu
		//save head in transient array
		byte[] temp = JCSystem.makeTransientByteArray((short)workoutplan.length, JCSystem.CLEAR_ON_DESELECT );
		Util.arrayCopy(workoutplan, (short)0, temp, (short)0, (short)workoutplan.length);
		//workoutplan is now bigger
		workoutplan = new byte[temp.length+length-1];
		//copy head
		Util.arrayCopy(temp, (short)0, workoutplan, (short)0, (short)temp.length);
		//copy tail
		Util.arrayCopy(buffer, (short)(DATA+1), workoutplan, (short)0, (short)(length-1));
		
		return new byte[]{0x01, 0x01, 0x01};
	}
	
	/**
	 * Save the given Progress array
	 * 
	 * data bytes should include the number of the apdu and after that byte all bytes of the progress
	 * 
	 * return just the success byte
	 * 
	 * example send:
	 * /send 80 03 01 00 00
	 * @param buffer
	 * @return
	 */
	private byte[] saveProgress(byte[] buffer) {
		byte countOfApdus = buffer[NOA];
		short length = (short)(buffer[LENGTH] & 0xff);
		byte apduNumber = buffer[DATA];
		boolean firstApdu = apduNumber == (byte)0x01 ? true : false;
		
		byte[] errorbytes = JCSystem.makeTransientByteArray((short)3, JCSystem.CLEAR_ON_DESELECT );
		errorbytes[0] = 0x01;
		errorbytes[1] = 0x01;
		errorbytes[2] = 0x00;
		
//		if (!sportsman_loggedin)
//			return errorbytes;
		
		if (firstApdu) {
			//create bytearray which holds the progress array
			progress = new byte[length-1];
			Util.arrayCopy(buffer, (short)(DATA+1), progress, (short)0, (short)(length-1));
			
			return new byte[]{0x01, 0x01, 0x01};
		}
		
		//!firstApdu
		//save head in transient array
		byte[] temp = JCSystem.makeTransientByteArray((short)progress.length, JCSystem.CLEAR_ON_DESELECT );
		Util.arrayCopy(progress, (short)0, temp, (short)0, (short)progress.length);
		//progress is now bigger
		progress = new byte[temp.length+length-1];
		//copy head
		Util.arrayCopy(temp, (short)0, progress, (short)0, (short)temp.length);
		//copy tail
		Util.arrayCopy(buffer, (short)(DATA+1), progress, (short)0, (short)(length-1));
		
		return new byte[]{0x01, 0x01, 0x01};
	}
	
	private byte[] getProgress(byte[] buffer) {
		byte apduNumber = buffer[DATA];
		
		byte[] errorbytes = JCSystem.makeTransientByteArray((short)3, JCSystem.CLEAR_ON_DESELECT );
		errorbytes[0] = 0x01;
		errorbytes[1] = 0x01;
		errorbytes[2] = 0x00;
		
		if (apduNumber < 1 || progress == null || progress.length < 3)
			return errorbytes;
		
		//calculate needed amount of apdus and the range which should returned
		byte mod = (byte)(progress.length % (MAXRESPONSEDATALENGTH & 0xff));
		byte mod_ = (byte)((mod & 0xff) == 0 ? 0 : 1);
		byte apduAmount = (byte)1;
		if (progress.length-(mod & 0xff) > 0)//no division with zero
			apduAmount = (byte)(((progress.length-(mod & 0xff))/(MAXRESPONSEDATALENGTH & 0xff))+mod_);
		
		//create ret array which contains the part of the workoutplan
		byte retLength = (byte)(MAXRESPONSEDATALENGTH & 0xff);
		if (apduAmount == 1)
			retLength = (byte)progress.length;
		else if (apduAmount == apduNumber)
			retLength = mod;
		retLength += (byte)3;	//for NoA, LEN und apduNumber
		byte[] ret = JCSystem.makeTransientByteArray(retLength, JCSystem.CLEAR_ON_DESELECT );
		
		//copy the part
		short startIndex = (short)((apduNumber-1)*(MAXRESPONSEDATALENGTH & 0xff));
		if (startIndex > progress.length)
			return errorbytes;
		Util.arrayCopy(progress, startIndex, ret, (short)3, (short)(retLength-3));
		
		//set header
		ret[0] = apduAmount;
		ret[1] = (byte)(ret.length-2);
		ret[2] = apduNumber;
		
		
		return ret;
	}

	public void deselect() {
		super.deselect();
		
		//TODO: save data persistent
	}

}
