package fr.thomas.proto0.view.swing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;

import fr.thomas.proto0.controller.GameController;
import fr.thomas.proto0.model.Answer;
import fr.thomas.proto0.model.Question;

public class PlayView extends JFrame {

	private GameController controller;
	private Question loadedQuestion;
	private int qpointer;
	
	private ArrayList<Question> gameQuestions;

	private JLabel questionLabel;

	private JPanel questionAnswersPanel;

	private JProgressBar progressQuestionBar;

	private JButton nextButton;
	private JButton prevButton;

	private HashMap<Question, ButtonGroup> answerButtonMap;
	private HashMap<Question, Answer> gameHistory;

	private int onlineGameID = -1;
	
	private static final long serialVersionUID = -6452443507319459902L;
	private JButton submitButton;
	
	private int timeToAnswer;

	public PlayView(GameController controller) {
		this.controller = controller;
		getContentPane().setLayout(null);

		this.gameQuestions = new ArrayList<>();

		setResizable(false);
		setSize(400, 400);

		answerButtonMap = new HashMap<>();
		gameHistory = new HashMap<>();

		JPanel hidQuestionPanel = new JPanel();
		hidQuestionPanel.setBounds(0, 0, 392, 58);
		getContentPane().add(hidQuestionPanel);
		hidQuestionPanel.setLayout(null);

		questionLabel = new JLabel("<placeholder>");
		questionLabel.setBounds(12, 29, 368, 17);
		hidQuestionPanel.add(questionLabel);

		progressQuestionBar = new JProgressBar();
		progressQuestionBar.setBounds(12, 0, 368, 14);
		hidQuestionPanel.add(progressQuestionBar);

		questionAnswersPanel = new JPanel();
		questionAnswersPanel.setBounds(38, 69, 323, 130);
		getContentPane().add(questionAnswersPanel);
		questionAnswersPanel.setLayout(null);

		nextButton = new JButton("Suivant");
		nextButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (qpointer < gameQuestions.size() - 1) {
					prevButton.setEnabled(true);
					qpointer++;
					loadQuestion(gameQuestions.get(qpointer));
					progressQuestionBar.setValue(qpointer + 1);

					revalidate();
					repaint();
				} else {
					nextButton.setEnabled(false);
				}
			}
		});
		nextButton.setBounds(122, 329, 105, 27);
		getContentPane().add(nextButton);

		prevButton = new JButton("Précédent");
		prevButton.setEnabled(false);
		prevButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (qpointer > 0) {
					nextButton.setEnabled(true);
					qpointer--;
					loadQuestion(gameQuestions.get(qpointer));
					progressQuestionBar.setValue(qpointer + 1);
					revalidate();
					repaint();
				} else {
					prevButton.setEnabled(false);
				}
			}
		});
		prevButton.setBounds(12, 329, 105, 27);
		getContentPane().add(prevButton);

		submitButton = new JButton("Terminer");
		submitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (submitButton.isEnabled())
					controller.finishGame(gameHistory);
			}
		});
		submitButton.setEnabled(false);
		submitButton.setBounds(275, 329, 105, 27);
		getContentPane().add(submitButton);
	}
	
	/**
	 * Only use this method if this view is used in a multiplayer context
	 */
	public void initMultiplayerContext(int onlineGameID) {
		nextButton.setVisible(false);
		prevButton.setVisible(false);
		submitButton.setVisible(false);
		this.onlineGameID = onlineGameID;
	}
	
	public void disableMultiplayerContext() {
		nextButton.setVisible(true);
		prevButton.setVisible(true);
		submitButton.setVisible(true);
		this.onlineGameID = -1;
	}
	
	
	public void setTimeToAnswer(int timeToAnswer) {
		this.timeToAnswer = timeToAnswer;
	}
	 
	public void loadSingleQuestion(Question question) {
		loadedQuestion = question;
		int i = 0;
		questionLabel.setText(question.getLabel());
		this.progressQuestionBar.setValue(this.timeToAnswer);
		
		ButtonGroup buttonGroup = new ButtonGroup();

		questionAnswersPanel.removeAll();

		for (Answer answer : question.getAnswers()) {
			JRadioButton jrb = new JRadioButton(answer.getLabel());
			jrb.setBounds(0, 0 + (i * 45), 300, 50);
			jrb.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					System.out.println("Selected answer : " + answer.getLabel());
					controller.sendChoosenAnswer(answer, onlineGameID);
				}
			});
			buttonGroup.add(jrb);
			i++;
		}

		answerButtonMap.put(question, buttonGroup);
		revalidate();
		repaint();
		loadQuestion(question);
	}
	
	public void loadUIQuestions(ArrayList<Question> questions) {
		this.gameQuestions = questions;
		this.qpointer = 0;
		this.progressQuestionBar.setMaximum(gameQuestions.size());
		this.progressQuestionBar.setValue(1);

		for (Question question : questions) {
			this.gameHistory.put(question, null);

			int i = 0;
			questionLabel.setText(question.getLabel());

			ButtonGroup buttonGroup = new ButtonGroup();

			questionAnswersPanel.removeAll();

			for (Answer answer : question.getAnswers()) {
				JRadioButton jrb = new JRadioButton(answer.getLabel());
				jrb.setBounds(0, 0 + (i * 45), 300, 50);
				jrb.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						gameHistory.put(question, answer);

						if (areAllQuestionsAnswered())
							submitButton.setEnabled(true);
					}
				});
				buttonGroup.add(jrb);
				i++;
			}

			answerButtonMap.put(question, buttonGroup);
		}

		loadQuestion(questions.get(qpointer));
	}

	public boolean areAllQuestionsAnswered() {
		for (Answer a : gameHistory.values()) {
			if (a == null)
				return false;
		}

		return true;
	}
	
	public void updateTimeLeftToAnswer(int timeLeft, int maxTime) {
		this.progressQuestionBar.setMaximum(maxTime);
		this.progressQuestionBar.setValue(timeLeft);
	}

	public void loadQuestion(Question question) {
		questionAnswersPanel.removeAll();
		ButtonGroup group = answerButtonMap.get(question);

		Enumeration<AbstractButton> buttons = group.getElements();
		while (buttons.hasMoreElements()) {
			JRadioButton button = (JRadioButton) buttons.nextElement();

			questionAnswersPanel.add(button);
		}

		questionLabel.setText(question.getLabel());
		revalidate();
		repaint();
	}

	public Question getLoadedQuestion() {
		return loadedQuestion;
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
	
	public int getOnlineGameID() {
		return onlineGameID;
	}

	
}
