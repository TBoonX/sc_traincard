package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.ByteBuffer;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import main.Constants;
import main.Traingui;
import model.Date;
import model.Set;
import model.Stage;
import model.Workoutplan;

public class TrainerView extends JFrame{
	private JTable table;
	private JScrollPane scrollPane;
	private JButton backButton;
	private JButton btnNewButton;
	private JButton btnNewStage;
	private JTextField txtStartdatum;
	private JTextField txtEnddatum;
	private JComboBox comboBoxMonthStart;
	private JComboBox comboBoxDayEnd;
	private JComboBox comboBoxMonthEnd;
	private JComboBox comboBoxYearStart;
	private JComboBox comboBoxYearEnd;
	private JComboBox comboBoxDayStart;
	private TableRowSorter sorter;
	
	
	public TrainerView() {
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
		TableColumn sportColumn = table.getColumnModel().getColumn(6);
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("warm up");
		comboBox.addItem("training");
		comboBox.addItem("cooldown");
		sportColumn.setCellEditor(new DefaultCellEditor(comboBox));
		
		sorter = new TableRowSorter(table.getModel());
		table.setRowSorter(sorter);
		sorter.setSortsOnUpdates(true);
		table.setAutoCreateRowSorter(true);
		
		new TableMethods().loadWorkoutplanTable(table);
		
				panel.setLayout(null);
				
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
				btnNewButton = new JButton("Trainingsplan speichern");
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						saveWorkoutplan();
					}

					
				});
				btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
				btnNewButton.setBounds(300, 393, 265, 58);
				panel.add(btnNewButton);
				
				JButton button_1 = new JButton("neuer Trainingsplan");
				button_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						new TableMethods().clearTable(table);
					}
				});
				button_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
				button_1.setBounds(10, 393, 265, 58);
				panel.add(button_1);
				
				btnNewStage = new JButton("neue \u00DCbung");
				btnNewStage.setFont(new Font("Tahoma", Font.PLAIN, 20));
				btnNewStage.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						new TableMethods().addEmptyTableRow(table);
					}
				});
				btnNewStage.setBounds(10, 282, 184, 39);
				panel.add(btnNewStage);
				
				txtStartdatum = new JTextField();
				txtStartdatum.setFont(new Font("Tahoma", Font.PLAIN, 14));
				txtStartdatum.setEditable(false);
				txtStartdatum.setHorizontalAlignment(SwingConstants.CENTER);
				txtStartdatum.setText("Startdatum: ");
				txtStartdatum.setBounds(300, 282, 125, 39);
				panel.add(txtStartdatum);
				txtStartdatum.setColumns(10);
				
				txtEnddatum = new JTextField();
				txtEnddatum.setFont(new Font("Tahoma", Font.PLAIN, 14));
				txtEnddatum.setHorizontalAlignment(SwingConstants.CENTER);
				txtEnddatum.setEditable(false);
				txtEnddatum.setText("Enddatum:");
				txtEnddatum.setBounds(300, 332, 125, 39);
				panel.add(txtEnddatum);
				txtEnddatum.setColumns(10);
				
				comboBoxDayStart = new JComboBox();
				comboBoxDayStart.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
				comboBoxDayStart.setBounds(435, 282, 45, 39);
				panel.add(comboBoxDayStart);
				
				comboBoxMonthStart = new JComboBox();
				comboBoxMonthStart.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
				comboBoxMonthStart.setBounds(490, 282, 45, 39);
				panel.add(comboBoxMonthStart);
				
				comboBoxDayEnd = new JComboBox();
				comboBoxDayEnd.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
				comboBoxDayEnd.setBounds(435, 332, 45, 39);
				panel.add(comboBoxDayEnd);
				
				comboBoxMonthEnd = new JComboBox();
				comboBoxMonthEnd.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
				comboBoxMonthEnd.setBounds(490, 332, 45, 39);
				panel.add(comboBoxMonthEnd);
				
				comboBoxYearStart = new JComboBox();
				comboBoxYearStart.setModel(new DefaultComboBoxModel(new String[] {"2014", "2015", "2016", "2017", "2018", "2019", "2020"}));
				comboBoxYearStart.setBounds(545, 282, 56, 39);
				panel.add(comboBoxYearStart);
				
				comboBoxYearEnd = new JComboBox();
				comboBoxYearEnd.setModel(new DefaultComboBoxModel(new String[] {"2014", "2015", "2016", "2017", "2018", "2019", "2020"}));
				comboBoxYearEnd.setBounds(545, 332, 56, 39);
				panel.add(comboBoxYearEnd);
	}
	
	
	
	private void saveWorkoutplan() {
		
		//start date
		byte[] bytesDay = ByteBuffer.allocate(4).putInt(Integer.parseInt((String)comboBoxYearStart.getSelectedItem())).array();
		byte[] bytesMonth = ByteBuffer.allocate(4).putInt(Integer.parseInt((String)comboBoxMonthStart.getSelectedItem())).array();
		byte[] bytesYear = ByteBuffer.allocate(4).putInt(Integer.parseInt((String)comboBoxDayStart.getSelectedItem())).array();
		Date startDate = new Date(bytesDay[3], bytesMonth[3], bytesYear[3]);
		
		//end date
		bytesDay = ByteBuffer.allocate(4).putInt(Integer.parseInt((String)comboBoxYearEnd.getSelectedItem())).array();
		bytesMonth = ByteBuffer.allocate(4).putInt(Integer.parseInt((String)comboBoxMonthEnd.getSelectedItem())).array();
		bytesYear = ByteBuffer.allocate(4).putInt(Integer.parseInt((String)comboBoxDayEnd.getSelectedItem())).array();
		Date endDate = new Date(bytesDay[3], bytesMonth[3], bytesYear[3]);
		
		//warmup stage
		Stage[] warmup = new Stage[0];
		Stage[] train = new Stage[0];
		Stage[] cooldown = new Stage[0];
		for(int i= 0; i< table.getRowCount();i++){
			
			byte[] day = ByteBuffer.allocate(4).putInt((int)table.getValueAt(i, 0)).array();
			byte[] musclegroupID = ByteBuffer.allocate(4).putInt(Constants.WORKOUTNAMES.get((String)table.getValueAt(i, 2))).array();
			byte[] deviceID = ByteBuffer.allocate(4).putInt((int)table.getValueAt(i, 1)).array();
			byte[] stageID = ByteBuffer.allocate(4).putInt(i).array();
						
			Set[] sets = new Set[(int)table.getValueAt(i, 3)];
			
			for(int j=0; j< sets.length ;j++){
				byte[] number = ByteBuffer.allocate(4).putInt(j).array();
				byte[] weight, replicates;
				String replicatesText = (String)table.getValueAt(i, 5);
				String weightText = (String)table.getValueAt(i, 4);
				if(sets.length > 1){
					String[] weightNumbers = weightText.split("-");
					String[] replicatesNumbers = replicatesText.split("-");
					weight = ByteBuffer.allocate(4).putInt(Integer.valueOf(weightNumbers[j])).array();
					replicates = ByteBuffer.allocate(4).putInt(Integer.valueOf(replicatesNumbers[j])).array();
				}
				else{
					weight = ByteBuffer.allocate(4).putInt(Integer.valueOf((String)table.getValueAt(i, 4))).array();
					replicates = ByteBuffer.allocate(4).putInt(Integer.valueOf((String)table.getValueAt(i, 5))).array();
				}
				
				
				
				
				
				sets[j] = new Set(number[3], weight[3], replicates[3]);
				
			}
			
			if(((String)table.getValueAt(i, 6)).equals("warm up")){
				System.out.println(table.getValueAt(i, 6));
				Stage [] tmp = new Stage[(warmup.length+1)];
				for(int k=0;k < warmup.length;k++){
					tmp[k]=warmup[k];
				}
				tmp[warmup.length]=new Stage(day[3], deviceID[3], sets, musclegroupID[3], stageID[3]);
				warmup=tmp;
			}
			if(((String)table.getValueAt(i, 6)).equals("training")){
				System.out.println("train");
				Stage [] tmp = new Stage[(train.length+1)];
				for(int k=0;k < train.length;k++){
					tmp[k]=train[k];
				}
				tmp[train.length]=new Stage(day[3], deviceID[3], sets, musclegroupID[3], stageID[3]);
				train=tmp;
			}
			if(((String)table.getValueAt(i, 6)).equals("cooldown")){
				System.out.println("cool");
				Stage [] tmp = new Stage[(cooldown.length+1)];
				for(int k=0;k < cooldown.length;k++){
					tmp[k]=cooldown[k];
				}
				tmp[cooldown.length]=new Stage(day[3], deviceID[3], sets, musclegroupID[3], stageID[3]);
				cooldown=tmp;
				
			}
		}
		
		Traingui.getTraingui().setWorkoutplan(new Workoutplan(warmup, train, cooldown, startDate, endDate));
		
	}
	
	
	
	
	
}
