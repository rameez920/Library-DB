

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Admin {
	
	private int id;
	private String password;
	
	public Admin(int id, String password) {
		this.id = id;
		this.password = password;
	}
	
	public void addReader(String name, String address, String phoneNumber) throws SQLException {
		
		Connection con = Connect.getConnection();
		
		String query = "INSERT into Reader (Reader_Name, Reader_Address, Reader_pNum)" +
						"VALUES (?, ?, ?)"; 
		
		PreparedStatement addReader = con.prepareStatement(query);
		
		addReader.setString(1, name);
		addReader.setString(2, address);
		addReader.setString(3, phoneNumber);
		
		addReader.execute();
		
		con.close();
	}
	
	public void addBook(String title, String author, int ISBN, int publisherID, 
							String publishDate, int branchID) throws SQLException {
		
		//update entry in author table
		//create new publisher if necessary
		Connection con = Connect.getConnection();
		
		String query1 = "INSERT into Book" + "(Title, P_ID, P_Date, ISBN, Branch_ID)" 
							+ "VALUES" + "(?, ?, ?, ?, ?)";
		
		PreparedStatement addBook = con.prepareStatement(query1);
		
		addBook.setString(1, title);
		addBook.setInt(2, publisherID);
		addBook.setString(3, publishDate);
		addBook.setInt(4, ISBN);
		addBook.setInt(5, branchID);
		
		addBook.execute();
		con.close();
	
		String query2 = "INSERT into Author";
		//need to get book id before save into author table
	}
	
	public static boolean checkLogin(int ID, String password) throws SQLException {
		
		Connection con = Connect.getConnection();
		PreparedStatement st = con.prepareStatement("select * from Admin where Admin_ID = ? and Admin_Password = ?");
		
		st.setInt(1, ID);
		st.setString(2, password);
		
		ResultSet rs = st.executeQuery();
		con.close();
		
		//if rs is empty then admin credentials do not exist in DB
		if (!rs.next()) 
			return false;
		else 
			return true;
	}
	
	//search branch by ID print name, location, 10 most frequent borrowers, 10 most borrowed books
	public Library getBranchInfo(int branchID) throws SQLException {
		
		Connection con = Connect.getConnection();
		PreparedStatement st = con.prepareStatement("select * from Branch where Branch_ID = ?");
		
		st.setInt(1, branchID);
		
		ResultSet rs = st.executeQuery();
		
		
		return null;
	
	}
	

}
