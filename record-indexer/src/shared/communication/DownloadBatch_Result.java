package shared.communication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import client.communication.ClientCommunicator;

import shared.model.Field;

public class DownloadBatch_Result implements java.io.Serializable{

	private int batch_id;
	private int project_id;
	private String image_url;
	private int first_y_coord;
	private int record_height;
	private int num_records;
	private int num_fields;
	private List<Field> fieldlist;
	
	/**
	 * create an object with the result received from the model class containing the informations about this batch
	 * @param batch_id
	 * @param project_id
	 * @param image_url
	 * @param first_y_coord
	 * @param record_height
	 * @param num_records
	 * @param num_fields
	 * @param allfields
	 */
	public DownloadBatch_Result(int batch_id, int project_id, String image_url,
			int first_y_coord, int record_height, int num_records,
			int num_fields, List<Field> fieldlist) {
		
		this.batch_id = batch_id;
		this.project_id = project_id;
		this.image_url = image_url;
		this.first_y_coord = first_y_coord;
		this.record_height = record_height;
		this.num_records = num_records;
		this.num_fields = num_fields;
		this.fieldlist = fieldlist;
	}

	public DownloadBatch_Result() {
		// TODO Auto-generated constructor stub
		this.fieldlist = new ArrayList<Field>();
		
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
	 * @return project_id
	 */
	public int getProject_id() {
		return project_id;
	}
	/**
	 * 
	 * @param project_id
	 */
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	/**
	 * 
	 * @return image_url
	 */
	public String getImage_url() {
		return ClientCommunicator.getStringurl() + File.separator + image_url;
	}
	/**
	 * 
	 * @param image_url
	 */
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	/**
	 * 
	 * @return first_y_coord
	 */
	public int getFirst_y_coord() {
		return first_y_coord;
	}
	/**
	 * 
	 * @param first_y_coord
	 */
	public void setFirst_y_coord(int first_y_coord) {
		this.first_y_coord = first_y_coord;
	}
	/**
	 * 
	 * @return record_height
	 */
	public int getRecord_height() {
		return record_height;
	}
	/**
	 * 
	 * @param record_height
	 */
	public void setRecord_height(int record_height) {
		this.record_height = record_height;
	}
	/**
	 * 
	 * @return num_records
	 */
	public int getNum_records() {
		return num_records;
	}
	/**
	 * 
	 * @param num_records
	 */
	public void setNum_records(int num_records) {
		this.num_records = num_records;
	}
	/**
	 * 
	 * @return num_fields
	 */
	public int getNum_fields() {
		return num_fields;
	}
	/**
	 * 
	 * @param num_fields
	 */
	public void setNum_fields(int num_fields) {
		this.num_fields = num_fields;
	}
	/**
	 * 
	 * @return fieldlist - all of the fields that belong to this batch
	 */
	public List<Field> getAllfields() {
		return fieldlist;
	}
	/**
	 * 
	 * @param allfields
	 */
	public void setAllfields(List<Field> fieldlist) {
		this.fieldlist = fieldlist;
	}
	
	public void addFields(Field field){
		this.fieldlist.add(field);
	}
	
	@Override
	public String toString() {
		String output = batch_id + "\n" +
			   project_id + "\n" +
			   ClientCommunicator.getStringurl() + File.separator + image_url + "\n" +
			   first_y_coord + "\n" +
			   record_height + "\n" +
			   num_records + "\n" +
			   num_fields + "\n";
		for(Field temp: fieldlist){
			output += temp.getField_id() + "\n" +
					  temp.getField_number() + "\n" +
					  temp.getTitle() + "\n" +
					  ClientCommunicator.getStringurl() + File.separator + temp.getHelphtml() + "\n" +
					  temp.getXcoord() + "\n" +
					  temp.getWidth() + "\n";
			if(!temp.getKnowndata().equals("")){
				output += ClientCommunicator.getStringurl() + File.separator + temp.getKnowndata() + "\n";
			}
		}
		
		return output;
			   
	}
		
	
}
