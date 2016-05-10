package client.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import client.communication.ClientException;

import shared.communication.DownloadBatch_Params;
import shared.communication.DownloadBatch_Result;
import shared.communication.GetProjects_Params;
import shared.communication.GetProjects_Result;
import shared.communication.GetSampleimage_Params;
import shared.communication.GetSampleimage_Result;
import shared.communication.SubmitBatch_Params;
import shared.communication.SubmitBatch_Result;
import shared.model.Project;


public class Indexer extends JFrame{

	private static final int DEFAULT_WIDTH = 1200;
	private static final int DEFAULT_HEIGHT = 800;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	
	private VisibleManager manager;
	private JMenuItem downloadItem;
	private JMenuItem logoutItem;
	private JMenuItem exitItem;
	private JPanel toppanel;
	private JPanel buttonpanel;
	private JButton zoomin;
	private JButton zoomout;
	private JButton invert;
	private JButton toggle;
	private JButton save;
	private JButton submit;
	private ImageComponent imagecomponent;
	private JDialog downloadbox;
	private int projectid = 1;
	private String projectname;
	private JDialog samplebox;
	private JButton closebutton;
	private TablePane tablepane;
	private FieldPane fieldpane;
	private boolean downloadenable;
	private boolean submitable;
	private State state;
	private JSplitPane splitPane;
	private JSplitPane bottomPane;
	private JMenuBar menuBar;
	private int initialX;
	
	public Indexer(VisibleManager manager) {
		this.manager = manager;
		this.setTitle("Indexer");
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.downloadenable = true;
		submitable = false;
		
		createComponent();
	}
	
	public void createComponent(){
		menuBar = new JMenuBar();
		
		setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("File");
		menu.setMnemonic('F');
		menuBar.add(menu);
		
		downloadItem = new JMenuItem("DownloadBatch");
		downloadItem.addActionListener(actionListener);
		menu.add(downloadItem);
		
		logoutItem = new JMenuItem("Logout");
		logoutItem.addActionListener(actionListener);
		menu.add(logoutItem);

		exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(actionListener);
		menu.add(exitItem);
		
		toppanel = new JPanel(new BorderLayout());
		
		buttonpanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		
		zoomin = new JButton("Zoom In");
		buttonpanel.add(zoomin);
		zoomin.addActionListener(actionListener);
		
		zoomout = new JButton("Zoom Out");
		buttonpanel.add(zoomout);
		zoomout.addActionListener(actionListener);
		
		invert = new JButton("Invert Image");
		buttonpanel.add(invert);
		invert.addActionListener(actionListener);

		toggle = new JButton("Toggle Highlights");
		buttonpanel.add(toggle);
		toggle.addActionListener(actionListener);

		save = new JButton("Save");
		buttonpanel.add(save);
		save.addActionListener(actionListener);

		submit = new JButton("Submit");
		buttonpanel.add(submit);
		submit.setEnabled(submitable);
		submit.addActionListener(actionListener);
		
		
		imagecomponent = new ImageComponent();
		toppanel.add(buttonpanel, BorderLayout.NORTH);
		toppanel.add(imagecomponent, BorderLayout.CENTER);
		
		tablepane = new TablePane();
		fieldpane = new FieldPane();
		
		bottomPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tablepane, fieldpane);
		bottomPane.setDividerLocation(400);
		bottomPane.setContinuousLayout(true);
		
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, toppanel, bottomPane);
		splitPane.setDividerLocation(400);
		splitPane.setContinuousLayout(true);
		
		this.add(splitPane);
		this.setLocationRelativeTo(null);
		this.initialX = getX();
//		this.setLocation((dim.width - DEFAULT_WIDTH)/2, (dim.height- DEFAULT_HEIGHT)/2);
		this.pack();
		
	}

	private ActionListener actionListener = new ActionListener(){
		
		private JButton viewbutton;
		private JButton cancelbutton;
		private JButton downloadbutton;
		private JComboBox<String> projectlist;
		
		public void actionPerformed(ActionEvent e){
			
			// downloadItem in Menu
			if(e.getSource() == downloadItem){
				System.out.println("Download Batch");
				downloadbox = new JDialog();
				downloadbox.setTitle("Download Batch");
				downloadbox.setResizable(false);
				downloadbox.setModal(true);
				downloadbox.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				downloadbox.setPreferredSize(new Dimension(350, 120));
				downloadbox.setLayout(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
				
				JLabel label = new JLabel("Projects: ");
				gbc.insets.right = 5;
				gbc.insets.bottom = 2;
				gbc.gridx = 0;
				gbc.gridy = 0;
				downloadbox.add(label, gbc);
				
				GetProjects_Params params = new GetProjects_Params(manager.getUsername(), manager.getPassword());
				GetProjects_Result result = null;

				try {
					result = manager.getcCommunicator().GetProjects(params);
				} catch (ClientException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				List<Project> projects = result.getAllprojects();
				projectname = projects.get(0).getTitle();
				
				projectlist = new JComboBox<>();
				
				for(Project temp: projects){
					projectlist.addItem(temp.getTitle());
				}
				gbc.gridx = 1;
				gbc.gridy = 0;
				downloadbox.add(projectlist,gbc);
				projectlist.addActionListener(actionListener);
				
				viewbutton = new JButton("View Sample");
				gbc.gridx = 2;
				gbc.gridy = 0;
				downloadbox.add(viewbutton, gbc);
				viewbutton.addActionListener(actionListener);
				
				cancelbutton = new JButton("Cancel");
				gbc.anchor = GridBagConstraints.WEST;
				gbc.gridx = 1;
				gbc.gridy = 1;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				downloadbox.add(cancelbutton, gbc);
				cancelbutton.addActionListener(actionListener);
				
				downloadbutton = new JButton("Download");
				gbc.gridx = 2;
				gbc.gridy = 1;
				downloadbox.add(downloadbutton, gbc);
				downloadbutton.addActionListener(actionListener);
							
				downloadbox.setLocation((dim.width - 350)/2, (dim.height- 120)/2);
				downloadbox.pack();
				downloadbox.setVisible(true);
			}			
			// Logout in Menu
			else if(e.getSource() == logoutItem){
				System.out.println("Logout");
				serializeBatch();				
				manager.setLoginVisible();
			}
			// Exit in Menu
			else if(e.getSource() == exitItem){
				System.out.println("Exit");
				serializeBatch();
				manager.close();
			}
			// changes in JComboBox for project list in DownloadBatch JDialog
			else if(e.getSource() == projectlist){
				System.out.println((projectlist.getSelectedIndex()+1) + "  " + projectlist.getItemAt(projectlist.getSelectedIndex()));
				projectid = projectlist.getSelectedIndex()+1;
				projectname = projectlist.getItemAt(projectlist.getSelectedIndex());
			}
			// "View Sample" Button in DownloadBatch JDialog
			else if(e.getSource() == viewbutton){
				System.out.println("View Sample");
				
				GetSampleimage_Params params = new GetSampleimage_Params(manager.getUsername(), manager.getPassword(), projectid);
				GetSampleimage_Result result = null;
				
				try {
					result = manager.getcCommunicator().GetSampleimage(params);
				} catch (ClientException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				BufferedImage image = null;
				String url = result.toString();
				System.out.println("Sample Image: " + url);
				
				try {
					image = ImageIO.read(new URL(url));
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				samplebox = new JDialog();
				samplebox.setTitle("Sample image from " + projectname);
				samplebox.setResizable(false);
				samplebox.setModal(true);
				samplebox.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				
				JPanel wrapper = new JPanel(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
				
				JLabel sample = new JLabel(new ImageIcon(image.getScaledInstance(image.getWidth()/2, image.getHeight()/2, 0)));
				gbc.gridx = 0;
				gbc.gridy = 0;
				wrapper.add(sample, gbc);
				
				closebutton = new JButton("Close");
				
				closebutton.addActionListener(actionListener);
				gbc.gridx = 0;
				gbc.gridy = 1;
				wrapper.add(closebutton, gbc);
				
				samplebox.add(wrapper);
				samplebox.setLocation((dim.width - (image.getWidth()/2))/2, (dim.height - (image.getHeight()/2))/2);
				samplebox.pack();
				samplebox.setVisible(true);
			}
			// "Cancel" Button in DownloadBatch JDialog
			else if(e.getSource() == cancelbutton){
				System.out.println("Cancel");
				downloadbox.setVisible(false);
			}
			// "Download" Button in DownloadBatch JDialog
			else if(e.getSource() == downloadbutton){
				System.out.println("Download");
				System.out.println(manager.getUsername() + manager.getPassword() + projectid);
				DownloadBatch_Params params = new DownloadBatch_Params(manager.getUsername(), manager.getPassword(), projectid);
				DownloadBatch_Result result = null;
				
				try {
					result = manager.getcCommunicator().DownloadBatch(params);
				} catch (ClientException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				submitable = true;
				submit.setEnabled(submitable);
				downloadenable = false;
				state.downloadBatch(result);
				downloadItem.setEnabled(downloadenable);
				downloadbox.setVisible(false);
				
			}
			// "Close" Button in DownloadBatch JDialog
			else if(e.getSource() == closebutton){
				System.out.println("Close Sample");
				samplebox.setVisible(false);
			}
			
			// "Zoom in" Button in Button Panel
			else if(e.getSource() == zoomin){
				imagecomponent.setScale(imagecomponent.getScale()*1.2);
			}
			// "Zoom out" Button in Button Panel
			else if(e.getSource() == zoomout){
				imagecomponent.setScale(imagecomponent.getScale()*0.8);
			}
			// "Invert" button in Button Panel
			else if(e.getSource() == invert){
				imagecomponent.invertImage();
			}
			// "Toggle Highlight in Button Panel
			else if(e.getSource() == toggle){
				imagecomponent.toggleHighlight();
			}
			// "Save" Button in Button Panel
			else if(e.getSource() == save){
				System.out.println("Save");
				serializeBatch();
			}
			// "Submit" Button in Button Panel
			else if(e.getSource() == submit){
				System.out.println("Submit");
				String[][] data = state.getValues();
				String word = "";
				System.out.println("rows: " + state.getRecords() + " " + "columns: " + state.getFields());
				for(int i = 0; i < state.getRecords(); i++){
					for(int j = 0; j < state.getFields(); j++){
						if(data[i][j] == null){
							data[i][j] = " ";
						}
						if(j != 0){
							if(j != state.getFields()-1){
								word += data[i][j] + ",";
							}
							else{
//								if(i != state.getRecords()-1){
									word += data[i][j] + ";";
//								}
//								else{
//									word += data[i][j];
//								}
							}
						}
					}
				}
				System.out.println(word);
				System.out.println("Batch ID: " + state.getDownloadResult().getBatch_id());
				SubmitBatch_Result result = null;
				try {
					result = manager.getcCommunicator().SubmitBatch(new SubmitBatch_Params(manager.getUsername(), manager.getPassword(), state.getDownloadResult().getBatch_id(), word));
				} catch (ClientException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(result == null){
					System.out.println("SubmitBatch failed"); 
				}
				
				state.initializeState();
				submitable = false;
				submit.setEnabled(submitable);
				downloadenable = true;
				downloadItem.setEnabled(downloadenable);
			}
		}		
		
	};
	
	public void serializeBatch(){
		System.out.println("Serializing State");
		
		state.setInitialX(initialX);
		state.setWindowPositionX(getX());
		state.setWindowPositionY(getY());
		state.setWindowSize(getSize());
		state.setHorizontalSplit(splitPane.getDividerLocation());
		state.setVerticalSplit(bottomPane.getDividerLocation());
//		state.setTableScrollY(tablepane.getTableScrollY());
//		imagecomponent.removeAllshapes();
		state.setInvert(imagecomponent.isInvert());
		state.setOriginX(imagecomponent.getW_originX());
		state.setOriginY(imagecomponent.getW_originY());
		state.setScale(imagecomponent.getScale());
		state.setDownloadenble(downloadenable);
		state.setSubmitable(submitable);
		
		try {
			FileOutputStream fileOut = new FileOutputStream(manager.getUsername() + ".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(state);
			out.close();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void setState(State state){
		this.state = state;
		downloadItem.setEnabled(state.isDownloadenble());
		submit.setEnabled(state.isSubmitable());
		imagecomponent.setState(state);
		tablepane.setState(state);
		fieldpane.setState(state);
	}
	
	public void setSplits(int x, int y){
		splitPane.setDividerLocation(x);
		bottomPane.setDividerLocation(y);
	}
	
}
