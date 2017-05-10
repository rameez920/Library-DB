
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainMenu {

	public static void main(String[] args) {
		
		MainMenu menu = new MainMenu();
		menu.init();
	}
	
	public void init(){
		
		GuiPanel guiPanel = new GuiPanel();
		
		JFrame frame = new JFrame("Library DataBase System");
		frame.add(guiPanel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
			
	}
	
	
	private class GuiPanel extends JPanel{
		
		JLabel readerLogin, adminLogin;
		JLabel readerLabel, adminLabel1, adminLabel2;
		JTextField readerText, adminText1, adminText2;
		JButton readerButton, adminButton;
		
		public GuiPanel(){
			
			this.setPreferredSize(new Dimension(250, 250));
			this.setLayout(null);
			
			readerText = new JTextField(20);
			readerText.setBounds(135,45,100,20);
			adminText1 = new JTextField(20);
			adminText1.setBounds(135,125,100,20);
			adminText2 = new JTextField(20);
			adminText2.setBounds(135,145,100,20);
			
			readerLogin = new JLabel("Reader Login");
			adminLogin = new JLabel("Admin Login");
			readerLogin.setBounds(80,10,100,50);
			adminLogin.setBounds(80,90,100,50);
			
			readerLabel = new JLabel("Enter card Number: ");
			readerLabel.setBounds(20,30,200,50);
			adminLabel1 = new JLabel("Enter ID Number: ");
			adminLabel1.setBounds(20, 110, 200, 50);
			adminLabel2 = new JLabel("Enter Password: ");
			adminLabel2.setBounds(20, 130, 200, 50);
			
			readerButton = new JButton("Submit");
			readerButton.setBounds(135, 70, 100, 20);
			
			readerButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					String str = readerText.getText();
					readerText.setText("");
					new ReaderMenu(str);
				}
				
			});
			
			adminButton = new JButton("Submit");
			adminButton.setBounds(135,170,100,20);
			adminButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					String userName = adminText1.getText();
					String password = adminText2.getText();
					
					adminText1.setText("");
					adminText2.setText("");
					new AdminMenu();
					
				}
				
			});
			
			this.add(readerLogin);
			this.add(readerLabel);
			this.add(readerText);
			this.add(adminLogin);
			this.add(adminLabel1);
			this.add(adminLabel2);
			this.add(adminText1);
			this.add(adminText2);
			this.add(readerButton);
			this.add(adminButton);
		}
		
		
		
	}
}
