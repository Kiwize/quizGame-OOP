package fr.thomas.proto0.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import fr.thomas.proto0.controller.GameController;
import fr.thomas.proto0.model.Answer;
import fr.thomas.proto0.model.Question;

public class PlayView extends JFrame {

	private GameController controller;
	private Question loadedQuestion;

	private JLabel questionLabel;

	private JPanel questionAnswersPanel;
	
	private HashMap<Answer, JRadioButton> answerButtonMap;

	public PlayView(GameController controller) {
		this.controller = controller;
		getContentPane().setLayout(null);

		setResizable(false);
		setSize(400, 400);
		
		answerButtonMap = new HashMap<>();

		JPanel hidQuestionPanel = new JPanel();
		hidQuestionPanel.setBounds(0, 0, 392, 58);
		getContentPane().add(hidQuestionPanel);
		hidQuestionPanel.setLayout(null);

		JLabel questionCounterLabel = new JLabel("0/0");
		questionCounterLabel.setBounds(12, 0, 71, 17);
		hidQuestionPanel.add(questionCounterLabel);

		questionLabel = new JLabel("<placeholder>");
		questionLabel.setBounds(12, 29, 368, 17);
		hidQuestionPanel.add(questionLabel);

		questionAnswersPanel = new JPanel();
		questionAnswersPanel.setBounds(38, 69, 323, 130);
		getContentPane().add(questionAnswersPanel);
		questionAnswersPanel.setLayout(null);

		JLabel lblQuestionInfos = new JLabel("question infos");
		lblQuestionInfos.setBounds(12, 211, 368, 74);
		getContentPane().add(lblQuestionInfos);

		JLabel scoreLabel = new JLabel("score");
		scoreLabel.setBounds(23, 323, 60, 17);
		getContentPane().add(scoreLabel);

		JButton submitButton = new JButton("Valider");
		submitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				submitQuestion();
			}
		});
		submitButton.setBounds(275, 318, 105, 27);
		getContentPane().add(submitButton);
	}

	public void loadQuestion(Question question) {
		this.loadedQuestion = question;
		questionLabel.setText(question.getLabel());
		questionAnswersPanel.removeAll();
		answerButtonMap.clear();
		
		for(Answer answer : question.getAnswers()) {
			JRadioButton jrb = new JRadioButton(answer.getLabel());
			questionAnswersPanel.add(jrb);
			answerButtonMap.put(answer, jrb);
		}
	}

	public void submitQuestion() {
		for(Map.Entry<Answer, JRadioButton> set : answerButtonMap.entrySet()) {
			if(set.getValue().isSelected()) {
				
			}
		}
	}

	private static final long serialVersionUID = -6452443507319459902L;
}
