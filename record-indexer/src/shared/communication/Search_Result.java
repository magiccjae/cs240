package shared.communication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import client.communication.ClientCommunicator;


public class Search_Result {

	private List<One_Search_Result> allresults;	
	
	/**
	 * create an object with the result received from the model class 
	 * containing all of the search_result objects which contain the searched string
	 * @param allresults
	 */
	public Search_Result(List<One_Search_Result> allresults) {

		this.allresults = allresults;
	}	
	
	public Search_Result() {
		// TODO Auto-generated constructor stub
		allresults = new ArrayList<One_Search_Result>();
	}

	/**
	 * 
	 * @return allresults - all of the search_result objects that contain the searched string
	 */
	public List<One_Search_Result> getAllresults() {
		return allresults;
	}
	/**
	 * 
	 * @param allresults
	 */
	public void setAllresults(List<One_Search_Result> allresults) {
		this.allresults = allresults;
	}
	public void addAllAllresult(One_Search_Result oneresult){
		allresults.add(oneresult);
	}
			
	
	@Override
	public String toString() {
		String output = "";
		
		for(One_Search_Result temp: allresults){
			output += temp.toString();
		}
		
		return output;
	}

	public class One_Search_Result {
		
		private int batch_id;
		private String image_url;
		private int record_num;
		private int field_id;
		
		/**
		 * create an object for a search result
		 * @param batch_id
		 * @param image_url
		 * @param record_num
		 * @param field_id
		 */
		public One_Search_Result(int batch_id, String image_url, int record_num,
				int field_id) {

			this.batch_id = batch_id;
			this.image_url = image_url;
			this.record_num = record_num;
			this.field_id = field_id;
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
		 * @return image_url
		 */
		public String getImage_url() {
			return image_url;
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
		 * @return record_num
		 */
		public int getRecord_num() {
			return record_num;
		}
		/**
		 * 
		 * @param record_num
		 */
		public void setRecord_num(int record_num) {
			this.record_num = record_num;
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

		@Override
		public String toString() {
			String output = "";
			
			output += batch_id + "\n" +
					  ClientCommunicator.getStringurl() + File.separator + image_url + "\n" +
					  record_num + "\n" +
					  field_id + "\n";
			
			return output;
		}
		
	}
	
}
