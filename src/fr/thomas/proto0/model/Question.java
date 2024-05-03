package fr.thomas.proto0.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.thomas.proto0.controller.GameController;
import fr.thomas.proto0.net.object.AnswerNetObject;
import fr.thomas.proto0.net.object.QuestionNetObject;
import fr.thomas.proto0.utils.QuestionsBuilder;

public class Question {

	private int id;
	private String label;
	private int difficultyLevel;
	private ArrayList<Answer> answers;
	private GameController controller;

	public Question(GameController controller, String label, ArrayList<Answer> answers) {
		this.label = label;
		this.answers = answers;
		this.controller = controller;
	}
	
	public Question(QuestionNetObject netObject) {
		this.id = netObject.getId();
		this.label = netObject.getLabel();
		this.difficultyLevel = netObject.getDifficultyLevel();
		
		answers = new ArrayList<Answer>();
		for(AnswerNetObject answerNetObject : netObject.getAnswers()) {
			answers.add(new Answer(answerNetObject));
		}
	}

	public Question(int id, GameController controller) {
		try {
			Statement st = controller.getDatabaseHelper().getStatement(0);
			ResultSet res = st.executeQuery("SELECT * FROM Question WHERE idquestion = " + id + ";");
			this.controller = controller;
			if (!res.next()) {
				this.id = -1;
				this.label = "";
				this.difficultyLevel = 0;
				this.answers = new ArrayList<Answer>();
			} else {
				this.id = res.getInt("idquestion");
				this.label = res.getString("label");
				this.difficultyLevel = res.getInt("difficultyLevel");
				this.answers = new ArrayList<>();

				ResultSet qr = st.executeQuery("SELECT * FROM Answer WHERE Answer.idquestion = " + this.id + ";");

				int count = 1;
				while (qr.next()) {
					answers.add(new Answer(QuestionsBuilder.getCharForNumber(count).charAt(0), qr.getString("label"),
							qr.getBoolean("iscorrect")));
					count++;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Answer> getAnswers() {
		return answers;
	}
	
	public String getAnAnswer(int id) {
		return answers.get(id).getLabel();
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

		// Tant que la réponse choisie par le joueur n'est pas valide.
		while (!isValid) {
			char response = controller.getView().askPlayer(label + "\n" + answersString).charAt(0);
			controller.getView().output("Vous avez répondu : " + response);

			// Vérifie quelle réponse a été choisie par le joueur.
			for (Answer answer : answers) {

				if (answer.getQchar() == response) {
					if (answer.isCorrect()) {
						controller.getView().output("Bravo !! C'est la bonne réponse.");
						controller.getGame().addScore(difficultyLevel * 100);
					} else
						controller.getView().output("Dommage.. Ce n'est pas la bonne réponse..");

					isValid = true;
					return;
				}
			}

			controller.getView().output("Réponse invalide, veuillez réessayer...");
		}
	}

	public int getDifficulty() {
		return difficultyLevel;
	}
}
