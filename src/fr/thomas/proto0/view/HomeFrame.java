package fr.thomas.proto0.view;

import fr.thomas.engine.GameEngine;
import fr.thomas.engine.graphics.texture.Font;
import fr.thomas.engine.graphics.ui.IOnClickCallback;
import fr.thomas.engine.graphics.ui.UserInterface;
import fr.thomas.engine.graphics.ui.component.Button;
import fr.thomas.engine.graphics.ui.component.GraphicalComponent;
import fr.thomas.engine.graphics.ui.component.Text;
import fr.thomas.proto0.controller.GameController;

public class HomeFrame extends UserInterface {
	
	private GameController controller;

	private Text gameName;
	private Text version;

	private Button monoplayerGame;
	private Button multiplayerGame;
	private Button quitButton;
	
	private Font font;

	public HomeFrame(GameController controller) {
		super("home_frame");
		this.controller = controller;
		this.font = GameEngine.instance.getFontManager().getLoadedFonts().get(0);
		this.gameName = new Text(0, 600, 50, 50, font, "Cybersecurity Quizzgame").centerHorizontally();
		this.version = new Text(0, 20, 20, 20, font, "Version " + GameEngine.VERSION).left();

		this.monoplayerGame = new Button(0, 500, 300, 40, "Solo").centerHorizontally();
		this.multiplayerGame = new Button(0, 450, 300, 40, "Multiplayer").centerHorizontally();
		this.quitButton = new Button(0, 40, 300, 40, "Quitter").centerHorizontally();
		
		quitButton.setOnClickCallback(new IOnClickCallback() {
			
			@Override
			public void onClick(GraphicalComponent component) {
				System.exit(0);
			}
		});
		
		monoplayerGame.setOnClickCallback(new IOnClickCallback() {
			
			@Override
			public void onClick(GraphicalComponent component) {
				controller.startGame();
			}
		});
		
		super.add(gameName);
		super.add(version);
		super.add(monoplayerGame);
		super.add(multiplayerGame);
		super.add(quitButton);

		super.updateComponentsHitbox();
	}

	@Override
	public void onMousePress() {
		monoplayerGame.click();
		multiplayerGame.click();
		quitButton.click();
	}

	@Override
	public void onCharPress(char key) {
	}
	
	@Override
	public void onKeyPress(int keycode) {
	}
	
}
