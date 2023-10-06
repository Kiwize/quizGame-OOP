package fr.thomas.proto0.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import fr.thomas.proto0.controller.GameController;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginView extends JFrame{
	
	private GameController controller;
	
	public LoginView(GameController controller) {
		setResizable(false);
		setSize(400, 300);
		getContentPane().setLayout(null);
		this.controller = controller;
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(73, 59, 83, 17);
		getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(73, 88, 83, 17);
		getContentPane().add(lblPassword);
		
		usernameTextfield = new JTextField();
		usernameTextfield.setBounds(159, 57, 114, 21);
		getContentPane().add(usernameTextfield);
		usernameTextfield.setColumns(10);
		
		passwordTextfield = new JTextField();
		passwordTextfield.setBounds(159, 86, 114, 21);
		getContentPane().add(passwordTextfield);
		passwordTextfield.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.playerAuth(usernameTextfield.getText(), passwordTextfield.getText());
			}
		});
		btnLogin.setBounds(159, 133, 105, 27);
		getContentPane().add(btnLogin);
	}
	
	private static final long serialVersionUID = -2228161448011267735L;
	private JTextField usernameTextfield;
	private JTextField passwordTextfield;
}
