package com.way.chat.common.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * 
 * @author way
 * 
 */
public class DButil {
	/**
	 *
	 * 
	 * @return
	 */
	public static Connection connect() {
		String url = "jdbc:mysql://localhost";
		String user = "root";
		String password = "qqmima654123";

		System.out.println("trying to connect");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return null;
		}
		Connection mConnection = null;

		System.out.println("to connect!!!");

        try {

			System.out.println("start connecting!!");

            mConnection = DriverManager.getConnection(url, user, password);
            System.out.println("Success");
        } catch (Exception e) {
            e.printStackTrace();
        }
		if(mConnection != null) {
			System.out.println("you have successfully taken control of the database, enjoy!");
		} else {
			System.out.println("failed to make connection!!!");
		}
		
		return mConnection;
/*		Properties pro = new Properties();
		String driver = null;
		String url = null;
		String username = null;
		String password = null;
		try {
			InputStream is = DButil.class.getClassLoader()
					.getResourceAsStream("DB.properties");
			// System.out.println(is.toString());
			pro.load(is);
			driver = pro.getProperty("driver");
			url = pro.getProperty("url");
			username = pro.getProperty("username");
			password = pro.getProperty("password");
//			 System.out.println(driver + ":" + url + ":" + username + ":"
//			 + password);
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, username,
					password);
			return conn;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
*/
	}

	/**
	 * 
	 * 
	 * @param conn
	 *           
	 */
	public static void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

//	public static void main(String[] args) {
//		Connection con = new DButil().connect();
//		System.out.println(con);
//	}
}
