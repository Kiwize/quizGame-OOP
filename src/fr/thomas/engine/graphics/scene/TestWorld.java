package fr.thomas.engine.graphics.scene;

import fr.thomas.engine.GameEngine;
import fr.thomas.engine.InputManager;
import fr.thomas.engine.entity.Player;
import fr.thomas.engine.level.world.World;

public class TestWorld extends Scene {

	private Player player;
	private World world;
	private InputManager in;

	public TestWorld(String name) {
		super(name);
		world = new World(128, 128);
		player = new Player(GameEngine.instance.getCamera(), world);
		in = new InputManager(GameEngine.instance.getWindow().getWindow(), player);
	}

	@Override
	public void onLoad() {
		super.onLoad();
	}

	@Override
	public void update() {
		world.tickUpdate();
		super.update();
	}

	@Override
	public void render() {
		in.updateInput();
		world.render();
		player.render();
		super.render();
	}

	@Override
	public void onUnload() {
		super.onUnload();
	}

	public Player getPlayer() {
		return player;
	}

}
