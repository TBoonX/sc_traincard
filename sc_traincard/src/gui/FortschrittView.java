package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FortschrittView extends JFrame{
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnZurck;
	public FortschrittView() {
getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 684, 462);
		getContentPane().add(panel);
		
		table = new JTable();
		table.setBounds(56, 91, 537, 160);
		//		getContentPane().add(table);
				table.setModel(new DefaultTableModel(
					new Object[][] {
						{"Bankdr\u00FCcken", "30kg", null, null},
						{"Nackendr\u00FCcken", "20kg", null, null},
						{"Latziehen", "25kg", null, null},
						{"Rudern mit weitem Griff", "25kg", null, null},
						{"Crosstrainer", "-", null, null},
						{null, null, null, null},
						{"Kniebeuge", "-", null, null},
						{"SZ-Curls", "15kg", null, null},
						{"enges Bankdr\u00FCcken", "15kg", null, null},
						{"Situps", "-", null, null},
						{"Crosstrainer", "-", null, null},
						{null, null, null, null},
						{"K\u00F6rpergewicht", "80kg", null, null},
					},
					new String[] {
						"\u00DCbung", "Anfangswert", "Durchschnittswert", "Bestwert"
					}
				));
				table.getColumnModel().getColumn(0).setPreferredWidth(130);
				table.getColumnModel().getColumn(1).setPreferredWidth(112);
				table.getColumnModel().getColumn(2).setPreferredWidth(122);
				table.getColumnModel().getColumn(3).setPreferredWidth(122);
				panel.setLayout(null);
				
				scrollPane = new JScrollPane(table);
				scrollPane.setBounds(10, 11, 664, 362);
				panel.add(scrollPane);
				btnZurck = new JButton("zur\u00FCck");
				btnZurck.setBounds(585, 428, 89, 23);
				panel.add(btnZurck);
	}

}
