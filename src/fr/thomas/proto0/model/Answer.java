package fr.thomas.proto0.model;

public class Answer {
	
	private String label;
	
	private boolean isCorrect;
	
	public Answer(String label, boolean isCorrect) {
		this.label = label;
		this.isCorrect = isCorrect;
	}
	
	public String getLabel() {
		return label;
	}
	
	public boolean isCorrect() {
		return isCorrect;
	}

}
