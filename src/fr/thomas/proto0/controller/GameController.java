package fr.thomas.proto0.controller;

import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.EnglishSequenceData;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

import fr.thomas.proto0.model.Answer;
import fr.thomas.proto0.model.Game;
import fr.thomas.proto0.model.Player;
import fr.thomas.proto0.model.Question;
import fr.thomas.proto0.view.ConsoleView;
import fr.thomas.proto0.view.HomeView;
import fr.thomas.proto0.view.LoginView;
import fr.thomas.proto0.view.PasswordChangeView;
import fr.thomas.proto0.view.PlayView;

public class GameController {

	private ConsoleView view;
	private Player player;
	private ArrayList<Question> questions;
	private Game game;
	private int diff;

	private LoginView loginView;
	private HomeView homeView;
	private PlayView playView;
	private PasswordChangeView passchView;

	private boolean isGameStarted = false;

	private PasswordValidator passwordValidator;

	/**
	 * @author Thomas PRADEAU
	 */
	public GameController() {
		// Créer la vue
		this.view = new ConsoleView(this);
		this.loginView = new LoginView(this);
		player = new Player("", this);
		this.homeView = new HomeView(this);
		this.loginView.setVisible(true);
		this.playView = new PlayView(this);
		this.passchView = new PasswordChangeView(this);

		passwordValidator = new PasswordValidator(new LengthRule(12, 24),
				new CharacterRule(EnglishCharacterData.LowerCase, 1),
				new CharacterRule(EnglishCharacterData.UpperCase, 1), new CharacterRule(EnglishCharacterData.Digit, 1),
				new CharacterRule(EnglishCharacterData.Special, 1),
				new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 4, false),
				new IllegalSequenceRule(EnglishSequenceData.Numerical, 4, false),
				new IllegalSequenceRule(EnglishSequenceData.USQwerty, 4, false), new WhitespaceRule());
	}

	public void playerAuth(String name, String password) {
		if (player.authenticate(name, password)) {
			this.loginView.setVisible(false);
			this.homeView.setVisible(true);
			this.game = new Game(this, player);
			this.homeView.updatePlayerData(player.getName(), game.getHighestScore(player));
		} else {
			System.err.println("Invalid password...");
		}
	}

	public void startGame() {
		if (!isGameStarted) {
			game.getRandomQuestions(); // Choisir les questions aléatoirement
			game.begin();
			isGameStarted = true;
			homeView.setPlayButtonState(false);
			homeView.setVisible(false);
			homeView.revalidate();
			homeView.repaint();

			// Partie terminee
			game.insert();
			this.homeView.updatePlayerData(player.getName(), game.getHighestScore(player));
		}
	}

	public void finishGame(HashMap<Question, Answer> gameHistory) {
		// TODO Show recap view
		isGameStarted = false;
		homeView.setPlayButtonState(true);
		homeView.setVisible(true);
		homeView.revalidate();
		homeView.repaint();

		playView.setVisible(false);

		view.showGameRecap(gameHistory);
	}

	/**
	 * Asks controller to show password change view if the logins provided by the
	 * user are correct.
	 * 
	 * @param username
	 * @param password
	 */
	public void submitPasswordChange(String username, String password) {
		if (player.authenticate(username, password)) {
			loginView.setVisible(false);
			passchView.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(loginView.getComponent(0), "Identifiants invalides.", "Erreur",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void changePassword(String password, String confirm) {
		if (password.equals(confirm)) {
			final PasswordData passwordData = new PasswordData(password);
			final RuleResult validate = passwordValidator.validate(passwordData);

			if (!validate.isValid()) {
				JOptionPane.showMessageDialog(loginView.getComponent(0), "Mot de passe non conforme !", "Erreur",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (player.updatePassword(password)) {
				JOptionPane.showMessageDialog(loginView.getComponent(0), "Mot de passe changé avec succés !",
						"Mot de passe modifié.", JOptionPane.INFORMATION_MESSAGE);

				loginView.dispatchEvent(new WindowEvent(loginView, WindowEvent.WINDOW_CLOSING));
				passchView.dispatchEvent(new WindowEvent(loginView, WindowEvent.WINDOW_CLOSING));
				homeView.setVisible(true);
				this.game = new Game(this, player);
				this.homeView.updatePlayerData(player.getName(), game.getHighestScore(player));
			}
		} else {
			JOptionPane.showMessageDialog(loginView.getComponent(0), "Les mots de passe sont différents !", "Erreur",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public HashMap<EPasswordError, Boolean> passwordFieldUpdate(String password, String confirmation) {
		final HashMap<EPasswordError, Boolean> errsBuffer = new HashMap<>();
		final String specialChars = "/*!@#$%^&*()\"{}_[]|\\?/<>,.";

		for (final EPasswordError error : EPasswordError.values()) {
			errsBuffer.put(error, true);
		}

		if (password.equals(confirmation)) {
			errsBuffer.replace(EPasswordError.SIMILAR_PASSWORDS, false);
		}

		if (password.length() >= 12) {
			errsBuffer.replace(EPasswordError.MIN_LENGTH, false);
		}

		for (int i = 0; i < password.length(); i++) {
			final char c = password.charAt(i);

			if (Character.isUpperCase(c)) {
				errsBuffer.replace(EPasswordError.REQUIRE_UPPERCASE, false);
			}

			if (Character.isLowerCase(c)) {
				errsBuffer.replace(EPasswordError.REQUIRE_LOWERCASE, false);
			}

			if (Character.isDigit(c)) {
				errsBuffer.replace(EPasswordError.REQUIRE_DIGIT, false);
			}

			if (specialChars.contains(password.substring(i, i + 1))) {
				errsBuffer.replace(EPasswordError.REQUIRE_SPECIAL, false);
			}
		}

		passchView.showPasswordStatus(errsBuffer);

		return errsBuffer;
	}

	public ConsoleView getView() {
		return view;
	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}

	public Player getPlayer() {
		return player;
	}

	public Game getGame() {
		return game;
	}

	public PlayView getPlayView() {
		return playView;
	}

	/**
	 * Password errors callback enumeration That allows to define error messages for
	 * each password requirement.
	 */
	public enum EPasswordError {
		// TODO, translate errors messages
		SIMILAR_PASSWORDS("Les mots de passes sont différents", (PasswordChangeView frame, boolean state) -> {
			frame.getChkboxSimilarPasswords().setSelected(state);
		}), REQUIRE_UPPERCASE("Une majuscule requise", (PasswordChangeView frame, boolean state) -> {
			frame.getChkboxMajuscule().setSelected(state);
		}), REQUIRE_LOWERCASE("Une minuscule requise", (PasswordChangeView frame, boolean state) -> {
			frame.getChkboxMinuscule().setSelected(state);
		}), REQUIRE_SPECIAL("Un caractère spécial requis", (PasswordChangeView frame, boolean state) -> {
			frame.getChkboxSpecialChar().setSelected(state);
		}), REQUIRE_DIGIT("Un chiffre requis", (PasswordChangeView frame, boolean state) -> {
			frame.getChkboxDigit().setSelected(state);
		}), MIN_LENGTH("Le mot de passe doit faire " + 12 + " caractères minimum",
				(PasswordChangeView frame, boolean state) -> {
					frame.getChkboxMinLength().setSelected(state);
				});

		private final String textError;
		private final IPasswordCallback callbackError;

		EPasswordError(String textError, IPasswordCallback callbackError) {
			this.textError = textError;
			this.callbackError = callbackError;
		}

		public String getTextError() {
			return textError;
		}

		public IPasswordCallback getCallbackError() {
			return callbackError;
		}
	}

}
