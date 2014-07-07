package gui;

import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.Constants;
import main.Traingui;
import model.Set;
import model.Stage;
import model.Workoutplan;

public class TableMethods {
	
	private static int numberOfDays = 1;
	
	public JTable loadWorkoutplanTable(JTable myTable) {
		numberOfDays = 1;
		clearTable(myTable);
		DefaultTableModel model = (DefaultTableModel) myTable.getModel();
		
		Workoutplan myPlan = Traingui.getTraingui().getWorkoutplan();
		if(myPlan == null){
			return myTable;
		}
		
		Stage[] currStageArray;
		Stage currStage;
		
		outerloop:
		for (int i = 0; i < (myPlan.getWarmupstage().length
				/*+ myPlan.getTrainingstage().length + myPlan.getCooldownstage().length*/); i++) {
			currStageArray = myPlan.getWarmupstage();
			
			for(int j=0; j < currStageArray.length; j++){
				
				String weights="",repeats="";
				currStage = currStageArray[j];
				System.out.print("Testday:"+(int)currStage.getDay());
				if((int)currStage.getDay() != numberOfDays){
					if((int)currStage.getDay() > numberOfDays){
					System.out.print("day:"+(int)currStage.getDay());
					numberOfDays=(int) currStage.getDay();
					break;}
					continue;
				}
				
				Set[] currSet = currStage.getSets();
				for(int k=0;k<currSet.length;k++){
					weights = weights+ currSet[k].getWeight();
					repeats = repeats+ currSet[k].getReplicates();
					if(k+1 < currSet.length){
						weights = weights+"-";
						repeats = repeats+"-";
					}
				}
				String muscleGroup="";
				int value = currStage.getMusclegroupID();
				for(Map.Entry entry: Constants.WORKOUTNAMES.entrySet()){
		            if(value == (int)entry.getValue()){
		            	muscleGroup = (String) entry.getKey();
		                break; //breaking because its one to one map
		            }
		        }
				
				
				model.addRow(new Object[]{(int)currStage.getDay(), (int)currStage.getDeviceID(),muscleGroup , currStage.getSets().length,weights ,repeats});
				if(j==currStageArray.length-1){
					break outerloop;
				}
			}
			
			
		}
		return myTable;
	}
	
	public JTable loadWorkoutplanTable(JTable myTable, int day) {
		numberOfDays = 1;
		clearTable(myTable);
		DefaultTableModel model = (DefaultTableModel) myTable.getModel();
		
		Workoutplan myPlan = Traingui.getTraingui().getWorkoutplan();
		if(myPlan == null){
			return myTable;
		}
		
		Stage[] currStageArray;
		Stage currStage;
		
		outerloop:
		for (int i = 0; i < (myPlan.getWarmupstage().length
				/*+ myPlan.getTrainingstage().length + myPlan.getCooldownstage().length*/); i++) {
			currStageArray = myPlan.getWarmupstage();
			
			for(int j=0; j < currStageArray.length; j++){
				
				String weights="",repeats="";
				currStage = currStageArray[j];
				System.out.print("Testday:"+(int)currStage.getDay());
				if ((int) currStage.getDay() != numberOfDays) {
					if ((int) currStage.getDay() > numberOfDays ) {
						System.out.print("day:" + (int) currStage.getDay());
						numberOfDays=(int) currStage.getDay();
						break;
					}
					continue;
				}
				Set[] currSet = currStage.getSets();
				for(int k=0;k<currSet.length;k++){
					weights = weights+ currSet[k].getWeight();
					repeats = repeats+ currSet[k].getReplicates();
					if(k+1 < currSet.length){
						weights = weights+"-";
						repeats = repeats+"-";
					}
				}
				String muscleGroup="";
				int value = currStage.getMusclegroupID();
				for(Map.Entry entry: Constants.WORKOUTNAMES.entrySet()){
		            if(value == (int)entry.getValue()){
		            	muscleGroup = (String) entry.getKey();
		                break; //breaking because its one to one map
		            }
		        }
				
				if((int)currStage.getDay() ==  day ){
					model.addRow(new Object[]{(int)currStage.getDay(), (int)currStage.getDeviceID(),muscleGroup , currStage.getSets().length,weights ,repeats});
				}
				if(j==currStageArray.length-1){
					break outerloop;
				}
				
			}
			
			
		}
		return myTable;
	}
	
	public void clearTable(JTable myTable){
		 for(int i=myTable.getRowCount()-1;i>=0;i--)
		 {
			 deleteTableRow(i,myTable);
		 }
	}
	
	public void addEmptyTableRow(JTable myTable){
		DefaultTableModel model = (DefaultTableModel) myTable.getModel();
		model.addRow(new Object[]{null, null, null, null,null ,null});
	}
	
	public void deleteTableRow(int Index,JTable myTable){
		((DefaultTableModel)myTable.getModel()).removeRow(Index);
	}
	
	public int getNumberOfDays(){
		return numberOfDays;
	}
}
