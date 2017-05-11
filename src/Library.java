import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;




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


	public JPanel getTopReaders() {
//		String query = "SELECT borrow.Reader_ID, COUNT(borrow.Book_ID) "
//						+ "FROM borrow LEFT JOIN reader ON borrow.Reader_ID = reader.Reader_ID "
//						+ "GROUP BY borrow.Reader_ID IN "
//						+ "(SELECT borrow.Reader_ID FROM borrow, book "
//						+ "WHERE borrow.Book_ID = book.Book_ID AND book.Branch_ID = ?)"; 
		
		String query = "SELECT borrow.Reader_ID, COUNT(borrow.Book_ID) FROM borrow LEFT JOIN reader ON borrow.Reader_ID = reader.Reader_ID GROUP BY borrow.Reader_ID IN (SELECT borrow.Reader_ID FROM borrow, book WHERE borrow.Book_ID = book.Book_ID AND book.Branch_ID = ?)";
	
		Connection con = Connect.getConnection();
		
		String[] columnNames = {"Reader_ID", "Num of Books"};
		
		Object[][] data = new Object[10][2];
		
		try {
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, this.libId);
			ResultSet rs = st.executeQuery();
		
			int i = 0;
			while(rs.next()){
				data[i][0] = rs.getString("Reader_ID");
				data[i][1] = rs.getString("COUNT(borrow.Book_ID)");
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new SearchGuiPanel(data, columnNames);
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
	
	public JPanel getTopBooks() {
//		String query = "SELECT borrow.Book_ID, Count(borrow.Reader_ID) "
// 				+ "FROM borrow left join book ON borrow.Book_ID = book.Book_ID "
// 				+ "WHERE book.Branch_ID = ? group by borrow.Book_ID";
		
		String query = "SELECT borrow.Book_ID, Count(borrow.Reader_ID) FROM borrow left join book ON borrow.Book_ID = book.Book_ID WHERE book.Branch_ID = ? group by borrow.Book_ID";
		Connection con = Connect.getConnection();
		
		String[] columnNames = {"Book_ID", "Num of Borrowers"};
		
		Object[][] data = new Object[10][2];
		
		try {
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, this.libId);
			ResultSet rs = st.executeQuery();
		
			int i = 0;
			while (rs.next()) {
				
				data[i][0] = rs.getString("Book_ID");
				System.out.println(data[i][0]);
				data[i][1] = rs.getString("COUNT(borrow.Reader_ID)");
				System.out.println(data[i][1]);
				i++;
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new SearchGuiPanel(data, columnNames);
	} 
	
	
}
