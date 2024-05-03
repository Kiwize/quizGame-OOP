package fr.thomas.proto0.model;

public interface IModel {
	
	/**
	 * Insert new record into database
	 * @return
	 */
	public abstract boolean insert();
	
	/**
	 * Update record in database if exists
	 * @return
	 */
	public abstract boolean save();

}
