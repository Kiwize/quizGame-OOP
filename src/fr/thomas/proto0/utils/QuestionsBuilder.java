package fr.thomas.proto0.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import fr.thomas.proto0.controller.GameController;
import fr.thomas.proto0.model.Answer;
import fr.thomas.proto0.model.Question;

public class QuestionsBuilder {

	/**
	 * Stubdata des questions
	 * @param gamecontroller contrôleur de jeu
	 * @param filePath chemin du fichier
	 * @return ArrayList<Question> Questions chargées depuis le fichier
	 * @author Thomas PRADEAU
	 */
	public static ArrayList<Question> readQuestions(GameController controller, String filePath) {
		try {
			ArrayList<Question> questions = new ArrayList<Question>();
			String content = readFromInputStream(filePath);
			String questionsStringArray[] = content.split("\n");
			
			for(String stringQuestion : questionsStringArray) {
				String elements[] = stringQuestion.split("[|]");
				
				ArrayList<Answer> answers = new ArrayList<Answer>();
				
				for(int i = 1; i < elements.length; i++) {
					boolean response = false;
					
					if(elements[i].charAt(elements[i].length() - 1) == '$') {
						response = true;
						elements[i] = elements[i].substring(0, elements[i].length() - 1);
					}
					answers.add(new Answer(elements[i], response));
				}
				
				Question questionModel = new Question(controller, elements[0], answers);
				questions.add(questionModel);
			}

			return questions;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Lecture depuis fichier 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	private static String readFromInputStream(String path) throws IOException {
		StringBuilder resultStringBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))))) {
			String line;
			while ((line = br.readLine()) != null) {
				resultStringBuilder.append(line).append("\n");
			}
		}
		return resultStringBuilder.toString();
	}

}
