package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.Traingui;
import model.Progress;

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
						{"", null, "", null, null},
					},
					new String[] {
						"Ger\u00E4t", "Muskelgruppe", "best", "worst", "last"
					}
				));
				table.getColumnModel().getColumn(0).setPreferredWidth(84);
				table.getColumnModel().getColumn(1).setPreferredWidth(108);
				table.getColumnModel().getColumn(2).setPreferredWidth(112);
				table.getColumnModel().getColumn(3).setPreferredWidth(122);
				table.getColumnModel().getColumn(4).setPreferredWidth(122);
				table.getTableHeader().setReorderingAllowed(false);
				panel.setLayout(null);
				
				new TableMethods().clearTable(table);
				table = new TableMethods().loadWorkoutplanTableFortschritt(table);
				
				scrollPane = new JScrollPane(table);
				scrollPane.setBounds(10, 11, 664, 251);
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
