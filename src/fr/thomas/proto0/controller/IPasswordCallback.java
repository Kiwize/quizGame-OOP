package fr.thomas.proto0.controller;

import fr.thomas.proto0.view.swing.PasswordChangeView;

public interface IPasswordCallback {

	void match(PasswordChangeView view, boolean state);

}
