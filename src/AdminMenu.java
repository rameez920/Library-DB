

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class AdminMenu {
	
	private static Admin admin;
	
	//create root menu for admin actions
	public AdminMenu(int id, String password) {
		admin = new Admin(id, password);
		
		JFrame f = new JFrame();
        
		JButton addBookButton = new JButton("Add new book");
		addBookButton.setBounds(130,100,100, 40);  
		
		addBookButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 bookMenu();
	         }          
	      });
		
		JTextField branchSearchField = new JTextField(15);
		JLabel branchSearchLabel = new JLabel("Branch ID:");
		
		JButton addReaderButton = new JButton("Add new reader");
		addReaderButton.setBounds(130,100,100, 40);  
		
		addReaderButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 addReaderMenu();
	         }          
	      });
		
		JButton branchSearchButton = new JButton("Search Branch");
		branchSearchButton.setBounds(130,100,100, 40);  
		
		branchSearchButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 int branchID = Integer.parseInt(branchSearchField.getText());
	        	 
	        	 try {
				
	        		 Library branch = admin.getBranchInfo(branchID);
				
	        		 if (branch == null) {
	        			 JOptionPane.showMessageDialog(null, "Invalid Branch ID");
	        		 } else {
	        			 //display top readers and books
	        			 branchDisplay(branch);
	        		 }
	        	 
	        	 
	        	 } catch (SQLException e1) {
					e1.printStackTrace();
				}
	         }          
	      });
		
		JTextField bookSearchField = new JTextField(15);
		JLabel bookSearchLabel = new JLabel("Book ID:");
		
		JButton bookSearchButton = new JButton("Search Book");
		bookSearchButton.setBounds(130,150,100, 40);  
		
		bookSearchButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 int bookID = Integer.parseInt(bookSearchField.getText());
	        	 
	        	 try {
	        		 Book book = admin.searchBook(bookID);
				
	        		 if (book== null) {
	        			 JOptionPane.showMessageDialog(null, "Invalid Search");
	        		 } else {
	        			 JOptionPane.showMessageDialog(null, "ID: " + book.getBookId() + "\nTitle: " 
	        					 							+ book.getTitle() +  "\nStatus: " + book.getStatus());
	        		 }
	        	 
	        	 } catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	         }          
	      });
		          
		
		f.add(branchSearchLabel);
		f.add(branchSearchField);
		f.add(branchSearchButton);
		f.add(bookSearchLabel);
		f.add(bookSearchField);
		f.add(bookSearchButton);
		f.add(addBookButton);  
		f.add(addReaderButton);    
		
		
		f.setTitle("Library Management System");
		f.setSize(425,130);
		//f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setLayout(new GridLayout(3, 0, 10, 4));  
		f.setVisible(true);  
	}

	
	//frame that displays branch info and top readers and top books
	private static void branchDisplay(Library lib) {
		JFrame f = new JFrame();
		
		JLabel branchNameLabel = new JLabel("BRANCH NAME: " + lib.getBranchName());
		JLabel branchLocLabel = new JLabel("LOCATION: " + lib.getAddress());
		
		JPanel namePanel = new JPanel(new GridLayout(2, 0, 10, 4));
		namePanel.add(branchNameLabel);
		namePanel.add(branchLocLabel);
		
		
		
		f.setLayout(new FlowLayout());
		f.add(namePanel);
		
		//Spaghetti code that should be cleaned up
		f.add(lib.getTopReaders());
		f.add(lib.getTopBooks());
		
		
		f.setTitle("Branch Info");
		f.setLocationRelativeTo(null);
		f.setSize(350, 400);
		f.setVisible(true);
	}
	
	
	//Menu for adding a new book into the DB 
	private static void bookMenu() {
		
		String[] labels = {"Title: ", "Author: ", "ISBN: ", "Publisher: ", 
							"Publish Date (YYYY-MM-DD): ", "Publisher Address:", "Branch ID:"};
		
		JTextField titleField = new JTextField();
		JTextField authorField = new JTextField();
		JTextField isbnField = new JTextField();
		JTextField publisherField = new JTextField();
		JTextField publishDateField = new JTextField();
		JTextField publishAddressField = new JTextField();
		JTextField branchIDField = new JTextField();
		
		JTextField[] textFields = {titleField, authorField, isbnField, 
									publisherField, publishDateField, publishAddressField, branchIDField};
		
		int numPairs = labels.length;

		//Create and populate the panel.
		JPanel p = new JPanel(new GridLayout(8, 0, 10, 4));
		
		JButton addBookButton = new JButton("Add Book");
		
		for (int i = 0; i < numPairs; i++) {
		    JLabel l = new JLabel(labels[i]);
		    p.add(l);
		    l.setLabelFor(textFields[i]);
		    p.add(textFields[i]);
		}
	
		p.add(addBookButton);
		
		addBookButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 String title = titleField.getText();
	        	 String author = authorField.getText();
	        	 int isbn = Integer.parseInt(isbnField.getText());
	        	 String publisher = publisherField.getText();
	        	 String publishAddress = publishAddressField.getText();
	        	 String publishDate = publishDateField.getText();
	        	 int branchID = Integer.parseInt(branchIDField.getText());
	        	 
	        	 
	        	 try {
					admin.addBook(title, author, isbn, publisher, publishDate, publishAddress, branchID);
					JOptionPane.showMessageDialog(null, "Book added");
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "ERROR: Book could not be added");
					e1.printStackTrace();
				}
	         }          
	      });
		
		JFrame frame = new JFrame();
		frame.setTitle("Add a new Book");
		frame.add(p);
		frame.setSize(350, 250);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	
	}
	
	//Menu for adding a new Reader to the DB
	private static void addReaderMenu() {
		
		JFrame frame = new JFrame();
		String[] labels = {"Name: ", "Address: ", "Phone Number: "};
		
		JTextField nameField = new JTextField();
		JTextField addressField = new JTextField();
		JTextField phoneNumberField = new JTextField();
		
		JTextField[] textFields = {nameField, addressField, phoneNumberField};
		
		int numPairs = labels.length;

		//Create and populate the panel.
		JPanel p = new JPanel(new GridLayout(6,0, 10, 4));
		
		JButton addBookButton = new JButton("Add Reader");
		JButton cancelButton = new JButton("Cancel");
		
		for (int i = 0; i < numPairs; i++) {
		    JLabel l = new JLabel(labels[i]);
		    p.add(l);
		    l.setLabelFor(textFields[i]);
		    p.add(textFields[i]);
		}
	
		p.add(addBookButton);
		p.add(cancelButton);
		
		//TODO get this shit to work
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSED));
				
			}
			
		});
		
		addBookButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 String name = nameField.getText();
	        	 String address = addressField.getText();
	        	 String phoneNumber = phoneNumberField.getText();
	        	 
	        	 try {
					admin.addReader(name, address, phoneNumber);
					System.out.println("reader added");
					JOptionPane.showMessageDialog(null, "New Reader Added");
	        	 } catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	         }          
	      });
		
		frame.setTitle("Add a new Reader");
		frame.add(p);
		frame.setSize(300, 200);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	
	}

}
