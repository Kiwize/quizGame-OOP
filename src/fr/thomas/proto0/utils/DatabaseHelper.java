package fr.thomas.proto0.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import fr.thomas.proto0.controller.GameController;
import fr.thomas.proto0.log.ELogLevel;

public class DatabaseHelper {

	// TODO Move to env file
	private String bdname = "";
	private String url = "";
	private String username = "";
	private String password = "";
	private GameController myController;

	private Connection con;

	private final int ACTIVE_STATEMENT_COUNT = 2;
	private ArrayList<Statement> activeStatements;

	public DatabaseHelper(GameController controller) throws ClassNotFoundException, SQLException {
		
		myController = controller;
		
		bdname = myController.getMyConfig().readAParam("db.name");
		url = myController.getMyConfig().readAParam("db.url");
		username = myController.getMyConfig().readAParam("db.username");
		password = myController.getMyConfig().decryptAParam("db.password");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			this.url += this.bdname;
			this.con = DriverManager.getConnection(url, username, password);
		} catch (CommunicationsException e) {
			controller.getLogger().log("Database connexion error", ELogLevel.CRITICAL);
			controller.getLogger().log("Exiting application...", ELogLevel.CRITICAL);
			System.exit(-2);
		}

		try {
			activeStatements = new ArrayList<Statement>();
			for (int i = 0; i < ACTIVE_STATEMENT_COUNT; i++) {
				activeStatements.add(con.createStatement());
			}
		} catch (NullPointerException e) {}
	}

	public Statement create() {
		try {
			return con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Connection getCon() {
		return con;
	}
	
	public Statement getStatement(int id) {
		if(id <= ACTIVE_STATEMENT_COUNT - 1 && id >= 0) {
			return activeStatements.get(id);
		}
		
		throw new IllegalArgumentException("Invalid statement ID !");
	}

	public void close() {
		try {
			this.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
