import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import admin.AdminMenu;

public class Connect {
	
	
	//how to connect to mysql
	//http://stackoverflow.com/questions/2839321/connect-java-to-a-mysql-database/2840358#2840358
	
	private static final String URL = "jdbc:mysql://localhost:3306/librarydb";
	static String username = "java";
	static String password ="password";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Connecting database...");
		
		try {
			Connection con = DriverManager.getConnection( URL, username, password );
			System.out.println("connected to DB");
		}
		catch (SQLException err) {
			System.out.println(err.getMessage());
		}
		
		
		new AdminMenu();

	}

}
