package client.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import shared.communication.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ClientCommunicator {

	public static String stringurl = "http://localhost:39640";
	public static String HOST = "localhost";
	public static String PORT = "39640";
	
	public ClientCommunicator() {
		
	}
	public ClientCommunicator(String host, String port){
		
		ClientCommunicator.HOST = host;
		ClientCommunicator.PORT = port;
		stringurl = "http://"+host+":"+port;		
	}
	
//	public ClientCommunicator getInstance(){
//		if(instance == null){
//			instance = new ClientCommunicator(HOST, PORT);
//		}
//		return instance;
//	}
	
	public static String getStringurl(){
		stringurl = "http://"+HOST+":"+PORT;
		return stringurl;
	}
	
	public static String getHOST() {
		return HOST;
	}
	public static void setHOST(String hOST) {
		HOST = hOST;
	}
	public static String getPORT() {
		return PORT;
	}
	public static void setPORT(String pORT) {
		PORT = pORT;
	}
	/**
	 * Validates user credentials
	 * @param params
	 * @return ValidateUser_Result
	 * @throws ClientException
	 */
	public ValidateUser_Result ValidateUser(ValidateUser_Params params) throws ClientException {
		return (ValidateUser_Result)doPost("/ValidateUser", params);
	}
	/**
	 * Returns information about all of the available projects
	 * @param params
	 * @return GetProjects_Result
	 * @throws ClientException
	 */
	public GetProjects_Result GetProjects(GetProjects_Params params) throws ClientException {
		
		return (GetProjects_Result)doPost("/GetProjects", params);
	}
	/**
	 * Returns a sample image for the specified project
	 * @param params
	 * @return GetSampleimage_Result
	 * @throws ClientException
	 */
	public GetSampleimage_Result GetSampleimage(GetSampleimage_Params params) throws ClientException {
		return (GetSampleimage_Result)doPost("/GetSampleimage", params);
	}
	/**
	 * Downloads a batch for the user to index
	 * @param params
	 * @return DownloadBatch_Result
	 * @throws ClientException
	 */
	public DownloadBatch_Result DownloadBatch(DownloadBatch_Params params) throws ClientException {
		
		return (DownloadBatch_Result)doPost("/DownloadBatch", params);
	}
	/**
	 * Submits the indexed record field values for a batch to the Server
	 * @param params
	 * @return SubmitBatch_Result
	 * @throws ClientException
	 */
	public SubmitBatch_Result SubmitBatch(SubmitBatch_Params params) throws ClientException {
		
		return (SubmitBatch_Result)doPost("/SubmitBatch", params);
	}
	/**
	 * Returns information about all of the fields for the specified project
	 * @param params
	 * @return GetFields_Result
	 * @throws ClientException
	 */
	public GetFields_Result GetFields(GetFields_Params params) throws ClientException {
		
		return (GetFields_Result)doPost("/GetFields", params);
	}
	/**
	 * Searches the indexed records for the specified strings
	 * @param params
	 * @return Search_Result
	 * @throws ClientException
	 */
	public Search_Result Search(Search_Params params) throws ClientException {
		
		return (Search_Result)doPost("/Search", params);
	}
		
	
//	private Object doGet(String urlPath) throws ClientException {
//		// Make HTTP GETrequest to the specified URL,
//		// and return the object returned by the server
//		Object resultObject = null;
//		
//		try {
//			URL url = new URL(stringurl + urlPath);
//			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
//			connection.setRequestMethod("GET");
////			connection.setDoOutput(true);
////			connection.setDoInput(true);
//			
//			connection.connect();
//						
//			
//			if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
//				XStream xStream = new XStream(new DomDriver());				
//				System.out.println("response received!");
//				InputStream responseBody = connection.getInputStream();
//				resultObject = xStream.fromXML(responseBody);
//				System.out.println("deserializing");
//				responseBody.close();
//				return resultObject;
//			}
//			else{
//				throw new ClientException();
//			}
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw new ClientException();
//		}		
//		
//	}
	private Object doPost(String urlPath, Object postData) throws ClientException {
		// Make HTTP Post request to the specified URL,
		// passing in the specified postDataobject
		Object resultObject = null;
		
		try {
			URL url = new URL(getStringurl() + urlPath);
			
			System.out.println(getStringurl() + urlPath);
			
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
						
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			
			connection.connect();
			
			OutputStream requestBody = connection.getOutputStream();
			
			XStream xStream = new XStream(new DomDriver());
			System.out.println("serializing object");
			String xml = xStream.toXML(postData);
			
			requestBody.write(xml.getBytes());
			System.out.println("sending to handlers");			
			requestBody.close();
			
			if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
				System.out.println("response received!");
				InputStream responseBody = connection.getInputStream();
				resultObject = xStream.fromXML(responseBody);
				System.out.println("deserializing");
				responseBody.close();
				return resultObject;
			}
			else{
				throw new ClientException();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("connection refused");
			e.printStackTrace();
			throw new ClientException();
		}
		
	}
	
}