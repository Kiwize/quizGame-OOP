package fr.thomas.engine.graphics.scene;

public class Scene implements IScene{

	private String name;
	
	public Scene(String name) {
		this.name = name;
	}
	
	@Override
	public void onLoad() {
		System.out.println("Loading scene " + name);
	}

	@Override
	public void render() {
	}

	@Override
	public void update() {
	}
	
	@Override
	public void onUnload() {
		System.out.println("Unloading scene " + name);
	}
	
}
