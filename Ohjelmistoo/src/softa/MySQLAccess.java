package softa;

import java.sql.*;

public class MySQLAccess {
	public static void main(String[] args) {
		   try {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "DBA123");
		System.out.println("Connected With the database successfully");
		} catch (SQLException e) {
		System.out.println("Error while connecting to the database");
		}
	}
}
