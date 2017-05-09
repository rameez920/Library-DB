
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
					
					
					String query3 = "SELECT publisher.P_ID FROM publisher where publisher.P_Name = ?";
					PreparedStatement st2 = con.prepareStatement(query3);
					
					st2.setString(1, "Random House");
					
					ResultSet pID = st2.executeQuery();
					
					while(pID.next())
						System.out.println(pID.getInt("P_ID"));
					
					con.close();
				
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
	
	private static void generateReaders() {
		
	}

}
