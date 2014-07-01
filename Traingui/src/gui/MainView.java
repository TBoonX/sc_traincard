package gui;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.Font;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import java.awt.Dimension;
import javax.swing.JPanel;

public class MainView extends JFrame{
	public MainView() {
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 684, 462);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnTrainingsplan = new JButton("Trainingsplan");
		btnTrainingsplan.setBounds(205, 11, 251, 71);
		panel.add(btnTrainingsplan);
		btnTrainingsplan.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JButton btnNewButton = new JButton("Tagesplan");
		btnNewButton.setBounds(205, 93, 251, 71);
		panel.add(btnNewButton);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JButton btnTrainingsfortschritt = new JButton("Trainingsfortschritt");
		btnTrainingsfortschritt.setBounds(205, 175, 251, 71);
		panel.add(btnTrainingsfortschritt);
		btnTrainingsfortschritt.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JButton btnNewButton_1 = new JButton("Login: Sportler");
		btnNewButton_1.setBounds(64, 339, 206, 71);
		panel.add(btnNewButton_1);
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JButton btnLogin = new JButton("Login: Trainer");
		btnLogin.setBounds(413, 339, 206, 71);
		panel.add(btnLogin);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
	}
}
