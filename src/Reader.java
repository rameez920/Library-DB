
public class Reader {

	private int readerId;
	private String firstName;
	private String lastName;
	private String address;
	private int phoneNumber;
	private int borrowCount;
	private int reserveCount;
	
	public Reader(int readerId, String firstName, String lastName, String address, int phoneNumber){
		this.readerId = readerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		borrowCount = 0;
		reserveCount = 0;
	}
	
	public boolean reserve(String bookTitle){
		//return true if book is successfully reserved
		//Reserved books must be picked up before 6pm o/w cancel
		//Can not reserve more than 10 books
		return true;
	}
	
	public void printReserved(){
		//Print stack of currently reserved books
		return;
	}
	
	public boolean borrow(String bookTitle){
		//return true if book is successfully borrowed
		return true;
	}
	
	public void printBorrowed(){
		//Print stack of currently borrowed books
		return;
	}
	
	public int calculateFine(Book book){
		//Calculate the fine on the book
		//(Current date - Due date) * 20 cents
		return 0;
	}
	
	public boolean returnBook(String bookTitle){
		
		return true;
	}
	
	public boolean returnBook(int bookIsbn){
		
		return true;
	}
	
	public int getReaderId() {
		return readerId;
	}
	
	public void setReaderId(int readerId) {
		this.readerId = readerId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
