package shared.communication;

public class SubmitBatch_Params {

	private String username;
	private String password;
	private int batch_id;
	private String allvalues;
	
	/**
	 * create an object to pass to the model class to submit the indexed record field values for a batch
	 * @param username
	 * @param password
	 * @param batch_id
	 * @param allvalues
	 */
	public SubmitBatch_Params(String username, String password, int batch_id,
			String allvalues) {
		
		this.username = username;
		this.password = password;
		this.batch_id = batch_id;
		this.allvalues = allvalues;
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
	 * @return allvalues - all of the field values in one String
	 */
	public String getAllvalues() {
		return allvalues;
	}

	/**
	 * 
	 * @param allvalues
	 */
	public void setAllvalues(String allvalues) {
		this.allvalues = allvalues;
	}

	
	@Override
	public String toString() {
		return "SubmitBatch_Params [username=" + username + ", password="
				+ password + ", batch_id=" + batch_id + ", allvalues="
				+ allvalues + "]";
	}
	
}
