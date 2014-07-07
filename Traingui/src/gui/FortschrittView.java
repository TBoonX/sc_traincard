package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.Traingui;

public class FortschrittView extends JFrame{
	private JTable table;
	private JScrollPane scrollPane;
	private JButton backButton;
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
						{"Bankdr\u00FCcken", "30kg", null, null, null, null, null},
						{"Nackendr\u00FCcken", "20kg", null, null, null, null, null},
						{"Latziehen", "25kg", null, null, null, null, null},
						{"Rudern mit weitem Griff", "25kg", null, null, null, null, null},
						{"Crosstrainer", "-", null, null, null, null, null},
						{null, null, null, null, null, null, null},
						{"Kniebeuge", "-", null, null, null, null, null},
						{"SZ-Curls", "15kg", null, null, null, null, null},
						{"enges Bankdr\u00FCcken", "15kg", null, null, null, null, null},
						{"Situps", "-", null, null, null, null, null},
						{"Crosstrainer", "-", null, null, null, null, null},
						{null, null, null, null, null, null, null},
						{"K\u00F6rpergewicht", "80kg", null, null, null, null, null},
					},
					new String[] {
						"Ger\u00E4t", "best-weight", "best-repeats", "worst-weight", "worst-repeats", "last-weight", "last-repeats"
					}
				));
				table.getColumnModel().getColumn(0).setPreferredWidth(130);
				table.getColumnModel().getColumn(1).setPreferredWidth(112);
				table.getColumnModel().getColumn(2).setPreferredWidth(122);
				table.getColumnModel().getColumn(3).setPreferredWidth(122);
				table.getTableHeader().setReorderingAllowed(false);
				panel.setLayout(null);
				
				scrollPane = new JScrollPane(table);
				scrollPane.setBounds(10, 11, 664, 362);
				panel.add(scrollPane);
				backButton = new JButton("zur\u00FCck");
				backButton.setBounds(585, 428, 89, 23);
				backButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Traingui.getTraingui().setScreen(new MainView());
						
					}
				});
				panel.add(backButton);
	}

}
