package gui;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

public class TagesplanView extends JFrame{
	private JTable table;
	private JScrollPane scrollPane;
	private JButton button;
	public TagesplanView() {
getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 684, 462);
		getContentPane().add(panel);
		
		table = new JTable();
		table.setBounds(56, 91, 537, 160);
		//		getContentPane().add(table);
				table.setModel(new DefaultTableModel(
					new Object[][] {
						{"Brust", "Bankdr\u00FCcken", "3", "30kg", "15-12-10"},
						{"Schulter", "Nackendr\u00FCcken", "3", "20kg", "15-12-10"},
						{"Latissimus", "Latziehen", "3", "25kg", "15-12-10"},
						{"mittlerer R\u00FCcken", "Rudern mit weitem Griff", "3", "25kg", "15-12-10"},
						{"alle", "Crosstrainer", "1", "-", "60min"},
					},
					new String[] {
						"Muskelgruppe", "\u00DCbung", "S\u00E4tze", "Gewicht", "Wiederholungen"
					}
				));
				table.getColumnModel().getColumn(0).setPreferredWidth(90);
				table.getColumnModel().getColumn(1).setPreferredWidth(130);
				table.getColumnModel().getColumn(4).setPreferredWidth(92);
				panel.setLayout(null);
				
				scrollPane = new JScrollPane(table);
				scrollPane.setBounds(52, 43, 585, 215);
				panel.add(scrollPane);
				
				button = new JButton("zur\u00FCck");
				button.setBounds(548, 428, 89, 23);
				panel.add(button);
				
				
	}
	
	
}
