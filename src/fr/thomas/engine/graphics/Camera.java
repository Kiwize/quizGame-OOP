package fr.thomas.engine.graphics;

import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.joml.Vector3f;

import fr.thomas.engine.GameEngine;

public class Camera {
	private Vector3f position;
	private Vector2i worldPos;
	
	private Matrix4f projection;
	
	private Matrix4f target = new Matrix4f();
	private Matrix4f pos = new Matrix4f();
	
	
	private boolean isMoving = false;

	public Camera(int width, int height) {
		position = new Vector3f(0);
		worldPos = new Vector2i(0);
		projection = new Matrix4f().setOrtho2D(-width / 2, width / 2, -height / 2, height / 2);
		
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public void addPosition(Vector3f pos) {
		isMoving = true;
		this.position.add(pos);
		worldPos.set((int) (this.position.x + (GameEngine.instance.getWindow().getWidth() / 2)),(int) (this.position.y - (GameEngine.instance.getWindow().getHeight() / 2)));
	}

	public Vector3f getPosition() {
		return position;
	}
	
	public Vector2i getWorldPos() {
		return worldPos;
	}

	public void updateProjection(int width, int height) {
		projection.setOrtho2D(-width / 2, width / 2, -height / 2, height / 2);
	}

	public Matrix4f getProjection() {
		pos.setTranslation(position);
		projection.mul(pos, target);

		return target;
	}
	
	public boolean isMoving() {
		return isMoving;
	}
	
	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}
}