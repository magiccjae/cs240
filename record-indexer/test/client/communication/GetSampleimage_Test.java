package client.communication;

import static org.junit.Assert.*;

import org.junit.Test;

import shared.communication.GetSampleimage_Params;
import shared.communication.GetSampleimage_Result;

public class GetSampleimage_Test {
	
	@Test
	public void testValid() throws Exception {
		ClientCommunicator sCommunicator = new ClientCommunicator();
		
		GetSampleimage_Params params =  new GetSampleimage_Params("sheila","parker",1);
		
		GetSampleimage_Result result = sCommunicator.GetSampleimage(params);
		
		assertFalse(result == null);
		
		StringBuilder sb = new StringBuilder();
		String url = ClientCommunicator.getStringurl();
		sb.append(url + "/images/1890_image0.png");
		
		assertEquals(sb.toString(),result.toString());
						
	}
	
	@Test
	public void testInvalid1() throws Exception {
		ClientCommunicator sCommunicator = new ClientCommunicator();
		
		GetSampleimage_Params params =  new GetSampleimage_Params("sheila","parker",4);
		
		GetSampleimage_Result result = sCommunicator.GetSampleimage(params);
		
		assertFalse(result != null);
				
	}
	@Test
	public void testInvalid2() throws Exception {
		ClientCommunicator sCommunicator = new ClientCommunicator();
		
		GetSampleimage_Params params =  new GetSampleimage_Params("test1","test2",2);
		
		GetSampleimage_Result result = sCommunicator.GetSampleimage(params);
		
		assertFalse(result != null);
				
	}	

}
