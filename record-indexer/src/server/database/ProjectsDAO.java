package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.*;

import shared.model.*;


public class ProjectsDAO {

	private static Logger logger;
	
	static {
		logger = Logger.getLogger("projectmanager");
	}

	private Project project = null;
	private Database db;
	private Connection connection = null;
	
	ProjectsDAO(Database db) {
		this.db = db;
		project = new Project();
		connection = db.getConnection();
	}
	
	public void setConnection(Connection connection){
		this.connection = connection;
	}	
	
	/**
	 * get all projects in the database
	 * @return a list of all projects
	 * @throws DatabaseException
	 */
	public List<Project> getAll() throws DatabaseException {
				
		List<Project> projectlist = new ArrayList<Project>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM projects";
			stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				int project_id = rs.getInt(1);
				String title = rs.getString(2);
				int recordsperimage = rs.getInt(3);
				int firstycoord = rs.getInt(4);
				int recordheight = rs.getInt(5);
				
				Project project = new Project(title, recordsperimage, 
						firstycoord, recordheight);
				project.setID(project_id);
				projectlist.add(project);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return projectlist;	
	}
	
	/**
	 * add a project in the database
	 * @param project
	 * @throws DatabaseException
	 */
	public void add(Project project) throws DatabaseException {
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		
		try {
			String sql = "INSERT INTO projects (title, recordsperimage, firstycoord, recordheight) " +
					"VALUES (?, ?, ?, ?)";			
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, project.getTitle());
			stmt.setInt(2, project.getRecordsperimage());
			stmt.setInt(3, project.getFirstycoord());
			stmt.setInt(4, project.getRecordheight());
			if(project.getTitle() == null){
				throw new DatabaseException();
			}
			
			if(stmt.executeUpdate()==1){
				keyStmt = connection.createStatement();
				keyRS = keyStmt.executeQuery("SELECT last_insert_rowid()");
				keyRS.next();
				int project_id = keyRS.getInt(1);	// ID of the new project
				project.setID(project_id);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(keyRS != null){
				try {
					keyRS.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(keyStmt != null){
				try {
					keyStmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	
	/**
	 * update a project in the database
	 * @param project
	 * @throws DatabaseException
	 */
	public void update(Project project) throws DatabaseException {
		PreparedStatement stmt = null;
		
		try {
			String sql = "UPDATE projects " +
					 "SET title=?, recordsperimage=?, firstycoord=?, recordheight=? " +
					 "WHERE project_id=?";
			
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, project.getTitle());
			stmt.setInt(2, project.getRecordsperimage());
			stmt.setInt(3, project.getFirstycoord());
			stmt.setInt(4, project.getRecordheight());
			stmt.setInt(5, project.getID());
			
			if(stmt.executeUpdate() != 1){
				throw new DatabaseException("Could not update Project");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}

	}
	
	/**
	 * delete a project in the database
	 * @param project
	 * @throws DatabaseException
	 */
	public void delete(Project project) throws DatabaseException {
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		
		try {
			String sql = "DELETE FROM projects " +
						 "WHERE title=?";			
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, project.getTitle());
			if(stmt.executeUpdate() != 1){
				throw new DatabaseException("Could not delete Project");
			}
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(keyRS != null){
				try {
					keyRS.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(keyStmt != null){
				try {
					keyStmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}


}