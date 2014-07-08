package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import main.Traingui;

public class PasswordCheck extends JFrame{
	
	private JTextField txtPassword;
	private JTextField txtBenutzer;
	private JTextField textField;
	private JPasswordField passwordField;
	private JTextPane textInfo;
	public final static String SPORTLER = "Sportler";
	public final static String TRAINER = "Trainer";
	
	public PasswordCheck(final String benutzer) {
		passwordField = new JPasswordField(10);
		passwordField.setLocation(327, 179);
		passwordField.setSize(200, 38);
		getContentPane().setLayout(null);
		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLogin.setSize(410, 52);
		btnLogin.setLocation(117, 260);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(checkPassword(passwordField.getText())){
					textInfo.setEnabled(false);
					if(benutzer.equals(SPORTLER)){
						
						Traingui.getTraingui().setScreen(new SportlerView());
					}
					if(benutzer.equals(TRAINER)){
						Traingui.getTraingui().setScreen(new TrainerView());
					}
				}
				else{
					textInfo.setText("falsches Passwort");
				}
				

			}

			
		});
		
		getContentPane().add(btnLogin);
		getContentPane().add(passwordField);
		
		txtPassword = new JTextField();
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtPassword.setHorizontalAlignment(SwingConstants.CENTER);
		txtPassword.setEditable(false);
		txtPassword.setText("Password:");
		txtPassword.setBounds(117, 179, 200, 38);
		getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		
		txtBenutzer = new JTextField();
		txtBenutzer.setEditable(false);
		txtBenutzer.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtBenutzer.setHorizontalAlignment(SwingConstants.CENTER);
		txtBenutzer.setText("Benutzer:");
		txtBenutzer.setBounds(117, 130, 200, 38);
		getContentPane().add(txtBenutzer);
		txtBenutzer.setColumns(10);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField.setEditable(false);
		textField.setText(benutzer);
		textField.setBounds(327, 130, 200, 38);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton backButton = new JButton("zur\u00FCck");
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		backButton.setBounds(550, 421, 124, 30);
		getContentPane().add(backButton);
		
		textInfo = new JTextPane();
		textInfo.setBackground(SystemColor.control);
		textInfo.setEnabled(true);
		textInfo.setEditable(false);
		textInfo.setBounds(117, 228, 410, 20);
		SimpleAttributeSet attribs = new SimpleAttributeSet();  
		StyleConstants.setAlignment(attribs , StyleConstants.ALIGN_CENTER);  
		StyleConstants.setForeground(attribs, Color.RED);
		textInfo.setParagraphAttributes(attribs,true);
		getContentPane().add(textInfo);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Traingui.getTraingui().setScreen(new MainView());

			}
		});
	}
	
	private boolean checkPassword(final String text) {
		// TODO Auto-generated method stub
		System.out.println(text);
		System.out.println(text.equals("1234"));
		if(text.equals("1234")){
			return true;
		}
		
		return false;
	}
}
