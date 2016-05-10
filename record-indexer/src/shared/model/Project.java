package shared.model;

public class Project {
	
	private int project_id;
	private String title;
	private int recordsperimage;
	private int firstycoord;
	private int recordheight;
	
	/**
	 * 
	 * @param id
	 * @param title - title of the project
	 * @param recordsperimage - the number of records that appear on each batch image
	 * @param firstycoord - the y-coordinate of the top of the first record on the project's images
	 * @param recordheight - the height of each record in the project's images, measured in pixels
	 */
	public Project(String title, int recordsperimage, 
						   int firstycoord, int recordheight) {
		setID(0);
		setTitle(title);
		setRecordsperimage(recordsperimage);
		setFirstycoord(firstycoord);
		setRecordheight(recordheight);
		
	}
	
	public Project() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param id
	 */
	public void setID(int id) {
		this.project_id = id;
	}
	
	/**
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 
	 * @param recordsperimage
	 */
	public void setRecordsperimage(int recordsperimage) {
		this.recordsperimage = recordsperimage;
	}
	
	/**
	 * 
	 * @param firstycoord
	 */
	public void setFirstycoord(int firstycoord) {
		this.firstycoord = firstycoord;
	}
	
	/**
	 * 
	 * @param recordheight
	 */
	public void setRecordheight(int recordheight) {
		this.recordheight = recordheight;
	}
	
	
	/**
	 * 
	 * @return project_id
	 */
	public int getID() {
		return project_id;
	}
	
	/**
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * 
	 * @return recordsperimage
	 */
	public int getRecordsperimage() {
		return recordsperimage;
	}
	
	/**
	 * 
	 * @return firstycoord
	 */
	public int getFirstycoord() {
		return firstycoord;
	}
	
	/**
	 * 
	 * @return recordheight
	 */
	public int getRecordheight() {
		return recordheight;
	}

	
}