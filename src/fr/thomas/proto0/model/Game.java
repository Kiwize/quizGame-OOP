package fr.thomas.proto0.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game {

	private Player player;
	private ArrayList<Question> questions;
	private Random rand;

	public Game(Player player, ArrayList<Question> questions) {
		this.player = player;
		this.questions = questions;
		this.rand = new Random();
	}

	/**
	 * Débute la partie, pose toutes les questions une par une.
	 * @author Thomas PRADEAU
	 */
	public void begin() {
		for (Question question : questions) {
			question.ask();
		}
	}

	/**
	 * Choisis des questions aléatoires parmis toutes les questions.
	 * @param amount Nombre de questions à choisir
	 * @author Thomas PRADEAU
	 */
	public void getRandomQuestions(int amount) {
		if (amount >= questions.size()) {
			System.err.println("Erreur dans le nombre de questions à choisir.");
			return;
		}

		ArrayList<Question> cq = new ArrayList<Question>();

		//Questions déjà choisies
		int chosenBuffer[] = new int[amount];
		
		for (int i = 0; i < amount; i++) {
			int choosenQuestion = rand.nextInt(0, questions.size() - 1);
			while(Arrays.binarySearch(chosenBuffer, choosenQuestion) == 0) {
				choosenQuestion = rand.nextInt(0, questions.size() - 1);
			}
			
			chosenBuffer[i] = choosenQuestion;
			cq.add(questions.get(choosenQuestion));
		}

		this.questions = cq;
	}
}
