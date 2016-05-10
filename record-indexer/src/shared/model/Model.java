package shared.model;

import java.util.ArrayList;
import java.util.List;

import server.database.Database;
import server.database.DatabaseException;
import shared.communication.DownloadBatch_Params;
import shared.communication.DownloadBatch_Result;
import shared.communication.GetFields_Params;
import shared.communication.GetFields_Result;
import shared.communication.GetProjects_Params;
import shared.communication.GetProjects_Result;
import shared.communication.GetSampleimage_Params;
import shared.communication.GetSampleimage_Result;
import shared.communication.Search_Params;
import shared.communication.Search_Result;
import shared.communication.Search_Result.One_Search_Result;
import shared.communication.SubmitBatch_Params;
import shared.communication.SubmitBatch_Result;
import shared.communication.ValidateUser_Params;
import shared.communication.ValidateUser_Result;

public class Model {
	
	public Model(){
		
	}
	
	public static void initialize() throws ModelException {
		try {
			Database.initialize();
		} catch (DatabaseException e) {
			throw new ModelException(e.getMessage(), e);
		}
	}
	
	// additional function to check validity of the parameters
	
	public boolean UserValidate(String username, String password) throws ModelException{
		ValidateUser_Params userparams = new ValidateUser_Params(username, password);
		if(ValidateUser(userparams).getValid()=="FALSE"){
			return false;
		}
		return true;
	}
	
	public Batch getBatch(){
		
		return null;
	}
	
	public User getUser(Database db, String username) throws DatabaseException{
		List<User> userlist = db.getUsersDAO().getAll();
		for(User temp: userlist){
			if(temp.getName().equals(username)){
				return temp;
			}
		}
		return null;
	}
	public Project getProject(Database db, int project_id) throws DatabaseException{
		List<Project> projectlist = db.getProjectsDAO().getAll();
		for(Project temp: projectlist){
			if(temp.getID() == project_id){
				return temp;
			}
		}
		return null;
	}

	public Batch getBatch(Database db, int batch_id) throws DatabaseException{
		
		List<Batch> batchlist = db.getBatchesDAO().getAll();
		for(Batch temp: batchlist){
			if(temp.getBatch_id() == batch_id){
				return temp;
			}
		}
		
		return null;
	}	
	
	public Field getField(Database db, int field_id) throws DatabaseException{
		List<Field> fieldlist = db.getFieldsDAO().getAll();
		for(Field temp: fieldlist){
			if(temp.getField_id() == field_id){
				return temp;
			}
		}
		return null;		
	}
	
	// operational functions
	
	public ValidateUser_Result ValidateUser(ValidateUser_Params params) throws ModelException {		
		
		
		Database db = new Database();
		ValidateUser_Result result = null;
		try {
			db.startTransaction();
			List<User> userlist = db.getUsersDAO().getAll();
			boolean contain = false;
			String firstname = "";
			String lastname = "";
			int num_records = 0;
			for(User temp: userlist){
				if(temp.getName().equals(params.getUsername()) && temp.getPassword().equals(params.getPassword())){
					contain = true;
					firstname = temp.getFirstname();
					lastname = temp.getLastname();
					num_records = temp.getIndexedrecords();

				}
			}
			if(contain){
				String valid = "TRUE";
				result = new ValidateUser_Result(valid,firstname,lastname,num_records);				
			}
			else{
				String valid = "FALSE";
				result = new ValidateUser_Result(valid,firstname,lastname,num_records);				
			}
			db.endTransaction(true);
			return result;
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			db.endTransaction(false);			
			throw new ModelException(e.getMessage(),e);
		}
	}
	/**
	 * Returns information about all of the available projects
	 * @param paramsparams
	 * @return GetProjects_Result
	 * @throws ModelException 
	 */
	public GetProjects_Result GetProjects(GetProjects_Params params) throws ModelException {
		
		Database db = new Database();
		GetProjects_Result result = new GetProjects_Result();
		if(!UserValidate(params.getUsername(),params.getPassword())){
			return null;
		}
		try {
			db.startTransaction();
			
			List<Project> projectlist = db.getProjectsDAO().getAll();
			result.setAllprojects(projectlist);
			db.endTransaction(true);
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			db.endTransaction(false);
			return null;
		}
		return result;
	}
	/**
	 * Returns a sample image for the specified project
	 * @param params
	 * @return GetSampleimage_Result
	 * @throws ModelException 
	 */
	public GetSampleimage_Result GetSampleimage(GetSampleimage_Params params) throws ModelException {
		
		Database db= new Database();
		GetSampleimage_Result result = new GetSampleimage_Result();
		if(!UserValidate(params.getUsername(),params.getPassword())){
			return null;
		}
		try {
			db.startTransaction();
			List<Batch> batchlist = db.getBatchesDAO().getAll();
			Batch batch = new Batch();
			boolean valid_projectid = false;
			for(Batch temp: batchlist){
				if(temp.getProject_id()==params.getProject_id()){
					batch = temp;
					valid_projectid = true;
					break;
				}
			}
			if(!valid_projectid){
				db.endTransaction(false);
				return null;
			}
			result.setImage_url(batch.getFile());
			db.endTransaction(true);
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			db.endTransaction(false);			
			throw new ModelException(e.getMessage(),e);
		}	
		
		return result;
	}
		
	/**
	 * Downloads a batch for the user to index
	 * @param params
	 * @return DownloadBatch_Result
	 * @throws ModelException 
	 */
	public DownloadBatch_Result DownloadBatch(DownloadBatch_Params params) throws ModelException {

		Database db= new Database();
		DownloadBatch_Result result = new DownloadBatch_Result();
		// checking for invalid user, password
		if(!UserValidate(params.getUsername(),params.getPassword())){
			return null;
		}
		try {
			
			db.startTransaction();
			
			// checking if the user has an assigned batch
			User user = getUser(db, params.getUsername());

			if(user == null){		
	
				db.endTransaction(false);
				return null;				
			}
			
			if(user.getBatch_id() != -1){
				db.endTransaction(false);
				return null;
			}
			
			// checking for invalid project ID
			Project project = getProject(db, params.getProject_id());
			
			if(project == null){
				db.endTransaction(false);
				return null;
			}
			result.setFirst_y_coord(project.getFirstycoord());
			result.setRecord_height(project.getRecordheight());
			result.setNum_records(project.getRecordsperimage());


			List<Batch> batchlist = db.getBatchesDAO().getAll();
			
			boolean contain = false;
			// assign an available batch to the user
			for(Batch temp: batchlist){
				
				if(temp.getProject_id() == params.getProject_id() && temp.getStatus().equals("available")){

					result.setBatch_id(temp.getBatch_id());
					result.setProject_id(temp.getProject_id());
					result.setImage_url(temp.getFile());
					user.setBatch_id(temp.getBatch_id());
					temp.setStatus("checkedout");	

					db.getUsersDAO().update(user);
					db.getBatchesDAO().update(temp);
					contain = true;
					break;

				}
			}
			if(!contain){
				db.endTransaction(false);
				return null;
			}
			// getting all the field values for this project
			List<Field> fieldlist = db.getFieldsDAO().getAll();
			int num_fields = 0;
			for(Field temp: fieldlist){
				if(temp.getProject_id() == params.getProject_id()){
					num_fields++;
					result.addFields(temp);
				}
			}
			result.setNum_fields(num_fields);
			
			db.endTransaction(true);
			return result;
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			db.endTransaction(false);			
			throw new ModelException(e.getMessage(),e);
		}	
		
	}
	
	/**
	 * Submits the indexed record field values for a batch to the Server
	 * @param params
	 * @return SubmitBatch_Result
	 * @throws ModelException 
	 */
	public SubmitBatch_Result SubmitBatch(SubmitBatch_Params params) throws ModelException {
		
		Database db = new Database();
		SubmitBatch_Result result = new SubmitBatch_Result();
		if(!UserValidate(params.getUsername(),params.getPassword())){
			return null;
		}
		
		try {
			db.startTransaction();
			
			// checking if user's batch ID and parameter batch ID are matching
			User user = getUser(db, params.getUsername());
			
			if(user == null){
				db.endTransaction(false);
				return null;				
			}
			if(user.getBatch_id() != params.getBatch_id()){
				db.endTransaction(false);
				return null;
			}
			// getting the batch that user has been working on
			Batch batch = getBatch(db, params.getBatch_id());
			if(batch == null){
				db.endTransaction(false);
				return null;
			}
			
			List<Field> fieldlist = db.getFieldsDAO().getAll();
			List<Integer> field_ids = new ArrayList<Integer>();
			for(Field temp: fieldlist){
				if(batch.getProject_id() == temp.getProject_id()){
					field_ids.add(temp.getField_id());
				}
			}
			
			Project project = getProject(db, batch.getProject_id());
			if(project == null){
				db.endTransaction(false);
				return null;
			}
			
			
			String[] each_row = params.getAllvalues().split(";");
//			String[] checkingsize = each_row[0].split(",");
			// checking for wrong number of values
//			if(each_row.length*checkingsize.length != project.getRecordsperimage()*field_ids.size()){
//				db.endTransaction(false);
//				return null;
//			}
			int count = 0;
			for(int i = 0; i < each_row.length; i++){
				String[] each_cell = each_row[i].split(",");
				
				for(int j = 0; j < each_cell.length; j++){
					if(!each_cell[j].equals(" ")){
						Cell cell = new Cell(i+1, each_cell[j], field_ids.get(j), batch.getBatch_id());
						db.getCellsDAO().add(cell);
						count ++;
					}
				}
			}
			
			user.setBatch_id(-1);
			user.addIndexedrecords(count);
			db.getUsersDAO().update(user);
			batch.setStatus("complete");
			db.getBatchesDAO().update(batch);
			
			result.setValid("TRUE");
			
			db.endTransaction(true);
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			db.endTransaction(false);			
			throw new ModelException(e.getMessage(),e);
		}
		return result;
	}
	/**
	 * Returns information about all of the fields for the specified project
	 * @param params
	 * @return GetFields_Result
	 * @throws ModelException 
	 */
	public GetFields_Result GetFields(GetFields_Params params) throws ModelException {
	
		Database db = new Database();
		GetFields_Result result = new GetFields_Result();
		if(!UserValidate(params.getUsername(),params.getPassword())){
			return null;
		}
		try {
			db.startTransaction();
			
			if(params.getProject_id().equals("")){
				
				List<Field> fieldlist = db.getFieldsDAO().getAll();
				result.setAllfields(fieldlist);
				
			}
			else{
				// checking if the project ID is valid
				Project project = getProject(db, Integer.parseInt(params.getProject_id()));
				if(project == null){
					db.endTransaction(false);
					return null;
				}
				
				List<Field> fieldlist = db.getFieldsDAO().getAll();
				for(Field temp: fieldlist){
					if(temp.getProject_id() == project.getID()){
						result.addAllfields(temp);
					}
				}
			}
			
			db.endTransaction(true);
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			db.endTransaction(false);			
			throw new ModelException(e.getMessage(),e);
		}
		return result;
		
	}
	/**
	 * Searches the indexed records for the specified strings
	 * @param params
	 * @return Search_Result
	 * @throws ModelException 
	 */
	public Search_Result Search(Search_Params params) throws ModelException {
		
		Database db = new Database();
		Search_Result result = new Search_Result();
		if(!UserValidate(params.getUsername(),params.getPassword())){
			return null;
		}
		try {
			db.startTransaction();
			
			String[] field_ids = params.getFields().split(",");
			String[] search_values = params.getSearch_values().split(",");
			if(search_values[0].equals("")){
				db.endTransaction(false);
				return null;
			}
			
			// checking if the field IDs are valid
			for(int i = 0; i < field_ids.length; i++){
				Field field = getField(db,Integer.parseInt(field_ids[i]));
				if(field == null){
					db.endTransaction(false);
					return null;
				}
			}
			
			List<Cell> celllist = db.getCellsDAO().getAll();
			for(int i = 0; i < field_ids.length; i++){
				for(int j = 0; j < search_values.length; j++){
					for(Cell temp: celllist){
						if(search_values[j].equals(temp.getValue()) 
								&& Integer.parseInt(field_ids[i]) == temp.getField_id()){
							
							Batch batch = getBatch(db, temp.getBatch_id());
							
							One_Search_Result oneresult = result.new One_Search_Result
										(batch.getBatch_id(),batch.getFile(),temp.getRow(),temp.getField_id());
							result.addAllAllresult(oneresult);
							
						}
					}
				}
				
			}
			
			db.endTransaction(true);
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			db.endTransaction(false);			
			throw new ModelException(e.getMessage(),e);
		}
		return result;
	}
}
