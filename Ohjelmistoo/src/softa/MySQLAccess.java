package softa;

import java.sql.*;

public class MySQLAccess {
	public static void main(String[] args) { // tehk‰‰ vaa muutoksia, t‰‰ oli testailua vaan
		   try {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "root"); 
		System.out.println("Connected With the database successfully");
		PreparedStatement preparedStatement=connection.prepareStatement("select * from henkilo");
        //Creating Java ResultSet object
        ResultSet resultSet=preparedStatement.executeQuery();
        while(resultSet.next()){
             String rollNo=resultSet.getString("idHenkilo");
             String name=resultSet.getString("nimi");
             String dept=resultSet.getString("rooli");
             //Printing Results
             System.out.println(rollNo+" "+name+" "+dept);
        }
		} catch (SQLException e) {
		System.out.println("Error while connecting to the database");
		}
	}
}
