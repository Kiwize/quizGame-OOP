package fr.thomas.proto0.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import fr.thomas.proto0.controller.GameController;

public class MultiplayerGameHub extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private GameController controller;
	private int gameID;
	private JLabel gameNamePlayerCountLabel;
	
	public MultiplayerGameHub(GameController controller) {
		this.controller = controller;
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		gameNamePlayerCountLabel = new JLabel("<placeholder>");
		gameNamePlayerCountLabel.setBounds(0, 0, 442, 17);
		getContentPane().add(gameNamePlayerCountLabel);
		
		JLabel waitingPlayersLabel = new JLabel("En attente de joueurs");
		waitingPlayersLabel.setBounds(0, 17, 442, 224);
		getContentPane().add(waitingPlayersLabel);
		
		JButton btnRafrachir = new JButton("Rafraîchir");
		btnRafrachir.setBounds(0, 241, 191, 27);
		btnRafrachir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				controller.updateMultiplayerHubServerInfos(gameID);
			}
		});
		getContentPane().add(btnRafrachir);
		
		JButton quitButton = new JButton("Quitter");
		quitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				controller.quitOnlineGame(gameID);
			}
		});
		quitButton.setBounds(251, 241, 191, 27);
		getContentPane().add(quitButton);
	}
	
	public void updateServerInfos(String name, int maxPlayer, ArrayList<Integer> infos) {
		gameNamePlayerCountLabel.setText(name + " | Online players : " + infos.size() + " / " + maxPlayer);
		revalidate();
		repaint();
	}

	public void setGameID(int id) {
		this.gameID = id;
	}
}
