package shared.communication;

public class GetFields_Params {

	private String username;
	private String password;
	private String project_id;
	
	/**
	 * create an object to pass to the model class to get information about all of the fields for a project
	 * @param username
	 * @param password
	 * @param project_id
	 */
	public GetFields_Params(String username, String password, String project_id) {

		this.username = username;
		this.password = password;
		this.project_id = project_id;
	}
	/**
	 * 
	 * @return username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 
	 * @return project_id
	 */
	public String getProject_id() {
		return project_id;
	}
	/**
	 * 
	 * @param project_id
	 */
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	@Override
	public String toString() {
		return "GetFields_Params [username=" + username + ", password="
				+ password + ", project_id=" + project_id + "]";
	}
	
}
