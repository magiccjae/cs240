package client.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import shared.communication.DownloadBatch_Result;
import shared.model.Field;

import client.GUI.State.BatchStateListener;
import client.GUI.State.Cell;

public class TablePane extends JTabbedPane implements BatchStateListener{

	private State state;
	private JScrollPane tableScroll;
	private JPanel formPanel;
	private JTable tableEntry;
	private TableModel tableModel;
	private JScrollPane formLeft;
	private JScrollPane formRight;
	private JList<String> formList;
	private JPanel formEntry;
	private List<JTextField> textFieldlist;
	private int textFieldindex;
	private int listIndex;
	private int selectedFieldindex;
	private int formlistIndex;
	private int selectedRow;
	private int selectedColumn;
	private String newValue;
	private int row;
	private int column;
	private JPopupMenu suggestionPop;
	
	public TablePane() {
		textFieldindex = 0;
		tableScroll = new JScrollPane();
		formPanel = new JPanel(new BorderLayout());
		suggestionPop = new JPopupMenu("See Suggestion");
		
		this.setTabPlacement(JTabbedPane.TOP);
		this.addTab("Table Entry", tableScroll);
		this.addTab("Form Entry", formPanel);
		this.addChangeListener(changeListener);
	}
		
	public void setState(State state){
		this.state = state;
		state.addListener(this);
		if(state.getRecords() != 0){
			System.out.println("Loading the State");
			createComponent(state.getDownloadResult());

//			System.out.println(state.getTableScrollY());
			
//			tableScroll.getVerticalScrollBar().setValue(state.getTableScrollY());
			
//			System.out.println(state.getRecords() + " " + state.getFields());
			
			int selectedRow = state.getSelectedCell().record;
			int selectedColumn = state.getSelectedCell().field;
			
			for(int i = 0; i < state.getRecords(); i++){
				for(int j = 0; j < state.getFields(); j++){
					if(j != 0){
						state.getSelectedCell().record = i;
						state.getSelectedCell().field = j;
//						System.out.println(state.getValues()[i][j]);
						state.setValue(state.getSelectedCell(), state.getValues()[i][j]);
						tableEntry.getModel().setValueAt(state.getValues()[i][j], i, j);				
					}
				}
			}
			state.getSelectedCell().record = selectedRow;
			state.getSelectedCell().field = selectedColumn;
			
			state.setSelectedCell(state.getSelectedCell());
			
		}
	}
	
	public int getTableScrollY(){
		return tableScroll.getVerticalScrollBar().getValue();
	}
	
	
	public void createComponent(DownloadBatch_Result result){
		row = result.getNum_records();
		column = result.getNum_fields();
		List<Field> fieldlist = result.getAllfields();
		
		tableModel = new TableModel(row, column+1, new String[row][column+1]);
		tableModel.setFieldlist(fieldlist);
		
		tableEntry = new JTable(tableModel);
		tableEntry.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableEntry.setCellSelectionEnabled(true);
		tableEntry.getTableHeader().setReorderingAllowed(false);
		tableEntry.addMouseListener(mouseListener);
		tableEntry.getModel().addTableModelListener(tableModelListener);		
		
		TableColumnModel columnModel = tableEntry.getColumnModel();
		for(int i = 0; i < tableModel.getColumnCount(); ++i){
			TableColumn temp_column = columnModel.getColumn(i);
			temp_column.setPreferredWidth(100);
		}
		
		tableScroll.add(tableEntry);
		tableScroll.setViewportView(tableEntry);
		tableScroll.repaint();
		
		String[] indexes = new String[row];
		for(int i = 0; i < row; i++){
			indexes[i] = Integer.toString(i+1);
		}
		
		formLeft = new JScrollPane();
		formList = new JList<>(indexes);
		formList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		formList.addMouseListener(mouseListener);
		formList.setPreferredSize(new Dimension(50, 180));
		
		formLeft.add(formList);
		formLeft.setViewportView(formList);
		formPanel.add(formLeft, BorderLayout.WEST);
		
		formRight = new JScrollPane();
		formEntry = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets.bottom = 15;
		gbc.insets.right = 5;
		gbc.anchor = GridBagConstraints.WEST;
		
		textFieldlist = new ArrayList<JTextField>();
		
		for(int i = 0; i < column; i++){
			gbc.gridx = 0;
			gbc.gridy = i;
			JLabel entryTitle = new JLabel(fieldlist.get(i).getTitle());
			
			formEntry.add(entryTitle, gbc);
			
			gbc.gridx = 1;
			JTextField entryBox = new JTextField(20);
			entryBox.addFocusListener(focusListener);
			
			textFieldlist.add(entryBox);
			formEntry.add(entryBox, gbc);
		}
		formRight.add(formEntry);
		formRight.setViewportView(formEntry);
		
		formPanel.add(formRight, BorderLayout.CENTER);
		this.repaint();
	}
	
	@Override
	public void valueChanged(Cell cell, String newValue) {
		selectedRow = cell.record;
		selectedColumn = cell.field;
		
//		System.out.println("selectedRow: " + selectedRow + ", selectedColumn: " + selectedColumn);
		this.textFieldindex = selectedColumn-1;
		this.listIndex = selectedRow;
	}

	@Override
	public void selectedCellChanged(Cell newSelectedCell) {
		selectedRow = newSelectedCell.record;
		selectedColumn = newSelectedCell.field;
		
//		System.out.println("selectedRow: " + selectedRow + ", selectedColumn: " + selectedColumn);
		
		tableEntry.changeSelection(selectedRow, selectedColumn, false, false);
		this.textFieldindex = selectedColumn-1;
		this.listIndex = selectedRow;
	}

	@Override
	public void batchDownloaded(DownloadBatch_Result result) {
		
		for(int i = 0; i < result.getAllfields().size(); i++){
			System.out.println(result.getAllfields().get(i).getKnowndata());
		}
		System.out.println(result.getImage_url());
		createComponent(result);
		state.createValues(row, column+1);		
		
		state.setDownloadResult(result);
		state.getSelectedCell().record = 0;
		state.getSelectedCell().field = 1;
		state.setSelectedCell(state.getSelectedCell());
		
	}

	@Override
	public void initializeState() {
		tableScroll.removeAll();
		tableEntry.removeAll();
		formPanel.removeAll();
		formPanel.revalidate();
		this.repaint();
		
	}	
	private TableModelListener tableModelListener = new TableModelListener() {
		
		@Override
		public void tableChanged(TableModelEvent e) {
//			System.out.println("changed row: " + e.getFirstRow() + " changed column: " + (e.getColumn()+1));
			
			TableModel model = (TableModel)e.getSource();
//			System.out.println("changed value: " + model.getValueAt(e.getFirstRow(), e.getColumn()+1));
			
			
			state.getSelectedCell().record = e.getFirstRow();
			state.getSelectedCell().field = e.getColumn()+1;
			
			state.setValue(state.getSelectedCell(), (String)model.getValueAt(e.getFirstRow(), e.getColumn()+1));
			
		}
	};
	
	private FocusListener focusListener = new FocusListener(){

		@Override
		public void focusGained(FocusEvent e) {
//			if(e.getComponent() != tableEntry)
//			{
				Object selectedField = e.getComponent();
				selectedFieldindex = textFieldlist.indexOf(selectedField);
				state.getSelectedCell().field = selectedFieldindex+1;
				state.setSelectedCell(state.getSelectedCell());
//			}
		}

		@Override
		public void focusLost(FocusEvent e) {
//			System.out.println("listIndex: " + listIndex);
						
			Object selectedField = e.getComponent();
			selectedFieldindex = textFieldlist.indexOf(selectedField);
//			System.out.println("selectedFieldindex: " + textFieldlist.indexOf(selectedField));
			
			state.getSelectedCell().field = selectedFieldindex+1;
			
//			System.out.println(textFieldlist.get(selectedFieldindex).getText());
//			System.out.println(state.getSelectedCell().record + " " + state.getSelectedCell().field);
			state.setValue(state.getSelectedCell(), textFieldlist.get(selectedFieldindex).getText());			
			tableEntry.getModel().setValueAt(textFieldlist.get(selectedFieldindex).getText(), listIndex, state.getSelectedCell().field);
			
		}
		
	};
	
	private ChangeListener changeListener = new ChangeListener(){

		@Override
		public void stateChanged(ChangeEvent e) {
			textFieldlist.get(textFieldindex).grabFocus();
			formList.setSelectedIndex(listIndex);
			int originalField = state.getSelectedCell().field;
			for(int i = 0; i < column+1; i++){
				if(i == 0){
					
				}
				else{
					state.getSelectedCell().record = listIndex;
					state.getSelectedCell().field = i;
//					System.out.println("changed anything on " + (i-1) +" ? " + state.getValue(state.getSelectedCell()));
					textFieldlist.get(i-1).setText(state.getValue(state.getSelectedCell()));
				}
			}
			state.getSelectedCell().field = originalField;
		}
	};
		
	private MouseListener mouseListener = new MouseListener(){
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getSource() == tableEntry){

				state.getSelectedCell().record = tableEntry.rowAtPoint(e.getPoint());
				state.getSelectedCell().field = tableEntry.columnAtPoint(e.getPoint());

				state.setSelectedCell(state.getSelectedCell());
			}
			else if(e.getSource() == formList){
//				System.out.println("formList index: " + formList.getSelectedIndex());
				formlistIndex = formList.getSelectedIndex();
				state.getSelectedCell().record = formlistIndex;
				state.setSelectedCell(state.getSelectedCell());
//				System.out.println("originalField index: " + state.getSelectedCell().field); 
				int originalField = state.getSelectedCell().field;
				for(int i = 0; i < column+1; i++){
					if(i == 0){
						
					}
					else{
						state.getSelectedCell().record = listIndex;
						state.getSelectedCell().field = i;
//						System.out.println("changed anything on " + (i-1) +" ? " + state.getValue(state.getSelectedCell()));
						textFieldlist.get(i-1).setText(state.getValue(state.getSelectedCell()));
					}
				}
				state.getSelectedCell().field = originalField;
				
			}
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if(e.isMetaDown()){
				System.out.println("right clicked");							
				if(e.getSource() == tableEntry){
					
//					Jpopup.setvisible(true);
//					jpopup.setinvoker(jsuggestiononscreen)
//					jpopup.setlocation(e.getLocationOnScreen());
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	};


}
