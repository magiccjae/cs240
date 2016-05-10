package client.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.*;

import shared.communication.ValidateUser_Params;
import shared.communication.ValidateUser_Result;

import client.communication.ClientException;

public class Login extends JFrame{

	private static final int DEFAULT_WIDTH = 450;
	private static final int DEFAULT_HEIGHT = 130;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	
	private JPanel loginpanel;
	private JLabel label;
	private JTextField username_text;
	private JTextField password_text;
	private JButton login_button;
	private JButton exit_button;
	private VisibleManager manager;
	private JButton okbutton;
	private JDialog welcomebox;
	private JDialog errorbox;
	private State state;

	
	public Login(VisibleManager manager) {
		
		this.manager = manager;
		setResizable(false);
		setTitle("Login to Indexer");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		loginpanel = new JPanel();
		loginpanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets.bottom = 7;
		
		label = new JLabel("Username: ");
		setGbc(gbc, 0, 0, 1);
		loginpanel.add(label, gbc);
		
		username_text = new JTextField(30);
		username_text.setText("sheila");
		setGbc(gbc, 1, 0, 3);
		loginpanel.add(username_text, gbc);

		label = new JLabel("Password: ");
		setGbc(gbc, 0, 1, 1);
		loginpanel.add(label, gbc);
		
		password_text = new JTextField(30);	
		password_text.setText("parker");
		setGbc(gbc, 1, 1, 3);
		loginpanel.add(password_text, gbc);
		
		login_button = new JButton("Login");
		setGbc(gbc, 2, 3, 1);
		gbc.insets.left = 60;
		gbc.insets.bottom = 0;
		gbc.anchor = GridBagConstraints.EAST;
		loginpanel.add(login_button, gbc);
		login_button.addActionListener(actionListener);
		
		exit_button = new JButton("Exit");
		gbc.anchor = GridBagConstraints.WEST;		
		setGbc(gbc, 3, 3, 1);
		gbc.insets.left = 3;
		loginpanel.add(exit_button, gbc);
		exit_button.addActionListener(actionListener);

		this.setLocationRelativeTo(null);
		//this.setLocation((dim.width - DEFAULT_WIDTH)/2, (dim.height- DEFAULT_HEIGHT)/2);
		this.add(loginpanel);
	}
	
	private ActionListener actionListener = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == login_button){
				System.out.println("Login Requested");
				String username = username_text.getText();
				String password = password_text.getText();
				ValidateUser_Params params = new ValidateUser_Params(username, password);

				try {
					ValidateUser_Result result = manager.getcCommunicator().ValidateUser(params);
					
					if(result.getValid().equals("TRUE")){
						System.out.println("Valid User");
						manager.setUsername(username);
						manager.setPassword(password);
						
						state = new State();
												
						try {
							System.out.println("Deserializing State");
							FileInputStream fileIn = new FileInputStream(username + ".ser");
							System.out.println(username + ".ser");
							ObjectInputStream in = new ObjectInputStream(fileIn);
							state = (State) in.readObject();
//							System.out.println(state.getWindowPositionX() + " " + state.getWindowPositionY());
							manager.setIndexerLocation(state.getWindowPositionX(), state.getWindowPositionY());
							manager.setIndexerSize(state.getWindowSize());
							manager.setIndexerSplits(state.getHorizontalSplit(), state.getVerticalSplit());
							
							state.createListener();
							
							in.close();
							fileIn.close();
						} catch (IOException e1) {
							System.out.println("No serialized file existing for this user");							
							e1.printStackTrace();
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
						
						if(state.getInitialX() != state.getWindowPositionX()){
							System.out.println("previous state");
							manager.setState(state);
						}
						else{
							System.out.println("new state");
							manager.setNewstate(state);
						}
						
						welcomebox = new JDialog();
						welcomebox.setResizable(false);
						welcomebox.setModal(true);
						welcomebox.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						
						JPanel welcomepanel = new JPanel();
						welcomepanel.setLayout(new BorderLayout(5, 5));
						
						JLabel boxcontent1 = new JLabel("Welcome, " + result.getFirstname()  + " " + result.getLastname() + ".");
						JLabel boxcontent2 = new JLabel("You have indexed " + result.getIndexedrecords() + " records.");
						welcomepanel.add(boxcontent1, BorderLayout.NORTH);
						welcomepanel.add(boxcontent2, BorderLayout.CENTER);
						okbutton = new JButton("OK");
						okbutton.addActionListener(actionListener);
						welcomepanel.add(okbutton, BorderLayout.SOUTH);
						
						welcomebox.add(welcomepanel);
						welcomebox.setSize(300, 100);
						welcomebox.setLocationRelativeTo(null);
						//welcomebox.setLocation((dim.width - 300)/2, (dim.height- 100)/2);	
						welcomebox.pack();
						welcomebox.setVisible(true);
						
					}
					
					else{
						System.out.println("Invalid User");
						
						JDialog errorbox = new JDialog();
						errorbox.setResizable(false);
						errorbox.setModal(true);
												
						JOptionPane.showMessageDialog(errorbox, "Invalid User", "Error", JOptionPane.ERROR_MESSAGE);
						
					}
		
				} catch (ClientException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			else if(e.getSource() == exit_button){
				manager.close();
			}
			else if(e.getSource() == okbutton){
				welcomebox.setVisible(false);
				manager.setIndexerVisible();
			}
		}
		
	};
	
	public void setGbc(GridBagConstraints gbc, int gridx, int gridy, int gridwidth){
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridwidth = gridwidth;
	}

}
