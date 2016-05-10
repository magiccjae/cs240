package server.database;

import java.util.*;
import static org.junit.Assert.*;

import org.junit.*;

import server.database.Database;
import server.database.DatabaseException;
import server.database.ProjectsDAO;
import shared.model.*;


public class ProjectDAOTest {

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
	private ProjectsDAO dbProjects;

	@Before
	public void setUp() throws Exception {

		// Delete all projects from the database	
		db = new Database();
		db.startTransaction();
		dbProjects = new ProjectsDAO(db);
		
		List<Project> projects = dbProjects.getAll();
		
		for (Project c : projects) {
			dbProjects.delete(c);
		}
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new Database();
		db.startTransaction();
		dbProjects = new ProjectsDAO(db);
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		
		db = null;
		dbProjects = null;
	}
	
	@Test
	public void testGetAll() throws DatabaseException {
		
		List<Project> all = dbProjects.getAll();
		assertEquals(0, all.size());
	}

	@Test
	public void testAdd() throws DatabaseException {
		
		Project project1986 = new Project("project1986", 7, 50, 15);
		Project project1987 = new Project("project1987", 6, 50, 15);
		
		dbProjects.add(project1986);
		dbProjects.add(project1987);
		
		List<Project> all = dbProjects.getAll();
		assertEquals(2, all.size());
		
		boolean foundproject1986 = false;
		boolean foundproject1987 = false;
		
		for (Project c : all) {
			
			assertFalse(c.getID() == -1);
			
			if (!foundproject1986) {
				foundproject1986 = areEqual(c, project1986, false);
			}		
			if (!foundproject1987) {
				foundproject1987 = areEqual(c, project1987, false);
			}
		}
		
		assertTrue(foundproject1986 && foundproject1987);
	}

	@Test
	public void testUpdate() throws DatabaseException {
		
		Project project1986 = new Project("project1986", 7, 50, 15);
		Project project1987 = new Project("project1987", 6, 50, 15);
		
		dbProjects.add(project1986);
		dbProjects.add(project1987);
		
		project1986.setTitle("project1");
		project1986.setRecordsperimage(1);
		project1986.setFirstycoord(10);
		project1986.setRecordheight(10);
		
		project1987.setTitle("project2");
		project1987.setRecordsperimage(1);
		project1987.setFirstycoord(10);
		project1987.setRecordheight(10);
				
		dbProjects.update(project1986);
		dbProjects.update(project1987);
		
		List<Project> all = dbProjects.getAll();
		
		assertEquals(2, all.size());
		
		boolean foundproject1986 = false;
		boolean foundproject1987 = false;
		
		for (Project c : all) {
			
			if (!foundproject1986) {
				foundproject1986 = areEqual(c, project1986, false);
			}		
			if (!foundproject1987) {
				foundproject1987 = areEqual(c, project1987, false);
			}
		}
		
		assertTrue(foundproject1986 && foundproject1987);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		Project invalidProject = new Project(null,0,0,0);
		dbProjects.add(invalidProject);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		Project invalidProject = new Project(null,0,0,0);
		dbProjects.update(invalidProject);
	}
		
	private boolean areEqual(Project a, Project b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getID() != b.getID()) {
				return false;
			}
		}	
		return (safeEquals(a.getTitle(), b.getTitle()) &&
				safeEquals(a.getRecordsperimage(), b.getRecordsperimage()) &&
				safeEquals(a.getFirstycoord(), b.getFirstycoord()) &&
				safeEquals(a.getRecordheight(), b.getRecordheight()));
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