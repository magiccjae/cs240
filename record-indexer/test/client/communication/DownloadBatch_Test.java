package client.communication;

import static org.junit.Assert.*;

import org.junit.Test;

import shared.communication.DownloadBatch_Params;
import shared.communication.DownloadBatch_Result;

public class DownloadBatch_Test {
	
	@Test
	public void testInvalid() throws Exception {
		ClientCommunicator sCommunicator = new ClientCommunicator();
		
		DownloadBatch_Params params =  new DownloadBatch_Params("sheila","parker",4);
		
		DownloadBatch_Result result = sCommunicator.DownloadBatch(params);
		
		assertFalse(result != null);
		
	}
//	@Test
//	public void testValid() throws Exception {
//		ClientCommunicator sCommunicator = new ClientCommunicator();
//		
//		DownloadBatch_Params params =  new DownloadBatch_Params("sheila","parker",1);
//		
//		DownloadBatch_Result result = sCommunicator.DownloadBatch(params);
//		
//		assertFalse(result != null);
//		
//		StringBuilder sb = new StringBuilder();
//		String url = ClientCommunicator.getStringurl();
//		sb.append(url + "/images/1890_image0.png");
//		
//		assertEquals(sb.toString(),result.toString());
//						
//	}
	
}
