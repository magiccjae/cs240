package searchGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import shared.communication.GetFields_Params;
import shared.communication.GetFields_Result;
import shared.communication.GetProjects_Params;
import shared.communication.GetProjects_Result;
import shared.communication.Search_Params;
import shared.communication.Search_Result;
import shared.communication.Search_Result.One_Search_Result;
import shared.communication.ValidateUser_Params;
import shared.communication.ValidateUser_Result;
import shared.model.Project;
import shared.model.Field;

import client.communication.ClientCommunicator;
import client.communication.ClientException;

public class MainFrame extends JFrame {
	
	
	private static final int DEFAULT_WIDTH = 700;
	private static final int DEFAULT_HEIGHT = 500;
	ClientCommunicator cCommunicator = new ClientCommunicator();
	
	private JLabel label;
	JComboBox<String> projectlist;
	private String username;
	private String password;
	private JList fieldlist;
	private JButton button;
	private JTextField text;
	private int[] field_id;
	private JList imagelist;
	private JScrollPane scrollPane;
	
	public MainFrame(String username, String password){
		
		setUsername(username);
		setPassword(password);
		
		setTitle("What Do You Want?");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// panel for entire frame
		JPanel thepanel = new JPanel();
		thepanel.setLayout(new GridBagLayout());
		
		// panel for top half
		JPanel toppanel = new JPanel();
		toppanel.setLayout(new GridBagLayout());
		GridBagConstraints topgbc = new GridBagConstraints();
		topgbc.insets.top = 20;
		topgbc.insets.bottom = 20;
		topgbc.insets.left = 10;
		topgbc.insets.right = 10;
		
		// project list
		label = new JLabel("Projects");
		setGbc(topgbc, 0, 0);
		toppanel.add(label, topgbc);
		
		GetProjects_Params params = new GetProjects_Params(username, password);
		GetProjects_Result result = null;
		try {
			result = cCommunicator.GetProjects(params);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Project> projects = result.getAllprojects();
		
		
		projectlist = new JComboBox<>();
		
		for(Project temp: projects){
			projectlist.addItem(temp.getTitle());
		}
		
		setGbc(topgbc, 1, 0);
		toppanel.add(projectlist, topgbc);
		
		projectlist.addActionListener(actionListener);
		
		
		// field list
		label = new JLabel("Fields");	
		setGbc(topgbc, 2, 0);
		toppanel.add(label, topgbc);
		
		String listData[] =	{"                     ", "                    ", "                    ", "                    ", "                    "};
		
		fieldlist = new JList(listData);
		
		setGbc(topgbc, 3, 0);
		toppanel.add(fieldlist, topgbc);
		
		
		// panel for bottom half
		JPanel bottompanel = new JPanel();
		bottompanel.setLayout(new GridBagLayout());
		GridBagConstraints bottomgbc = new GridBagConstraints();
		bottomgbc.insets.top = 10;
		bottomgbc.insets.bottom = 10;
		bottomgbc.insets.left = 10;
		bottomgbc.insets.right = 10;

		
		// search
		label = new JLabel("Search");
		setGbc(bottomgbc, 0, 0);
		bottompanel.add(label, bottomgbc);
		
		text = new JTextField(20);
		setGbc(bottomgbc, 0, 1);
		bottompanel.add(text, bottomgbc);
		
		button = new JButton("Search Now");
		setGbc(bottomgbc, 1, 1);
		bottompanel.add(button, bottomgbc);
		
		button.addActionListener(actionListener);
		
		// result
		label = new JLabel("Image URL");
		setGbc(bottomgbc, 0, 2);
		bottompanel.add(label, bottomgbc);
		
		imagelist = new JList(listData);
		
		scrollPane = new JScrollPane(imagelist);
		scrollPane.setPreferredSize(new Dimension(400, 100));
		setGbc(bottomgbc, 0, 3);
		bottomgbc.gridwidth = 2;
		
		imagelist.addMouseListener(mouseListener);
		
		bottompanel.add(scrollPane, bottomgbc);
		
		GridBagConstraints thegbc = new GridBagConstraints();		
		
		setGbc(thegbc, 0, 0);
		thepanel.add(toppanel, thegbc);
		setGbc(thegbc, 0, 1);
		thepanel.add(bottompanel, thegbc);
		this.setLocation(500, 250);
		this.add(thepanel);
		
		
	}
	
	private MouseListener mouseListener = new MouseAdapter(){
		public void mouseClicked(MouseEvent mouseEvent){
			if(mouseEvent.getClickCount() == 2){
				System.out.println("Mouse double-clicked");
				String url = (String) imagelist.getSelectedValue();
				System.out.println(url);
				BufferedImage image = null;
				try {
					image = ImageIO.read(new URL(url));
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JLabel website = new JLabel(new ImageIcon(image));
				
				
				JFrame frame = new JFrame("Image");
				frame.setSize(1000, 800);
				frame.setLocation(200, 200);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().add(website);
				frame.pack();
				frame.setVisible(true);
			}
		}
	};
	
	private ActionListener actionListener = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			if(e.getSource() == projectlist){
				System.out.println(projectlist.getSelectedIndex() + "  " + projectlist.getItemAt(projectlist.getSelectedIndex()));
				GetFields_Params params = new GetFields_Params(username, password, Integer.toString(projectlist.getSelectedIndex()+1));
				GetFields_Result result = null;
				try {
					result = cCommunicator.GetFields(params);
					
				} catch (ClientException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				List<Field> fields = result.getAllfields();
				String[] listdata =	new String[fields.size()];
				field_id = new int[fields.size()];
				for(int i = 0; i < fields.size(); i++){
					listdata[i] = fields.get(i).getTitle();
					field_id[i] = fields.get(i).getField_id();
				}
								
				fieldlist.setListData(listdata);
				
			}
			else if(e.getSource() == button){
				System.out.println("Search Clicked");
				String fieldids = "";
				
				int[] selected_index = fieldlist.getSelectedIndices();
				
				System.out.println("Number of fields selected: " + selected_index.length);
				
				if(selected_index.length == 1){
					fieldids = Integer.toString(field_id[selected_index[0]]);
					System.out.println("Field IDs: " + fieldids);
				}
				else if(selected_index.length > 1){
					for(int i = 0; i < selected_index.length; i++){
						if(i == 0){
							fieldids = Integer.toString(field_id[selected_index[i]]);
						}
						else{
							fieldids = fieldids + "," + Integer.toString(field_id[selected_index[i]]);
						}
					}
					System.out.println("Field IDs: " + fieldids);
				}
				
				String search_value = text.getText();
				System.out.println("Search_Value: " + search_value);
				
				Search_Params params = new Search_Params(username, password, fieldids, search_value);
				Search_Result result = null;
				try {
					result = cCommunicator.Search(params);
				} catch (ClientException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				List<One_Search_Result> search_result = result.getAllresults();
				Set<String> imageurls = new TreeSet<String>();
				String[] search_data = new String[search_result.size()];
				for(int i = 0; i < search_result.size(); i++){
					imageurls.add(cCommunicator.getStringurl() + File.separator + search_result.get(i).getImage_url());
				}
				
				int j = 0;
				for(String temp: imageurls){
					search_data[j] = temp;
					j++;
				}
				
				System.out.println(imageurls.size());
				
				imagelist.setListData(search_data);
			}
			
		}
	};
	
	public void setGbc(GridBagConstraints gbc, int gridx, int gridy){
		gbc.gridx = gridx;
		gbc.gridy = gridy;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				
				MainFrame mainframe = new MainFrame("test1", "test1");
				
				mainframe.setVisible(true);
				
			}
			
		});
	}
}