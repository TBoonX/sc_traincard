package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultRowSorter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;

import main.Traingui;

public class TrainingsplanView extends JFrame{
	private JTable table;
	private JScrollPane scrollPane;
	private JTextArea txtTag;
	private JButton backButton;
	private DefaultRowSorter sorter;
	
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
					{new Integer(1), new Integer(1), "Bankdr\u00FCcken", "3", "30-20-10", "15-15-15", null},
				},
				new String[] {
					"Trainingstag", "Ger\u00E4t", "Muskelgruppe", "S\u00E4tze", "Gewicht", "Wiederholungen", "Phase"
				}
			) {
				Class[] columnTypes = new Class[] {
					Integer.class, Integer.class, Object.class, Integer.class, Object.class, Object.class, Object.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});
			table.getColumnModel().getColumn(1).setPreferredWidth(85);
			table.getColumnModel().getColumn(2).setPreferredWidth(62);
			table.getColumnModel().getColumn(4).setPreferredWidth(92);
			table.getTableHeader().setReorderingAllowed(false);
			
			
//			sorter = new TableRowSorter(table.getModel());
//			table.setRowSorter(sorter);
			table.setAutoCreateRowSorter(true);
			sorter = ((DefaultRowSorter)table.getRowSorter());
			ArrayList list = new ArrayList();
			list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING) );
			sorter.setSortKeys(list);
			sorter.sort();
				table.getTableHeader().setReorderingAllowed(false);
				
				table.getColumnModel().getColumn(1).setPreferredWidth(90);
				table.getColumnModel().getColumn(2).setPreferredWidth(130);
				table.getColumnModel().getColumn(5).setPreferredWidth(92);
				panel.setLayout(null);
				
				scrollPane = new JScrollPane(table);
				scrollPane.setBounds(52, 43, 585, 215);
				panel.add(scrollPane);
				
				
				new TableMethods().loadWorkoutplanTable(table);
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
