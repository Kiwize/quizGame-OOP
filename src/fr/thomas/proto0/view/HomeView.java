package fr.thomas.proto0.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import fr.thomas.proto0.controller.GameController;

public class HomeView extends JFrame {

	private GameController controller;

	private JLabel usernameLabel;
	private JLabel scoreLabel;

	private JButton playButton;
	
	public HomeView(GameController controller) {
		getContentPane().setLayout(null);

		JLabel lblBestScore = new JLabel("Meilleur score : ");
		lblBestScore.setBounds(12, 12, 115, 17);
		getContentPane().add(lblBestScore);

		JLabel lblScore = new JLabel("");
		lblScore.setBounds(106, 12, 111, 17);
		getContentPane().add(lblScore);
		this.scoreLabel = lblScore;

		JLabel lblBienvenue = new JLabel("Bienvenue");
		lblBienvenue.setBounds(27, 72, 85, 17);
		getContentPane().add(lblBienvenue);

		JLabel lblUsername = new JLabel("");
		lblUsername.setBounds(106, 72, 141, 17);
		getContentPane().add(lblUsername);
		this.usernameLabel = lblUsername;

		playButton = new JButton("Jouer");
		playButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.startGame();
			}
		});
		playButton.setBounds(140, 178, 105, 27);
		getContentPane().add(playButton);
		setSize(400, 300);
		this.controller = controller;
	}

	public void updatePlayerData(String name, int score) {
		this.usernameLabel.setText(name);
		this.scoreLabel.setText(Integer.toString(score));
	}

	private static final long serialVersionUID = -912987546852985245L;

	public void setPlayButtonState(boolean state) {
		playButton.setEnabled(state);
	}

}
