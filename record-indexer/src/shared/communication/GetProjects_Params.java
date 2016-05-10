package shared.communication;

public class GetProjects_Params {

	private String username;
	private String password;
	
	/**
	 * create an object to pass to the model class to get all of the available projects
	 * @param username
	 * @param password
	 */
	public GetProjects_Params(String username, String password) {
		
		this.username = username;
		this.password = password;
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

	@Override
	public String toString() {
		return "GetProjects_Params [username=" + username + ", password="
				+ password + "]";
	}
	
}
