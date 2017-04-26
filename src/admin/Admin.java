package admin;

public class Admin {
	private int id;
	private String password;
	
	public Admin(int id, String password) {
		this.id = id;
		this.password = password;
	}
	
	
	public void addReader(String name, String address, String phoneNumber) {
		//create new reader save into DB
	}
	
	public void addBook(String title, String author, String ISBN, 
							String publisher, String publishDate) {
		
		//create a new book instance and save into DB
	}
	
	public static boolean checkLogin() {
		//if credentials are found in db
		
		return true;
	}
	
	

}
