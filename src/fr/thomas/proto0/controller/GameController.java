package fr.thomas.proto0.controller;

import java.util.ArrayList;

import fr.thomas.proto0.model.Game;
import fr.thomas.proto0.model.Player;
import fr.thomas.proto0.model.Question;
import fr.thomas.proto0.utils.QuestionsBuilder;
import fr.thomas.proto0.view.ConsoleView;

public class GameController {

	private ConsoleView view;
	private Player player;
	private ArrayList<Question> questions;
	private Game game;
	private int diff;
	
	/**
	 * @author Thomas PRADEAU
	 */
	public GameController() {
		//Créer la vue
		this.view = new ConsoleView(this);
		
		//Créer le joueur
		player = new Player(view.askPlayer("Quel est votre nom ?"), this);
		player.insert();
		
		diff = view.askDifficulty("Choisissez la difficulté :");
		
		//questions = QuestionsBuilder.readQuestions(this, "resources/data/questions.txt"); //Stubdata questions
		questions = QuestionsBuilder.loadQuestions(this, diff);
	
		
		//Créer la game avec joueur et questions
		this.game = new Game(this);
		game.getRandomQuestions(2); //Choisir les questions aléatoirement
		game.begin(); 
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
