package fr.thomas.proto0.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import fr.thomas.proto0.controller.GameController;

public class MultiplayerGameHub extends JFrame {

	private static final long serialVersionUID = 1L;
	private GameController controller;
	private int gameID;
	private JLabel gameNamePlayerCountLabel;
	private JLabel waitingPlayersLabel;

	private Timer serverSyncTimer;
	private boolean syncServerData;

	public MultiplayerGameHub(GameController controller) {
		this.controller = controller;
		syncServerData = false;
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		gameNamePlayerCountLabel = new JLabel("<placeholder>");
		gameNamePlayerCountLabel.setBounds(0, 0, 442, 17);
		getContentPane().add(gameNamePlayerCountLabel);

		waitingPlayersLabel = new JLabel("En attente de joueurs... N joueurs manquants.");
		waitingPlayersLabel.setBounds(0, 17, 442, 224);
		getContentPane().add(waitingPlayersLabel);

		serverSyncTimer = new Timer();
		// Update server info every .8 secs
		// TODO : Find a better way to do that
		serverSyncTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				if (syncServerData) {
					controller.updateMultiplayerHubServerInfos(gameID);
				}

			}
		}, 0, 800);

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

	public void startSync() {
		syncServerData = true;
	}

	public void stopSync() {
		syncServerData = false;
	}

	public void updateServerInfos(String name, int maxPlayer, ArrayList<Integer> infos, int playerRequierment) {
		gameNamePlayerCountLabel.setText(name + " | Online players : " + infos.size() + " / " + maxPlayer);

		if (infos.size() >= playerRequierment) {
			//waitingPlayersLabel.setText("La partie commence dans X secondes.");
		} else {
			waitingPlayersLabel
					.setText("En attente de joueurs... " + (playerRequierment - infos.size()) + " joueurs manquants.");
		}

		// Handle timer when conditions are met

		revalidate();
		repaint();
	}

	public void setGameID(int id) {
		this.gameID = id;
	}

	public void updateTimeLeft(int time) {
		if (time > 0)
			waitingPlayersLabel.setText("La partie commence dans " + time + " secondes.");
		else {
			waitingPlayersLabel.setText("La partie va commencer !");
			controller.startOnlineGame(gameID); 
			serverSyncTimer.cancel();
		}
	}
}
