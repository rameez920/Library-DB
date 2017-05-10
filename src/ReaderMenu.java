
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ReaderMenu{
	
	private final String URL = "jdbc:mysql://localhost:3306/librarydb";
	private String username = "root";
	private String password ="java";
	private String readerId;
	private int checkOutCount;
	private int reserveCount;
	private double bookFine;
		
	public ReaderMenu(String readerId){
		this.readerId = readerId;
		createReaderMenu();
		checkOutCount = checkOutCount();
		reserveCount = reserveCount();
	}
	
	public void search(String str){
		
		String[] columnNames = {"Book_ID", "Title", "P_ID", "P_DATE", "Branch_ID", "ISBN", "Auth_ID"};
		
		Object[][] data = new Object[200][7];
		
		String query = "SELECT Book_ID, Title, Book.P_ID, P_Date, Branch_ID, ISBN, Auth_ID FROM Book, "
				+ "Publisher WHERE (Title LIKE '%" + str + "%' OR BOOK_ID = '" + str + "' OR P_NAME = '" + str + "') AND "
						+ "Book.P_ID = Publisher.P_ID GROUP BY Title";
		
		try(Connection con = DriverManager.getConnection(URL, username, password);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query);){
			
			int i = 0;
			while(rs.next()){
				data[i][0] = rs.getString("BOOK_ID");
				data[i][1] = rs.getString("Title");
				data[i][2] = rs.getString("P_ID");
				data[i][3] = rs.getString("P_Date");
				data[i][4] = rs.getString("Branch_ID");
				data[i][5] = rs.getString("ISBN");
				data[i][6] = rs.getString("Auth_ID");
				i++;
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		createSearchMenu(data, columnNames);
	}
	
	public void checkOut(String book_id){
		
		if(checkOutCount == 10) return;
		
		//See if book is in the reader's reserve table, if it is, then you can check it out
		
		String query = "SELECT Book_ID From Reserve WHERE Reader_ID = " + readerId + " AND Book_ID = " + book_id;
		String query2 = "INSERT into Borrow(Reader_ID, Book_ID, B_Date, R_Date) VALUES (" + readerId + ", "
				+ book_id + ", current_date(), date_add(current_date(), interval 7 day))";
		String query3 = "DELETE FROM Reserve WHERE Book_ID = " + book_id + " AND Reader_ID = " + readerId;
		
		try(Connection con = DriverManager.getConnection(URL, username, password);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query);){
			
			if(!rs.next()) return;
			
			int bookid = rs.getInt("Book_ID");
			
			if(bookid > 0){
				
				try(Connection con2 = DriverManager.getConnection(URL, username, password);
						PreparedStatement preparedStmt = con2.prepareStatement(query2)){

					preparedStmt.execute();
				}
				
				try(Connection con3 = DriverManager.getConnection(URL, username, password);
						PreparedStatement preparedStmt = con.prepareStatement(query3);){
					
					preparedStmt.execute();
					reserveCount--;
				}
				
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		checkOutCount++;
	}
			
	public void returnBook(String book_id){
		
		//Remove from the checkOut table
		
		String query = "DELETE FROM Borrow WHERE Book_ID = " + book_id + " AND Reader_ID = " + readerId;
		
		try(Connection con = DriverManager.getConnection(URL, username, password);
				PreparedStatement preparedStmt = con.prepareStatement(query);){
			
			preparedStmt.execute();
			checkOutCount--;
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	
	public void reserve(String book_id){
		
		//Can't reserve a book that's currently checked out or reserved
		
		if(reserveCount == 10) return;
		
		String query1 = "SELECT Book_ID FROM Borrow WHERE Book_ID = " + book_id + " union "
				+ "SELECT Book_ID FROM Reserve WHERE Book_ID = " + book_id; 
		String query2 = "INSERT into Reserve(Reader_ID, Book_ID, Reserve_Date) VALUES (" + readerId +
				", " + book_id + ", current_date())";
		
		try(Connection con = DriverManager.getConnection(URL, username, password);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query1);){
			
			if(rs.next()) return; 
			
			try(Connection con2 = DriverManager.getConnection(URL, username, password);
						PreparedStatement preparedStmt = con2.prepareStatement(query2);){
				
				preparedStmt.execute();
				System.out.println("hello");
			}
			
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		reserveCount++;
	}
	
	public void calculateFine(String book_id){
		
		String query = "SELECT R_Date FROM Borrow WHERE Reader_ID = " + readerId + " AND Book_ID = " + book_id ;
		
		try(Connection con = DriverManager.getConnection(URL, username, password);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query);){
			
			rs.next();
			String returnDate = rs.getString("R_Date");
			
			Date date = new Date();
			String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			Date d1 = null;
			Date d2 = null;
			
			try{
				d1 = format.parse(returnDate);
				d2 = format.parse(currentDate);
				
				long diff = d2.getTime() - d1.getTime();
				
	            long diffDays = diff / (24 * 60 * 60 * 1000);

	            if(diffDays < 0) return;
	            
	            bookFine = diffDays * .20;
	            
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void printReserved(){
		
		String[] columnNames = {"Reader_ID", "Book_ID", "Reserve_Date"};
		
		Object[][] data = new Object[10][3];
		
		String query = "SELECT Reader_ID, Book.Book_ID, Reserve_Date FROM Book, Reserve WHERE "
				+ "Reserve.Reader_ID = " + readerId + " AND Book.Book_ID = Reserve.Book_ID";
		
		try(Connection con = DriverManager.getConnection(URL, username, password);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query)){
			
			int i = 0;
			while(rs.next()){
				data[i][0] = rs.getString("Reader_ID");
				data[i][1] = rs.getString("Book_ID");
				data[i][2] = rs.getString("Reserve_Date");
				i++;
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		createBorrowMenu(data, columnNames);
	}
	
	public void printBorrowed(){
		
		String[] columnNames = {"Book_ID", "Title", "Branch_ID", "B_Date", "R_Date"};
		
		Object[][] data = new Object[10][5];
		
		String query = "SELECT Book.Book_ID, Title, Branch_ID, B_Date, R_Date FROM Book, Borrow WHERE "
				+ "Borrow.Reader_ID = " + readerId + " AND Book.Book_ID = Borrow.Book_ID";
		
		try(Connection con = DriverManager.getConnection(URL, username, password);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query);){
			
			int i = 0;
			while(rs.next()){
				data[i][0] = rs.getString("BooK_ID");
				data[i][1] = rs.getString("Title");
				data[i][2] = rs.getString("Branch_ID");
				data[i][3] = rs.getString("B_Date");
				data[i][4] = rs.getString("R_Date");
				i++;
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		createBorrowMenu(data, columnNames);
	}
	
	public int checkOutCount(){
		
		String query = "SELECT COUNT(Book_ID) FROM Borrow WHERE Reader_ID = " + readerId;
		int result = 0;
		
		try(Connection con = DriverManager.getConnection(URL, username, password);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query)){
			
			rs.next();
			result = rs.getInt("COUNT(Book_ID)");
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	public int reserveCount(){
		
		String query = "SELECT COUNT(Book_ID) FROM Reserve WHERE Reader_ID = " + readerId;
		int result = 0;
		
		try(Connection con = DriverManager.getConnection(URL, username, password);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query)){
			
			rs.next();
			result = rs.getInt("COUNT(Book_ID)");
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	public void createBorrowMenu(Object[][] data, String[] columnNames){
		
		SearchGuiPanel guiPanel = new SearchGuiPanel(data, columnNames);
		
		JFrame frame = new JFrame("Borrowed List");
		frame.add(guiPanel);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	public void createReaderMenu(){
		
		ReaderGuiPanel guiPanel = new ReaderGuiPanel();
		
		JFrame frame = new JFrame("Reader Menu");
		frame.add(guiPanel);

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	public void createSearchMenu(Object[][] data, String[] columnNames){
		
		SearchGuiPanel guiPanel = new SearchGuiPanel(data, columnNames);
		
		JFrame frame = new JFrame("Search Menu");
		frame.add(guiPanel);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	private class SearchGuiPanel extends JPanel{
		
		private JTable table;
		private JScrollPane scrollPane;
		
		public SearchGuiPanel(Object[][] data, String[] columnNames){
			
			this.setPreferredSize(new Dimension(500,150));
			this.setLayout(null);
			
			table = new JTable(data, columnNames);
			scrollPane = new JScrollPane(table);
			table.setFillsViewportHeight(true);

			scrollPane.setBounds(0,0,500, 150);
			
			this.add(scrollPane);
		}
	}
	
	private class ReaderGuiPanel extends JPanel{
		
		private JButton searchButton, checkOutButton, returnButton, reserveButton,
						computeFineButton, printBorrowButton, printReservedButton;
		private JTextField searchField, checkOutField, returnField, reserveField,
						   computeFineField;
		private JDialog fineDialog;
		
		public ReaderGuiPanel(){

			this.setPreferredSize(new Dimension(450, 420));
			this.setLayout(null);
			
			searchField = new JTextField(20);
			searchField.setBounds(55, 20, 200, 20);
			
			checkOutField = new JTextField(20);
			checkOutField.setBounds(55, 80, 200, 20);
			
			returnField = new JTextField(20);
			returnField.setBounds(55, 140, 200, 20);
			
			reserveField = new JTextField(20);
			reserveField.setBounds(55, 200, 200, 20);
			
			computeFineField = new JTextField(20);
			computeFineField.setBounds(55, 260, 200, 20);
			
			searchButton = new JButton("Search");
			searchButton.setBounds(270, 20, 120, 20);
			searchButton.addActionListener(new ActionListener(){
			
				@Override
				public void actionPerformed(ActionEvent e) {
					search(searchField.getText());
					searchField.setText("");
				}
			});
			
			checkOutButton = new JButton("Check Out");
			checkOutButton.setBounds(270, 80, 120, 20);
			checkOutButton.addActionListener(new ActionListener(){
				
				@Override
				public void actionPerformed(ActionEvent e) {
					checkOut(checkOutField.getText());
					checkOutField.setText("");
				}
			});
			
			returnButton = new JButton("Return Book");
			returnButton.setBounds(270, 140, 120, 20);
			returnButton.addActionListener(new ActionListener(){
				
				@Override
				public void actionPerformed(ActionEvent e) {
					returnBook(returnField.getText());
					returnField.setText("");
				}
			});
			
			reserveButton = new JButton("Reserve Book");
			reserveButton.setBounds(270, 200, 120, 20);
			reserveButton.addActionListener(new ActionListener(){
				
				@Override
				public void actionPerformed(ActionEvent e) {
					reserve(reserveField.getText());
					reserveField.setText("");
				}
			});
			
			computeFineButton = new JButton("Compute Fine");
			computeFineButton.setBounds(270, 260, 120, 20);
			computeFineButton.addActionListener(new ActionListener(){
				
				@Override
				public void actionPerformed(ActionEvent e) {
					calculateFine(computeFineField.getText());
					computeFineField.setText("");
					JOptionPane.showMessageDialog(new JFrame(), "Current Fine For The Book Is: $" + bookFine);

				}
			});
			
			printBorrowButton = new JButton("Print Borrowed");
			printBorrowButton.setBounds(270, 320, 120, 20);
			printBorrowButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					printBorrowed();
				}
			});
			
			printReservedButton = new JButton("Print Reserved");
			printReservedButton.setBounds(270, 380, 120, 20);
			printReservedButton.addActionListener(new ActionListener(){
				
				@Override
				public void actionPerformed(ActionEvent e) {
					printReserved();
				}
			});
			
			this.add(searchButton);
			this.add(checkOutButton);
			this.add(returnButton);
			this.add(reserveButton);
			this.add(computeFineButton);
			this.add(printBorrowButton);
			this.add(printReservedButton);
			
			this.add(searchField);
			this.add(checkOutField);
			this.add(returnField);
			this.add(reserveField);
			this.add(computeFineField);
			
		}
		
	}
	
	//Implement actionlistener on button1 to search a book by ID, title, or publisher name
	
}

