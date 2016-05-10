package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.*;

import shared.model.*;


public class BatchesDAO {

	private static Logger logger;
	
	static {
		logger = Logger.getLogger("fieldmanager");
	}

	private Batch batch = null;
	private Database db;
	private Connection connection = null;
	
	BatchesDAO(Database db) {
		this.db = db;
		batch = new Batch();
		connection = db.getConnection();
	}
	
	public void setConnection(Connection connection){
		this.connection = connection;
	}
	
	/**
	 * get all batches in the database
	 * @return a list of all batches
	 * @throws DatabaseException
	 */
	public List<Batch> getAll() throws DatabaseException {
				
		List<Batch> batchlist = new ArrayList<Batch>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM batches";
			stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				int batch_id = rs.getInt(1);
				String file = rs.getString(2);
				String status = rs.getString(3);
				int project_id = rs.getInt(4);				
				
				Batch batch = new Batch(file, status, project_id);
				batch.setBatch_id(batch_id);
				batchlist.add(batch);
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
		
		return batchlist;	
	}
	
	/**
	 * add a batch in the database
	 * @param batch
	 * @throws DatabaseException
	 */
	public void add(Batch batch) throws DatabaseException {
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		
		try {
			String sql = "INSERT INTO batches (file, status, fk_project_id) " +
					"VALUES (?, ?, ?)";			
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, batch.getFile());
			stmt.setString(2, batch.getStatus());
			stmt.setInt(3, batch.getProject_id());

			if(batch.getFile() == null && batch.getStatus() == null){
				throw new DatabaseException();
			}

			if(stmt.executeUpdate()==1){
				keyStmt = connection.createStatement();
				keyRS = keyStmt.executeQuery("SELECT last_insert_rowid()");
				keyRS.next();
				int batch_id = keyRS.getInt(1);	// ID of the new batch
				batch.setBatch_id(batch_id);
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
	 * update a batch in the database
	 * @param batch
	 * @throws DatabaseException
	 */
	public void update(Batch batch) throws DatabaseException {
		PreparedStatement stmt = null;
		
		try {
			String sql = "UPDATE batches " +
					 "SET file=?, status=?, fk_project_id=? " +
					 "WHERE batch_id=?";
			
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, batch.getFile());
			stmt.setString(2, batch.getStatus());
			stmt.setInt(3, batch.getProject_id());
			stmt.setInt(4, batch.getBatch_id());
									
			if(stmt.executeUpdate() != 1){
				throw new DatabaseException("Could not update Batch");
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
	 * delete a batch in the database
	 * @param batch
	 * @throws DatabaseException
	 */
	public void delete(Batch batch) throws DatabaseException {
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		
		try {
			String sql = "DELETE FROM batches " +
						 "WHERE batch_id=?";			
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, batch.getBatch_id());
			
			if(stmt.executeUpdate() != 1){
				throw new DatabaseException("Could not delete Batch");
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