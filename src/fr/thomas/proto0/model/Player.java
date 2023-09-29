package fr.thomas.proto0.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.thomas.proto0.controller.GameController;
import fr.thomas.proto0.utils.DatabaseHelper;

public class Player implements IModel {

	private int id;
	private String name;
	private GameController controller;

	public Player(String name, GameController controller) {
		this.name = name;
		this.controller = controller;
	}

	public String getName() {
		return name;
	}

	public boolean authenticate(String name) {
		try {
			DatabaseHelper db = new DatabaseHelper();

			Statement st = db.create();
			ResultSet set = st.executeQuery("SELECT idplayer, name FROM Player WHERE name = '" + name + "';");

			if (!set.next()) {
				System.err.println("Unknown user");
				db.close();
				return false;
			} else {
				this.id = set.getInt("idplayer");
				this.name = set.getString("name");
				
				db.close();
				return true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean insert() {
		try {
			DatabaseHelper db = new DatabaseHelper();
			Statement st = db.create();

			boolean res = st.execute("INSERT INTO Player (name) VALUES ('" + name + "');");
			db.close();

			return res;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean save() {
		try {
			DatabaseHelper db = new DatabaseHelper();
			Statement st = db.create();

			boolean res = st.execute("UPDATE Player SET name = '" + name + "' WHERE Player.idplayer=" + id + ";");
			db.close();

			return res;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public int getID() {
		return id;
	}
}
