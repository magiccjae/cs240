package client.GUI;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import shared.communication.DownloadBatch_Result;
import shared.model.Field;

import client.GUI.State.BatchStateListener;
import client.GUI.State.Cell;
import client.communication.ClientCommunicator;

public class FieldPane extends JTabbedPane implements BatchStateListener{

	private State state;
	private JScrollPane fieldhelpPane;
	private JPanel imagenaviPanel;
	private JEditorPane helpContent;
	private List<String> htmlList;
	
	public FieldPane() {
		
		fieldhelpPane = new JScrollPane();
		imagenaviPanel = new JPanel();
		
		helpContent = new JEditorPane();
		helpContent.setEditable(false);
		helpContent.setContentType("text/html");
		
		
		this.setTabPlacement(JTabbedPane.TOP);
		this.addTab("Field Help", fieldhelpPane);
		this.addTab("Image Navigation", imagenaviPanel);		
		
	}

	public void setState(State state){
		this.state = state;
		state.addListener(this);
		if(state.getRecords() != 0){
			createComponent(state.getDownloadResult(), state.getSelectedCell().field-1);			
		}
	}
		
	public void createComponent(DownloadBatch_Result result, int index){
		
		List<Field> fieldList = result.getAllfields();
		
		htmlList = new ArrayList<String>();
		
		for(Field temp: fieldList){
			htmlList.add(ClientCommunicator.getStringurl() + File.separator + temp.getHelphtml());
		}
		
		System.out.println("Help Html URL: " + ClientCommunicator.getStringurl() + File.separator + htmlList.get(index));
		
		try {
			helpContent.setPage(new URL(htmlList.get(index)));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fieldhelpPane.add(helpContent);
		fieldhelpPane.setViewportView(helpContent);
		fieldhelpPane.repaint();
		this.repaint();
	}
	
	@Override
	public void valueChanged(Cell cell, String newValue) {
		// TODO Auto-generated method stub
	}

	@Override
	public void selectedCellChanged(Cell newSelectedCell) {

		if(htmlList != null){
			if(!htmlList.isEmpty()){
				try {
					helpContent.setPage(new URL(htmlList.get(newSelectedCell.field-1)));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fieldhelpPane.add(helpContent);
				fieldhelpPane.setViewportView(helpContent);
			}
		}
	}

	@Override
	public void batchDownloaded(DownloadBatch_Result result) {
		
		createComponent(result, 0);
		
	}

	@Override
	public void initializeState() {
		fieldhelpPane.removeAll();
		htmlList.clear();
		this.repaint();
	}

}
