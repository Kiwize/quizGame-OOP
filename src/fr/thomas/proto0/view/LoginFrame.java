package fr.thomas.proto0.view;

import fr.thomas.engine.graphics.ui.IOnClickCallback;
import fr.thomas.engine.graphics.ui.UserInterface;
import fr.thomas.engine.graphics.ui.component.Button;
import fr.thomas.engine.graphics.ui.component.GraphicalComponent;
import fr.thomas.engine.graphics.ui.component.TextField;
import fr.thomas.proto0.controller.GameController;

public class LoginFrame extends UserInterface {

	private GameController controller;

	private Button submitButton;

	private TextField username;
	private TextField password;

	public LoginFrame(GameController controller) {
		super("login_frame");
		this.controller = controller;
		submitButton = new Button(0, 250, 250, 30, "Submit").centerHorizontally();
		username = new TextField(0, 350, 200, 30, "Username").centerHorizontally();
		password = new TextField(0, 300, 200, 30, "Password").centerHorizontally();
		password.hideContent(true);

		submitButton.setOnClickCallback(new IOnClickCallback() {

			@Override
			public void onClick(GraphicalComponent component) {
				controller.playerAuth(username.getValue(), password.getValue());
			}
		});

		super.add(submitButton);
		super.add(username);
		super.add(password);

		super.updateComponentsHitbox();
	}

	@Override
	public void onMousePress() {
		submitButton.click();
		username.click();
		password.click();
	}

	@Override
	public void onCharPress(char key) {
		username.updateCharPress(key);
		password.updateCharPress(key);
	}

	@Override
	public void onKeyPress(int keycode) {
		username.updateKeyPress(keycode);
		password.updateKeyPress(keycode);
	}
}
