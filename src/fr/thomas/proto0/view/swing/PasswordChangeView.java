package fr.thomas.proto0.view.swing;

import java.awt.Label;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import fr.thomas.proto0.controller.GameController;
import fr.thomas.proto0.controller.GameController.EPasswordError;

public class PasswordChangeView extends JFrame {

	private static final long serialVersionUID = 8961167478637393103L;

	private final JTextField passwordField;
	private final JTextField confirmationPasswordField;

	private final JCheckBox chkboxSimilarPasswords;
	private final JCheckBox chkboxMajuscule;
	private final JCheckBox chkboxMinuscule;
	private final JCheckBox chkboxSpecialChar;
	private final JCheckBox chkboxMinLength;
	private final JCheckBox chkboxDigit;

	public PasswordChangeView(GameController controller) {
		setResizable(false);
		getContentPane().setLayout(null);

		final Label lblMotDePasse = new Label("Mot de passe : ");
		lblMotDePasse.setBounds(12, 46, 98, 17);
		getContentPane().add(lblMotDePasse);

		final Label lblConfirmation = new Label("Confirmation :");
		lblConfirmation.setBounds(12, 93, 98, 17);
		getContentPane().add(lblConfirmation);

		passwordField = new JTextField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				controller.passwordFieldUpdate(passwordField.getText(), confirmationPasswordField.getText());
			}
		});
		passwordField.setBounds(117, 44, 114, 21);
		getContentPane().add(passwordField);
		passwordField.setColumns(10);

		confirmationPasswordField = new JTextField();
		confirmationPasswordField.setBounds(117, 91, 114, 21);
		getContentPane().add(confirmationPasswordField);
		confirmationPasswordField.setColumns(10);
		confirmationPasswordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				controller.passwordFieldUpdate(passwordField.getText(), confirmationPasswordField.getText());
			}
		});

		final Label lblFor = new Label("");
		lblFor.setBounds(12, 131, 164, 17);
		getContentPane().add(lblFor);

		final JButton btnValider = new JButton("Valider");
		btnValider.setLocation(56, 176);
		btnValider.setSize(120, 30);
		btnValider.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.changePassword(passwordField.getText(), confirmationPasswordField.getText());
			}
		});
		getContentPane().add(btnValider);

		chkboxSimilarPasswords = new JCheckBox("Mots de passes identiques");
		chkboxSimilarPasswords.setBounds(270, 8, 214, 25);
		chkboxSimilarPasswords.setEnabled(false);
		getContentPane().add(chkboxSimilarPasswords);

		chkboxMajuscule = new JCheckBox("Une majuscule");
		chkboxMajuscule.setBounds(270, 39, 114, 25);
		getContentPane().add(chkboxMajuscule);
		chkboxMajuscule.setEnabled(false);

		chkboxMinuscule = new JCheckBox("Une minuscule");
		chkboxMinuscule.setBounds(270, 68, 114, 25);
		getContentPane().add(chkboxMinuscule);
		chkboxMinuscule.setEnabled(false);

		chkboxSpecialChar = new JCheckBox("Un caractère spécial");
		chkboxSpecialChar.setBounds(270, 124, 214, 25);
		getContentPane().add(chkboxSpecialChar);
		chkboxSpecialChar.setEnabled(false);

		chkboxMinLength = new JCheckBox("12 caractères minimum");
		chkboxMinLength.setBounds(270, 153, 214, 25);
		getContentPane().add(chkboxMinLength);
		chkboxMinLength.setEnabled(false);

		chkboxDigit = new JCheckBox("Un chiffre");
		chkboxDigit.setBounds(270, 97, 114, 25);
		getContentPane().add(chkboxDigit);
		chkboxDigit.setEnabled(false);
		super.setSize(500, 250);
	}

	public void showPasswordStatus(HashMap<EPasswordError, Boolean> errors) {
		for(final EPasswordError eerr : EPasswordError.values()) {
			eerr.getCallbackError().match(this, !errors.get(eerr));
		}
	}

	public JCheckBox getChkboxSimilarPasswords() {
		return chkboxSimilarPasswords;
	}

	public JCheckBox getChkboxMinLength() {
		return chkboxMinLength;
	}

	public JCheckBox getChkboxMajuscule() {
		return chkboxMajuscule;
	}

	public JCheckBox getChkboxSpecialChar() {
		return chkboxSpecialChar;
	}

	public JCheckBox getChkboxMinuscule() {
		return chkboxMinuscule;
	}

	public JCheckBox getChkboxDigit() {
		return chkboxDigit;
	}
}
