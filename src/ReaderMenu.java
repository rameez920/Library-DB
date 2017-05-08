
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ReaderMenu{
	
	private final String URL = "jdbc:mysql://localhost:3306/librarydb";
	private String username = "root";
	private String password ="17184387254";
	private String readerId;
		
	public ReaderMenu(String readerId){
		this.readerId = readerId;
		createReaderMenu();
		System.out.println(readerId);
	}
	
	public void search(String str){
		
		String[] columnNames = {"Book_ID", "Title", "P_ID", "P_DATE", "Branch_ID"};
		
		Object[][] data = new Object[100][5];
		
		String query = "SELECT Book_ID, Title, Book.P_ID, P_Date, Branch_ID FROM Book, Publisher WHERE Title "
				+ "LIKE '%" + str + "%' OR BOOK_ID = '" + str + "' OR P_NAME = '" + str + "' AND "
						+ "Book.P_ID = Publisher.P_ID";
		
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
				i++;
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		createSearchMenu(data, columnNames);
	}
	
	public void checkOut(String book_id){
		
		String query = "INSERT into Borrow(Reader_ID, Book_ID, B_Date, R_Date) VALUES ("
				+ readerId + ", ";
		
		try(Connection con = DriverManager.getConnection(URL, username, password);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query);){
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		//Need to insert into Borrowed table
	}
	
	public void returnBook(String bookTitle){
		
		//Need to remove from Borrowed table
	}
	
	public void reserve(String bookTitle){

		//Need to insert into reserve table
	}
	
	public int calculateFine(Book book){
		//Calculate the fine on the book
		//(Current date - Due date) * 20 cents
		return 0;
	}
	
	public void printReserved(){
		//Query from reserve table
		return;
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
				data[i][0] = rs.getString("BOOK_ID");
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
		
		private JButton searchButton, returnButton;
		private JTextField searchField, returnField;
		private JLabel checkOutLabel, reservedLabel;
		private JScrollPane jScrollPane;
		private JTable checkOutTable, reservedTable;
		private JDialog	confirm;
		
		public ReaderGuiPanel(){

			this.setPreferredSize(new Dimension(600, 400));
			this.setLayout(null);
			
			searchField = new JTextField(20);
			searchField.setBounds(55, 20, 200, 20);
			returnField = new JTextField(20);
			returnField.setBounds(55, 50, 200, 20);
			
			searchButton = new JButton("Search");
			searchButton.setBounds(270, 20, 75, 20);
			
			searchButton.addActionListener(new ActionListener(){
			
				@Override
				public void actionPerformed(ActionEvent e) {
					search(searchField.getText());
					searchField.setText("");
				}
				
			});
			
			returnButton = new JButton("Return");
			returnButton.setBounds(270, 50, 75, 20);
			
			returnButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					printBorrowed();
				}
				
			});
			
			checkOutLabel = new JLabel("Checked Out: ");
			checkOutLabel.setBounds(55, 80, 100, 20);
			reservedLabel = new JLabel("Reserved: ");
			reservedLabel.setBounds(55, 220, 100, 20);
			
			checkOutTable = new JTable(5, 8);
			checkOutTable.setBounds(55, 110, 500, 80);
			reservedTable = new JTable(5, 8);
			reservedTable.setBounds(55, 250, 500, 80);			
			
			
			this.add(searchField);
			this.add(searchButton);
			this.add(returnField);
			this.add(returnButton);
			this.add(checkOutLabel);
			this.add(reservedLabel);
			this.add(checkOutTable);
			this.add(reservedTable);
			
		}
		
	}
	
	//Implement actionlistener on button1 to search a book by ID, title, or publisher name
	
}

