package shared.model;

public class User {
	
	private int user_id;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String email;
	private int indexedrecords;
	private int batch_id;
	
	/**
	 * 
	 * @param id
	 * @param username
	 * @param password
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @param indexedrecords - how many records this user have indexed
	 */
	public User(String username, String password, String firstname, 
				String lastname, String email, int indexedrecords, int batch_id) {
		setID(0);
		setName(username);
		setPassword(password);
		setFirstname(firstname);
		setLastname(lastname);
		setEmail(email);
		setIndexedrecords(indexedrecords);
		setBatch_id(batch_id);
	}
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param id
	 */
	public void setID(int id) {
		this.user_id = id;
	}
	
	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.username = name;
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
	 * @param firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
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
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * 
	 * @param indexedrecords
	 */
	public void setIndexedrecords(int indexedrecords) {
		this.indexedrecords = indexedrecords;
	}
	public void addIndexedrecords(int indexedrecords){
		this.indexedrecords += indexedrecords;
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
	 * @return user_id
	 */
	public int getID() {
		return user_id;
	}
	
	/**
	 * 
	 * @return username
	 */
	public String getName() {
		return username;
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
	 * @return firstname
	 */
	public String getFirstname() {
		return firstname;
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
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * 
	 * @return indexedrecords
	 */
	public int getIndexedrecords() {
		return indexedrecords;
	}
	
	/**
	 * 
	 * @return batch_id
	 */
	public int getBatch_id() {
		return batch_id;
	}
	
	
}
