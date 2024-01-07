package fr.thomas.proto0;

import fr.thomas.proto0.controller.GameController;

public class Start {

	private GameController game;
	
	public static void main(String[] args) {
		new Start().init();
	}
	
	public void init() {
		game = new GameController();
	}
	
}
	