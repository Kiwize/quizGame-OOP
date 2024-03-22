package fr.thomas.proto0.controller;

import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.EnglishSequenceData;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

import fr.thomas.proto0.model.Answer;
import fr.thomas.proto0.model.Game;
import fr.thomas.proto0.model.Player;
import fr.thomas.proto0.model.Question;
import fr.thomas.proto0.net.object.OnlineGameNetObject;
import fr.thomas.proto0.net.object.PlayerNetObject;
import fr.thomas.proto0.net.request.Login;
import fr.thomas.proto0.net.request.ServerInfo.ServerInfoRequest;
import fr.thomas.proto0.net.request.ServerInfo.ServerInfoResponse;
import fr.thomas.proto0.net.request.ServerJoin.ServerJoinRequest;
import fr.thomas.proto0.net.request.ServerJoin.ServerJoinResponse;
import fr.thomas.proto0.net.request.ServerList.ServerListRequest;
import fr.thomas.proto0.net.request.ServerList.ServerListResponse;
import fr.thomas.proto0.net.request.ServerQuit.ServerQuitRequest;
import fr.thomas.proto0.net.request.ServerQuit.ServerQuitResponse;
import fr.thomas.proto0.net.threading.NetworkThread;
import fr.thomas.proto0.net.threading.NetworkThread.NetworkJobStatus;
import fr.thomas.proto0.utils.DatabaseHelper;
import fr.thomas.proto0.view.ConsoleView;
import fr.thomas.proto0.view.GameScore;
import fr.thomas.proto0.view.HomeView;
import fr.thomas.proto0.view.LoginView;
import fr.thomas.proto0.view.MultiplayerGameHub;
import fr.thomas.proto0.view.MultiplayerListView;
import fr.thomas.proto0.view.PasswordChangeView;
import fr.thomas.proto0.view.PlayView;

public class GameController {

	private DatabaseHelper databaseHelper;

	private ConsoleView view;
	private Player player;
	private ArrayList<Question> questions;
	private Game game;
	private int diff;
	private Config myConfig;

	private LoginView loginView;
	private HomeView homeView;
	private PlayView playView;
	private PasswordChangeView passchView;
	private GameScore gameScoreView;
	private MultiplayerListView multiplayerListView;
	private MultiplayerGameHub multiplayerGameHub;

	private boolean isGameStarted = false;

	private PasswordValidator passwordValidator;

	private NetworkThread netThreadClass;
	private Thread networkThread;

	private ArrayList<OnlineGameNetObject> serverList;

	/**
	 * @author Thomas PRADEAU
	 */
	public GameController() {

		// Créer la vue
		this.myConfig = new Config();

		// Start client
		this.netThreadClass = new NetworkThread(this, "127.0.0.1");
		networkThread = new Thread(netThreadClass);
		networkThread.setName("network_thread");
		networkThread.start();

		try {
			this.databaseHelper = new DatabaseHelper(this);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		this.view = new ConsoleView(this);
		this.loginView = new LoginView(this);
		player = new Player("", this);
		this.homeView = new HomeView(this);
		this.loginView.setVisible(true);
		this.playView = new PlayView(this);
		this.passchView = new PasswordChangeView(this);
		this.multiplayerListView = new MultiplayerListView(this);
		this.multiplayerGameHub = new MultiplayerGameHub(this);
		this.serverList = new ArrayList<OnlineGameNetObject>();

		passwordValidator = new PasswordValidator(new LengthRule(12, 24),
				new CharacterRule(EnglishCharacterData.LowerCase, 1),
				new CharacterRule(EnglishCharacterData.UpperCase, 1), new CharacterRule(EnglishCharacterData.Digit, 1),
				new CharacterRule(EnglishCharacterData.Special, 1),
				new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 4, false),
				new IllegalSequenceRule(EnglishSequenceData.Numerical, 4, false),
				new IllegalSequenceRule(EnglishSequenceData.USQwerty, 4, false), new WhitespaceRule());
	}

	public void playerAuth(String name, String password) {
		Login.LoginRequest request = new Login.LoginRequest();
		request.username = name;
		request.password = password;
		netThreadClass.sendTCPRequest(request);

		while (!netThreadClass.updateForResponse()) {
			// Can do things while waiting for response from the server.
		}

		try {
			if (netThreadClass.getCurrentJobStatus().isSuccedded()) {
				Login.LoginResponse response = ((Login.LoginResponse) netThreadClass.getResponse());
				if (response.isConnected) {
					this.loginView.setVisible(false);
					this.homeView.setVisible(true);

					PlayerNetObject playerNet = response.player;
					this.homeView.updatePlayerData(playerNet.getName(), playerNet.getHighestScore());
					
					this.player.setName(playerNet.getName());
					this.player.setId(playerNet.getId());
					this.player.setPassword(playerNet.getPassword());
				} else {
					// TODO Error popup or invalid password message
					System.err.println("Invalid password...");
				}
			}
		} catch (NullPointerException ex) {
		}
	}

	public void joinOnlineGame(OnlineGameNetObject game, Player player) {
		
		/*
		netThreadClass.defineServerInfoRefreshCallback(new IServerInfoRefreshRequest() {
			
			@Override
			public void onServerInfoRefresh(Object object) {
				if(((ServerInfoRefresh) object).playerIDs.contains(player.getID())) {
					updateMultiplayerHubServerInfos(game.getId());
					System.out.println("Server asks info refresh for game " + game.getName() + " player " + player.getName());
				}
			}
		});
		*/

		// Ask server to join a game
		ServerJoinRequest request = new ServerJoinRequest();
		request.game = game;
		request.player = new PlayerNetObject(player.getID(), player.getName(), player.getPassword(), player.getHighestScore());		
		netThreadClass.sendTCPRequest(request);

		while (!netThreadClass.updateForResponse()) {
			// Display loading screen and update some shit
		}

		if (netThreadClass.getResponse() != null) {
			ServerJoinResponse response = (ServerJoinResponse) netThreadClass.getResponse();
			if (response.isJoinable) {
				homeView.setVisible(false);
				multiplayerListView.setVisible(false);
				multiplayerGameHub.setVisible(true);
				multiplayerGameHub.setGameID(game.getId());
			} else
				System.out.println("Le serveur " + game.getName() + " n'est pas joignable... ");
		}

	}
	
	public void quitOnlineGame(int gameID) {
		ServerQuitRequest request = new ServerQuitRequest();
		request.gameID = gameID;
		request.playerID = this.player.getID();
		netThreadClass.sendTCPRequest(request);
		
		while(!netThreadClass.updateForResponse()) {
			
		}
		
		if(netThreadClass.getResponse() != null) {
			ServerQuitResponse response = (ServerQuitResponse) netThreadClass.getResponse();
			if(response.hasQuit) {
				multiplayerGameHub.dispose();
				multiplayerListView.setVisible(true);
				homeView.setVisible(true);
			}
		}
	}

	public void startGame() {
		this.game = new Game(this, player);
		this.playView = new PlayView(this);
		this.gameScoreView = new GameScore(this);
		if (!isGameStarted) {
			game.getRandomQuestions(); // Choisir les questions aléatoirement
			game.begin();
			playView.revalidate();
			playView.repaint();
			isGameStarted = true;
			homeView.setPlayButtonState(false);
			homeView.setVisible(false);
			homeView.revalidate();
			homeView.repaint();
		}
	}

	public void finishGame(HashMap<Question, Answer> gameHistory) {
		// TODO Show recap view
		isGameStarted = false;
		homeView.setPlayButtonState(true);
		homeView.setVisible(true);
		homeView.revalidate();
		homeView.repaint();

		playView.setVisible(false);

		int gameScoreBuffer = 0;

		for (Map.Entry<Question, Answer> gameEntry : gameHistory.entrySet()) {
			if (gameEntry.getValue().isCorrect()) {
				gameScoreBuffer += gameEntry.getKey().getDifficulty() * 100;
			}
		}

		game.setScore(gameScoreBuffer);
		game.insert();
		this.homeView.updatePlayerData(player.getName(), player.getHighestScore());

		// view.showGameRecap(gameHistory);
		gameScoreView.setGameHistory(gameHistory);
		gameScoreView.setVisible(true);

	}

	/**
	 * Asks controller to show password change view if the logins provided by the
	 * user are correct.
	 * 
	 * @param username
	 * @param password
	 */
	public void submitPasswordChange(String username, String password) {
		/*
		 * if (player.authenticate(username, password)) { loginView.setVisible(false);
		 * passchView.setVisible(true); } else {
		 * JOptionPane.showMessageDialog(loginView.getComponent(0),
		 * "Identifiants invalides.", "Erreur", JOptionPane.ERROR_MESSAGE); }
		 */
	}

	public void displayOnlineGames() {
		// Display online games window
		multiplayerListView.setVisible(true);

		// Gather game list from server
		ServerListRequest request = new ServerListRequest();
		netThreadClass.sendTCPRequest(request);

		while (!netThreadClass.updateForResponse()) {
			// Show loading screen
		}

		if (netThreadClass.getResponse() != null) {
			ServerListResponse response = (ServerListResponse) netThreadClass.getResponse();
			ArrayList<OnlineGameNetObject> serverList = response.servers;

			this.serverList = serverList;
			System.out.println(serverList.size() + " server(s) founds.");

			// Send server list to the view
			multiplayerListView.displayServerList(serverList);
		} else if (netThreadClass.getCurrentJobStatus().getStatus() == NetworkJobStatus.TIMEDOUT) {
			System.err.println("The server is unreachable, the request has timed out... Please try again.");
		}

	}
	
	public void updateMultiplayerHubServerInfos(int gameID) {
		ServerInfoRequest request = new ServerInfoRequest();
		request.gameID = gameID;
		netThreadClass.sendTCPRequest(request);
		
		while(!netThreadClass.updateForResponse()) {
			//Loading stuff
		}
		
		if(netThreadClass.getResponse() != null) {
			ServerInfoResponse response = (ServerInfoResponse) netThreadClass.getResponse();
			multiplayerGameHub.updateServerInfos(response.name, response.maxPlayers, response.players);
		}
	}

	public void changePassword(String password, String confirm) {
		if (password.equals(confirm)) {
			final PasswordData passwordData = new PasswordData(password);
			final RuleResult validate = passwordValidator.validate(passwordData);

			if (!validate.isValid()) {
				JOptionPane.showMessageDialog(loginView.getComponent(0), "Mot de passe non conforme !", "Erreur",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (player.updatePassword(password)) {
				JOptionPane.showMessageDialog(loginView.getComponent(0), "Mot de passe changé avec succés !",
						"Mot de passe modifié.", JOptionPane.INFORMATION_MESSAGE);

				loginView.dispatchEvent(new WindowEvent(loginView, WindowEvent.WINDOW_CLOSING));
				passchView.dispatchEvent(new WindowEvent(loginView, WindowEvent.WINDOW_CLOSING));
				homeView.setVisible(true);
				this.homeView.updatePlayerData(player.getName(), player.getHighestScore());
			}
		} else {
			JOptionPane.showMessageDialog(loginView.getComponent(0), "Les mots de passe sont différents !", "Erreur",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public HashMap<EPasswordError, Boolean> passwordFieldUpdate(String password, String confirmation) {
		final HashMap<EPasswordError, Boolean> errsBuffer = new HashMap<>();
		final String specialChars = "/*!@#$%^&*()\"{}_[]|\\?/<>,.";

		for (final EPasswordError error : EPasswordError.values()) {
			errsBuffer.put(error, true);
		}

		if (password.equals(confirmation)) {
			errsBuffer.replace(EPasswordError.SIMILAR_PASSWORDS, false);
		}

		if (password.length() >= 12) {
			errsBuffer.replace(EPasswordError.MIN_LENGTH, false);
		}

		for (int i = 0; i < password.length(); i++) {
			final char c = password.charAt(i);

			if (Character.isUpperCase(c)) {
				errsBuffer.replace(EPasswordError.REQUIRE_UPPERCASE, false);
			}

			if (Character.isLowerCase(c)) {
				errsBuffer.replace(EPasswordError.REQUIRE_LOWERCASE, false);
			}

			if (Character.isDigit(c)) {
				errsBuffer.replace(EPasswordError.REQUIRE_DIGIT, false);
			}

			if (specialChars.contains(password.substring(i, i + 1))) {
				errsBuffer.replace(EPasswordError.REQUIRE_SPECIAL, false);
			}
		}

		passchView.showPasswordStatus(errsBuffer);

		return errsBuffer;
	}

	public ConsoleView getView() {
		return view;
	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}

	public Player getPlayer() {
		return player;
	}

	public Game getGame() {
		return game;
	}

	public PlayView getPlayView() {
		return playView;
	}

	public DatabaseHelper getDatabaseHelper() {
		return databaseHelper;
	}

	/**
	 * Password errors callback enumeration That allows to define error messages for
	 * each password requirement.
	 */
	public enum EPasswordError {
		// TODO, translate errors messages
		SIMILAR_PASSWORDS("Les mots de passes sont différents", (PasswordChangeView frame, boolean state) -> {
			frame.getChkboxSimilarPasswords().setSelected(state);
		}),

		REQUIRE_UPPERCASE("Une majuscule requise", (PasswordChangeView frame, boolean state) -> {
			frame.getChkboxMajuscule().setSelected(state);
		}), REQUIRE_LOWERCASE("Une minuscule requise", (PasswordChangeView frame, boolean state) -> {
			frame.getChkboxMinuscule().setSelected(state);
		}), REQUIRE_SPECIAL("Un caractère spécial requis", (PasswordChangeView frame, boolean state) -> {
			frame.getChkboxSpecialChar().setSelected(state);
		}), REQUIRE_DIGIT("Un chiffre requis", (PasswordChangeView frame, boolean state) -> {
			frame.getChkboxDigit().setSelected(state);
		}), MIN_LENGTH("Le mot de passe doit faire " + 12 + " caractères minimum",
				(PasswordChangeView frame, boolean state) -> {
					frame.getChkboxMinLength().setSelected(state);
				});

		private final String textError;
		private final IPasswordCallback callbackError;

		EPasswordError(String textError, IPasswordCallback callbackError) {
			this.textError = textError;
			this.callbackError = callbackError;
		}

		public String getTextError() {
			return textError;
		}

		public IPasswordCallback getCallbackError() {
			return callbackError;
		}
	}

	public Config getMyConfig() {
		return myConfig;
	}

	public void setMyConfig(Config myConfig) {
		this.myConfig = myConfig;
	}

	
}
