package fr.thomas.engine.graphics.texture;

import fr.thomas.engine.GameEngine;
import fr.thomas.engine.graphics.sprite.SpriteSheet;

public class Animation {

	private SpriteSheet sp;
	private int frames;
	private boolean play;
	
	private int pointer = 0;
	private int frameFreeze;
	private int speed;

	public Animation(SpriteSheet spriteSheet) {
		this.sp = spriteSheet;
		this.frames = sp.getSprites().size();
		sp.renderSprite(0);
		this.play = true;
		this.speed = GameEngine.FPS_LIMIT * 2;
		this.frameFreeze = GameEngine.FPS / speed;
	}

	public void render() {
		if (play) {
 			if(frameFreeze > 0) {
 				frameFreeze--;
 				return;
 			}
 			frameFreeze = GameEngine.FPS / speed;
			
			sp.renderSprite(pointer);
			pointer++;
			
			if(pointer >= frames) {
				pointer = 0;
			}
		}
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
