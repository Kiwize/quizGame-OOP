package fr.thomas.proto0.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import fr.thomas.proto0.controller.GameController;
import fr.thomas.proto0.model.Answer;
import fr.thomas.proto0.model.Question;

public class ConsoleView {

	private Scanner scanner;
	private GameController controller;

	public ConsoleView(GameController controller) {
		scanner = new Scanner(System.in);
		this.controller = controller;
	}

	/**
	 * Pose une question au joueur
	 * 
	 * @param label
	 * @return
	 */
	public String askPlayer(String label) {
		System.out.println(label);
		String in = scanner.nextLine();

		while (in.isEmpty()) {
			System.out.println("Veuillez indiquer une réponse.");
			in = scanner.nextLine();
		}

		return in;
	}

	public int askDifficulty(String label) {
		System.out.println(label);
		int diff = scanner.nextInt();

		switch (diff) {
		default:
			return 1;
		case 2:
			return 2;
		case 3:
			return 3;
		}
	}
	
	public void showGameRecap(HashMap<Question, Answer> gameHistory) {
		for(Map.Entry<Question, Answer> history : gameHistory.entrySet()) {
			System.out.println("Question : " + history.getKey().getLabel());
			System.out.println("Réponse : " + history.getValue().getLabel() + "\n");
		}
	}

	/**
	 * Affiche du texte
	 * 
	 * @param text
	 */
	public void output(String text) {
		System.out.println(text);
	}
}
