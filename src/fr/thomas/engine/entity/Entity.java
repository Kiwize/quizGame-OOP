package fr.thomas.engine.entity;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import fr.thomas.engine.graphics.Camera;
import fr.thomas.engine.graphics.Model;
import fr.thomas.engine.graphics.Shader;
import fr.thomas.engine.graphics.sprite.SpriteSheet;
import fr.thomas.engine.graphics.texture.Texture;
import fr.thomas.engine.level.world.World;

public class Entity {
	protected Camera camera;

	protected float speedFactor = 3.5f;
	protected float currentSpeed = speedFactor * Model.ZOOM_LEVEL; // Makes the entity's speed proportional to the
																	// current zoom level.
	protected SpriteSheet sp;
	protected World world;
	protected Texture spriteSheetTexture;

	private Matrix4f target = new Matrix4f();
	private Matrix4f translationMatrix = new Matrix4f();

	protected Model model;
	protected Shader shader;

	protected Vector3f position = new Vector3f(0);

	public Entity(Camera camera, World world) {
		this.camera = camera;
		this.world = world;
		model = new Model(Model.vertices, Model.tex_coords);
	}

	public void defineSpritesheet(SpriteSheet sp) {
		this.sp = sp;
	}

	public void render() {
		shader.start();
		spriteSheetTexture.bind(0);
		translationMatrix.translate(position, target);
		shader.setUniform("textureSampler", 0);
		shader.setUniform("projectionMatrix",
				camera.getProjection().mul(world.getProjection().mul(target)).scale(Model.OBJECT_SCALE));
		model.render();
		shader.stop();
	}

	public void setSpeed(int speed) {
		this.speedFactor = speed;
	}
}
