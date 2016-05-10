package shared.communication;

import java.util.List;

import shared.model.Project;

public class GetProjects_Result {

	private List<Project> allprojects;
	
	/**
	 * create an object with the result received from the model class containing all of the available projects
	 * @param allprojects
	 */
	public GetProjects_Result(List<Project> allprojects) {
		this.allprojects = allprojects;
	}

	public GetProjects_Result() {
		// TODO Auto-generated constructor stub
	}

	public List<Project> getAllprojects() {
		return allprojects;
	}

	public void setAllprojects(List<Project> allprojects) {
		this.allprojects = allprojects;
	}
	
	public String toString() {
		
		String output = "";
		for(Project temp : allprojects){
			output += temp.getID() + "\n" + temp.getTitle() + "\n";
		}
		
		return output;
	}

		
}
