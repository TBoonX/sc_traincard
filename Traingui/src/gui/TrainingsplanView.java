package gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ComponentColorModel;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import main.Traingui;

public class TrainingsplanView extends JFrame{
	private JTable table;
	private JScrollPane scrollPane;
	private JTextArea txtTag;
	private JButton backButton;
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
						
					},
					new String[] {
						"Trainingstag", "Muskelgruppe", "\u00DCbung", "S\u00E4tze", "Gewicht", "Wiederholungen", "Phase"
					}
				));
				table.getTableHeader().setReorderingAllowed(false);
				new TableMethods().loadWorkoutplanTable(table);
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
				
				backButton = new JButton("zur\u00FCck");
				backButton.setBounds(548, 428, 89, 23);
				panel.add(backButton);
				backButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Traingui.getTraingui().setScreen(new MainView());
						
					}
				});
	}
	
	
}
