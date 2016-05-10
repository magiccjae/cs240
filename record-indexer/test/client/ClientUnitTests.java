package client;

import org.junit.*;

import dataimporter.Importer;
import static org.junit.Assert.*;


public class ClientUnitTests {
	
	@Before
	public void setup() {
	}
	
	@After
	public void teardown() {
	}
	
	@Test
	public void test_1() {		
		assertEquals("OK", "OK");
		assertTrue(true);
		assertFalse(false);
	}

	public static void main(String[] args) {

		try {
			Importer.main(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] testClasses = new String[] {
				"client.ClientUnitTests",
				"client.communication.ValidateUser_Test"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
}

