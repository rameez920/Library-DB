
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
	
	
	public Reader[] getTopReaders() {
		
		
		
		
		
		return topReaders;
	} 
	
	public Book[] getTopBooks() {
		
		
		
		
		
		return topBooks;
	} 
	
	
}
