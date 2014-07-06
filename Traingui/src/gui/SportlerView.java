package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import main.Traingui;
import javax.swing.ListSelectionModel;

public class SportlerView extends JFrame {
	private JTable table;
	private JScrollPane scrollPane;
	private JButton backButton;
	private JButton btnNewButton;

	public SportlerView() {
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 684, 462);
		getContentPane().add(panel);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		table.setBounds(56, 91, 537, 160);
		// getContentPane().add(table);
		table.setModel(new DefaultTableModel(
				new Object[][] {
						{ "Bankdr\u00FCcken", "3", "", "30kg", "", "15-12-10",
								"", Boolean.FALSE },
						{ "Nackendr\u00FCcken", "3", "", "20kg", "",
								"15-12-10", "", Boolean.FALSE },
						{ "Latziehen", "3", "", "25kg", "", "15-12-10", "",
								Boolean.FALSE },
						{ "Rudern mit weitem Griff", "3", "", "25kg", "",
								"15-12-10", "", Boolean.FALSE },
						{ "Crosstrainer", "1", "", "-", "", "60min", "",
								Boolean.FALSE }, },
				new String[] { "\u00DCbung", "S\u00E4tze", "heute", "Gewicht",
						"heute", "Wiederholungen", "heute", "keine Abweichung" }) {
			Class[] columnTypes = new Class[] { String.class, String.class,
					String.class, String.class, String.class, String.class,
					String.class, Boolean.class };

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
		table.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				if ((table.getValueAt(e.getFirstRow(), e.getColumn())
						.equals(Boolean.TRUE))) {
					for (int i = 1; i < 4; i++) {
						table.setValueAt(
								table.getValueAt(e.getFirstRow(), (i + i - 1)),
								e.getFirstRow(), i * 2);
					}

				}
				if ((table.getValueAt(e.getFirstRow(), e.getColumn())
						.equals(Boolean.FALSE))) {
					for (int i = 1; i < 4; i++) {
						table.setValueAt("", e.getFirstRow(), i * 2);
					}

				}

			}

		});
		panel.setLayout(null);
		table.setValueAt("test", 0, 0);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 43, 664, 228);
		panel.add(scrollPane);

		backButton = new JButton("zur\u00FCck");
		backButton.setBounds(585, 428, 89, 23);
		panel.add(backButton);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Traingui.getTraingui().setScreen(new MainView());

			}
		});
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
