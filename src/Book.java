import java.util.Date;

public class Book {

	private int bookId;
	private int ISBN;
	private int branchId;
	private String title;
	private String author;
	private String publisher;
	private Date pubDate;
	private Date bDateTime;
	private Date rDateTime;
	
	public Book(int bookId, int ISBN, String title, String author, String publisher, Date pubDate, int branchId){
		this.bookId = bookId;
		this.ISBN = ISBN;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.pubDate = pubDate;
	}
	
	public Book(){
		
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
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getPublisher() {
		return publisher;
	}
	
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public Date getPubDate() {
		return pubDate;
	}
	
	public void setPubDate(Date pubDate) {
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
