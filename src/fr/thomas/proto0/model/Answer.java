package fr.thomas.proto0.model;

import fr.thomas.proto0.net.object.AnswerNetObject;

public class Answer {
	
	private String label;
	private boolean isCorrect;
	private char qchar;
	
	public Answer(char qchar, String label, boolean isCorrect) {
		this.label = label;
		this.isCorrect = isCorrect;
		this.qchar = qchar;
	}
	
	public Answer(AnswerNetObject netObject) {
		this.label = netObject.getLabel();
		this.isCorrect = netObject.isCorrect();
	}
	
	public String getLabel() {
		return label;
	}
	
	public boolean isCorrect() {
		return isCorrect;
	}
	
	public char getQchar() {
		return qchar;
	}
}
