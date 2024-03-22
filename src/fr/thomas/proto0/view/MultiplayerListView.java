package fr.thomas.proto0.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

import fr.thomas.proto0.controller.GameController;
import fr.thomas.proto0.net.object.OnlineGameNetObject;

public class MultiplayerListView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel gameList;
	private JLabel selectedGameName;
	private JLabel selectedGameMaxPlayer;
	private GameController controller;
	private ArrayList<OnlineGameNetObject> serverList;
	private GridBagConstraints gbc;

	private OnlineGameNetObject selectedGame;

	/**
	 * Create the frame.
	 */
	public MultiplayerListView(GameController controller) {
		this.controller = controller;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));

		// Créer les deux panneaux à séparer
		gameList = new JPanel();
		JPanel selectedGameInfoPanel = new JPanel();

		// Créer le JSplitPane
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, gameList, selectedGameInfoPanel);
		gameList.setLayout(new GridBagLayout());

		gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		selectedGameInfoPanel.setLayout(null);

		selectedGameName = new JLabel("<placeholder>");
		selectedGameName.setBounds(93, 5, 85, 17);
		selectedGameInfoPanel.add(selectedGameName);

		selectedGameMaxPlayer = new JLabel("gameMaxPlayer");
		selectedGameMaxPlayer.setBounds(83, 34, 104, 17);
		selectedGameInfoPanel.add(selectedGameMaxPlayer);

		JButton btnRejoindre = new JButton("Rejoindre");
		btnRejoindre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				controller.joinOnlineGame(selectedGame, controller.getPlayer());
			}
		});
		btnRejoindre.setBounds(83, 217, 105, 27);
		selectedGameInfoPanel.add(btnRejoindre);

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
		selectedGameName.setText(game.getName());
		selectedGameMaxPlayer.setText(String.valueOf(game.getMaxPlayer()));
	}
}
