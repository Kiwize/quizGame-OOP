package fr.thomas.proto0.view.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import fr.thomas.proto0.controller.GameController;
import fr.thomas.proto0.net.object.OnlineGameNetObject;

public class MultiplayerListView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel gameList;
	private JLabel selectedGameName;
	private JLabel selectedGameMinPlayer;
	private JLabel selectedGameMaxPlayer;
	private JLabel waitingPlayerCount;
	private JButton joinButton;
	private JTable playerRankTable;
	private GameController controller;
	private JScrollPane scrollPanePlayerRank;
	private ArrayList<OnlineGameNetObject> serverList;
	private JPanel selectedGameInfoPanel;
	private GridBagConstraints gbc;

	private OnlineGameNetObject selectedGame;

	/**
	 * Create the frame.
	 */
	public MultiplayerListView(GameController controller) {
		this.controller = controller;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 590, 336);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));

		// Créer les deux panneaux à séparer
		gameList = new JPanel();
		selectedGameInfoPanel = new JPanel();

		// Créer le JSplitPane
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, gameList, selectedGameInfoPanel);
		gameList.setLayout(new GridBagLayout());

		gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		selectedGameInfoPanel.setLayout(null);

		selectedGameName = new JLabel("<placeholder> | En attente de joueurs");
		selectedGameName.setBounds(12, 5, 257, 17);
		selectedGameInfoPanel.add(selectedGameName);

		selectedGameMinPlayer = new JLabel("Joueurs min : X ");
		selectedGameMinPlayer.setBounds(12, 45, 104, 17);
		selectedGameInfoPanel.add(selectedGameMinPlayer);

		joinButton = new JButton("Rejoindre");
		joinButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				controller.joinOnlineGame(selectedGame, controller.getPlayer());
			}
		});
		joinButton.setBounds(164, 255, 105, 27);
		selectedGameInfoPanel.add(joinButton);

		selectedGameMaxPlayer = new JLabel("Joueurs max : Y");
		selectedGameMaxPlayer.setBounds(12, 74, 104, 17);
		selectedGameInfoPanel.add(selectedGameMaxPlayer);

		waitingPlayerCount = new JLabel("X Joueurs en attente...");
		waitingPlayerCount.setBounds(12, 103, 189, 17);
		selectedGameInfoPanel.add(waitingPlayerCount);

		scrollPanePlayerRank = new JScrollPane();
		scrollPanePlayerRank.setBounds(12, 132, 385, 88);
		playerRankTable = new JTable();

		splitPane.setDividerLocation(150); // Définir la position initiale du diviseur
		splitPane.setDividerSize(10); // Définir la taille du diviseur

		// Ajouter le JSplitPane à la fenêtre principale
		this.getContentPane().add(splitPane);
	}

	public void displayServerList(ArrayList<OnlineGameNetObject> serverList) {
		this.serverList = serverList;
		gameList.removeAll();
		gbc.weighty = 0;

		for (OnlineGameNetObject game : serverList) {
			JLabel gameLabel = new JLabel(game.getName());
			gameLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					displayServerInfo(game);
				}
			});

			gameList.add(gameLabel, gbc);
		}

		gbc.weighty = 1;
		gameList.add(Box.createVerticalGlue(), gbc);
		displayServerInfo(serverList.get(0));
		revalidate();
		repaint();
	}

	public void displayServerInfo(OnlineGameNetObject game) {
		selectedGame = game;

		switch (game.getGameStatus()) {
		case 1:
			selectedGameName.setText(game.getName() + " | En cours");
			selectedGameMinPlayer.setText(" ");
			selectedGameMaxPlayer.setText(" ");
			waitingPlayerCount.setText(" ");
			joinButton.setEnabled(false);
			joinButton.setFocusable(false);
			joinButton.setVisible(false);
			break;

		case 2:
			selectedGameName.setText(game.getName() + " | Finie");
			// Ask player rank to controller and displays it
			HashMap<String, Integer> tmpPlayerRank = controller.getPlayerOnlineGameRank(game.getId());
			Object[][] displayedData = new Object[tmpPlayerRank.size()][3];

			int rank = 1;
			for (Map.Entry<String, Integer> entry : tmpPlayerRank.entrySet()) {
				Object[] playerData = { entry.getKey(), entry.getValue(), rank };
				displayedData[rank - 1] = playerData;
				rank++;
			}

			String[] columnNames = { "Nom", "Score", "Rang" };

			DefaultTableModel model = new DefaultTableModel(displayedData, columnNames);
			playerRankTable = new JTable(model);
			playerRankTable.setBounds(12, 132, 385, 88);
			
			scrollPanePlayerRank.add(playerRankTable);
			selectedGameInfoPanel.add(scrollPanePlayerRank);

			revalidate();
			repaint();

			selectedGameMinPlayer.setText(" ");
			selectedGameMaxPlayer.setText(" ");
			waitingPlayerCount.setText(" ");

			joinButton.setEnabled(false);
			joinButton.setFocusable(false);
			joinButton.setVisible(false);
			break;

		default:
			selectedGameName.setText(game.getName() + " | En attente de joueurs...");
			selectedGameMinPlayer.setText("Joueurs min : " + String.valueOf(game.getMinPlayer()));
			selectedGameMaxPlayer.setText("Joueurs max : " + String.valueOf(game.getMaxPlayer()));
			waitingPlayerCount.setText(" ");
			// waitingPlayerCount.setText("Joueurs en attente...");
			joinButton.setEnabled(true);
			joinButton.setFocusable(true);
			joinButton.setVisible(true);
			break;
		}

	}
}
