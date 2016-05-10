package searchGUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import shared.communication.ValidateUser_Params;
import shared.communication.ValidateUser_Result;

import client.communication.ClientCommunicator;
import client.communication.ClientException;


public class SearchFrame {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		EventQueue.invokeLater(new Runnable(){
			public void run(){
								
				LoginBox login = new LoginBox();
				
				login.setVisible(true);
			}
			
		});
	}

}


class LoginBox extends JFrame{
	
	private static final int DEFAULT_WIDTH = 275;
	private static final int DEFAULT_HEIGHT = 225;
	
	public static String DEFAULT_HOST = "localhost";
	public static String DEFAULT_PORT = "39640";
	
	private JLabel label;
	private JTextField host_text;
	private JTextField port_text;	
	private JTextField username_text;
	private JTextField password_text;
	private JButton button;
	private JLabel invalidinput;
	
	public LoginBox(){
		setTitle("LoginBox");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
				
		JPanel loginpanel = new JPanel();
		loginpanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.insets.bottom = 10;
		
		label = new JLabel("Host: ");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		setGbc(gbc, 0, 0);
		loginpanel.add(label, gbc);
		
		host_text = new JTextField(10);
		host_text.setText(DEFAULT_HOST);
		setGbc(gbc, 1, 0);
		loginpanel.add(host_text, gbc);

		label = new JLabel("Port: ");
		setGbc(gbc, 0, 1);
		loginpanel.add(label, gbc);
		
		port_text = new JTextField(10);
		port_text.setText(DEFAULT_PORT);
		setGbc(gbc, 1, 1);
		loginpanel.add(port_text, gbc);
		
		label = new JLabel("Username: ");
		setGbc(gbc, 0, 2);
		loginpanel.add(label, gbc);
		
		username_text = new JTextField(10);
		setGbc(gbc, 1, 2);
		loginpanel.add(username_text, gbc);

		label = new JLabel("Password: ");
		setGbc(gbc, 0, 3);
		loginpanel.add(label, gbc);
		
		password_text = new JTextField(10);
		setGbc(gbc, 1, 3);
		loginpanel.add(password_text, gbc);

		invalidinput = new JLabel("");
		gbc.gridwidth = 2;
		setGbc(gbc, 0, 4);
		loginpanel.add(invalidinput, gbc);
		
		button = new JButton("Login");
		gbc.gridwidth = 2;
		setGbc(gbc, 0, 5);
		loginpanel.add(button, gbc);
		button.addActionListener(actionListener);
		
		
		this.setLocation(500, 250);
		this.add(loginpanel);
				
		
	}
	
	private ActionListener actionListener = new ActionListener(){
		
		private ClientCommunicator cCommunicator = new ClientCommunicator();
		
		public void actionPerformed(ActionEvent e){
			
			if (e.getSource() == button){
				System.out.println("Login Requested");
				String host = host_text.getText();
				String port = port_text.getText();
				String username = username_text.getText();
				String password = password_text.getText();
				
				ClientCommunicator.setHOST(host);
				ClientCommunicator.setPORT(port);
				
				ValidateUser_Params params = new ValidateUser_Params(username, password);
				try {
					ValidateUser_Result result = cCommunicator.ValidateUser(params);
					
					if(result.getValid().equals("TRUE")){
						
						MainFrame mainframe = new MainFrame(username, password);
						mainframe.setVisible(true);
						
					}
					else{
						invalidinput.setText("Invalid Input");
						invalidinput.setHorizontalAlignment(SwingConstants.CENTER);
						invalidinput.setForeground(Color.RED);
					}
					
				} catch (ClientException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				System.out.println(host + "  " + port + "  " + username + "  " + password);
			}
			
		}
	};
	
	public void setGbc(GridBagConstraints gbc, int gridx, int gridy){
		gbc.gridx = gridx;
		gbc.gridy = gridy;
	}
		
}