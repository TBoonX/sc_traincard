package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.ByteBuffer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import main.Traingui;
import model.Progress;
import model.ProgressElement;
import model.Set;

public class SportlerView extends JFrame {
	private JTable table;
	private JScrollPane scrollPane;
	private JButton backButton;
	private JButton btnNewButton;

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
				{ null, "Nackendr\u00FCcken", "3", "", "20kg", "", "15-12-10",
						"", Boolean.FALSE },
				{ null, "Latziehen", "3", "", "25kg", "", "15-12-10", "",
						Boolean.FALSE },
				{ null, "Rudern mit weitem Griff", "3", "", "25kg", "",
						"15-12-10", "", Boolean.FALSE },
				{ null, "Crosstrainer", "1", "", "-", "", "60min", "",
						Boolean.FALSE }, }, new String[] { "Ger\u00E4t",
				"Muskelgruppe", "S\u00E4tze", "heute", "Gewicht", "heute",
				"Wiederholungen", "heute", "keine Abweichung" }) {
			Class[] columnTypes = new Class[] { Object.class, String.class,
					String.class, String.class, String.class, String.class,
					String.class, String.class, Boolean.class };

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
		table.getTableHeader().setReorderingAllowed(false);
		table.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
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
						table.setValueAt("", e.getFirstRow(), i * 2);
					}

				}

			}

		});
		panel.setLayout(null);
		table.setValueAt("test", 0, 0);
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

		Progress myProgress[] = getProgressArray();
		if (myProgress == null) {
			createNewProgress();
			return;
		}
		int weightInt = 0, replicatesInt = 0;
		byte[] newWeight = null, newReplicates = null;
		for (int i = 0; i < table.getRowCount(); i++) {
			Set[] sets = new Set[(int) table.getValueAt(i, 2)];

			for (int j = 0; j < sets.length; j++) {
				byte[] number = ByteBuffer.allocate(4).putInt(j).array();
				weightInt = 0;
				replicatesInt = 0;

				String replicatesText = (String) table.getValueAt(i, 5);
				String weightText = (String) table.getValueAt(i, 4);
				if (sets.length > 1) {
					String[] weightNumbers = weightText.split("-");
					String[] replicatesNumbers = replicatesText.split("-");
					weightInt = weightInt + Integer.valueOf(weightNumbers[j]);
					replicatesInt = replicatesInt
							+ Integer.valueOf(replicatesNumbers[j]);
				} else {
					weightInt = Integer
							.valueOf((String) table.getValueAt(i, 4));
					replicatesInt = Integer.valueOf((String) table.getValueAt(
							i, 5));
				}
				newWeight = ByteBuffer.allocate(4).putInt(weightInt/sets.length).array();
				newReplicates = ByteBuffer.allocate(4).putInt(replicatesInt/sets.length)
						.array();
			}
			ProgressElement currProgressElement = new ProgressElement(
					newWeight[3], null);

			for (int k = 0; k < myProgress.length; k++) {
				if (i == (int) myProgress[k].getStageID()) {
					if (myProgress[k].getWorst().getWeight() > currProgressElement
							.getWeight()) {
						myProgress[k].setBest(currProgressElement);
					}
					if (myProgress[k].getBest().getWeight() < currProgressElement
							.getWeight()) {
						myProgress[k].setBest(currProgressElement);
					}
					myProgress[k].setLast(currProgressElement);
					break;
				}

			}

		}
		setProgressArray(myProgress);
	}

	private void createNewProgress() {
		int weightInt = 0, replicatesInt = 0;
		byte[] newWeight = null, newReplicates = null;

		Progress myProgress[] = new Progress[table.getRowCount()];
		for (int i = 0; i < table.getRowCount(); i++) {
			Set[] sets = new Set[(int) table.getValueAt(i, 2)];

			for (int j = 0; j < sets.length; j++) {
				byte[] number = ByteBuffer.allocate(4).putInt(j).array();
				weightInt = 0;
				replicatesInt = 0;

				String replicatesText = (String) table.getValueAt(i, 5);
				String weightText = (String) table.getValueAt(i, 4);
				if (sets.length > 1) {
					String[] weightNumbers = weightText.split("-");
					String[] replicatesNumbers = replicatesText.split("-");
					weightInt = weightInt + Integer.valueOf(weightNumbers[j]);
					replicatesInt = replicatesInt
							+ Integer.valueOf(replicatesNumbers[j]);
				} else {
					weightInt = Integer
							.valueOf((String) table.getValueAt(i, 4));
					replicatesInt = Integer.valueOf((String) table.getValueAt(
							i, 5));
				}
				newWeight = ByteBuffer.allocate(4).putInt(weightInt/sets.length).array();
				newReplicates = ByteBuffer.allocate(4).putInt(replicatesInt/sets.length)
						.array();
			}
			ProgressElement currProgressElement = new ProgressElement(
					newWeight[3], null);

			myProgress[i].setBest(currProgressElement);

			myProgress[i].setBest(currProgressElement);

			myProgress[i].setLast(currProgressElement);

		}
		setProgressArray(myProgress);
	}
	
}
