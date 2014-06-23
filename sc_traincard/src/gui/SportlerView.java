package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SportlerView extends JFrame{
	private JTable table;
	private JScrollPane scrollPane;
	private JButton button;
	private JButton btnNewButton;
	public SportlerView() {
getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 684, 462);
		getContentPane().add(panel);
		
		table = new JTable();
		table.setBounds(56, 91, 537, 160);
		//		getContentPane().add(table);
				table.setModel(new DefaultTableModel(
					new Object[][] {
						{"Bankdr\u00FCcken", "3", null, "30kg", null, "15-12-10", null, null},
						{"Nackendr\u00FCcken", "3", null, "20kg", null, "15-12-10", null, null},
						{"Latziehen", "3", null, "25kg", null, "15-12-10", null, null},
						{"Rudern mit weitem Griff", "3", null, "25kg", null, "15-12-10", null, null},
						{"Crosstrainer", "1", null, "-", null, "60min", null, null},
					},
					new String[] {
						"\u00DCbung", "S\u00E4tze", "heute", "Gewicht", "heute", "Wiederholungen", "heute", "keine Abweichung"
					}
				) {
					Class[] columnTypes = new Class[] {
						Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Boolean.class
					};
					public Class getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}
				});
				table.getColumnModel().getColumn(0).setPreferredWidth(130);
				table.getColumnModel().getColumn(1).setPreferredWidth(46);
				table.getColumnModel().getColumn(2).setPreferredWidth(46);
				table.getColumnModel().getColumn(4).setPreferredWidth(39);
				table.getColumnModel().getColumn(5).setPreferredWidth(92);
				table.getColumnModel().getColumn(6).setPreferredWidth(50);
				table.getColumnModel().getColumn(7).setPreferredWidth(109);
				panel.setLayout(null);
				
				scrollPane = new JScrollPane(table);
				scrollPane.setBounds(10, 43, 664, 228);
				panel.add(scrollPane);
				
				button = new JButton("zur\u00FCck");
				button.setBounds(585, 428, 89, 23);
				panel.add(button);
				
				btnNewButton = new JButton("Trainingsdaten speichern");
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					}
				});
				btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
				btnNewButton.setBounds(212, 292, 265, 58);
				panel.add(btnNewButton);
	}

}
