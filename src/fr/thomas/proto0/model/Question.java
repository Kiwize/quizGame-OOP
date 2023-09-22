package fr.thomas.proto0.model;

import java.util.ArrayList;

import fr.thomas.proto0.controller.GameController;

public class Question {

	private String label;

	private ArrayList<Answer> answers;

	private GameController controller;

	public Question(GameController controller, String label, ArrayList<Answer> answers) {
		this.label = label;
		this.answers = answers;
		this.controller = controller;
	}

	public ArrayList<Answer> getAnswers() {
		return answers;
	}

	public String getLabel() {
		return label;
	}

	/**
	 * Pose la question au joueur
	 */
	public void ask() {
		String answersString = "";

		for (Answer answer : answers) {
			answersString += answer.getQchar() + ") " + answer.getLabel() + "\n";
		}

		boolean isValid = false;

		//Temps que la réponse choisie par le joueur n'est pas valide.
		while (!isValid) {
			char response = controller.getView().askPlayer(label + "\n" + answersString).charAt(0);
			controller.getView().output("Vous avez répondu : " + response);

			//Vérifie quelle question a été choisie par le joueur. 
			for (Answer answer : answers) {

				if (answer.getQchar() == response) {
					if (answer.isCorrect())
						controller.getView().output("Bravo !! C'est la bonne réponse.");
					else
						controller.getView().output("Dommage.. Ce n'est pas la bonne réponse..");

					isValid = true;
					return;
				}
			}

			controller.getView().output("Réponse invalide, veuillez réessayer...");
		}
	}
}
