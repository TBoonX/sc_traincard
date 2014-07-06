package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Traingui;




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
		
		JButton btnTagesplan = new JButton("Tagesplan");
		btnTagesplan.setBounds(205, 93, 251, 71);
		panel.add(btnTagesplan);
		btnTagesplan.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
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
		
		btnTagesplan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Traingui.getTraingui().setScreen(new TagesplanView());
				
			}
		});
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Traingui.getTraingui().setScreen(new PasswordCheck(PasswordCheck.SPORTLER));
				
			}
		});
		btnTrainingsfortschritt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Traingui.getTraingui().setScreen(new FortschrittView());
				
			}
		});
		
		btnTrainingsplan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Traingui.getTraingui().setScreen(new TrainingsplanView());
				
			}
		});
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Traingui.getTraingui().setScreen(new PasswordCheck(PasswordCheck.TRAINER));
				
			}
		});
		
	}
}
