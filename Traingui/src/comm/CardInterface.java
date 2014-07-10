package comm;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import model.Progress;
import model.Workoutplan;
import opencard.core.terminal.CardTerminalException;

public class CardInterface {
	
	static final byte MAXRESPONSEDATALENGTH = (byte)0xfc;

	public static boolean login(String password, boolean isTrainer) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			return false;
		}
		
		try {
			md.update(password.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		byte[] digest = md.digest();
		
		byte kind = 0x01;
		if (!isTrainer)
			kind = 0x02;
		byte[] instructions = new byte[]{(byte)0x00, 0x06 , 0x01, (byte)(digest.length+1), kind };
		
		byte[] response = send(instructions, digest);
		
		if (response == null)
			return false;
		
		return (response[3] == 0x01) ? true : false;
	}
	
	public static boolean logout(String password, boolean isTrainer) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			return false;
		}
		
		try {
			md.update(password.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		byte[] digest = md.digest();
		
		byte kind = 0x01;
		if (!isTrainer)
			kind = 0x02;
		byte[] instructions = new byte[]{(byte)0x00, 0x07 , 0x01, (byte)(digest.length+1), kind };
		
		byte[] response = send(instructions, digest);
		
		if (response == null)
			return false;
		
		return (response[3] == 0x01) ? true : false;
	}
	
	public static boolean saveWorkoutplan(Workoutplan wp) {
		byte[] bytes = wp.toBytes();
		
		//calculate needed amount of apdus and the range which should returned
		short mod = (short)(bytes.length % (MAXRESPONSEDATALENGTH & 0xff));
		byte mod_ = (byte)(mod == 0 ? 0 : 1);
		byte apduAmount = (byte)1;
		if (bytes.length-mod > 0)//no division with zero
			apduAmount = (byte)(((bytes.length-mod)/(MAXRESPONSEDATALENGTH & 0xff))+mod_);
		
		//create ret array which contains the part of the workoutplan
		short datalength = (short)(MAXRESPONSEDATALENGTH & 0xff);
		if (apduAmount == 1)
			datalength = (byte)bytes.length;
		
		for (short i = 1; i <= apduAmount; i++) {
			
			if (apduAmount == i)
				datalength = mod;
			
			byte[] instructions = new byte[]{(byte)0x00, 0x01, apduAmount, (byte)(datalength+1), (byte)i };
			
			byte[] data = new byte[datalength];
			System.arraycopy(bytes, (MAXRESPONSEDATALENGTH & 0xff)*(i-1), data, 0, datalength);
			
			
			
			byte[] response = send(instructions, data);
			
			if (response == null)
				return false;
			
			if (response[2] != 0x01)
				return false;
		}
		
		
		return true;
	}
	
	public static Workoutplan getWorkoutplan() {
		byte[] instructions = new byte[]{(byte)0x00, 0x02 , 0x01, 0x01 };
		byte[] data = new byte[1];
		data[0] = 0x01;
		
		byte[] response = send(instructions, data);
		
		if (response == null)
			return null;
		
		short apduAmount = (short)(response[0] & 0xff);
		short apduNumber = (short)(response[2] & 0xff);
		short dataLength = (short)(response[1] & 0xff);
		
		if (apduAmount < 1 || apduNumber < 1)
			return null;
		
		byte[] wpbytes = new byte[dataLength-1];
		System.arraycopy(response, 3, wpbytes, 0, dataLength-1);
		
		for (short i = 2; i <= apduAmount; i++) {
			data[0] = (byte)(i & 0xff);
			
			response = send(instructions, data);
			
			if (response == null)
				return null;
			
			apduAmount = (short)(response[0] & 0xff);
			apduNumber = (short)(response[2] & 0xff);
			dataLength = (short)(response[1] & 0xff);
			
			if (apduAmount < 1 || apduNumber < 1)
				return null;
			
			//save old bytes
			byte[] temp = new byte[wpbytes.length];
			System.arraycopy(wpbytes, 0, temp, 0, wpbytes.length);
			
			wpbytes = new byte[temp.length+dataLength-1];
			System.arraycopy(temp, 0, wpbytes, 0, temp.length);
			System.arraycopy(response, 3, wpbytes, temp.length, dataLength-1);
		}
		
		Workoutplan ret = Workoutplan.fromBytes(wpbytes);
		
		return ret;
	}
	
	public static boolean saveProgress(Progress[] ps) {
		//count length of needed bytearray
		short length = 0;
		for (short k = 0; k < ps.length; k++) {
			if (ps[k].getLast() == null)
				length += 4;
			else
				length += 37;
		}
		
		byte[] bytes = new byte[length];
		//copy elements
		short lastIndex = 0;
		for (short i = 0; i < ps.length; i++) {
			short pslength = 37;
			if (ps[i].getLast() == null)
				pslength = 4;
			System.arraycopy(ps[i].toBytes(), 0, bytes, lastIndex, pslength);
			lastIndex += pslength;
		}
		
		//calculate needed amount of apdus and the range which should returned
		short mod = (short)(bytes.length % (MAXRESPONSEDATALENGTH & 0xff));
		byte mod_ = (byte)(mod == 0 ? 0 : 1);
		byte apduAmount = (byte)1;
		if (bytes.length-mod > 0)//no division with zero
			apduAmount = (byte)(((bytes.length-mod)/(MAXRESPONSEDATALENGTH & 0xff))+mod_);
		
		//create ret array which contains the part of the workoutplan
		short datalength = (short)(MAXRESPONSEDATALENGTH & 0xff);
		if (apduAmount == 1)
			datalength = (byte)bytes.length;
		
		for (short i = 1; i <= apduAmount; i++) {
			
			if (apduAmount == i)
				datalength = mod;
			
			byte[] instructions = new byte[]{(byte)0x00, 0x03, apduAmount, (byte)(datalength+1), (byte)i };
			
			byte[] data = new byte[datalength];
			System.arraycopy(bytes, (MAXRESPONSEDATALENGTH & 0xff)*(i-1), data, 0, datalength);
			
			
			
			byte[] response = send(instructions, data);
			
			if (response == null)
				return false;
			
			if (response[2] != 0x01)
				return false;
		}
		
		
		return true;
	}
	
	public static Progress[] getProgress() {
		byte[] instructions = new byte[]{(byte)0x00, 0x04 , 0x01, 0x01 };
		byte[] data = new byte[1];
		data[0] = 0x01;
		
		byte[] response = send(instructions, data);
		
		if (response == null)
			return null;
		
		short apduAmount = (short)(response[0] & 0xff);
		short apduNumber = (short)(response[2] & 0xff);
		short dataLength = (short)(response[1] & 0xff);
		
		if (apduAmount < 1 || apduNumber < 1)
			return null;
		
		byte[] pbytes = new byte[dataLength-1];
		System.arraycopy(response, 3, pbytes, 0, dataLength-1);
		
		for (short i = 2; i <= apduAmount; i++) {
			data[0] = (byte)(i & 0xff);
			
			response = send(instructions, data);
			
			if (response == null)
				return null;
			
			apduAmount = (short)(response[0] & 0xff);
			apduNumber = (short)(response[2] & 0xff);
			dataLength = (short)(response[1] & 0xff);
			
			if (apduAmount < 1 || apduNumber < 1)
				return null;
			
			//save old bytes
			byte[] temp = new byte[pbytes.length];
			System.arraycopy(pbytes, 0, temp, 0, pbytes.length);
			
			pbytes = new byte[temp.length+dataLength-1];
			System.arraycopy(temp, 0, pbytes, 0, temp.length);
			System.arraycopy(response, 3, pbytes, temp.length, dataLength-1);
		}
		
		Progress[] ret = new Progress[0];
		Progress[] temp;
		
		try {
			short index = 0;
			while (index < pbytes.length) {
				byte id = pbytes[index];
				short length = (short)((pbytes[index+1]<<8) | (pbytes[index+2]));
				
				byte[] bytes = new byte[37];
				short copylength = 37;
				if (length == 1) {
					bytes = new byte[4];
					copylength = 4;
				}
				System.arraycopy(pbytes, index, bytes, 0, copylength);
				Progress newone = Progress.fromBytes(bytes);
				
				temp = ret;
				ret = new Progress[temp.length+1];
				for (short x = 0; x < temp.length; x++) {
					ret[x] = temp[x];
				}
				ret[ret.length-1] = newone;
				
				index += copylength;
			}
		} catch (IndexOutOfBoundsException ioobe) {
			ioobe.printStackTrace();
			return null;
		}
		
		return ret;
	}
	
	private static byte[] send(byte[] instructions, byte[] data) {
		CardHandler that;
		try {
			that = CardHandler.getInstance();
		} catch (CardTerminalException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		byte[] ret = null;
		try {
			ret = that.sendData(instructions, data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		System.out.println("Card returned:\n" + CardHandler.bytesToHex(ret));
		
		return ret;
	}
}
