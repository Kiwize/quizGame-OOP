package fr.thomas.proto0.controller;

import java.util.ArrayList;
import java.util.HashMap;

import fr.thomas.proto0.model.Answer;
import fr.thomas.proto0.model.Game;
import fr.thomas.proto0.model.Player;
import fr.thomas.proto0.model.Question;
import fr.thomas.proto0.view.ConsoleView;
import fr.thomas.proto0.view.HomeView;
import fr.thomas.proto0.view.LoginView;
import fr.thomas.proto0.view.PlayView;

public class GameController {

	private ConsoleView view;
	private Player player;
	private ArrayList<Question> questions;
	private Game game;
	private int diff;

	private LoginView loginView;
	private HomeView homeView;
	private PlayView playView;

	private boolean isGameStarted = false;

	/**
	 * @author Thomas PRADEAU
	 */
	public GameController() {
		// Créer la vue
		this.view = new ConsoleView(this);
		this.loginView = new LoginView(this);
		player = new Player("", this);
		this.homeView = new HomeView(this);
		this.loginView.setVisible(true);
		this.playView = new PlayView(this);
	}

	public void playerAuth(String name, String password) {
		if (player.authenticate(name, password)) {
			this.loginView.setVisible(false);
			this.homeView.setVisible(true);
			this.game = new Game(this, player);
			this.homeView.updatePlayerData(player.getName(), game.getHighestScore(player));
		} else {
			System.err.println("Invalid password...");
		}
	}

	public void startGame() {
		if (!isGameStarted) {
			game.getRandomQuestions(); // Choisir les questions aléatoirement
			game.begin();
			isGameStarted = true;
			homeView.setPlayButtonState(false);
			homeView.setVisible(false);
			homeView.revalidate();
			homeView.repaint();

			// Partie terminee
			game.insert();
			this.homeView.updatePlayerData(player.getName(), game.getHighestScore(player));
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

		view.showGameRecap(gameHistory);
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

}
