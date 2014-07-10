package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import comm.CardInterface;

import main.Traingui;
import model.MyDate;
import model.Progress;
import model.ProgressElement;
import model.Set;

public class SportlerView extends JFrame {
	private JTable table;
	private JScrollPane scrollPane;
	private JButton backButton;
	private JButton btnNewButton;
	private JComboBox comboBoxDay;

	public SportlerView() {
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 684, 462);
		getContentPane().add(panel);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		table.setBounds(56, 91, 537, 160);
		// getContentPane().add(table);
		table.setModel(new DefaultTableModel(new Object[][] {
				{ null, "Bankdr\u00FCcken", "3", "", "30kg", "", "15-12-10",
						"", Boolean.FALSE },
				 }, new String[] { "Ger\u00E4t",
				"Muskelgruppe", "S\u00E4tze", "heute", "Gewicht", "heute",
				"Wiederholungen", "heute", "keine Abweichung", "Stage ID" }) {
			Class[] columnTypes = new Class[] { Object.class, String.class,
					String.class, String.class, String.class, String.class,
					String.class, String.class, Boolean.class, Integer.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(46);
		table.getColumnModel().getColumn(3).setPreferredWidth(46);
		table.getColumnModel().getColumn(5).setPreferredWidth(39);
		table.getColumnModel().getColumn(6).setPreferredWidth(92);
		table.getColumnModel().getColumn(7).setPreferredWidth(50);
		table.getColumnModel().getColumn(8).setPreferredWidth(109);
		table.getColumnModel().getColumn(9).setMinWidth(0);
		table.getColumnModel().getColumn(9).setMaxWidth(0);
		table.getColumnModel().getColumn(9).setWidth(0);
		table.getTableHeader().setReorderingAllowed(false);

		panel.setLayout(null);
		table = new TableMethods().loadWorkoutplanTableSportler(table, 1);
		table.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(final TableModelEvent e) {
				if (e.getColumn() < 8) {
					return;
				}
				System.out.println(e.toString());
				System.out.println();
				if ((table.getValueAt(e.getFirstRow(), e.getColumn())
						.equals(Boolean.TRUE))) {
					for (int i = 1; i < 4; i++) {
						table.setValueAt(
								table.getValueAt(e.getFirstRow(), (i * 2)),
								e.getFirstRow(), i + i + 1);
					}

				}
				if ((table.getValueAt(e.getFirstRow(), e.getColumn())
						.equals(Boolean.FALSE))) {
					for (int i = 1; i < 4; i++) {
						table.setValueAt("", e.getFirstRow(), i + i + 1);
					}

				}

			}

		});
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 43, 664, 228);
		panel.add(scrollPane);

		backButton = new JButton("zur\u00FCck");
		backButton.setBounds(585, 428, 89, 23);
		panel.add(backButton);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Traingui.getTraingui().setScreen(new MainView());

			}
		});

		comboBoxDay = new JComboBox();
		String[] comboBoxString = new String[new TableMethods()
				.getNumberOfDays()];
		for (int i = 0; i < new TableMethods().getNumberOfDays(); i++) {
			comboBoxString[i] = "" + (i + 1);
		}
		comboBoxDay.setModel(new DefaultComboBoxModel(comboBoxString));
		comboBoxDay.setBounds(585, 269, 89, 43);
		comboBoxDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new TableMethods().loadWorkoutplanTableSportler(table,
						Integer.valueOf((String) comboBoxDay.getSelectedItem()));
			}
		});

		panel.add(comboBoxDay);

		btnNewButton = new JButton("Trainingsdaten speichern");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveProgress();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setBounds(212, 292, 265, 58);
		panel.add(btnNewButton);
	}

	private void saveProgress() {
		
		
		
		
		
		byte currStageID = 0;
//		Progress myProgress[] = Traingui.getTraingui().getMyProgress();
		Progress myProgress[] = CardInterface.getProgress();
		
		int weightInt = 0, replicatesInt = 0;
		byte[] newWeight = null, newReplicates = null;
		for (int i = 0; i < table.getRowCount(); i++) {
			byte StageID[] = ByteBuffer.allocate(4)
					.putInt((int) table.getModel().getValueAt(i, 9)).array();
//			myProgress[(int) table.getModel().getValueAt(i, 9)].getBest() == null
			if (myProgress[(int)StageID[3]] == null) {
				System.out.println("neues Element");
				myProgress=createNewProgress(myProgress,StageID[3], i);
				continue;
			}
			Set[] sets = new Set[(int) table.getValueAt(i, 2)];

			for (int j = 0; j < sets.length; j++) {
				byte[] number = ByteBuffer.allocate(4).putInt(j).array();
				weightInt = 0;
				replicatesInt = 0;

				String replicatesText = (String) table.getValueAt(i, 7);
				String weightText = (String) table.getValueAt(i, 5);
				if (sets.length > 1) {
					String[] weightNumbers = weightText.split("-");
					String[] replicatesNumbers = replicatesText.split("-");
					weightInt = weightInt + Integer.valueOf(weightNumbers[j]);
					replicatesInt = replicatesInt
							+ Integer.valueOf(replicatesNumbers[j]);
				} else {
					weightInt = Integer
							.valueOf((String) table.getValueAt(i, 5));
					replicatesInt = Integer.valueOf((String) table.getValueAt(
							i, 7));
				}
				newWeight = ByteBuffer.allocate(4)
						.putInt(weightInt / sets.length).array();
				newReplicates = ByteBuffer.allocate(4)
						.putInt(replicatesInt / sets.length).array();
			}
			Date today = new Date();
			byte[] day = ByteBuffer.allocate(4).putInt(today.getDate()).array();
			byte[] month = ByteBuffer.allocate(4).putInt(today.getMonth() +1).array();
			byte[] year = ByteBuffer.allocate(4).putInt(today.getYear()-100).array();
			ProgressElement currProgressElement = new ProgressElement(
					newWeight[3], newReplicates[3], new MyDate(year[3], month[3], day[3]));

			for (int k = 0; k < myProgress.length; k++) {
				if ((int)StageID[3] == (int)myProgress[k].getStageID()) {
//					if (myProgress[k].getWorst().getWeight() > currProgressElement
//							.getWeight()) {
//						myProgress[k].setWorst(currProgressElement);
//					}
//					if (myProgress[k].getBest().getWeight() < currProgressElement
//							.getWeight()) {
//						myProgress[k].setBest(currProgressElement);
//					}
//					
					if ((myProgress[k].getWorst().getWeight() * myProgress[k].getWorst().getReplicates() ) >
					(currProgressElement.getWeight() * currProgressElement.getReplicates())) {
						myProgress[k].setWorst(currProgressElement);
					}
					if ((myProgress[k].getBest().getWeight() * myProgress[k].getBest().getReplicates() ) <
					(currProgressElement.getWeight() * currProgressElement.getReplicates())) {
						myProgress[k].setBest(currProgressElement);
					}
					myProgress[k].setLast(currProgressElement);
					break;
				}

			}

		}
		CardInterface.saveProgress(myProgress);
//		Traingui.getTraingui().setMyProgress(myProgress);
	}

	private Progress[] createNewProgress(Progress myProgress[],byte stageID, int row) {
		int weightInt = 0, replicatesInt = 0;
		byte[] newWeight = null, newReplicates = null;

		
			Set[] sets = new Set[(int) table.getValueAt(row, 3)];
			
			for (int j = 0; j < sets.length; j++) {
				byte[] number = ByteBuffer.allocate(4).putInt(j).array();
				weightInt = 0;
				replicatesInt = 0;

				String replicatesText = (String) table.getValueAt(row, 7);
				String weightText = (String) table.getValueAt(row, 5);
				if (sets.length > 1) {
					String[] weightNumbers = weightText.split("-");
					String[] replicatesNumbers = replicatesText.split("-");
					weightInt = weightInt + Integer.valueOf(weightNumbers[j]);
					replicatesInt = replicatesInt
							+ Integer.valueOf(replicatesNumbers[j]);
				} else {
					weightInt = Integer
							.valueOf((String) table.getValueAt(row, 5));
					replicatesInt = Integer.valueOf((String) table.getValueAt(
							row, 7));
				}
				newWeight = ByteBuffer.allocate(4)
						.putInt(weightInt / sets.length).array();
				newReplicates = ByteBuffer.allocate(4)
						.putInt(replicatesInt / sets.length).array();
			}
			Date today = new Date();
			byte[] day = ByteBuffer.allocate(4).putInt(today.getDate()).array();
			byte[] month = ByteBuffer.allocate(4).putInt(today.getMonth() +1).array();
			byte[] year = ByteBuffer.allocate(4).putInt(today.getYear()-100).array();
			
			ProgressElement currProgressElement = new ProgressElement(
					newWeight[3], newReplicates[3], new MyDate(year[3], month[3], day[3]));
			myProgress[(int)stageID] = new Progress(stageID, currProgressElement,
					currProgressElement, currProgressElement);

		
		return myProgress;
	}

}
