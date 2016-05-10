package server.database;

import java.util.*;
import static org.junit.Assert.*;

import org.junit.*;

import server.database.Database;
import server.database.DatabaseException;
import server.database.FieldsDAO;
import shared.model.*;


public class FieldDAOTest {

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
	private FieldsDAO dbFields;

	@Before
	public void setUp() throws Exception {

		// Delete all projects from the database	
		db = new Database();
		db.startTransaction();
		dbFields = new FieldsDAO(db);
		
		List<Field> fields = dbFields.getAll();
				
		for (Field c : fields) {
			dbFields.delete(c);
		}
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new Database();
		db.startTransaction();
		dbFields = new FieldsDAO(db);
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		
		db = null;
		dbFields = null;
	}
	
	@Test
	public void testGetAll() throws DatabaseException {
		
		List<Field> all = dbFields.getAll();
		assertEquals(0, all.size());
	}

	@Test
	public void testAdd() throws DatabaseException {
		
		Field newfield1 = new Field(4,"year", 50, 15, "www", "knowndata", 1);
		Field newfield2 = new Field(5,"date", 50, 15, "zzz", null, 2);
		
		dbFields.add(newfield1);
		dbFields.add(newfield2);
		
		List<Field> all = dbFields.getAll();
		assertEquals(2, all.size());
		
		boolean foundnewfield1 = false;
		boolean foundnewfield2 = false;
		
		for (Field c : all) {
			
			assertFalse(c.getField_id() == -1);
			
			if (!foundnewfield1) {
				foundnewfield1 = areEqual(c, newfield1, false);
			}		
			if (!foundnewfield2) {
				foundnewfield2 = areEqual(c, newfield2, false);
			}
		}
		
		assertTrue(foundnewfield1 && foundnewfield2);
	}

	@Test
	public void testUpdate() throws DatabaseException {
		
		Field newfield1 = new Field(4,"year", 50, 15, "www", "knowndata", 1);
		Field newfield2 = new Field(5,"date", 50, 15, "zzz", null, 2);
		
		dbFields.add(newfield1);
		dbFields.add(newfield2);
		
		newfield1.setField_number(1);
		newfield1.setTitle("time");
		newfield1.setXcoord(100);
		newfield1.setWidth(25);
		newfield1.setHelphtml("xxx");
		newfield1.setKnowndata(null);
		newfield1.setProject_id(5);
		
		newfield2.setField_number(10);
		newfield2.setTitle("second");
		newfield2.setXcoord(100);
		newfield2.setWidth(25);
		newfield2.setHelphtml("kkk");
		newfield2.setKnowndata("knowndata");
		newfield2.setProject_id(7);
				
		dbFields.update(newfield1);
		dbFields.update(newfield2);
		
		List<Field> all = dbFields.getAll();
		
		assertEquals(2, all.size());
		
		boolean foundnewfield1 = false;
		boolean foundnewfield2 = false;
		
		for (Field c : all) {
			
			assertFalse(c.getField_id() == -1);
			
			if (!foundnewfield1) {
				foundnewfield1 = areEqual(c, newfield1, false);
			}		
			if (!foundnewfield2) {
				foundnewfield2 = areEqual(c, newfield2, false);
			}
		}
		
		assertTrue(foundnewfield1 && foundnewfield2);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		Field invalidField = new Field(0,null,0,0,null,null,0);
		dbFields.add(invalidField);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		Field invalidField = new Field(0,null,0,0,null,null,0);
		dbFields.update(invalidField);
	}
		
	private boolean areEqual(Field a, Field b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getField_id() != b.getField_id()) {
				return false;
			}
		}	
		return (safeEquals(a.getField_number(), b.getField_number()) &&
				safeEquals(a.getTitle(), b.getTitle()) &&
				safeEquals(a.getXcoord(), b.getXcoord()) &&
				safeEquals(a.getWidth(), b.getWidth()) &&
				safeEquals(a.getHelphtml(), b.getHelphtml()) &&
				safeEquals(a.getKnowndata(), b.getKnowndata()) &&
				safeEquals(a.getProject_id(), b.getProject_id()));
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