package server.database;

import java.io.File;
import java.sql.*;
import java.util.logging.*;


public class Database {
	
	private static Logger logger;
	
	static {
		logger = Logger.getLogger("dbmanager");
	}

	/**
	 * initializes the database
	 * @throws DatabaseException
	 */
	public static void initialize() throws DatabaseException {
		
		logger.entering("server.database.Database", "initialize");
		
		// TODO: Load the SQLite database driver
		final String driver = "org.sqlite.JDBC";
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			// ERROR! Could not load database driver			
			e.printStackTrace();
		}
		
		logger.exiting("server.database.Database", "initialize");
	}

	private UsersDAO usersDAO;
	private ProjectsDAO projectsDAO;
	private FieldsDAO fieldsDAO;
	private BatchesDAO batchesDAO;
	private CellsDAO cellsDAO;	
	private Connection connection;
	
	public Database() {
		usersDAO = new UsersDAO(this);
		projectsDAO = new ProjectsDAO(this);
		fieldsDAO = new FieldsDAO(this);
		batchesDAO = new BatchesDAO(this);
		cellsDAO = new CellsDAO(this);
		connection = null;
	}
	
	/**
	 * 
	 * @return usersDAO
	 */
	public UsersDAO getUsersDAO() {
		return usersDAO;
	}
	/**
	 * 
	 * @return projectsDAO
	 */
	public ProjectsDAO getProjectsDAO() {
		return projectsDAO;
	}
	/**
	 * 
	 * @return fieldsDAO
	 */
	public FieldsDAO getFieldsDAO() {
		return fieldsDAO;
	}
	/**
	 * 
	 * @return batchesDAO
	 */
	public BatchesDAO getBatchesDAO() {
		return batchesDAO;
	}
	/**
	 * 
	 * @return cellsDAO
	 */
	public CellsDAO getCellsDAO() {
		return cellsDAO;
	}
	/**
	 * 
	 * @return connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Open a connection to the database and start a transaction
	 * @throws DatabaseException
	 */
	public void startTransaction() throws DatabaseException {

		logger.entering("server.database.Database", "startTransaction");
		
		// TODO: Open a connection to the database and start a transaction
		String dbName = "database" + File.separator + "practice.sqlite";
		String connectionURL = "jdbc:sqlite:" + dbName;
		
		try {
			connection = DriverManager.getConnection(connectionURL);
			usersDAO.setConnection(connection);
			projectsDAO.setConnection(connection);
			fieldsDAO.setConnection(connection);
			batchesDAO.setConnection(connection);
			cellsDAO.setConnection(connection);
			connection.setAutoCommit(false);
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		logger.exiting("server.database.Database", "startTransaction");
	}
	
	/**
	 * Commit or rollback the transaction and close the connection
	 * @param commit
	 */
	public void endTransaction(boolean commit) {
		
		logger.entering("server.database.Database", "endTransaction");
		
		// TODO: Commit or rollback the transaction and close the connection
		
		try {
			if(commit){
				connection.commit();
			}
			else{
				connection.rollback();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
		logger.exiting("server.database.Database", "endTransaction");
	}
	
}