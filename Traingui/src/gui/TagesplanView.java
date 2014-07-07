package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import main.Traingui;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;

public class TagesplanView extends JFrame{
	private JTable table;
	private JScrollPane scrollPane;
	private JButton backButton;
	private JTextField txtTag;
	private JComboBox comboBoxDay;
	
	public TagesplanView() {
		getContentPane().setLayout(null);
		getContentPane().setSize(700, 500);
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 684, 462);
		getContentPane().add(panel);
		
		table = new JTable();
		table.setBounds(56, 91, 537, 160);
		//		getContentPane().add(table);
		table.setModel(new DefaultTableModel(
				new Object[][] {
					
				},
				new String[] {
					"Trainingstag", "Muskelgruppe", "\u00DCbung", "S\u00E4tze", "Gewicht", "Wiederholungen", "Phase"
				}
			));
		table.getTableHeader().setReorderingAllowed(false);
			new TableMethods().loadWorkoutplanTable(table, 1);
				table.getColumnModel().getColumn(0).setPreferredWidth(90);
				table.getColumnModel().getColumn(1).setPreferredWidth(130);
				table.getColumnModel().getColumn(4).setPreferredWidth(92);
				panel.setLayout(null);
				
				scrollPane = new JScrollPane(table);
				scrollPane.setBounds(10, 43, 664, 215);
				panel.add(scrollPane);
				
				backButton = new JButton("zur\u00FCck");
				backButton.setBounds(585, 428, 89, 23);
				backButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Traingui.getTraingui().setScreen(new MainView());
						
					}
				});
				panel.add(backButton);
				
				comboBoxDay = new JComboBox();
				String[] comboBoxString = new String[new TableMethods().getNumberOfDays()];
				for(int i=0;i< new TableMethods().getNumberOfDays();i++){
					comboBoxString[i] = ""+(i+1);
				}
				comboBoxDay.setModel(new DefaultComboBoxModel(comboBoxString));
				comboBoxDay.setBounds(585, 269, 89, 43);
				comboBoxDay.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						new TableMethods().loadWorkoutplanTable(table, Integer.valueOf((String)comboBoxDay.getSelectedItem()));
					}
				});
				
				panel.add(comboBoxDay);
				
				txtTag = new JTextField();
				txtTag.setEditable(false);
				txtTag.setHorizontalAlignment(SwingConstants.CENTER);
				txtTag.setFont(new Font("Tahoma", Font.PLAIN, 16));
				txtTag.setText("Tag");
				txtTag.setBounds(432, 269, 143, 43);
				panel.add(txtTag);
				txtTag.setColumns(10);
				
				
	}
}
