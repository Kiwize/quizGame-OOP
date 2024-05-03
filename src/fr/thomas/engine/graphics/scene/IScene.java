package fr.thomas.engine.graphics.scene;

public interface IScene {

	void onLoad();

	void render();
	
	void update();

	void onUnload();

}
