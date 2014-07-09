package gui;

import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.Constants;
import main.Traingui;
import model.Progress;
import model.ProgressElement;
import model.Set;
import model.Stage;
import model.Workoutplan;

public class TableMethods {

	private static int numberOfDaysWarmup = 1;
	private static int numberOfDaysTrain = 1;
	private static int numberOfDaysCooldown = 1;
	private boolean warmupFinished = false;
	private boolean trainFinished = false;
	private boolean cooldownFinished = false;

	public JTable loadWorkoutplanTable(JTable myTable) {
		numberOfDaysWarmup = numberOfDaysTrain = numberOfDaysCooldown = 1;
		int stages = 0;
		clearTable(myTable);
		DefaultTableModel model = (DefaultTableModel) myTable.getModel();

		Workoutplan myPlan = Traingui.getTraingui().getWorkoutplan();
		if (myPlan == null) {
			return myTable;
		}

		Stage[] currStageArray;
		Stage currStage;

		stages = myPlan.getWarmupstage().length
				+ myPlan.getTrainingstage().length
				+ myPlan.getCooldownstage().length;

		outerloop: for (int i = 0; i < stages; i++) {

			if (myPlan.getWarmupstage().length > 0 && !warmupFinished) {
				currStageArray = myPlan.getWarmupstage();
				for (int j = 0; j < currStageArray.length; j++) {

					String weights = "", repeats = "";
					currStage = currStageArray[j];

					if ((int) currStage.getDay() != numberOfDaysWarmup) {
						if ((int) currStage.getDay() > numberOfDaysWarmup) {
							numberOfDaysWarmup = (int) currStage.getDay();
							break;
						}
						continue;
					}
					Set[] currSet = currStage.getSets();
					for (int k = 0; k < currSet.length; k++) {
						weights = weights + currSet[k].getWeight();
						repeats = repeats + currSet[k].getReplicates();
						if (k + 1 < currSet.length) {
							weights = weights + "-";
							repeats = repeats + "-";
						}
					}
					String muscleGroup = "";
					int value = currStage.getMusclegroupID();
					for (Map.Entry entry : Constants.WORKOUTNAMES.entrySet()) {
						if (value == (int) entry.getValue()) {
							muscleGroup = (String) entry.getKey();
							break; // breaking because its one to one map
						}
					}

					model.addRow(new Object[] { (int) currStage.getDay(),
							(int) currStage.getDeviceID(), muscleGroup,
							currStage.getSets().length, weights, repeats,
							"warm up" });

					if (j == currStageArray.length - 1) {
						warmupFinished = true;
					}

				}
			}
			if (myPlan.getTrainingstage().length > 0 && !trainFinished) {
				currStageArray = myPlan.getTrainingstage();
				for (int j = 0; j < currStageArray.length; j++) {

					String weights = "", repeats = "";
					currStage = currStageArray[j];

					if ((int) currStage.getDay() != numberOfDaysTrain) {
						if ((int) currStage.getDay() > numberOfDaysTrain) {

							numberOfDaysTrain = (int) currStage.getDay();
							break;
						}
						continue;
					}
					Set[] currSet = currStage.getSets();
					for (int k = 0; k < currSet.length; k++) {
						weights = weights + currSet[k].getWeight();
						repeats = repeats + currSet[k].getReplicates();
						if (k + 1 < currSet.length) {
							weights = weights + "-";
							repeats = repeats + "-";
						}
					}
					String muscleGroup = "";
					int value = currStage.getMusclegroupID();
					for (Map.Entry entry : Constants.WORKOUTNAMES.entrySet()) {
						if (value == (int) entry.getValue()) {
							muscleGroup = (String) entry.getKey();
							break; // breaking because its one to one map
						}
					}

					model.addRow(new Object[] { (int) currStage.getDay(),
							(int) currStage.getDeviceID(), muscleGroup,
							currStage.getSets().length, weights, repeats,
							"training" });

					if (j == currStageArray.length - 1) {
						trainFinished = true;
					}

				}
			}
			if (myPlan.getCooldownstage().length > 0 && !cooldownFinished) {
				currStageArray = myPlan.getCooldownstage();
				for (int j = 0; j < currStageArray.length; j++) {

					String weights = "", repeats = "";
					currStage = currStageArray[j];

					if ((int) currStage.getDay() != numberOfDaysCooldown) {
						if ((int) currStage.getDay() > numberOfDaysCooldown) {

							numberOfDaysCooldown = (int) currStage.getDay();
							break;
						}
						continue;
					}
					Set[] currSet = currStage.getSets();
					for (int k = 0; k < currSet.length; k++) {
						weights = weights + currSet[k].getWeight();
						repeats = repeats + currSet[k].getReplicates();
						if (k + 1 < currSet.length) {
							weights = weights + "-";
							repeats = repeats + "-";
						}
					}
					String muscleGroup = "";
					int value = currStage.getMusclegroupID();
					for (Map.Entry entry : Constants.WORKOUTNAMES.entrySet()) {
						if (value == (int) entry.getValue()) {
							muscleGroup = (String) entry.getKey();
							break; // breaking because its one to one map
						}
					}

					model.addRow(new Object[] { (int) currStage.getDay(),
							(int) currStage.getDeviceID(), muscleGroup,
							currStage.getSets().length, weights, repeats,
							"cooldown" });

					if (j == currStageArray.length - 1) {
						cooldownFinished = true;
					}

				}
			}

		}
		return myTable;
	}

	public JTable loadWorkoutplanTable(JTable myTable, int day) {
		numberOfDaysWarmup = numberOfDaysTrain = numberOfDaysCooldown = 1;
		int stages = 0;
		clearTable(myTable);
		DefaultTableModel model = (DefaultTableModel) myTable.getModel();

		Workoutplan myPlan = Traingui.getTraingui().getWorkoutplan();
		if (myPlan == null) {
			return myTable;
		}

		Stage[] currStageArray;
		Stage currStage;

		stages = myPlan.getWarmupstage().length
				+ myPlan.getTrainingstage().length
				+ myPlan.getCooldownstage().length;

		outerloop: for (int i = 0; i < stages; i++) {

			if (myPlan.getWarmupstage().length > 0 && !warmupFinished) {
				currStageArray = myPlan.getWarmupstage();
				for (int j = 0; j < currStageArray.length; j++) {

					String weights = "", repeats = "";
					currStage = currStageArray[j];

					if ((int) currStage.getDay() != numberOfDaysWarmup) {
						if ((int) currStage.getDay() > numberOfDaysWarmup) {
							numberOfDaysWarmup = (int) currStage.getDay();
							break;
						}
						continue;
					}
					Set[] currSet = currStage.getSets();
					for (int k = 0; k < currSet.length; k++) {
						weights = weights + currSet[k].getWeight();
						repeats = repeats + currSet[k].getReplicates();
						if (k + 1 < currSet.length) {
							weights = weights + "-";
							repeats = repeats + "-";
						}
					}
					String muscleGroup = "";
					int value = currStage.getMusclegroupID();
					for (Map.Entry entry : Constants.WORKOUTNAMES.entrySet()) {
						if (value == (int) entry.getValue()) {
							muscleGroup = (String) entry.getKey();
							break; // breaking because its one to one map
						}
					}

					if ((int) currStage.getDay() == day) {
						model.addRow(new Object[] { (int) currStage.getDay(),
								(int) currStage.getDeviceID(), muscleGroup,
								currStage.getSets().length, weights, repeats,
								"warm up" });
					}
					if (j == currStageArray.length - 1) {
						warmupFinished = true;
					}

				}
			}
			if (myPlan.getTrainingstage().length > 0 && !trainFinished) {
				currStageArray = myPlan.getTrainingstage();
				for (int j = 0; j < currStageArray.length; j++) {

					String weights = "", repeats = "";
					currStage = currStageArray[j];

					if ((int) currStage.getDay() != numberOfDaysTrain) {
						if ((int) currStage.getDay() > numberOfDaysTrain) {

							numberOfDaysTrain = (int) currStage.getDay();
							break;
						}
						continue;
					}
					Set[] currSet = currStage.getSets();
					for (int k = 0; k < currSet.length; k++) {
						weights = weights + currSet[k].getWeight();
						repeats = repeats + currSet[k].getReplicates();
						if (k + 1 < currSet.length) {
							weights = weights + "-";
							repeats = repeats + "-";
						}
					}
					String muscleGroup = "";
					int value = currStage.getMusclegroupID();
					for (Map.Entry entry : Constants.WORKOUTNAMES.entrySet()) {
						if (value == (int) entry.getValue()) {
							muscleGroup = (String) entry.getKey();
							break; // breaking because its one to one map
						}
					}

					if ((int) currStage.getDay() == day) {
						model.addRow(new Object[] { (int) currStage.getDay(),
								(int) currStage.getDeviceID(), muscleGroup,
								currStage.getSets().length, weights, repeats,
								"training" });
					}
					if (j == currStageArray.length - 1) {
						trainFinished = true;
					}

				}
			}
			if (myPlan.getCooldownstage().length > 0 && !cooldownFinished) {
				currStageArray = myPlan.getCooldownstage();
				for (int j = 0; j < currStageArray.length; j++) {

					String weights = "", repeats = "";
					currStage = currStageArray[j];

					if ((int) currStage.getDay() != numberOfDaysCooldown) {
						if ((int) currStage.getDay() > numberOfDaysCooldown) {

							numberOfDaysCooldown = (int) currStage.getDay();
							break;
						}
						continue;
					}
					Set[] currSet = currStage.getSets();
					for (int k = 0; k < currSet.length; k++) {
						weights = weights + currSet[k].getWeight();
						repeats = repeats + currSet[k].getReplicates();
						if (k + 1 < currSet.length) {
							weights = weights + "-";
							repeats = repeats + "-";
						}
					}
					String muscleGroup = "";
					int value = currStage.getMusclegroupID();
					for (Map.Entry entry : Constants.WORKOUTNAMES.entrySet()) {
						if (value == (int) entry.getValue()) {
							muscleGroup = (String) entry.getKey();
							break; // breaking because its one to one map
						}
					}

					if ((int) currStage.getDay() == day) {
						model.addRow(new Object[] { (int) currStage.getDay(),
								(int) currStage.getDeviceID(), muscleGroup,
								currStage.getSets().length, weights, repeats,
								"cooldown" });
					}
					if (j == currStageArray.length - 1) {
						cooldownFinished = true;
					}

				}
			}

		}
		return myTable;
	}

	public JTable loadWorkoutplanTableSportler(JTable myTable, int day) {
		numberOfDaysWarmup = numberOfDaysTrain = numberOfDaysCooldown = 1;
		int stages = 0;
		clearTable(myTable);
		DefaultTableModel model = (DefaultTableModel) myTable.getModel();

		Workoutplan myPlan = Traingui.getTraingui().getWorkoutplan();
		if (myPlan == null) {
			return myTable;
		}

		Stage[] currStageArray;
		Stage currStage;

		stages = myPlan.getWarmupstage().length
				+ myPlan.getTrainingstage().length
				+ myPlan.getCooldownstage().length;

		outerloop: for (int i = 0; i < stages; i++) {

			if (myPlan.getWarmupstage().length > 0 && !warmupFinished) {
				currStageArray = myPlan.getWarmupstage();
				for (int j = 0; j < currStageArray.length; j++) {

					String weights = "", repeats = "";
					currStage = currStageArray[j];

					if ((int) currStage.getDay() != numberOfDaysWarmup) {
						if ((int) currStage.getDay() > numberOfDaysWarmup) {
							numberOfDaysWarmup = (int) currStage.getDay();
							break;
						}
						continue;
					}
					Set[] currSet = currStage.getSets();
					for (int k = 0; k < currSet.length; k++) {
						weights = weights + currSet[k].getWeight();
						repeats = repeats + currSet[k].getReplicates();
						if (k + 1 < currSet.length) {
							weights = weights + "-";
							repeats = repeats + "-";
						}
					}
					String muscleGroup = "";
					int value = currStage.getMusclegroupID();
					for (Map.Entry entry : Constants.WORKOUTNAMES.entrySet()) {
						if (value == (int) entry.getValue()) {
							muscleGroup = (String) entry.getKey();
							break; // breaking because its one to one map
						}
					}

					if ((int) currStage.getDay() == day) {
						model.addRow(new Object[] {
								(int) currStage.getDeviceID(), muscleGroup,
								currStage.getSets().length, "", weights, "",
								repeats, "", false,
								(int) currStage.getStageID() });
					}
					if (j == currStageArray.length - 1) {
						warmupFinished = true;
					}

				}
			}
			if (myPlan.getTrainingstage().length > 0 && !trainFinished) {
				currStageArray = myPlan.getTrainingstage();
				for (int j = 0; j < currStageArray.length; j++) {

					String weights = "", repeats = "";
					currStage = currStageArray[j];

					if ((int) currStage.getDay() != numberOfDaysTrain) {
						if ((int) currStage.getDay() > numberOfDaysTrain) {

							numberOfDaysTrain = (int) currStage.getDay();
							break;
						}
						continue;
					}
					Set[] currSet = currStage.getSets();
					for (int k = 0; k < currSet.length; k++) {
						weights = weights + currSet[k].getWeight();
						repeats = repeats + currSet[k].getReplicates();
						if (k + 1 < currSet.length) {
							weights = weights + "-";
							repeats = repeats + "-";
						}
					}
					String muscleGroup = "";
					int value = currStage.getMusclegroupID();
					for (Map.Entry entry : Constants.WORKOUTNAMES.entrySet()) {
						if (value == (int) entry.getValue()) {
							muscleGroup = (String) entry.getKey();
							break; // breaking because its one to one map
						}
					}

					if ((int) currStage.getDay() == day) {
						model.addRow(new Object[] {
								(int) currStage.getDeviceID(), muscleGroup,
								currStage.getSets().length, "", weights, "",
								repeats, "", false,
								(int) currStage.getStageID() });
					}
					if (j == currStageArray.length - 1) {
						trainFinished = true;
					}

				}
			}
			if (myPlan.getCooldownstage().length > 0 && !cooldownFinished) {
				currStageArray = myPlan.getCooldownstage();
				for (int j = 0; j < currStageArray.length; j++) {

					String weights = "", repeats = "";
					currStage = currStageArray[j];

					if ((int) currStage.getDay() != numberOfDaysCooldown) {
						if ((int) currStage.getDay() > numberOfDaysCooldown) {

							numberOfDaysCooldown = (int) currStage.getDay();
							break;
						}
						continue;
					}
					Set[] currSet = currStage.getSets();
					for (int k = 0; k < currSet.length; k++) {
						weights = weights + currSet[k].getWeight();
						repeats = repeats + currSet[k].getReplicates();
						if (k + 1 < currSet.length) {
							weights = weights + "-";
							repeats = repeats + "-";
						}
					}
					String muscleGroup = "";
					int value = currStage.getMusclegroupID();
					for (Map.Entry entry : Constants.WORKOUTNAMES.entrySet()) {
						if (value == (int) entry.getValue()) {
							muscleGroup = (String) entry.getKey();
							break; // breaking because its one to one map
						}
					}

					if ((int) currStage.getDay() == day) {
						model.addRow(new Object[] {
								(int) currStage.getDeviceID(), muscleGroup,
								currStage.getSets().length, "", weights, "",
								repeats, "", false,
								(int) currStage.getStageID() });
					}
					if (j == currStageArray.length - 1) {
						cooldownFinished = true;
					}

				}
			}

		}
		return myTable;
	}

	public void clearTable(JTable myTable) {
		for (int i = myTable.getRowCount() - 1; i >= 0; i--) {
			deleteTableRow(i, myTable);
		}
	}

	public void addEmptyTableRow(JTable myTable) {
		DefaultTableModel model = (DefaultTableModel) myTable.getModel();
		model.addRow(new Object[] { null, null, null, null, null, null });
	}

	public void deleteTableRow(int Index, JTable myTable) {
		((DefaultTableModel) myTable.getModel()).removeRow(Index);
	}

	public int getNumberOfDays() {
		return Math.max(numberOfDaysWarmup,
				Math.max(numberOfDaysTrain, numberOfDaysCooldown));
	}

	public JTable loadWorkoutplanTableFortschritt(JTable myTable) {
		numberOfDaysWarmup = numberOfDaysTrain = numberOfDaysCooldown = 1;
		int stages = 0;
		clearTable(myTable);
		DefaultTableModel model = (DefaultTableModel) myTable.getModel();
		Workoutplan myPlan = Traingui.getTraingui().getWorkoutplan();
		Progress[] myProgress = Traingui.getTraingui().getMyProgress();
		if (myPlan == null) {
			return myTable;
		}

		Stage[] currStageArray;
		Stage currStage;

		stages = myPlan.getWarmupstage().length
				+ myPlan.getTrainingstage().length
				+ myPlan.getCooldownstage().length;

		outerloop: for (int i = 0; i < stages; i++) {

			if (myPlan.getWarmupstage().length > 0 && !warmupFinished) {
				currStageArray = myPlan.getWarmupstage();
				for (int j = 0; j < currStageArray.length; j++) {

					currStage = currStageArray[j];

					if ((int) currStage.getDay() != numberOfDaysWarmup) {
						if ((int) currStage.getDay() > numberOfDaysWarmup) {
							numberOfDaysWarmup = (int) currStage.getDay();
							break;
						}
						continue;
					}

					String muscleGroup = "";
					int value = currStage.getMusclegroupID();
					for (Map.Entry entry : Constants.WORKOUTNAMES.entrySet()) {
						if (value == (int) entry.getValue()) {
							muscleGroup = (String) entry.getKey();
							break; // breaking because its one to one map
						}
					}

					model.addRow(new Object[] { (int) currStage.getDeviceID(),
							muscleGroup, null, null, null, null });

					if (j == currStageArray.length - 1) {
						warmupFinished = true;
					}

				}
			}
			if (myPlan.getTrainingstage().length > 0 && !trainFinished) {
				currStageArray = myPlan.getTrainingstage();
				for (int j = 0; j < currStageArray.length; j++) {

					currStage = currStageArray[j];

					if ((int) currStage.getDay() != numberOfDaysTrain) {
						if ((int) currStage.getDay() > numberOfDaysTrain) {

							numberOfDaysTrain = (int) currStage.getDay();
							break;
						}
						continue;
					}

					String muscleGroup = "";
					int value = currStage.getMusclegroupID();
					for (Map.Entry entry : Constants.WORKOUTNAMES.entrySet()) {
						if (value == (int) entry.getValue()) {
							muscleGroup = (String) entry.getKey();
							break; // breaking because its one to one map
						}
					}

					model.addRow(new Object[] { (int) currStage.getDeviceID(),
							muscleGroup, null, null, null, null });

					if (j == currStageArray.length - 1) {
						trainFinished = true;
					}

				}
			}
			if (myPlan.getCooldownstage().length > 0 && !cooldownFinished) {
				currStageArray = myPlan.getCooldownstage();
				for (int j = 0; j < currStageArray.length; j++) {

					currStage = currStageArray[j];

					if ((int) currStage.getDay() != numberOfDaysCooldown) {
						if ((int) currStage.getDay() > numberOfDaysCooldown) {

							numberOfDaysCooldown = (int) currStage.getDay();
							break;
						}
						continue;
					}

					String muscleGroup = "";
					int value = currStage.getMusclegroupID();
					for (Map.Entry entry : Constants.WORKOUTNAMES.entrySet()) {
						if (value == (int) entry.getValue()) {
							muscleGroup = (String) entry.getKey();
							break; // breaking because its one to one map
						}
					}

					model.addRow(new Object[] { (int) currStage.getDeviceID(),
							muscleGroup, null, null, null, null });

					if (j == currStageArray.length - 1) {
						cooldownFinished = true;
					}

				}
			}

		}

		if (myProgress == null) {
			return myTable;
		}
		ProgressElement currElement;
		for (int i = 0; i < myProgress.length; i++) {
			if(myProgress[i] == null){
				continue;
			}
			myTable.setValueAt(myProgress[i].getBest().getWeight()+" x "+myProgress[i].getBest().getReplicates()+" ("+myProgress[i].getBest().getDate().getDay()
					+"."+myProgress[i].getBest().getDate().getMonth()+"."+myProgress[i].getBest().getDate().getYear()+")", i, 2);
			myTable.setValueAt(myProgress[i].getWorst().getWeight()+" x "+myProgress[i].getWorst().getReplicates()+" ("+myProgress[i].getWorst().getDate().getDay()
					+"."+myProgress[i].getWorst().getDate().getMonth()+"."+myProgress[i].getWorst().getDate().getYear()+")", i, 3);
			myTable.setValueAt(myProgress[i].getLast().getWeight()+" x "+myProgress[i].getLast().getReplicates() +" ("+myProgress[i].getLast().getDate().getDay()
					+"."+myProgress[i].getLast().getDate().getMonth()+"."+myProgress[i].getLast().getDate().getYear()+")", i, 4);
			// model.addRow(new Object[]{myProgress[0].getStageID(),
			// myProgress[0].getBest().getWeight()+"x"+myProgress[0].getBest().getReplicates(),
			// null, null,null ,null});
		}

		return myTable;
	}
}
