package main;
import gui.MainView;

import javax.swing.JFrame;

import model.Progress;
import model.Workoutplan;

public class Traingui {
	
	private static Traingui myTraingui;
	private JFrame myScreen;
	private Workoutplan myPlan = null;
	private Progress[] myProgress = null;
	
	
	public static void main(String[] args) {
		myTraingui =  new Traingui();
	}
	
	public Traingui(){
		super();
		initScreen();
	}
	
	private void initScreen(){
		myScreen = new MainView();
		myScreen.setSize(700, 500);
		myScreen.setVisible(true);
	}
	
	public static Traingui getTraingui(){
		return myTraingui;
	}
	
	
	
	public JFrame getScreen(){
		return myScreen;
	}
	
	public void setScreen(JFrame screen){
		this.myScreen.setContentPane(screen.getContentPane());
		myScreen.show();
	}
	
	public Workoutplan getWorkoutplan(){
		return myPlan;
	}
	
	public void setWorkoutplan(Workoutplan plan){
		this.myPlan = plan;
	}

	public Progress[] getMyProgress() {
		return myProgress;
	}

	public void setMyProgress(Progress[] myProgress) {
		this.myProgress = myProgress;
	}
}
