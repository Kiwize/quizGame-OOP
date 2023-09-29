package fr.thomas.proto0.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

	//TODO Move to env file
	private String bdname = "quizzgame_proto0";
	private String url = "jdbc:mysql://192.168.122.245:3306/";
	private String username = "_gateway";
	private String password = "dev";
	
	private Connection con;

	public DatabaseHelper() throws ClassNotFoundException, SQLException {
		try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		    e.printStackTrace();
		}
		
		this.url += this.bdname;
		this.con = DriverManager.getConnection(url, username, password);
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
	
	public void close()  {
		try {
			this.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
