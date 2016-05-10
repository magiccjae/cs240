package client.communication;

import static org.junit.Assert.*;

import org.junit.Test;

import shared.communication.GetProjects_Params;
import shared.communication.GetProjects_Result;

public class GetProjects_Test {
	
	
	
	@Test
	public void testValid() throws Exception {
		ClientCommunicator sCommunicator = new ClientCommunicator();
		
		GetProjects_Params params =  new GetProjects_Params("sheila","parker");
		
		GetProjects_Result result = sCommunicator.GetProjects(params);
		
		assertFalse(result == null);
		
		StringBuilder sb = new StringBuilder();
		sb.append(1);
		sb.append("\n1890 Census\n");
		sb.append(2);
		sb.append("\n1900 Census\n");
		sb.append(3);
		sb.append("\nDraft Records\n");
		
		assertEquals(sb.toString(),result.toString());
		
	}
	
	public void testInalid() throws Exception {
		ClientCommunicator sCommunicator = new ClientCommunicator();
		
		GetProjects_Params params =  new GetProjects_Params("Ken","Rodham");
		
		GetProjects_Result result = sCommunicator.GetProjects(params);
		
		assertFalse(result != null);
				
	}
	

}
