package servertester.controllers;

import java.util.*;

import client.communication.ClientCommunicator;
import client.communication.ClientException;

import servertester.views.*;
import shared.communication.DownloadBatch_Params;
import shared.communication.DownloadBatch_Result;
import shared.communication.GetFields_Params;
import shared.communication.GetFields_Result;
import shared.communication.GetProjects_Params;
import shared.communication.GetProjects_Result;
import shared.communication.GetSampleimage_Params;
import shared.communication.GetSampleimage_Result;
import shared.communication.Search_Params;
import shared.communication.Search_Result;
import shared.communication.SubmitBatch_Params;
import shared.communication.SubmitBatch_Result;
import shared.communication.ValidateUser_Params;
import shared.communication.ValidateUser_Result;

public class Controller implements IController {

	private IView _view;
	private ClientCommunicator communicator = new ClientCommunicator();
	
	public Controller() {
		return;
	}
	
	public IView getView() {
		return _view;
	}
	
	public void setView(IView value) {
		_view = value;
	}
	
	// IController methods
	//
	
	@Override
	public void initialize() {
		getView().setHost("localhost");
		getView().setPort("39640");
		ClientCommunicator.setHOST("localhost");
		ClientCommunicator.setPORT("39640");
		operationSelected();
	}

	@Override
	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");
		
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}
		
		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() {
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}
		
	private void validateUser() {
		
		try {
			ClientCommunicator.setHOST(getView().getHost());
			ClientCommunicator.setPORT(getView().getPort());
			String username = getView().getParameterValues()[0];
			String password = getView().getParameterValues()[1];		
			ValidateUser_Params params = new ValidateUser_Params(username,password);
			
			ValidateUser_Result result = communicator.ValidateUser(params);
			
			getView().setRequest(params.getUsername() + "\n" + params.getPassword());
			
			
			if(result.getValid().equals("TRUE")){
				
				getView().setResponse(result.toString());
			}
			else if(result.getValid().equals("FALSE")){
				getView().setResponse("FALSE");
			}
			
			
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			getView().setResponse("FAILED\n");
			e.printStackTrace();
		}
		
	}
	
	private void getProjects() {
		
		try {
			ClientCommunicator.setHOST(getView().getHost());			
			ClientCommunicator.setPORT(getView().getPort());			
			String username = getView().getParameterValues()[0];
			String password = getView().getParameterValues()[1];		
			GetProjects_Params params = new GetProjects_Params(username,password);
			
			GetProjects_Result result = communicator.GetProjects(params);
			getView().setRequest(params.getUsername() + "\n" + params.getPassword());
			if(result == null){
				getView().setResponse("FAILED\n");
			}
			else{
				getView().setResponse(result.toString());
			}
			
			
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			getView().setResponse("FAILED\n");			
			e.printStackTrace();
		}
	}
	
	private void getSampleImage() {
		
		try {
			ClientCommunicator.setHOST(getView().getHost());			
			ClientCommunicator.setPORT(getView().getPort());			
			String username = getView().getParameterValues()[0];
			String password = getView().getParameterValues()[1];	
			int project_id = Integer.parseInt(getView().getParameterValues()[2]);
			GetSampleimage_Params params= new GetSampleimage_Params(username,password,project_id);
			
			GetSampleimage_Result result = communicator.GetSampleimage(params);
			getView().setRequest(params.getUsername() + "\n" + params.getPassword() + "\n" + params.getProject_id());
			if(result == null){
				getView().setResponse("FAILED");
			}
			else{
				getView().setResponse(result.toString());
			}
						
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			getView().setResponse("FAILED");						
			e.printStackTrace();
		}
	}
	
	private void downloadBatch() {
		try {
			ClientCommunicator.setHOST(getView().getHost());			
			ClientCommunicator.setPORT(getView().getPort());
			String username = getView().getParameterValues()[0];
			String password = getView().getParameterValues()[1];	
			int project_id = Integer.parseInt(getView().getParameterValues()[2]);
			DownloadBatch_Params params= new DownloadBatch_Params(username,password,project_id);
						
			DownloadBatch_Result result = communicator.DownloadBatch(params);
			getView().setRequest(params.getUsername() + "\n" + params.getPassword() + "\n" + params.getProject_id());
			if(result == null){
				getView().setResponse("FAILED");
			}
			else{
				getView().setResponse(result.toString());
			}
			
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			getView().setResponse("FAILED");						
			e.printStackTrace();
		}		
	}
	
	private void getFields() {
		try {
			ClientCommunicator.setHOST(getView().getHost());			
			ClientCommunicator.setPORT(getView().getPort());
			String username = getView().getParameterValues()[0];
			String password = getView().getParameterValues()[1];	
			String project_id = getView().getParameterValues()[2];
			
			GetFields_Params params= new GetFields_Params(username,password,project_id);
			
			GetFields_Result result = communicator.GetFields(params);
			
			getView().setRequest(params.getUsername() + "\n" + params.getPassword() + "\n" + params.getProject_id());
			
			if(result == null){
				getView().setResponse("FAILED");
			}
			else{
				getView().setResponse(result.toString());
			}		
			
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			getView().setResponse("FAILED");						
			e.printStackTrace();
		}		
		
	}
	
	private void submitBatch() {
		try {
			ClientCommunicator.setHOST(getView().getHost());			
			ClientCommunicator.setPORT(getView().getPort());			
			String username = getView().getParameterValues()[0];
			String password = getView().getParameterValues()[1];	
			int batch_id = Integer.parseInt(getView().getParameterValues()[2]);
			String allvalues = getView().getParameterValues()[3];
			SubmitBatch_Params params= new SubmitBatch_Params(username,password,batch_id,allvalues);
			
			SubmitBatch_Result result = communicator.SubmitBatch(params);
			getView().setRequest(params.getUsername() + "\n" + params.getPassword() + "\n" + params.getBatch_id() + "\n" + params.getAllvalues());
			
			if(result == null){
				getView().setResponse("FAILED");
			}
			else{
				
				getView().setResponse(result.toString());
			}		
			
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			getView().setResponse("FAILED");						
			e.printStackTrace();
		}		
		
	}
	
	private void search() {
		try{
			ClientCommunicator.setHOST(getView().getHost());			
			ClientCommunicator.setPORT(getView().getPort());
			String username = getView().getParameterValues()[0];
			String password = getView().getParameterValues()[1];	
			String fields = getView().getParameterValues()[2];
			String search_values = getView().getParameterValues()[3];
			Search_Params params= new Search_Params(username,password,fields,search_values);
			
			Search_Result result = communicator.Search(params);
			getView().setRequest(params.getUsername() + "\n" + params.getPassword() + "\n" + params.getFields() + "\n" + params.getSearch_values());
			if(result == null){
				getView().setResponse("FAILED");
			}
			else{
				getView().setResponse(result.toString());
			}		

			
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			getView().setResponse("FAILED");						
			e.printStackTrace();
		}		
	}
}

