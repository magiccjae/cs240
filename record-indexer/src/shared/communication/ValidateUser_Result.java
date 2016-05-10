package shared.communication;

public class ValidateUser_Result {
	
	private String valid;
	private String firstname;
	private String lastname;
	private int indexedrecords;
	
	/**
	 * create an object with the result received from the model class to validate user
	 * @param valid
	 * @param firstname
	 * @param lastname
	 * @param indexedrecords
	 */
	public ValidateUser_Result(String valid, String firstname, String lastname,
								int indexedrecords) {
		this.valid = valid;
		this.firstname = firstname;
		this.lastname = lastname;
		this.indexedrecords = indexedrecords;
	}
	/**
	 * 
	 * @return if the user is valid user or not
	 */
	public String getValid() {
		return valid;
	}
	/**
	 * 
	 * @param valid
	 */
	public void setValid(String valid) {
		this.valid = valid;
	}
	/**
	 * 
	 * @return firstname
	 */
	public String getFirstname() {
		return firstname;
	}
	/**
	 * 
	 * @param firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	/**
	 * 
	 * @return lastname
	 */
	public String getLastname() {
		return lastname;
	}
	/**
	 * 
	 * @param lastname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	/**
	 * 
	 * @return indexedrecords - how many records the user have indexed
	 */
	public int getIndexedrecords() {
		return indexedrecords;
	}
	/**
	 * 
	 * @param indexedrecords
	 */
	public void setIndexedrecords(int indexedrecords) {
		this.indexedrecords = indexedrecords;
	}

	@Override
	public String toString() {
		return valid + "\n" +
	           firstname + "\n" + 
			   lastname + "\n" +
	           indexedrecords;
	}
		
	
	
}
