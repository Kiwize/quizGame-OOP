package fr.thomas.proto0.model;

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
}
