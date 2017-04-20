import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

	private static final String URL = "";
	String username = "";
	String password ="";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			Connection con = DriverManager.getConnection( URL, username, password );
		}
		catch (SQLException err) {
			System.out.println(err);
		}


	}

}
