import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Library {

	private int libId;
	private String branchName;
	private String address;
	private Book[] bookName;
	private int[] numOfCopies;
	private Reader[] topReaders = new Reader[10]; 
	private Book[] topBooks = new Book[10];
	
	
	public Library(int libId, String branchName, String address){
		this.libId = libId;
		this.branchName = branchName;
		this.address = address;
	}
	
	
	public int getLibId() {
		return libId;
	}

	public String getBranchName() {
		return branchName;
	}

	public String getAddress() {
		return address;
	}


	public Reader[] getTopReaders() {
		String query = "SELECT borrow.Reader_ID, COUNT(borrow.Book_ID) "
						+ "FROM borrow LEFT JOIN reader ON borrow.Reader_ID = reader.Reader_ID "
						+ "GROUP BY borrow.Reader_ID IN "
						+ "(SELECT borrow.Reader_ID FROM borrow, book"
						+ "WHERE borrow.Book_ID = book.Book_ID AND book.Branch_ID = ?)"; 
		
		Connection con = Connect.getConnection();
		
		try {
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, this.libId);
			ResultSet rs = st.executeQuery();
		
			while (rs.next()) {
				
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return topReaders;
	} 
	
	public Book[] getTopBooks() {
		String query = "SELECT borrow.Book_ID, Count(borrow.Reader_ID) "
 				+ "FROM borrow left join book ON borrow.Book_ID = book.Book_ID "
 				+ "WHERE book.Branch_ID = ? group by borrow.Book_ID";
		
		Connection con = Connect.getConnection();
		
		try {
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, this.libId);
			ResultSet rs = st.executeQuery();
		
			while (rs.next()) {
				
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return topBooks;
	} 
	
	
}
