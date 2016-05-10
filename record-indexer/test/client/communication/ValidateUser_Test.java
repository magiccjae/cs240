package client.communication;

import static org.junit.Assert.*;

import org.junit.Test;

import shared.communication.ValidateUser_Params;
import shared.communication.ValidateUser_Result;

public class ValidateUser_Test {

	
	@Test
	public void testValid() throws Exception {
		ClientCommunicator sCommunicator = new ClientCommunicator();
		
		ValidateUser_Params params =  new ValidateUser_Params("sheila","parker");
		
		ValidateUser_Result result = sCommunicator.ValidateUser(params);
		
		assertFalse(result == null);
		StringBuilder sb = new StringBuilder();
		sb.append("TRUE\n");
		sb.append("Sheila\n");
		sb.append("Parker\n");
		sb.append(0);
		assertEquals(sb.toString(),result.toString());
		
	}
	
	@Test
	public void testInvalidUsername() throws Exception {
		
		ClientCommunicator sCommunicator = new ClientCommunicator();
		
		ValidateUser_Params params =  new ValidateUser_Params("TA","parker");
		
		ValidateUser_Result result = sCommunicator.ValidateUser(params);
		
		assertFalse(result == null);
		assertEquals("FALSE", result.getValid());
		
	}
	@Test
	public void testInvalidPassword() throws Exception {
		
		ClientCommunicator sCommunicator = new ClientCommunicator();
		
		ValidateUser_Params params =  new ValidateUser_Params("test1","test2");
		
		ValidateUser_Result result = sCommunicator.ValidateUser(params);
		
		assertFalse(result == null);
		assertEquals("FALSE", result.getValid());
		
	}
	

}
