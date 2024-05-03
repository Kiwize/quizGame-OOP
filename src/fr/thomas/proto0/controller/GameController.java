package fr.thomas.proto0.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.EnglishSequenceData;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

import fr.thomas.engine.GameEngine;
import fr.thomas.proto0.log.Logger;
import fr.thomas.proto0.model.Answer;
import fr.thomas.proto0.model.Game;
import fr.thomas.proto0.model.Player;
import fr.thomas.proto0.model.Question;
import fr.thomas.proto0.net.object.AnswerNetObject;
import fr.thomas.proto0.net.object.OnlineGameNetObject;
import fr.thomas.proto0.net.object.PlayerNetObject;
import fr.thomas.proto0.net.request.Login;
import fr.thomas.proto0.net.request.ServerInfo.ServerInfoRequest;
import fr.thomas.proto0.net.request.ServerInfo.ServerInfoResponse;
import fr.thomas.proto0.net.request.ServerJoin.ServerJoinRequest;
import fr.thomas.proto0.net.request.ServerJoin.ServerJoinResponse;
import fr.thomas.proto0.net.request.ServerList.ServerListRequest;
import fr.thomas.proto0.net.request.ServerList.ServerListResponse;
import fr.thomas.proto0.net.request.ServerPlay.GetPlayerAnswer;
import fr.thomas.proto0.net.request.ServerPlayerRank.PlayerRankRequest;
import fr.thomas.proto0.net.request.ServerPlayerRank.PlayerRankResponse;
import fr.thomas.proto0.net.request.ServerQuit.ServerQuitRequest;
import fr.thomas.proto0.net.request.ServerQuit.ServerQuitResponse;
import fr.thomas.proto0.net.threading.NetworkThread;
import fr.thomas.proto0.net.threading.NetworkThread.NetworkJobStatus;
import fr.thomas.proto0.utils.DatabaseHelper;
import fr.thomas.proto0.view.LoginFrame;
import fr.thomas.proto0.view.swing.ConsoleView;
import fr.thomas.proto0.view.swing.GameScore;
import fr.thomas.proto0.view.swing.MultiplayerGameHub;
import fr.thomas.proto0.view.swing.MultiplayerListView;
import fr.thomas.proto0.view.swing.PasswordChangeView;

public class GameController {

	private DatabaseHelper databaseHelper;

	private ConsoleView view;
	private Player player;
	private ArrayList<Question> questions;
	private Game game;
	private Config myConfig;

	private LoginFrame loginView;

	private PasswordChangeView passchView;
	private GameScore gameScoreView;
	private MultiplayerListView multiplayerListView;
	private MultiplayerGameHub multiplayerGameHub;

	private boolean isGameStarted = false;

	private PasswordValidator passwordValidator;

	private NetworkThread netThreadClass;
	private Thread networkThread;

	private ArrayList<OnlineGameNetObject> serverList;

	private String serverAdress;

	private GameEngine gameEngine;

	private Logger logger;

	/**
	 * @author Thomas PRADEAU
	 */
	public GameController(String[] args) {

		logger = new Logger(true);
		logger.updateDebugLogState(true);

		logger.log("Starting client...");

		this.serverAdress = "192.168.122.173";

		if (args.length == 2) {
			if (args[0] == "-h") {
				System.out.println("Trying to connect to server : " + args[1]);
				this.serverAdress = args[1];
			}
		}
		// Créer la vue
		this.myConfig = new Config();

		// Start client
		this.netThreadClass = new NetworkThread(this, serverAdress);
		networkThread = new Thread(netThreadClass);
		networkThread.setName("network_thread");
		networkThread.start();

		try {
			this.databaseHelper = new DatabaseHelper(this);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		player = new Player("", this);
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

		this.gameEngine = new GameEngine(this);
		this.gameEngine.openWindow(); //Main window loop
		
		multiplayerGameHub.stopSyncTimer();
		netThreadClass.getClient().stop();
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

					PlayerNetObject playerNet = response.player;
					//TODO Update player data
					player.setId(playerNet.getId());
					player.setName(playerNet.getName());
					player.setPassword(playerNet.getPassword());
					
					gameEngine.getFrameManager().showFrame("home_frame");
				} else {
					// TODO Error popup or invalid password message
					System.err.println("Invalid password...");
				}
			}
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
	}

	public void joinOnlineGame(OnlineGameNetObject game, Player player) {

		// Ask server to join a game
		ServerJoinRequest request = new ServerJoinRequest();
		request.game = game;
		request.player = new PlayerNetObject(player.getID(), player.getName(), player.getPassword(),
				player.getHighestScore());
		netThreadClass.sendTCPRequest(request);

		while (!netThreadClass.updateForResponse()) {
			// Display loading screen and update some shitł
		}

		if (netThreadClass.getResponse() != null) {
			ServerJoinResponse response = (ServerJoinResponse) netThreadClass.getResponse();
			if (response.isJoinable) {
//				homeView.setVisible(false);
				multiplayerListView.setVisible(false);
				multiplayerGameHub.setVisible(true);
				multiplayerGameHub.setGameID(game.getId());
				multiplayerGameHub.startSync();
			} else
				System.out.println("Le serveur " + game.getName() + " n'est pas joignable... ");
		}
	}

	public void quitOnlineGame(int gameID) {
		ServerQuitRequest request = new ServerQuitRequest();
		request.gameID = gameID;
		request.playerID = this.player.getID();
		netThreadClass.sendTCPRequest(request);

		while (!netThreadClass.updateForResponse()) {

		}

		if (netThreadClass.getResponse() != null) {
			ServerQuitResponse response = (ServerQuitResponse) netThreadClass.getResponse();
			if (response.hasQuit) {
				multiplayerGameHub.stopSync();
				multiplayerGameHub.dispose();
				multiplayerListView.setVisible(true);
//				homeView.setVisible(true);
			}
		}
	}

	public void startGame() {
		this.game = new Game(this, player);
		
		this.gameScoreView = new GameScore(this);
		if (!isGameStarted) {
			game.getRandomQuestions(); // Choisir les questions aléatoirement
			game.begin();
			
			gameEngine.getFrameManager().showFrame("play_frame");
			
			isGameStarted = true;
		}
	}

	public void finishGame(HashMap<Question, Answer> gameHistory) {
		// TODO Show recap view
		isGameStarted = false;
//		homeView.setPlayButtonState(true);
//		homeView.revalidate();
//		homeView.repaint();
		
		gameEngine.getFrameManager().showFrame("home_frame");


		int gameScoreBuffer = 0;

		for (Map.Entry<Question, Answer> gameEntry : gameHistory.entrySet()) {
			if (gameEntry.getValue().isCorrect()) {
				gameScoreBuffer += gameEntry.getKey().getDifficulty() * 100;
			}
		}

		game.setScore(gameScoreBuffer);
		game.insert();
//		this.homeView.updatePlayerData(player.getName(), player.getHighestScore());

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

		while (!netThreadClass.updateForResponse()) {
			// Loading stuff
		}

		if (netThreadClass.getResponse() != null) {
			ServerInfoResponse response = (ServerInfoResponse) netThreadClass.getResponse();
			multiplayerGameHub.updateServerInfos(response.name, response.maxPlayers, response.players,
					response.minPlayers);
		}
	}

	public void changePassword(String password, String confirm) {
		if (password.equals(confirm)) {
			final PasswordData passwordData = new PasswordData(password);
			final RuleResult validate = passwordValidator.validate(passwordData);

			if (!validate.isValid()) {
				return;
			}

			if (player.updatePassword(password)) {
				loginView.setVisible(false); // TODO : Close window
//				homeView.setVisible(true);
//				this.homeView.updatePlayerData(player.getName(), player.getHighestScore());
			}
		} else {
			// JOptionPane.showMessageDialog(loginView.getComponent(0), "Les mots de passe
			// sont différents !", "Erreur",
			// JOptionPane.ERROR_MESSAGE);
		}
	}

	public void sendChoosenAnswer(Answer answer, int onlineGameID) {
		GetPlayerAnswer getPlayerAnswerRequest = new GetPlayerAnswer();
		getPlayerAnswerRequest.answer = new AnswerNetObject(answer.getLabel(), answer.isCorrect());
		getPlayerAnswerRequest.onlineGameID = onlineGameID;
		getPlayerAnswerRequest.playerID = player.getID();

		netThreadClass.sendTCPRequest(getPlayerAnswerRequest);
	}

	public void startOnlineGame(int onlineGameID) {
		multiplayerGameHub.setVisible(false);
//		playView.initMultiplayerContext(onlineGameID);
//		playView.setVisible(true);
	}

	public void endOnlineGame(int score) {
		System.out.println("Your score " + score);
		multiplayerGameHub.setVisible(true);
//		playView.setVisible(false);
//		playView.disableMultiplayerContext();
	}

	public void showQuestion(Question question) {
//		playView.loadSingleQuestion(question);
	}

	public HashMap<String, Integer> getPlayerOnlineGameRank(int id) {
		HashMap<String, Integer> playerNameScoreMap = new HashMap<String, Integer>();

		PlayerRankRequest playerRankRequest = new PlayerRankRequest();
		PlayerRankResponse playerRankResponse;
		playerRankRequest.gameid = id;

		netThreadClass.sendTCPRequest(playerRankRequest);

		while (!netThreadClass.updateForResponse()) {

		}

		if (netThreadClass.getResponse() != null) {
			playerRankResponse = (PlayerRankResponse) netThreadClass.getResponse();
			playerNameScoreMap = playerRankResponse.playerRankMap;
		}

		return playerNameScoreMap;
	}

	public void updateTimeLeftToAnswer(int timeLeft, int maxTime) {
//		playView.updateTimeLeftToAnswer(timeLeft, maxTime);
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

	public void updateTimeLeftBeforeGameStart(int time) {
		this.multiplayerGameHub.updateTimeLeft(time);
	}

	public Logger getLogger() {
		return logger;
	}
	
	public GameEngine getGameEngine() {
		return gameEngine;
	}

}
