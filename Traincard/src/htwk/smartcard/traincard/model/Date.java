package htwk.smartcard.traincard.model;

public class Date extends IModel {

	private short year;
	private char month;
	private char day;
	public Date(short year, char month, char day) {
		super();
		// TODO Auto-generated constructor stub
		this.year = year;
		this.month = month;
		this.day = day;
	}
	public char getDay() {
		return day;
	}
	public void setDay(char day) {
		this.day = day;
	}
	public char getMonth() {
		return month;
	}
	public void setMonth(char month) {
		this.month = month;
	}
	public short getYear() {
		return year;
	}
	public void setYear(short year) {
		this.year = year;
	}
	
	
}
