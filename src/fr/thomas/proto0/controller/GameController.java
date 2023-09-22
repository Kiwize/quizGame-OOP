package fr.thomas.proto0.controller;

import java.util.ArrayList;

import fr.thomas.proto0.model.Game;
import fr.thomas.proto0.model.Player;
import fr.thomas.proto0.model.Question;
import fr.thomas.proto0.utils.QuestionsBuilder;
import fr.thomas.proto0.view.ConsoleView;

public class GameController extends Controller{

	private ConsoleView view;
	private Player player;
	private ArrayList<Question> questions;
	private Game game;
	
	/**
	 * @author Thomas PRADEAU
	 */
	public GameController() {
		//Créer la vue
		this.view = new ConsoleView(this);
		
		//Créer le joueur
		player = new Player(view.askPlayer("Quel est votre nom ?"));
		questions = QuestionsBuilder.readQuestions(this, "resources/data/questions.txt"); //Stubdata questions
		
		//Créer la game avec joueur et questions
		this.game = new Game(player, questions);
		game.getRandomQuestions(2); //Choisir les questions aléatoirement
		game.begin(); 
	}
	
	public ConsoleView getView() {
		return view;
	}
}
