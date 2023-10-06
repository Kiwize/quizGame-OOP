package fr.thomas.proto0.controller;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.thomas.proto0.model.Game;
import fr.thomas.proto0.model.Player;
import fr.thomas.proto0.model.Question;
import fr.thomas.proto0.utils.DatabaseHelper;
import fr.thomas.proto0.utils.QuestionsBuilder;
import fr.thomas.proto0.view.ConsoleView;
import fr.thomas.proto0.view.HomeView;
import fr.thomas.proto0.view.LoginView;

public class GameController {

	private ConsoleView view;
	private Player player;
	private ArrayList<Question> questions;
	private Game game;
	private int diff;

	private LoginView loginView;
	private HomeView homeView;

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
		diff = view.askDifficulty("Choisissez la difficulté :");
		questions = QuestionsBuilder.loadQuestions(this, diff);

		//TODO Déduire nb de questions choisies aleatoirement
		game.getRandomQuestions(2); // Choisir les questions aléatoirement
		game.begin();

		//Partie terminee
		
		game.insert();
		this.homeView.updatePlayerData(player.getName(), game.getHighestScore(player));
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
}
