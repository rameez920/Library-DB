

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
	public AdminMenu() {
		admin = new Admin(123, "password");
		
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
		
		JButton branchSearchButton = new JButton("Search");
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	         }          
	      });
		          
		f.add(addBookButton);  
		f.add(addReaderButton);    
		f.add(branchSearchLabel);
		f.add(branchSearchField);
		f.add(branchSearchButton);
		
		FlowLayout flow = new FlowLayout();
		
		f.setTitle("Library Management System");
		f.setSize(300,150);
		f.setLayout(flow);  
		f.setVisible(true);  
	}

	
	//frame that displays branch info and top readers and top books
	private static void branchDisplay(Library lib) {
		JFrame f = new JFrame();
		
		JLabel branchNameLabel = new JLabel("Name: " + lib.getBranchName());
		JLabel branchLocLabel = new JLabel("Location: " + lib.getAddress());
		
		FlowLayout flow = new FlowLayout();
		
		f.setLayout(flow);
		f.add(branchNameLabel);
		f.add(branchLocLabel);
		f.setTitle("Branch Info");
		f.setSize(200, 250);
		f.setVisible(true);
	}
	
	
	//Menu for adding a new book into the DB 
	private static void bookMenu() {
		
		String[] labels = {"Title: ", "Author: ", "ISBN: ", "Publisher: ", 
							"Publish Date: ", "Publisher Address:", "Branch ID:"};
		
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
		
		
		
		
		//add panel to frame
		JFrame frame = new JFrame();
		frame.setTitle("Add a new Book");
		frame.add(p);
		frame.setSize(300, 250);
		
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
		
		//add panel to frame
		
		frame.setTitle("Add a new Reader");
		frame.add(p);
		frame.setSize(300, 200);
		
		frame.setVisible(true);
	
	}

}
