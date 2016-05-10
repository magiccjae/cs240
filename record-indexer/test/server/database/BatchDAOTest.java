package server.database;

import java.util.*;
import static org.junit.Assert.*;

import org.junit.*;

import server.database.Database;
import server.database.DatabaseException;
import server.database.BatchesDAO;
import shared.model.*;


public class BatchDAOTest {

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
	private BatchesDAO dbBatches;

	@Before
	public void setUp() throws Exception {

		// Delete all projects from the database	
		db = new Database();
		db.startTransaction();
		dbBatches = new BatchesDAO(db);
		
		List<Batch> batches = dbBatches.getAll();
				
		for (Batch c : batches) {
			dbBatches.delete(c);
		}
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new Database();
		db.startTransaction();
		dbBatches = new BatchesDAO(db);
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		
		db = null;
		dbBatches = null;
	}
	
	@Test
	public void testGetAll() throws DatabaseException {
		
		List<Batch> all = dbBatches.getAll();
		assertEquals(0, all.size());
	}

	@Test
	public void testAdd() throws DatabaseException {
		
		Batch newbatch1 = new Batch("file1", "available", 1);
		Batch newbatch2 = new Batch("file2", "checkedout", 2);
		
		dbBatches.add(newbatch1);
		dbBatches.add(newbatch2);
		
		List<Batch> all = dbBatches.getAll();
		assertEquals(2, all.size());
		
		boolean foundnewbatch1 = false;
		boolean foundnewbatch2 = false;
		
		for (Batch c : all) {
			
			assertFalse(c.getBatch_id() == -1);
			
			if (!foundnewbatch1) {
				foundnewbatch1 = areEqual(c, newbatch1, false);
			}		
			if (!foundnewbatch2) {
				foundnewbatch2 = areEqual(c, newbatch2, false);
			}
		}
		
		assertTrue(foundnewbatch1 && foundnewbatch2);
	}

	@Test
	public void testUpdate() throws DatabaseException {
		
		Batch newbatch1 = new Batch("file1", "available", 1);
		Batch newbatch2 = new Batch("file2", "checkedout", 2);
		
		dbBatches.add(newbatch1);
		dbBatches.add(newbatch2);
		
		newbatch1.setFile("file3");
		newbatch1.setStatus("unavailable");
		newbatch1.setProject_id(10);
		
		newbatch2.setFile("file4");
		newbatch2.setStatus("complete");
		newbatch2.setProject_id(20);
		
				
		dbBatches.update(newbatch1);
		dbBatches.update(newbatch2);
		
		List<Batch> all = dbBatches.getAll();
		
		assertEquals(2, all.size());
		
		boolean foundnewbatch1 = false;
		boolean foundnewbatch2 = false;
		
		for (Batch c : all) {
			
			assertFalse(c.getBatch_id() == -1);
			
			if (!foundnewbatch1) {
				foundnewbatch1 = areEqual(c, newbatch1, false);
			}		
			if (!foundnewbatch2) {
				foundnewbatch2 = areEqual(c, newbatch2, false);
			}
		}
		
		assertTrue(foundnewbatch1 && foundnewbatch2);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		Batch invalidBatch = new Batch(null,null,0);
		dbBatches.add(invalidBatch);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		Batch invalidBatch = new Batch(null,null,0);
		dbBatches.update(invalidBatch);
	}
		
	private boolean areEqual(Batch a, Batch b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getBatch_id() != b.getBatch_id()) {
				return false;
			}
		}	
		return (safeEquals(a.getFile(), b.getFile()) &&
				safeEquals(a.getStatus(), b.getStatus()) &&
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