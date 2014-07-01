package htwk.smartcard.traincard.model;

public class Workoutplan extends IModel {

	private Stage warmupstage ;
	private Stage trainingstage ;
	private Stage cooldownstage;
	private Date startdate;
	private Date enddate;
	public Workoutplan(Stage warmupstage, Stage trainingstage, Stage cooldownstage, Date startdate, Date enddate) {
		super();
		// TODO Auto-generated constructor stub
		this.warmupstage = warmupstage;
		this.trainingstage = trainingstage;
		this.cooldownstage = cooldownstage;
		this.startdate = startdate;
		this.enddate = enddate;
	}
	public Stage getCooldownstage() {
		return cooldownstage;
	}
	public void setCooldownstage(Stage cooldownstage) {
		this.cooldownstage = cooldownstage;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Stage getTrainingstage() {
		return trainingstage;
	}
	public void setTrainingstage(Stage trainingstage) {
		this.trainingstage = trainingstage;
	}
	public Stage getWarmupstage() {
		return warmupstage;
	}
	public void setWarmupstage(Stage warmupstage) {
		this.warmupstage = warmupstage;
	}
	
	
}
