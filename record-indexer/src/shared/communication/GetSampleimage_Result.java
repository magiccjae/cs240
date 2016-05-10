package shared.communication;

import java.io.File;

import client.communication.ClientCommunicator;


public class GetSampleimage_Result {

	private String image_url;

	/**
	 * create an object with the result received from the model class containing a URL for the image
	 * @param image_url
	 */
	public GetSampleimage_Result() {
	}
	
	/**
	 * 
	 * @return image_url
	 */
	public String getImage_url() {
		return image_url;
	}
	/**
	 * 
	 * @param image_url
	 */
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	@Override
	public String toString() {
		return ClientCommunicator.getStringurl() + File.separator + image_url;
	}
	
}
