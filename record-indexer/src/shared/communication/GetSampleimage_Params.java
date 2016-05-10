package shared.communication;

public class GetSampleimage_Params {

	private String username;
	private String password;
	private int project_id;
	
	/**
	 * create an object to pass to the model class to get a sample image
	 * @param user
	 * @param password
	 * @param project_id
	 */
	public GetSampleimage_Params(String username, String password, int project_id) {

		this.username = username;
		this.password = password;
		this.project_id = project_id;
	}
	/**
	 * 
	 * @return user
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 
	 * @param user
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

	@Override
	public String toString() {
		return "GetSampleimage_Params [user=" + username + ", password=" + password
				+ ", project_id=" + project_id + "]";
	}
	
}