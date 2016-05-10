package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.*;

import shared.model.*;


public class FieldsDAO {

	private static Logger logger;
	
	static {
		logger = Logger.getLogger("fieldmanager");
	}

	private Field field = null;
	private Database db;
	private Connection connection = null;
	
	FieldsDAO(Database db) {
		this.db = db;
		field = new Field();
		connection = db.getConnection();
	}
	
	public void setConnection(Connection connection){
		this.connection = connection;
	}	
	
	/**
	 * get all fields in the database
	 * @return a list of all fields
	 * @throws DatabaseException
	 */
	public List<Field> getAll() throws DatabaseException {	
		List<Field> fieldlist = new ArrayList<Field>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM fields";
			stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				int field_id = rs.getInt(1);
				int number = rs.getInt(2);
				String title = rs.getString(3);
				int xcoord = rs.getInt(4);
				int width = rs.getInt(5);
				String helphtml = rs.getString(6);
				String knowndata = rs.getString(7);
				int project_id = rs.getInt(8);
				
				
				Field field = new Field(number, title, xcoord, width, 
						helphtml, knowndata, project_id);
				field.setField_id(field_id);				
				fieldlist.add(field);
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
		
		return fieldlist;	
	}
	
	/**
	 * add a field in the database
	 * @param field
	 * @throws DatabaseException
	 */
	public void add(Field field) throws DatabaseException {
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		
		try {
			String sql = "INSERT INTO fields (number, title, xcoord, " +
					"width, helphtml, knowndata, fk_project_id) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?)";			
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, field.getField_number());
			stmt.setString(2, field.getTitle());
			stmt.setInt(3, field.getXcoord());
			stmt.setInt(4, field.getWidth());
			stmt.setString(5, field.getHelphtml());
			stmt.setString(6, field.getKnowndata());
			stmt.setInt(7, field.getProject_id());
			if(field.getTitle() == null && field.getHelphtml() == null){
				throw new DatabaseException();
			}			
			
			if(stmt.executeUpdate()==1){
				keyStmt = connection.createStatement();
				keyRS = keyStmt.executeQuery("SELECT last_insert_rowid()");
				keyRS.next();
				int field_id = keyRS.getInt(1);	// ID of the new field
				field.setField_id(field_id);
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
	 * update a field in the database
	 * @param field
	 * @throws DatabaseException
	 */
	public void update(Field field) throws DatabaseException {
		PreparedStatement stmt = null;
		
		try {
			String sql = "UPDATE fields " +
					 "SET number=?, title=?, xcoord=?, width=?, " +
					 "helphtml=?, knowndata=?, fk_project_id=?" +
					 "WHERE field_id=?";
			
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, field.getField_number());
			stmt.setString(2, field.getTitle());
			stmt.setInt(3, field.getXcoord());
			stmt.setInt(4, field.getWidth());
			stmt.setString(5, field.getHelphtml());
			stmt.setString(6, field.getKnowndata());
			stmt.setInt(7, field.getProject_id());
			stmt.setInt(8, field.getField_id());
			
			if(stmt.executeUpdate() != 1){
				throw new DatabaseException("Could not update Field");
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
	 * delete a field in the database
	 * @param field
	 * @throws DatabaseException
	 */
	public void delete(Field field) throws DatabaseException {
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		
		try {
			String sql = "DELETE FROM fields " +
						 "WHERE field_id=?";			
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, field.getField_id());
			
//			System.out.println(field.getField_id());
			
			if(stmt.executeUpdate() != 1){
				throw new DatabaseException("Could not delete Field");
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