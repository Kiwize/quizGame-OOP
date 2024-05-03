package fr.thomas.engine.entity;

public enum Direction {
	NORTH(0), EAST(1), SOUTH(2), WEST(3);
	
	private int ID;
	
	private Direction(int ID) {
		this.ID = ID;
	}
	
	public int getID() {
		return ID;
	}
}
