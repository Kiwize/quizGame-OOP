package fr.thomas.engine.entity;

import org.joml.Vector3f;

import fr.thomas.engine.GameEngine;
import fr.thomas.engine.graphics.Camera;
import fr.thomas.engine.graphics.Shader;
import fr.thomas.engine.graphics.sprite.SpriteSheet;
import fr.thomas.engine.graphics.texture.Animation;
import fr.thomas.engine.level.world.World;

public class Player extends Entity {
	
	private Animation anim;
	private Vector3f moveBuffer = new Vector3f();

	public Player(Camera camera, World world) {
		super(camera, world);
		super.shader = new Shader("player");
		sp = new SpriteSheet(model, 32, 32, 16, 16);
		spriteSheetTexture = GameEngine.instance.getTextureLoader().getTexture("sprites_test");
		anim = new Animation(sp);
		anim.setSpeed(2);
	}
	
	public void render() {
		anim.render();
		super.render();
	}

	public void move(Direction direction) {
		moveBuffer.set(0);
		switch (direction.getID()) {
		case 0:
			moveBuffer.set(0, -currentSpeed, 0);
			break;

		case 1:
			moveBuffer.set(-currentSpeed, 0, 0);
			break;

		case 2:
			moveBuffer.set(0, currentSpeed, 0);
			break;

		case 3:
			moveBuffer.set(currentSpeed, 0, 0);
			break;
		}

		position.sub(moveBuffer);
		camera.addPosition(moveBuffer);
	}
}
