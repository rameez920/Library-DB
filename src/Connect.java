
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {
	
	
	//how to connect to mysql
	//http://stackoverflow.com/questions/2839321/connect-java-to-a-mysql-database/2840358#2840358
	
	private static final String URL = "jdbc:mysql://localhost:3306/librarydb";
	static String username = "java";
	static String password ="password";
	static String selectQuery= "SELECT * FROM Book";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Connecting database...");
		
		//Try with resources so connection auto closes
		try {
					Connection con = DriverManager.getConnection( URL, username, password); 
					System.out.println("Connected to Database: ");
					
					Admin admin = new Admin(112, "password");
					
					admin.addReader("James Jones", "123 Cleaveland OH", "84511226633");
				}
				catch (SQLException err) {
					System.out.println(err.getMessage());
				}
		
		
		new AdminMenu();

	}
	
	
	public static Connection getConnection() {
		try {
			
			Connection con = DriverManager.getConnection( URL, username, password);
			
			return con;
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		} 
		return null;
	}

}
