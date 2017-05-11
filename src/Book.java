import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Book {

	private int bookId;
	private int ISBN;
	private String title;
	private int authorID;
	private int publisher;
	private String pubDate;
	private Date bDateTime;
	private Date rDateTime;
	
	
	public Book(int bookId, int ISBN, String title, int author, int publisher, String pubDate){
		this.bookId = bookId;
		this.ISBN = ISBN;
		this.title = title;
		this.authorID = author;
		this.publisher = publisher;
		this.pubDate = pubDate;
		
	}
	
	public String getStatus() throws SQLException {
		String status = null;
		
		Connection con = Connect.getConnection();
		PreparedStatement st = con.prepareStatement("select * from borrow where Book_ID = ?");
		
		st.setInt(1, this.bookId);
		
		ResultSet rs = st.executeQuery();
		
		if (rs.next()) 
			status = "Borrowed";
		else {
			PreparedStatement st2 = con.prepareStatement("select * from reserve where Book_ID = ?");
			
			st2.setInt(1, this.bookId);
			
			ResultSet rs2 = st.executeQuery();
			
			if (rs2.next()) 
				status = "Reserved";
			else
				status = "Available";
		} 
			
		return status;
	}
	
	public int getBookId() {
		return bookId;
	}
	
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	
	public int getISBN() {
		return ISBN;
	}
	
	public void setISBN(int iSBN) {
		ISBN = iSBN;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getAuthor() {
		return authorID;
	}
	
	public void setAuthor(int author) {
		this.authorID = author;
	}
	
	public int getPublisher() {
		return publisher;
	}
	
	public void setPublisher(int publisher) {
		this.publisher = publisher;
	}
	
	public String getPubDate() {
		return pubDate;
	}
	
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public Date getbDateTime() {
		return bDateTime;
	}

	public void setbDateTime(Date bDateTime) {
		this.bDateTime = bDateTime;
	}

	public Date getrDateTime() {
		return rDateTime;
	}

	public void setrDateTime(Date rDateTime) {
		this.rDateTime = rDateTime;
	}
	
}
