package fr.thomas.proto0.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import fr.thomas.proto0.model.Question;

public class QuestionView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private String QuestionLabel;
	private Question question;

	/**
	 * Create the frame.
	 */
	public QuestionView() {
		this.QuestionLabel = question.getLabel();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblScoreLabel = new JLabel("Score :");
		lblScoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblScoreLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblScoreLabel.setBounds(10, 10, 71, 45);
		contentPane.add(lblScoreLabel);
		
		JLabel lblScoreNumber = new JLabel("Score Number");
		lblScoreNumber.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblScoreNumber.setBounds(78, 13, 187, 38);
		contentPane.add(lblScoreNumber);
		
		JLabel lblQuestionLabel = new JLabel(QuestionLabel);
		lblQuestionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuestionLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblQuestionLabel.setBounds(10, 61, 416, 74);
		contentPane.add(lblQuestionLabel);
		
		JButton btnA = new JButton("A.");
		btnA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnA.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnA.setBounds(10, 145, 187, 45);
		contentPane.add(btnA);
		
		JButton btnB = new JButton("B.");
		btnB.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnB.setBounds(239, 145, 187, 45);
		contentPane.add(btnB);
		
		JButton btnC = new JButton("C.");
		btnC.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnC.setBounds(10, 208, 187, 45);
		contentPane.add(btnC);
		
		JButton btnD = new JButton("D.");
		btnD.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnD.setBounds(239, 208, 187, 45);
		contentPane.add(btnD);
	}
}
