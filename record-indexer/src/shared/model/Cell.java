package shared.model;

public class Cell {

	private int cell_id;
	private int row;
	private int batch_id;
	private int field_id;
	private String value;
	
	/**
	 * 
	 * @param cell_id
	 * @param row - this indicates which vertical position this cell belongs to
	 * @param batch_id
	 * @param field_id
	 * @param value - actual information in this cell
	 */
	public Cell(int row, String value, int field_id, int batch_id) {

		setCell_id(0);
		this.row = row;
		this.value = value;		
		this.batch_id = batch_id;
		this.field_id = field_id;
	}

	public Cell() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @return cell_id
	 */
	public int getCell_id() {
		return cell_id;
	}

	/**
	 * 
	 * @param cell_id
	 */
	public void setCell_id(int cell_id) {
		this.cell_id = cell_id;
	}

	/**
	 * 
	 * @return row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * 
	 * @param row
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * 
	 * @return batch_id
	 */
	public int getBatch_id() {
		return batch_id;
	}

	/**
	 * 
	 * @param batch_id
	 */
	public void setBatch_id(int batch_id) {
		this.batch_id = batch_id;
	}

	/**
	 * 
	 * @return field_id
	 */
	public int getField_id() {
		return field_id;
	}

	/**
	 * 
	 * @param field_id
	 */
	public void setField_id(int field_id) {
		this.field_id = field_id;
	}

	/**
	 * 
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
