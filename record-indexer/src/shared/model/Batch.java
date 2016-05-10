package shared.model;

public class Batch {
	
	private int batch_id;
	private String file;
	private String status;
	private int project_id;

	
	/**
	 * 
	 * @param batch_id
	 * @param file - the location of a PNG file that contains a single image for the project
	 * @param project_id
	 */
	public Batch(String file, String status, int project_id) {
		
		setBatch_id(0);
		this.status = status;
		this.file = file;
		this.project_id = project_id;
	}

	public Batch() {
		// TODO Auto-generated constructor stub
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
	 * @return file
	 */
	public String getFile() {
		return file;
	}
	
	/**
	 * 
	 * @param file
	 */
	public void setFile(String file) {
		this.file = file;
	}

	/**
	 * 
	 * @return status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
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
	
	
}
