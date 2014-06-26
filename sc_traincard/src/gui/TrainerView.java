package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TrainerView extends JFrame{
	private JTable table;
	private JScrollPane scrollPane;
	private JButton button;
	private JButton btnNewButton;
	public TrainerView() {
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
				table.getColumnModel().getColumn(1).setPreferredWidth(130);
				table.getColumnModel().getColumn(2).setPreferredWidth(46);
				table.getColumnModel().getColumn(4).setPreferredWidth(92);
				panel.setLayout(null);
				
				scrollPane = new JScrollPane(table);
				scrollPane.setBounds(10, 43, 664, 228);
				panel.add(scrollPane);
				
				button = new JButton("zur\u00FCck");
				button.setBounds(585, 428, 89, 23);
				panel.add(button);
				
				btnNewButton = new JButton("Trainingsplan speichern");
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					}
				});
				btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
				btnNewButton.setBounds(378, 310, 265, 58);
				panel.add(btnNewButton);
				
				JButton button_1 = new JButton("neuer Trainingsplan");
				button_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					}
				});
				button_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
				button_1.setBounds(40, 310, 265, 58);
				panel.add(button_1);
	}
}
