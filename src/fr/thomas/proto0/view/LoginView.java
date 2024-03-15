package fr.thomas.proto0.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import fr.thomas.proto0.controller.GameController;

public class LoginView extends JFrame{
	
	private GameController controller;
	
	public LoginView(GameController controller) {
		setResizable(false);
		setSize(400, 300);
		getContentPane().setLayout(null);
		this.controller = controller;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
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
		
		passwordTextfield = new JPasswordField();
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
		btnLogin.setBounds(226, 133, 105, 27);
		getContentPane().add(btnLogin);
		
		final JButton btnChangePassword = new JButton("Changer MDP");
		btnChangePassword.setLocation(86, 131);
		btnChangePassword.setSize(120, 30);
		btnChangePassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				controller.submitPasswordChange(usernameTextfield.getText(), passwordTextfield.getText());
			}
		});
		getContentPane().add(btnChangePassword);
	}
	
	private static final long serialVersionUID = -2228161448011267735L;
	private JTextField usernameTextfield;
	private JTextField passwordTextfield;
}
