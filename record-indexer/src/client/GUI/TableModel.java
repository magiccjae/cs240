package client.GUI;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import shared.model.Field;

public class TableModel extends AbstractTableModel{
	
	private int row;
	private int column;
	private String[][] data;
	private List<Field> fieldlist;
		
	public TableModel(int row, int column, String[][] data){
		this.row = row;
		this.column = column;
		this.data = data;
	}
	
	public void setFieldlist(List<Field> fieldlist){
		this.fieldlist = fieldlist;
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		if(column == 0){
			return false;
		}
		else{
			return true;
		}
	}	
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return column;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return row;
	}
	
	@Override
	public String getColumnName(int column) {
		String result = null;
		if(column >= 0 && column < getColumnCount()){
			if(column == 0){
				result = "Record Numbers";
			}
			else{
				result = fieldlist.get(column-1).getTitle();
			}
		}
		else{
			throw new IndexOutOfBoundsException();
		}		
		
		return result;
	}

	@Override
	public Object getValueAt(int row, int column) {
		
		String result = null;
		if(column >= 0 && column < getColumnCount()){
			if(column == 0){
					result = Integer.toString(row+1);
			}
			else{
				result = data[row][column];
			}
		}
		else{
			throw new IndexOutOfBoundsException();
		}		
		
		return result;
	}
	
	@Override
	public void setValueAt(Object value, int row, int column) {

		if(column >= 0 && column < getColumnCount()){
			if(column == 0){
				System.out.println("You can't edit this column");
			}
			else{
				data[row][column] = (String) value;
			}
			this.fireTableCellUpdated(row, column-1);
		}
		else{
			throw new IndexOutOfBoundsException();
		}		
		
	}
	
}
