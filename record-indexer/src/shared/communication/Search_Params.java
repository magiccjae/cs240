package shared.communication;

public class Search_Params {

	private String username;
	private String password;
	private String fields;
	private String search_values;
	
	/**
	 * create an object to pass to the model class to search the indexed records for the specified strings
	 * @param username
	 * @param password
	 * @param fields
	 * @param search_values
	 */
	public Search_Params(String username, String password, String fields,
			String search_values) {

		this.username = username;
		this.password = password;
		this.fields = fields;
		this.search_values = search_values;
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
	 * @return fields - fields to be searched
	 */
	public String getFields() {
		return fields;
	}
	/**
	 * 
	 * @param fields
	 */
	public void setFields(String fields) {
		this.fields = fields;
	}
	/**
	 * 
	 * @return search_values - values to be searched for
	 */
	public String getSearch_values() {
		return search_values;
	}
	/**
	 * 
	 * @param search_values
	 */
	public void setSearch_values(String search_values) {
		this.search_values = search_values;
	}

	@Override
	public String toString() {
		return "Search_Params [username=" + username + ", password=" + password
				+ ", fields=" + fields + ", search_values=" + search_values
				+ "]";
	}
	
}
