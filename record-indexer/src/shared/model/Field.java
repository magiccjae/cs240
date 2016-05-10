package shared.model;

public class Field implements java.io.Serializable{

	private int field_id;
	private int field_number;
	private String title;
	private int xcoord;
	private int width;
	private String helphtml;
	private String knowndata;
	private int project_id;
	
	/**
	 * 
	 * @param field_id
	 * @param field_number
	 * @param title
	 * @param xcoord - the x-coordinate of the field on the project's images
	 * @param width - the width of the field in the project's images, measured in pixels
	 * @param helphtml - the location of an HTML file that contains end-user help for this field
	 * @param knowndata - the location of a text file that contains known values for this field
	 * @param project_id
	 */
	public Field(int field_number, String title, int xcoord, int width,
			String helphtml, String knowndata, int project_id) {
		
		setField_id(0);
		this.field_number = field_number;
		this.title = title;
		this.xcoord = xcoord;
		this.width = width;
		this.helphtml = helphtml;
		this.knowndata = knowndata;
		this.project_id = project_id;
	}


	public Field() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @return field_id
	 */
	public int getField_id() {
		return field_id;
	}

	/**
	 * 
	 * @param field_id
	 */
	public void setField_id(int field_id) {
		this.field_id = field_id;
	}

	/**
	 * 
	 * @return field_number
	 */
	public int getField_number() {
		return field_number;
	}

	/**
	 * 
	 * @param field_number
	 */
	public void setField_number(int field_number) {
		this.field_number = field_number;
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
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 
	 * @return xcoord
	 */
	public int getXcoord() {
		return xcoord;
	}

	/**
	 * 
	 * @param xcoord
	 */
	public void setXcoord(int xcoord) {
		this.xcoord = xcoord;
	}

	/**
	 * 
	 * @return width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * 
	 * @param width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * 
	 * @return helphtml
	 */
	public String getHelphtml() {
		return helphtml;
	}

	/**
	 * 
	 * @param helphtml
	 */
	public void setHelphtml(String helphtml) {
		this.helphtml = helphtml;
	}

	/**
	 * 
	 * @return knowndata
	 */
	public String getKnowndata() {
		return knowndata;
	}

	/**
	 * 
	 * @param knowndata
	 */
	public void setKnowndata(String knowndata) {
		this.knowndata = knowndata;
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
