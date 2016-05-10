package shared.communication;

public class SubmitBatch_Result {

	private String valid;

	/**
	 * create an object with the result received from the model class to indicate the operation succeeded or not
	 * @param valid
	 */
	public SubmitBatch_Result(String valid) {

		this.valid = valid;
	}

	public SubmitBatch_Result() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @return valid - this tells that SubmitBatch operation was valid or not
	 */
	public String getValid() {
		return valid;
	}

	/**
	 * 
	 * @param valid
	 */
	public void setValid(String valid) {
		this.valid = valid;
	}

	@Override
	public String toString() {
		return valid;
	}
	
}
