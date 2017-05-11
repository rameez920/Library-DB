

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
	
	public void addBook(String title, String author, int ISBN, String publisher, 
							String publishDate, String publisherAddress, int branchID) throws SQLException {
		
		//update entry in author table
		//create new publisher if necessary
		int publisherID = checkPublisher(publisher, publisherAddress);
		int authorID = checkAuthor(author);
		
		Connection con = Connect.getConnection();
		
		String query1 = "INSERT into Book" + "(Title, P_ID, P_Date, ISBN, Branch_ID, Auth_ID)" 
							+ "VALUES" + "(?, ?, ?, ?, ?, ?)";
		
		PreparedStatement addBook = con.prepareStatement(query1);
		
		addBook.setString(1, title);
		addBook.setInt(2, publisherID);
		addBook.setString(3, publishDate);
		addBook.setInt(4, ISBN);
		addBook.setInt(5, branchID);
		addBook.setInt(6, authorID);
		
		addBook.execute();
		con.close();
	
		
		con.close();

		//need to get book id before save into author table
	}
	
	//Checks publisher info when a new book is added
	//if publisher already exists returns that ID
	//else insert the new publisher into DB and return new ID
	private static int checkPublisher(String publisherName, String address) throws SQLException {
		Connection con = Connect.getConnection();
		
		String query = "SELECT * FROM Publisher where publisher.P_Name = ?";
		
		PreparedStatement st = con.prepareStatement(query);
		
		st.setString(1, publisherName);
		
		ResultSet rs = st.executeQuery();
		
		if (rs.next())
			return rs.getInt("P_ID");
		else {
			String query2 = "INSERT into Publisher(P_Name, P_Address) VALUES (?, ?)";
			PreparedStatement st1 = con.prepareStatement(query2);
			
			st1.setString(1, publisherName);
			st1.setString(2, address);
			
			st1.execute();
			
			String query3 = "SELECT publisher.P_ID FROM publisher where publisher.P_Name = ?";
			PreparedStatement st2 = con.prepareStatement(query3);
			
			st2.setString(1, publisherName);
			
			ResultSet pID = st2.executeQuery();
			
			while(pID.next()) {
				return pID.getInt("P_ID");
			}
			
			return 0;
		}
	}
	
	
	private static int checkAuthor(String authorName) throws SQLException {
		Connection con = Connect.getConnection();
		
		String query = "SELECT * FROM author where author.Auth_Name = ?";
		
		PreparedStatement st = con.prepareStatement(query);
		
		st.setString(1, authorName);
		
		ResultSet rs = st.executeQuery();
		
		if (rs.next())
			return rs.getInt("Auth_ID");
		else {
			String query2 = "INSERT into Author(Auth_Name) VALUES (?)";
			PreparedStatement st1 = con.prepareStatement(query2);
			
			st1.setString(1, authorName);
			
			st1.execute();
			
			String query3 = "SELECT publisher.P_ID FROM publisher where publisher.P_Name = ?";
			PreparedStatement st2 = con.prepareStatement(query3);
			
			st2.setString(1, authorName);
			
			ResultSet aID = st2.executeQuery();
			
			while(aID.next()) {
				return aID.getInt("Auth_ID");
			}
		}
		return 0;
}
	
	public static boolean checkLogin(int ID, String password) throws SQLException {
		
		Connection con = Connect.getConnection();
		PreparedStatement st = con.prepareStatement("select * from Admin where Admin_ID = ? and Admin_Password = ?");
		
		st.setInt(1, ID);
		st.setString(2, password);
		
		ResultSet rs = st.executeQuery();
		
		
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
		
		
		if (rs.next()) {
			String branchName = rs.getString("BR_Name");
			String address = rs.getString("BR_Location");
			
			con.close();
			return new Library(branchID, branchName, address);
		
		} else 
			return null;
	}
	
	public Book searchBook(int bookID) throws SQLException {
		
		Connection con = Connect.getConnection();
		PreparedStatement st = con.prepareStatement("select * from Book where Book_ID = ?");
		st.setInt(1, bookID);
		
		ResultSet rs = st.executeQuery();
		
		if (rs.next()){
			String title = rs.getString("Title");
			int pID = rs.getInt("P_ID");
			String pDate = rs.getString("P_Date");
			String bID = rs.getString("Branch_ID");
			int isbn = rs.getInt("ISBN");
			int aID = rs.getInt("Auth_ID");
		
			return new Book(bookID, isbn, title, aID, pID, pDate);
		
		}
		
		return null;
	
	}
	

}
