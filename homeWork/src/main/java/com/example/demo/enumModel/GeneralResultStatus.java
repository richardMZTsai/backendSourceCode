package com.example.demo.enumModel;

public enum GeneralResultStatus {
	 
	 OK(0,"Success"),
	 SystemProgramError(-1, "System Program Error"),
	 ObjectNotFound(-2, "Object Not Found"),
	 DataShortage(-3, "Input Data not enough"),
	 DataDuplicate(-4, "Data Duplicate"),

	 Unknown(-100,"Unknown"); 


	  private int status;
	  private String description;

	  private GeneralResultStatus(int status, String description) {
	    this.status = status ;
	    this.description = description ;
	  }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	  
	  
}
