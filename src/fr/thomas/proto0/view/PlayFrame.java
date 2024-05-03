package fr.thomas.proto0.view;

import java.util.HashMap;

import javax.swing.ButtonGroup;

import fr.thomas.engine.GameEngine;
import fr.thomas.engine.graphics.ui.IOnClickCallback;
import fr.thomas.engine.graphics.ui.UserInterface;
import fr.thomas.engine.graphics.ui.component.Button;
import fr.thomas.engine.graphics.ui.component.GraphicalComponent;
import fr.thomas.engine.graphics.ui.component.ProgressBar;
import fr.thomas.engine.graphics.ui.component.RadioButton;
import fr.thomas.engine.graphics.ui.component.RadioButtonGroup;
import fr.thomas.engine.graphics.ui.component.Text;
import fr.thomas.proto0.controller.GameController;
import fr.thomas.proto0.model.Answer;
import fr.thomas.proto0.model.Question;

public class PlayFrame extends UserInterface {

	private HashMap<Question, ButtonGroup> answerButtonMap;
	private HashMap<Question, Answer> gameHistory;

	private Question loadedQuestion;

	private int qpointer;

	private GameController controller;

	private Button nextButton;
	private Button previousButton;
	private Button submitButton;

	private Text questionLabel;

	private RadioButtonGroup answerButtonGroup;

	private RadioButton answerOne;
	private RadioButton answerTwo;
	private RadioButton answerThree;

	private ProgressBar progressQuetionBar;

	public PlayFrame(GameController controller) {
		super("play_frame");
		this.controller = controller;
		this.gameHistory = new HashMap<Question, Answer>();

		previousButton = new Button(120, 30, 200, 30, "Previous");
		nextButton = new Button(previousButton.getPos().x + 200 * 1.2f, 30, 200, 30, "Next");
		submitButton = new Button(0, 30, 200, 30, "Finish").right();

		questionLabel = new Text(0, GameEngine.instance.getWindow().getHeight() - 200, 20, 20,
				GameEngine.instance.getFontManager().getLoadedFonts().get(0), "Placeholder").centerHorizontally();

		answerButtonGroup = new RadioButtonGroup();
		answerOne = new RadioButton(0, 400, 30, 30, "").centerHorizontally();
		answerTwo = new RadioButton(0, 350, 30, 30, "").centerHorizontally();
		answerThree = new RadioButton(0, 300, 30, 30, "").centerHorizontally();

		answerButtonGroup.addButton(answerOne);
		answerButtonGroup.addButton(answerTwo);
		answerButtonGroup.addButton(answerThree);

		progressQuetionBar = new ProgressBar(0, GameEngine.instance.getWindow().getHeight() - 80, 400, 30, 0, 1)
				.centerHorizontally();

		nextButton.setOnClickCallback(new IOnClickCallback() {

			@Override
			public void onClick(GraphicalComponent component) {
				if (qpointer < controller.getQuestions().size() - 1) {
					previousButton.setEnabled(true);
					qpointer++;
					loadQuestion(controller.getQuestions().get(qpointer));
					progressQuetionBar.setCurrentValue(qpointer + 1);
				} else {
					nextButton.setEnabled(false);
				}

				answerButtonGroup.clearAllButtons();

				if (gameHistory.get(loadedQuestion) != null) {
					answerButtonGroup.getButtonFromLabel(gameHistory.get(loadedQuestion).getLabel()).setChecked(true);
				}

				GameEngine.instance.getFrameManager().updateProjection(GameEngine.instance.getWindow().getWidth(),
						GameEngine.instance.getWindow().getHeight());
			}
		});

		previousButton.setOnClickCallback(new IOnClickCallback() {

			@Override
			public void onClick(GraphicalComponent component) {
				if (qpointer > 0) {
					nextButton.setEnabled(true);
					qpointer--;
					loadQuestion(controller.getQuestions().get(qpointer));
					progressQuetionBar.setCurrentValue(qpointer + 1);
				} else {
					previousButton.setEnabled(false);
				}

				answerButtonGroup.clearAllButtons();

				if (gameHistory.get(loadedQuestion) != null) {
					answerButtonGroup.getButtonFromLabel(gameHistory.get(loadedQuestion).getLabel()).setChecked(true);
				}
				GameEngine.instance.getFrameManager().updateProjection(GameEngine.instance.getWindow().getWidth(),
						GameEngine.instance.getWindow().getHeight());
			}
		});

		submitButton.setOnClickCallback(new IOnClickCallback() {

			@Override
			public void onClick(GraphicalComponent component) {
				controller.finishGame(gameHistory);
			}
		});

		answerOne.setOnClickCallback(new IOnClickCallback() {

			@Override
			public void onClick(GraphicalComponent component) {
				gameHistory.put(loadedQuestion, getAnswerFromLabel(answerOne.getText().getContent()));

				if (areAllQuestionsAnswered())
					submitButton.setEnabled(true);
			}
		});

		answerTwo.setOnClickCallback(new IOnClickCallback() {

			@Override
			public void onClick(GraphicalComponent component) {
				gameHistory.put(loadedQuestion, getAnswerFromLabel(answerTwo.getText().getContent()));

				if (areAllQuestionsAnswered())
					submitButton.setEnabled(true);
			}
		});

		answerThree.setOnClickCallback(new IOnClickCallback() {

			@Override
			public void onClick(GraphicalComponent component) {
				gameHistory.put(loadedQuestion, getAnswerFromLabel(answerThree.getText().getContent()));

				if (areAllQuestionsAnswered())
					submitButton.setEnabled(true);
			}
		});

		submitButton.setEnabled(false);

		super.add(previousButton);
		super.add(nextButton);
		super.add(questionLabel);
		super.add(submitButton);

		super.add(answerOne);
		super.add(answerTwo);
		super.add(answerThree);

		super.add(progressQuetionBar);

		super.updateComponentsHitbox();
	}

	public void init() {
		for (Question question : controller.getQuestions()) {
			gameHistory.put(question, null);
		}
	}

	public Answer getAnswerFromLabel(String label) {
		for (Answer answer : loadedQuestion.getAnswers()) {
			if (answer.getLabel().equals(label)) {
				return answer;
			}
		}

		return null;
	}

	public void loadQuestion(Question question) {
		loadedQuestion = question;
		questionLabel.setContent(question.getLabel());

		answerOne.setContent(question.getAnswers().get(0).getLabel());
		answerTwo.setContent(question.getAnswers().get(1).getLabel());
		answerThree.setContent(question.getAnswers().get(2).getLabel());
	}

	public boolean areAllQuestionsAnswered() {
		for (Answer a : gameHistory.values()) {
			if (a == null)
				return false;
		}

		System.out.println("All questions answered !");
		return true;
	}

	/**
	 * Resets question pointer
	 */
	public void resetPointer() {
		qpointer = 0;
	}

	public void resetGameHistory() {
		gameHistory.clear();
	}

	@Override
	public void onMousePress() {
		previousButton.click();
		nextButton.click();
		submitButton.click();
		answerOne.click();
		answerTwo.click();
		answerThree.click();
	}

	@Override
	public void onCharPress(char key) {
	}

	@Override
	public void onKeyPress(int keycode) {
	}

	public ProgressBar getProgressQuetionBar() {
		return progressQuetionBar;
	}

}
