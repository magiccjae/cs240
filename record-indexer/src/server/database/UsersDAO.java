package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.*;

import shared.model.*;


public class UsersDAO {

	private static Logger logger;
	
	static {
		logger = Logger.getLogger("usermanager");
	}

	private User user = null;
	private Database db;
	private Connection connection = null;
	
	public UsersDAO(Database db) {
		this.db = db;
		user = new User();
		connection = db.getConnection();
	}
	
	public void setConnection(Connection connection){
		this.connection = connection;
	}
	
	/**
	 * get all users in the database
	 * @return a list of all users
	 * @throws DatabaseException
	 */
	public List<User> getAll() throws DatabaseException {
		
		List<User> userlist = new ArrayList<User>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM users";
			stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				int user_id = rs.getInt(1);
				String username = rs.getString(2);
				String password = rs.getString(3);
				String firstname = rs.getString(4);
				String lastname = rs.getString(5);
				String email = rs.getString(6);
				int indexedrecords = rs.getInt(7);
				int batch_id = rs.getInt(8);
				
				
				User user = new User(username, password, 
									firstname, lastname, email, indexedrecords, batch_id);
				user.setID(user_id);
				userlist.add(user);
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
		
		return userlist;	
	}
	
	/**
	 * add a user in the database
	 * @param user
	 * @throws DatabaseException
	 */
	public void add(User user) throws DatabaseException {
		
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		
		try {
			String sql = "INSERT INTO users (username, password, firstname, lastname, " +
						 "email, indexedrecords, batch_id) " +
						 "VALUES (?, ?, ?, ?, ?, ?, ?)";			
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getFirstname());
			stmt.setString(4, user.getLastname());
			stmt.setString(5, user.getEmail());
			stmt.setInt(6, user.getIndexedrecords());
			stmt.setInt(7, user.getBatch_id());
			
			if(user.getName() == null && user.getPassword() == null && user.getFirstname() == null && user.getLastname() == null && user.getEmail() == null){
				throw new DatabaseException();
			}
			
			if(stmt.executeUpdate()==1){
				keyStmt = connection.createStatement();
				keyRS = keyStmt.executeQuery("SELECT last_insert_rowid()");
				keyRS.next();
				int user_id = keyRS.getInt(1);	// ID of the new user
				user.setID(user_id);
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
	 * update a user in the database
	 * @param user
	 * @throws DatabaseException
	 */
	public void update(User user) throws DatabaseException {
		PreparedStatement stmt = null;
		
		try {
			String sql = "UPDATE users " +
					 "SET username=?, password=?, firstname=?, lastname=?, " +
					 "email=?, indexedrecords=?, batch_id=? " +
					 "WHERE user_id=?";
			
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getFirstname());
			stmt.setString(4, user.getLastname());
			stmt.setString(5, user.getEmail());
			stmt.setInt(6, user.getIndexedrecords());
			stmt.setInt(7, user.getBatch_id());
			stmt.setInt(8, user.getID());
						
			if(stmt.executeUpdate() != 1){
				throw new DatabaseException("Could not update User");
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
	 * delete a user in the database
	 * @param user
	 * @throws DatabaseException
	 */
	public void delete(User user) throws DatabaseException {
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		
		try {
			String sql = "DELETE FROM users " +
						 "WHERE username=?";			
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, user.getName());
			if(stmt.executeUpdate() != 1){
				throw new DatabaseException("Could not delete User");
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