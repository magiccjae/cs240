package dataimporter;

import java.io.*;
import java.nio.file.*;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.parsers.*;

import org.w3c.dom.*;

import server.database.Database;
import server.database.DatabaseException;
import shared.model.*;

public class Importer {
	
	private Database db = new Database();
		
	public static void main(String[] args) throws Exception {
		
		Importer importer = new Importer();
		
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		
		String fileName = args[0];
		File file = new File(fileName);
		Document doc = builder.parse(file);
		
		String importing_path = file.getParent();
		String projectdir = System.getProperty("user.dir") + "/data";
		System.out.println(projectdir);
		importer.deleteAllFiles(projectdir);
		importer.copyFiles(importing_path, projectdir);
		
		importer.createTable();		
		importer.xmlParser(doc);
		
	}
	
	private void copyFiles(String sourcePath, String destination){
		File sourceFile = new File(sourcePath);
		if(sourceFile.isDirectory()){
			File[] subFiles = sourceFile.listFiles();
			
			for(File temp: subFiles){
				copyFiles(temp.getAbsolutePath(),destination + File.separator + temp.getName());
			}
		}
		else{
			java.nio.file.Path source = Paths.get(sourcePath);
			java.nio.file.Path dest = Paths.get(destination);
			try {
				Files.copy(source, dest);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	private void deleteAllFiles(String sourcePath){
		File sourceFile = new File(sourcePath);
		if(sourceFile.isDirectory()){
			File[] subFiles = sourceFile.listFiles();
			
			for(File temp: subFiles){
				deleteAllFiles(temp.getAbsolutePath());
			}
		}
		else{
			sourceFile.delete();
		}
	}
	
	public void createTable() throws DatabaseException, SQLException{
		Database.initialize();
		db.startTransaction();
		Statement stat = db.getConnection().createStatement();
		
		stat.executeUpdate("drop table if exists users;" +
						   "drop table if exists projects;" +
						   "drop table if exists fields;" + 
						   "drop table if exists batches;" +
						   "drop table if exists cells;" +
							
							"create table users" +
							"(" +
							"	user_id integer not null primary key autoincrement," +
							"	username text not null," +
							"	password text not null," +
							"	firstname text not null," +
							"	lastname text not null," +
							"	email text not null," +
							"	indexedrecords integer not null," +
							"	batch_id integer not null" +
							
							");" +
							
							"create table projects" +
							"(" +
							"	project_id integer not null primary key autoincrement," +
							"	title text not null," +
							"	recordsperimage integer not null," +
							"	firstycoord integer not null," +
							"	recordheight integer not null" +
							
							");" +
							
							"create table fields" +
							"(" +
							"	field_id integer not null primary key autoincrement," +
							"	number integer not null," +
							"	title text not null," +
							"	xcoord integer not null," +
							"	width integer not null," +
							"	helphtml text not null," +
							"	knowndata text," +
							"	fk_project_id not null" +
							
							");" +
							
							"create table batches" +
							"(" +
							"	batch_id integer not null primary key autoincrement," +
							"	file text not null," +
							"   status text not null," +
							"	fk_project_id not null" +
							
							");" +
							
							"create table cells" +
							"(" +
							"	cell_id integer not null primary key autoincrement," +
							"	row integer not null," +
							"	value text not null," +
							"	fk_field_id not null," +
							"	fk_batch_id not null" +
							");");
	
	}
	
	public void xmlParser(Document doc) throws DatabaseException{
		
		NodeList userList = doc.getElementsByTagName("user");
		int userLength = userList.getLength();
		for(int i = 0; i < userLength; i++){
			Element userElem = (Element)userList.item(i);
			
			Element usernameElem = (Element) userElem.getElementsByTagName("username").item(0);
			Element passwordElem = (Element) userElem.getElementsByTagName("password").item(0);
			Element firstnameElem = (Element) userElem.getElementsByTagName("firstname").item(0);
			Element lastnameElem = (Element) userElem.getElementsByTagName("lastname").item(0);
			Element emailElem = (Element) userElem.getElementsByTagName("email").item(0);
			Element indexedrecordsElem = (Element) userElem.getElementsByTagName("indexedrecords").item(0);
			
			String username = usernameElem.getTextContent();
			String password = passwordElem.getTextContent();
			String firstname = firstnameElem.getTextContent();
			String lastname = lastnameElem.getTextContent();
			String email = emailElem.getTextContent();
			String indexedrecords = indexedrecordsElem.getTextContent();
			
			User newuser = new User(username, password, firstname, lastname, 
									email, Integer.parseInt(indexedrecords), -1);
			db.getUsersDAO().add(newuser);
		}
		
		NodeList projectList = doc.getElementsByTagName("project");
		int field_id_help =0;
		int projectLength = projectList.getLength();
		for(int i = 0; i < projectLength; i++){
			Element projectElem = (Element)projectList.item(i);
			
			Element titleElem = (Element) projectElem.getElementsByTagName("title").item(0);
			Element recordsperimageElem = (Element) projectElem.getElementsByTagName("recordsperimage").item(0);
			Element firstycoordElem = (Element) projectElem.getElementsByTagName("firstycoord").item(0);
			Element recordheightElem = (Element) projectElem.getElementsByTagName("recordheight").item(0);

			String title = titleElem.getTextContent();
			String recordsperimage = recordsperimageElem.getTextContent();
			String firstycoord = firstycoordElem.getTextContent();
			String recordheight = recordheightElem.getTextContent();
			
			Project newproject = new Project(title, Integer.parseInt(recordsperimage), 
											Integer.parseInt(firstycoord), Integer.parseInt(recordheight));
			db.getProjectsDAO().add(newproject);

			NodeList fieldList = projectElem.getElementsByTagName("field");
			int fieldLength = fieldList.getLength();
			field_id_help += fieldLength;
			for(int j = 0; j < fieldLength; j++){
				int field_number = j+1;				
				Element fieldElem = (Element)fieldList.item(j);
				
				Element fieldtitleElem = (Element) fieldElem.getElementsByTagName("title").item(0);
				Element xcoordElem = (Element) fieldElem.getElementsByTagName("xcoord").item(0);
				Element widthElem = (Element) fieldElem.getElementsByTagName("width").item(0);
				Element helphtmlElem = (Element) fieldElem.getElementsByTagName("helphtml").item(0);
				Element knowndataElem = (Element) fieldElem.getElementsByTagName("knowndata").item(0);
				
				String fieldtitle = fieldtitleElem.getTextContent();
				String xcoord = xcoordElem.getTextContent();
				String width = widthElem.getTextContent();
				String helphtml = helphtmlElem.getTextContent();
				String knowndata = "";
				if(knowndataElem == null){
				}
				else{
					knowndata = knowndataElem.getTextContent();
				}
				
				Field newfield = new Field(field_number, fieldtitle, Integer.parseInt(xcoord), 
											Integer.parseInt(width), helphtml, knowndata, newproject.getID());
				db.getFieldsDAO().add(newfield);
			}
			
			NodeList imageList = projectElem.getElementsByTagName("image");
			int imageLength = imageList.getLength();
			for(int j = 0; j < imageLength; j++){
				Element batchElem = (Element)imageList.item(j);
				
				Element fileElem = (Element) batchElem.getElementsByTagName("file").item(0);
				
				String filename = fileElem.getTextContent();
				
				Batch newbatch = new Batch(filename, "available", newproject.getID());
				db.getBatchesDAO().add(newbatch);
				
				NodeList valuesList = batchElem.getElementsByTagName("values");
				int valuesLength = valuesList.getLength();
				for(int k = 0; k < valuesLength; k++){
					Element cellsElem = (Element)valuesList.item(k);
					
					NodeList cellsList = cellsElem.getElementsByTagName("value");
					int cellsLength = cellsList.getLength();
					for(int l = 0; l < cellsLength; l++){
						Element cellElem = (Element)cellsList.item(l);
						
						
						String value = cellElem.getTextContent();
						int row = k+1;
						int field_id = field_id_help - fieldLength + l + 1;
						Cell newcell = new Cell(row, value, field_id, newbatch.getBatch_id());
						db.getCellsDAO().add(newcell);
						
					}
										
				}
				
			}
			
		}
		db.endTransaction(true);
		
	}
	
}
