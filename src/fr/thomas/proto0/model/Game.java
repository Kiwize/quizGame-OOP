package fr.thomas.proto0.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import fr.thomas.proto0.controller.GameController;

public class Game {

	private Random rand;
	private GameController controller;

	public Game(GameController controller) {
		this.rand = new Random();
		this.controller = controller;
	}

	/**
	 * Débute la partie, pose toutes les questions une par une.
	 * @author Thomas PRADEAU
	 */
	public void begin() {
		for (Question question : controller.getQuestions()) {
			question.ask();
		}
	}

	/**
	 * Choisis des questions aléatoires parmis toutes les questions.
	 * @param amount Nombre de questions à choisir
	 * @author Thomas PRADEAU
	 */
	public void getRandomQuestions(int amount) {
		if (amount >= controller.getQuestions().size()) {
			System.err.println("Erreur dans le nombre de questions à choisir.");
			return;
		}

		ArrayList<Question> cq = new ArrayList<Question>();

		//Questions déjà choisies
		int chosenBuffer[] = new int[amount];
		
		for (int i = 0; i < amount; i++) {
			int choosenQuestion = rand.nextInt(0, controller.getQuestions().size() - 1);
			while(Arrays.binarySearch(chosenBuffer, choosenQuestion) == 0) {
				choosenQuestion = rand.nextInt(0, controller.getQuestions().size() - 1);
			}
			
			chosenBuffer[i] = choosenQuestion;
			cq.add(controller.getQuestions().get(choosenQuestion));
		}

		controller.setQuestions(cq);
	}
}
