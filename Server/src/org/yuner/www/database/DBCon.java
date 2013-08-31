package org.yuner.www.database;

import java.sql.*;

public class DBCon {

	private static final String url = "jdbc:mysql://localhost";
    private static final String user = "root";
    private static final String password = "qqmima654123";

	public static Connection getConnect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return null;
		}
	
		Connection con = null;
		try {
		    con = DriverManager.getConnection(url, user, password);
		    System.out.println("Success");
		} catch (Exception e) {
		    e.printStackTrace();
		}
		if(con != null) {
			System.out.println("you have successfully taken control of the database, enjoy!");
		} else {
			System.out.println("failed to make connection!!!");
		}

		return con;
	}

}
