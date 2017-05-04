
import java.awt.Dialog;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ReaderMenu{
	
	public static void main(String[] args) {
		
		ReaderMenu menu = new ReaderMenu();
		menu.create();
		
	}
	
	public void create(){
		
		GuiPanel guiPanel = new GuiPanel();
		
		JFrame frame = new JFrame("Title Holder");
		frame.add(guiPanel);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	private class GuiPanel extends JPanel{
		
		private JButton searchButton, returnButton;
		private JTextField searchField, returnField;
		private JLabel checkOutLabel, reservedLabel;
		private JScrollPane jScrollPane;
		private JTable checkOutTable, reservedTable;
		private JDialog	confirm;
		
		private Object[][] data;
		private String[] columnName = {"BookID", "Title", "Author", "ISBN", "Publisher", 
				"PubDate", "DueDate", "Fine"};
		
		public GuiPanel(){

			this.setPreferredSize(new Dimension(600, 400));
			this.setLayout(null);
			
			searchField = new JTextField(20);
			searchField.setBounds(55, 20, 200, 20);
			returnField = new JTextField(20);
			returnField.setBounds(55, 50, 200, 20);
			searchButton = new JButton("Search");
			searchButton.setBounds(270, 20, 75, 20);
			returnButton = new JButton("Return");
			returnButton.setBounds(270, 50, 75, 20);
			
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

