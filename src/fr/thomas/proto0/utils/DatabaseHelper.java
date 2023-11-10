package fr.thomas.proto0.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseHelper {

	// TODO Move to env file
	private String bdname = "";
	private String url = "";
	private String username = "";
	private String password = "";

	private Connection con;

	private final int ACTIVE_STATEMENT_COUNT = 2;
	private ArrayList<Statement> activeStatements;

	public DatabaseHelper() throws ClassNotFoundException, SQLException {

		BufferedReader reader;
		String cdx = "";

		try {
			reader = new BufferedReader(new FileReader("resources/data/db.env"));
			String line = reader.readLine();

			while (line != null) {
				cdx += line + ";";
				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			System.err.println("Database configuration file 'ressources/data/db.env' is missing !");
			System.exit(-1);
		}

		String[] arrc = cdx.split(";");

		bdname = arrc[0];
		url = arrc[1];
		username = arrc[2];
		password = arrc[3];

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		this.url += this.bdname;
		this.con = DriverManager.getConnection(url, username, password);

		activeStatements = new ArrayList<Statement>();
		for (int i = 0; i < ACTIVE_STATEMENT_COUNT; i++) {
			activeStatements.add(con.createStatement());
		}
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
