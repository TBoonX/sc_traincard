package gui;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

public class TrainingsplanView extends JFrame{
	private JTable table;
	private JScrollPane scrollPane;
	private JTextArea txtTag;
	private JButton btnZurck;
	public TrainingsplanView() {
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 684, 462);
		getContentPane().add(panel);
		
		table = new JTable();
		table.setBounds(56, 91, 537, 160);
		//		getContentPane().add(table);
				table.setModel(new DefaultTableModel(
					new Object[][] {
						{"1", "Brust", "Bankdr\u00FCcken", "3", "30kg", "15-12-10"},
						{"1", "Schulter", "Nackendr\u00FCcken", "3", "20kg", "15-12-10"},
						{"1", "Latissimus", "Latziehen", "3", "25kg", "15-12-10"},
						{"1", "mittlerer R\u00FCcken", "Rudern mit weitem Griff", "3", "25kg", "15-12-10"},
						{"1", "alle", "Crosstrainer", "1", "-", "60min"},
						{null, null, null, null, null, null},
						{"2", "Beine", "Kniebeuge", "3", "-", "15-12-10"},
						{"2", "Bizeps", "SZ-Curls", "3", "15kg", "15-12-10"},
						{"2", "Trizeps", "enges Bankdr\u00FCcken", "3", "15kg", "15-12-10"},
						{"2", "Bauch", "Situps", "3", "-", "15-12-10"},
						{"2", "alle", "Crosstrainer", "1", "-", "60min"},
					},
					new String[] {
						"Trainingstag", "Muskelgruppe", "\u00DCbung", "S\u00E4tze", "Gewicht", "Wiederholungen"
					}
				));
				table.getColumnModel().getColumn(1).setPreferredWidth(90);
				table.getColumnModel().getColumn(2).setPreferredWidth(130);
				table.getColumnModel().getColumn(5).setPreferredWidth(92);
				panel.setLayout(null);
				
				scrollPane = new JScrollPane(table);
				scrollPane.setBounds(52, 43, 585, 215);
				panel.add(scrollPane);
				
				txtTag = new JTextArea();
				txtTag.setText("Tag 1 = erster Trainingstag der Woche \nTag 2 = zweiter Trainingstag der Woche");
				txtTag.setFont(new Font("Tahoma", Font.PLAIN, 16));
				txtTag.setBounds(49, 269, 588, 54);
				panel.add(txtTag);
				txtTag.setColumns(10);
				
				btnZurck = new JButton("zur\u00FCck");
				btnZurck.setBounds(548, 428, 89, 23);
				panel.add(btnZurck);
		
	}
}
