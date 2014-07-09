package comm;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import model.MyDate;
import model.Progress;
import model.ProgressElement;
import model.Set;
import model.Stage;
import model.Workoutplan;
import opencard.core.terminal.CardTerminalException;

public class CardInterface {
	
	static final byte MAXRESPONSEDATALENGTH = (byte)0xfc;
	
	public static void main(String[] args) {
		testP();
	}
	
	private static void testWP() {
		System.out.println("start writing workoutplan...");
		
		//create workoutplan
		Workoutplan wp = createWPExtended();
		
		boolean success = saveWorkoutplan(wp);
		
		if (success) {
			Workoutplan wp2 = getWorkoutplan();
			
			System.out.println("Workoutplan recived!");
		}
	}
	
	private static void testP() {
		System.out.println("start writing progress...");
		
		//create workoutplan
		Progress[] ps = createPs();
		
		boolean success = saveProgress(ps);
		
		if (success) {
			Progress[] ps2 = getProgress();
			
			System.out.println("Progress recived!");
		}
	}
	
	private static Progress[] createPs() {
		MyDate date = new MyDate((byte)0x0b, (byte)0x07, (byte)0x05);
		ProgressElement last = new ProgressElement((byte)0x24,(byte)0x0a, date);
		ProgressElement best = new ProgressElement((byte)0x26,(byte)0x0a, date);
		ProgressElement worst = new ProgressElement((byte)0x2f,(byte)0x08, date);
		
		Progress that1 = new Progress((byte)0x01, last, last, last);
		Progress that2 = new Progress((byte)0x02, best, best, worst);
		Progress that3 = new Progress((byte)0x03, worst, best, worst);
		
		return new Progress[]{that1, that2, that3};
	}
	
	private static Workoutplan createWP() {
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
		
		MyDate now = new MyDate((byte)0x0e, (byte)0x07, (byte)0x03);	//6 bytes
		MyDate othernow = new MyDate((byte)0x0e, (byte)0x08, (byte)0x01);//6 bytes
		
		Workoutplan ret = new Workoutplan(fs, allStages, ss, now, othernow);
		
		return ret;
	}
	
	private static Workoutplan createWPExtended() {
		Set[] sets = new Set[8];
		sets[0] = new Set((byte)0x01, (byte)0x1d, (byte)0x0f);
		sets[1] = new Set((byte)0x02, (byte)0x28, (byte)0x08);
		sets[2] = new Set((byte)0x01, (byte)0x1d, (byte)0x0f);
		sets[3] = new Set((byte)0x02, (byte)0x28, (byte)0x08);
		sets[4] = new Set((byte)0x01, (byte)0x1d, (byte)0x0f);
		sets[5] = new Set((byte)0x02, (byte)0x28, (byte)0x08);
		sets[6] = new Set((byte)0x01, (byte)0x1d, (byte)0x0f);
		sets[7] = new Set((byte)0x02, (byte)0x28, (byte)0x08);
		Stage stage1 = new Stage((byte)0x01, (byte)0x0f, sets, (byte)0x33, (byte)0x01);//19 byte
		
		Set[] sets2 = new Set[9];
		sets2[0] = new Set((byte)0x01, (byte)0x1d, (byte)0x0e);
		sets2[1] = new Set((byte)0x02, (byte)0x28, (byte)0x08);
		sets2[2] = new Set((byte)0x03, (byte)0x0a, (byte)0x0f);
		sets2[3] = new Set((byte)0x01, (byte)0x1d, (byte)0x0e);
		sets2[4] = new Set((byte)0x02, (byte)0x28, (byte)0x08);
		sets2[5] = new Set((byte)0x03, (byte)0x0a, (byte)0x0f);
		sets2[6] = new Set((byte)0x01, (byte)0x1d, (byte)0x0e);
		sets2[7] = new Set((byte)0x02, (byte)0x28, (byte)0x08);
		sets2[8] = new Set((byte)0x03, (byte)0x0a, (byte)0x0f);
		Stage stage2 = new Stage((byte)0x01, (byte)0x10, sets2, (byte)0x2b, (byte)0x02);//25 bytes
		
		Stage stage3 = new Stage((byte)0x01, (byte)0x11, sets, (byte)0x2f, (byte)0x03);
		Stage stage4 = new Stage((byte)0x01, (byte)0x12, sets, (byte)0x2e, (byte)0x04);
		
		Set warmupset = new Set((byte)0x01, (byte)0x00, (byte)0xff);
		Set cooldownset = new Set((byte)0x01, (byte)0x00, (byte)0xaa);
		
		Stage warmupstage = new Stage((byte)0x01, (byte)0x01, new Set[]{warmupset, cooldownset}, (byte)0x00, (byte)0x05);
		Stage cooldownstage = new Stage((byte)0x01, (byte)0x01, new Set[]{cooldownset, warmupset}, (byte)0x00, (byte)0x06);
		
		
		MyDate now = new MyDate((byte)0x0e, (byte)0x07, (byte)0x03);	//6 bytes
		MyDate othernow = new MyDate((byte)0x0e, (byte)0x08, (byte)0x01);//6 bytes
		
		Workoutplan ret = new Workoutplan(new Stage[]{warmupstage, stage1}, new Stage[]{stage1, stage2, warmupstage, stage3, cooldownstage, stage4}, new Stage[]{warmupstage, cooldownstage}, now, othernow);
		
		return ret;
	}

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
			
			System.out.println("!!! Send data: "+getHex(instructions)+getHex(data));
			
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
		byte[] bytes = new byte[37*ps.length];
		//copy elements
		for (short i = 0; i < ps.length; i++) {
			System.arraycopy(ps[i].toBytes(), 0, bytes, (i-1)*37, 37);
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
			
			System.out.println("!!! Send data: "+getHex(instructions)+getHex(data));
			
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
		
		Progress[] ret = new Progress[pbytes.length/37];
		
		try {
			for (short i = 0; i < pbytes.length/37; i++) {
				byte[] temp = new byte[37];
				System.arraycopy(pbytes, (i)*37, temp, 0, 37);
				ret[i] = Progress.fromBytes(temp);
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
	
	
	
	///////////////////////////////////////////DEBUG
	static final String HEXES = "0123456789ABCDEF";
	  public static String getHex( byte [] raw ) {
	    if ( raw == null ) {
	      return null;
	    }
	    final StringBuilder hex = new StringBuilder( 2 * raw.length );
	    for ( final byte b : raw ) {
	      hex//.append("(byte)0x")
	      	 .append(HEXES.charAt((b & 0xF0) >> 4))
	         .append(HEXES.charAt((b & 0x0F)))
	         //.append(", ")
	         ;
	    }
	    return hex.toString();
	  }
}
