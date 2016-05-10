package shared.communication;

import java.util.ArrayList;
import java.util.List;

import shared.model.Field;

public class GetFields_Result {

	private List<Field> allfields;
	
	/**
	 * create an object with the result received from the model class containing information about all of the fields for the specified project
	 * @param allfields
	 */
	public GetFields_Result(List<Field> allfields) {

		this.allfields = allfields;
	}

	public GetFields_Result() {
		// TODO Auto-generated constructor stub
		allfields = new ArrayList<Field>();
	}

	/**
	 * 
	 * @return allfields - a list of all fields for the specified project
	 */
	public List<Field> getAllfields() {
		return allfields;
	}

	/**
	 * 
	 * @param allfields
	 */
	public void setAllfields(List<Field> allfields) {
		this.allfields = allfields;
	}
	public void addAllfields(Field field){
		this.allfields.add(field);
	}

	@Override
	public String toString() {
		String output = "";
		for(Field temp: allfields){
			output += temp.getProject_id() + "\n" +
					  temp.getField_id() + "\n" +
					  temp.getTitle() + "\n";
		}
		
		return output;
	}
	
}
