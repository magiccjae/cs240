package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.*;

import shared.model.*;


public class CellsDAO {

	private static Logger logger;
	
	static {
		logger = Logger.getLogger("cellmanager");
	}

	private Cell cell = null;
	private Database db;
	private Connection connection = null;
	
	CellsDAO(Database db) {
		this.db = db;
		cell = new Cell();
		connection = db.getConnection();
	}
	
	public void setConnection(Connection connection){
		this.connection = connection;
	}
	
	/**
	 * get all cells in the database
	 * @return a list of all cells
	 * @throws DatabaseException
	 */
	public List<Cell> getAll() throws DatabaseException {
				
		List<Cell> celllist = new ArrayList<Cell>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM cells";
			stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				int cell_id = rs.getInt(1);
				int row = rs.getInt(2);
				String value = rs.getString(3);
				int field_id = rs.getInt(4);
				int batch_id = rs.getInt(5);			
				
				Cell cell = new Cell(row, value, field_id, batch_id);
				cell.setCell_id(cell_id);
				celllist.add(cell);
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
		
		return celllist;	
	}
	
	/**
	 * add a cell in the database
	 * @param cell
	 * @throws DatabaseException
	 */
	public void add(Cell cell) throws DatabaseException {
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		
		try {
			String sql = "INSERT INTO cells (row, value, fk_field_id, fk_batch_id) " +
					"VALUES (?, ?, ?, ?)";			
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, cell.getRow());
			stmt.setString(2, cell.getValue());
			stmt.setInt(3, cell.getField_id());
			stmt.setInt(4, cell.getBatch_id());
			
			if(cell.getValue() == null){
				throw new DatabaseException();
			}
			
			
			if(stmt.executeUpdate()==1){
				keyStmt = connection.createStatement();
				keyRS = keyStmt.executeQuery("SELECT last_insert_rowid()");
				keyRS.next();
				int cell_id = keyRS.getInt(1);	// ID of the new cell
				cell.setCell_id(cell_id);
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
	 * update a cell in the database
	 * @param cell
	 * @throws DatabaseException
	 */
	public void update(Cell cell) throws DatabaseException {
		PreparedStatement stmt = null;
		
		try {
			String sql = "UPDATE cells " +
					 "SET row=?, value=?, fk_field_id=?, fk_batch_id=?" +
					 "WHERE cell_id=?";
			
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, cell.getRow());
			stmt.setString(2, cell.getValue());
			stmt.setInt(3, cell.getField_id());
			stmt.setInt(4, cell.getBatch_id());
			stmt.setInt(5, cell.getCell_id());
						
			if(stmt.executeUpdate() != 1){
				throw new DatabaseException("Could not update Cell");
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
	 * delete a cell in the database
	 * @param cell
	 * @throws DatabaseException
	 */
	public void delete(Cell cell) throws DatabaseException {
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		
		try {
			String sql = "DELETE FROM cells " +
						 "WHERE cell_id=?";			
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, cell.getCell_id());
			if(stmt.executeUpdate() != 1){
				throw new DatabaseException("Could not delete Cell");
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