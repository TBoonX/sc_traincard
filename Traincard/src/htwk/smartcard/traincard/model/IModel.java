package htwk.smartcard.traincard.model;

public abstract class IModel {
	/*
	* unique identifier for the class
	*/
	public static final byte IDENTIFICATOR = 0x00;
	
	public byte[] toBytes(){
		return null;
	}
	public IModel fromBytes(byte[] bytes) {
		return null;
	}
}
