package fr.thomas.proto0.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import fr.thomas.proto0.controller.GameController;
import fr.thomas.proto0.utils.DatabaseHelper;

public class Game implements IModel {

	private int id;
	private int score;
	private Player player;

	private Random rand;
	private GameController controller;

	public Game(GameController controller, Player player) {
		this.rand = new Random();
		this.player = player;
		this.controller = controller;
		this.score = 0;
	}

	/**
	 * Débute la partie, pose toutes les questions une par une.
	 * 
	 * @author Thomas PRADEAU
	 */
	public void begin() {
		for (Question question : controller.getQuestions()) {
			question.ask();
		}
	}

	public void addScore(int amount) {
		score += amount;
	}

	/**
	 * Choisis des questions aléatoires parmis toutes les questions.
	 * 
	 * @param amount Nombre de questions à choisir
	 * @author Thomas PRADEAU
	 */
	public void getRandomQuestions(int amount) {
		if (amount >= controller.getQuestions().size()) {
			System.err.println("Erreur dans le nombre de questions à choisir.");
			return;
		}

		ArrayList<Question> cq = new ArrayList<Question>();

		// Questions déjà choisies
		int chosenBuffer[] = new int[amount];

		for (int i = 0; i < amount; i++) {
			int choosenQuestion = rand.nextInt(0, controller.getQuestions().size() - 1);
			while (Arrays.binarySearch(chosenBuffer, choosenQuestion) == 0) {
				choosenQuestion = rand.nextInt(0, controller.getQuestions().size() - 1);
			}

			chosenBuffer[i] = choosenQuestion;
			cq.add(controller.getQuestions().get(choosenQuestion));
		}

		controller.setQuestions(cq);
	}

	@Override
	public boolean insert() {
		try {
			DatabaseHelper db = new DatabaseHelper();
			Statement st = db.create();

			st.executeUpdate("INSERT INTO Game (score, idplayer) VALUES ('" + score + "', " + player.getID() + ");",
					Statement.RETURN_GENERATED_KEYS);

			ResultSet res = st.getGeneratedKeys();
			
			if (res.next()) {
				this.id = res.getInt(1);
			}
			
			db.close();
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean save() {
		try {
			DatabaseHelper db = new DatabaseHelper();
			Statement st = db.create();

			boolean res = st.execute("UPDATE Game SET score = " + score + ", idplayer=" + player.getID()
					+ " WHERE Game.idgame=" + id + ";");
			db.close();

			return res;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
