package server.database;

import java.util.*;
import static org.junit.Assert.*;

import org.junit.*;

import server.database.Database;
import server.database.DatabaseException;
import server.database.CellsDAO;
import shared.model.*;


public class CellDAOTest {

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
	private CellsDAO dbCells;

	@Before
	public void setUp() throws Exception {

		// Delete all projects from the database	
		db = new Database();
		db.startTransaction();
		dbCells = new CellsDAO(db);
		
		List<Cell> Cells = dbCells.getAll();
				
		for (Cell c : Cells) {
			dbCells.delete(c);
		}
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new Database();
		db.startTransaction();
		dbCells = new CellsDAO(db);
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		
		db = null;
		dbCells = null;
	}
	
	@Test
	public void testGetAll() throws DatabaseException {
		
		List<Cell> all = dbCells.getAll();
		assertEquals(0, all.size());
	}

	@Test
	public void testAdd() throws DatabaseException {
		
		Cell newCell1 = new Cell(1, "Horse", 10, 40);
		Cell newCell2 = new Cell(2, "Dog", 11, 41);
		
		dbCells.add(newCell1);
		dbCells.add(newCell2);
		
		List<Cell> all = dbCells.getAll();
		assertEquals(2, all.size());
		
		boolean foundnewCell1 = false;
		boolean foundnewCell2 = false;
		
		for (Cell c : all) {
			
			assertFalse(c.getCell_id() == -1);
			
			if (!foundnewCell1) {
				foundnewCell1 = areEqual(c, newCell1, false);
			}		
			if (!foundnewCell2) {
				foundnewCell2 = areEqual(c, newCell2, false);
			}
		}
		
		assertTrue(foundnewCell1 && foundnewCell2);
	}

	@Test
	public void testUpdate() throws DatabaseException {
		
		Cell newCell1 = new Cell(1, "Horse", 10, 40);
		Cell newCell2 = new Cell(2, "Dog", 11, 41);
		
		dbCells.add(newCell1);
		dbCells.add(newCell2);
		
		newCell1.setRow(10);
		newCell1.setValue("Elephant");
		newCell1.setField_id(20);
		newCell1.setBatch_id(50);
		
		newCell2.setRow(20);
		newCell2.setValue("Tiger");
		newCell2.setField_id(21);
		newCell2.setBatch_id(51);
				
		dbCells.update(newCell1);
		dbCells.update(newCell2);
		
		List<Cell> all = dbCells.getAll();
		
		assertEquals(2, all.size());
		
		boolean foundnewCell1 = false;
		boolean foundnewCell2 = false;
		
		for (Cell c : all) {
			
			assertFalse(c.getCell_id() == -1);
			
			if (!foundnewCell1) {
				foundnewCell1 = areEqual(c, newCell1, false);
			}		
			if (!foundnewCell2) {
				foundnewCell2 = areEqual(c, newCell2, false);
			}
		}
		
		assertTrue(foundnewCell1 && foundnewCell2);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		Cell invalidCell = new Cell(0,null,0,0);
		dbCells.add(invalidCell);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		Cell invalidCell = new Cell(0,null,0,0);
		dbCells.update(invalidCell);
	}
		
	private boolean areEqual(Cell a, Cell b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getCell_id() != b.getCell_id()) {
				return false;
			}
		}	
		return (safeEquals(a.getRow(), b.getRow()) &&
				safeEquals(a.getValue(), b.getValue()) &&
				safeEquals(a.getField_id(), b.getField_id()) &&
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