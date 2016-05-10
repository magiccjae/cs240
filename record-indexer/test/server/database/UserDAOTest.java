package server.database;

import java.util.*;
import static org.junit.Assert.*;

import org.junit.*;

import server.database.Database;
import server.database.DatabaseException;
import server.database.UsersDAO;
import shared.model.*;


public class UserDAOTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		// Load database driver	
		Database.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}
		
	private Database db;
	private UsersDAO dbUsers;

	@Before
	public void setUp() throws Exception {

		// Delete all users from the database	
		db = new Database();
		db.startTransaction();
		dbUsers = new UsersDAO(db);
		
		List<User> users = dbUsers.getAll();
		
		for (User c : users) {
			dbUsers.delete(c);
		}
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new Database();
		db.startTransaction();
		dbUsers = new UsersDAO(db);
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		
		db = null;
		dbUsers = null;
	}
	
	@Test
	public void testGetAll() throws DatabaseException {
		
		List<User> all = dbUsers.getAll();
		assertEquals(0, all.size());
	}

	@Test
	public void testAdd() throws DatabaseException {
		
		User bob = new User("oldman", "1234", "Bob", 
									"White", "oldman@gmail.com", 200, 3);
		User amy = new User("oldwoman", "qwer", "Amy",
									"White", "oldwoman@hotmail.com", 777, 4);
		
		dbUsers.add(bob);
		dbUsers.add(amy);
		
		List<User> all = dbUsers.getAll();
		assertEquals(2, all.size());
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (User c : all) {
			
			assertFalse(c.getID() == -1);
			
			if (!foundBob) {
				foundBob = areEqual(c, bob, false);
			}		
			if (!foundAmy) {
				foundAmy = areEqual(c, amy, false);
			}
		}
		
		assertTrue(foundBob && foundAmy);
	}

	@Test
	public void testUpdate() throws DatabaseException {
		
		User bob = new User("oldman", "1234", "Bob", 
									"White", "oldman@gmail.com", 200, 3);
		User amy = new User("oldwoman", "qwer", "Amy",
									"White", "oldwoman@hotmail.com", 777, 4);
		
		dbUsers.add(bob);
		dbUsers.add(amy);
		
		bob.setName("Robert White");
		bob.setPassword("5678");
		bob.setFirstname("Ryan");
		bob.setLastname("Black");
		bob.setEmail("robert@white.org");
		bob.setIndexedrecords(999);
		bob.setBatch_id(44);
		
		amy.setName("Amy White");
		amy.setPassword("zxcv");
		amy.setFirstname("Yuna");
		amy.setLastname("Green");
		amy.setEmail("amy@white.org");
		amy.setIndexedrecords(4);
		amy.setBatch_id(33);
		
				
		dbUsers.update(bob);
		dbUsers.update(amy);
		
		List<User> all = dbUsers.getAll();
		
		assertEquals(2, all.size());
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (User c : all) {
			
			if (!foundBob) {
				foundBob = areEqual(c, bob, false);
			}		
			if (!foundAmy) {
				foundAmy = areEqual(c, amy, false);
			}
		}
		
		assertTrue(foundBob && foundAmy);
	}

	@Test
	public void testDelete() throws DatabaseException {
		
		User bob = new User("oldman", "1234", "Bob", 
									"White", "oldman@gmail.com", 200, 3);
		User amy = new User("oldwoman", "qwer", "Amy",
									"White", "oldwoman@hotmail.com", 777, 4);
		
		dbUsers.add(bob);
		dbUsers.add(amy);
		
		List<User> all = dbUsers.getAll();
		assertEquals(2, all.size());
		
		all.clear();
		
		dbUsers.delete(bob);
		dbUsers.delete(amy);
		
		all = dbUsers.getAll();
		
		assertEquals(0, all.size());
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		User invalidUser = new User(null,null,null,null,null,0,0);
		dbUsers.add(invalidUser);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		User invalidUser = new User(null,null,null,null,null,0,0);
		dbUsers.update(invalidUser);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException {
		
		User invalidUser = new User(null,null,null,null,null,0,0);
		dbUsers.delete(invalidUser);
	}
	
	private boolean areEqual(User a, User b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getID() != b.getID()) {
				return false;
			}
		}	
		return (safeEquals(a.getName(), b.getName()) &&
				safeEquals(a.getPassword(), b.getPassword()) &&
				safeEquals(a.getFirstname(), b.getFirstname()) &&
				safeEquals(a.getLastname(), b.getLastname()) &&
				safeEquals(a.getEmail(), b.getEmail()) &&
				safeEquals(a.getIndexedrecords(), b.getIndexedrecords()) &&
				safeEquals(a.getBatch_id(), b.getBatch_id()));
	}
	
	private boolean safeEquals(Object a, Object b) {
		if (a == null || b == null) {
			return (a == null && b == null);
		}
		else {
			return a.equals(b);
		}
	}

}