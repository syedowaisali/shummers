package com.crystal.shummers;

public class Work {
	
	///////////////////////////////////////////////////////////
	// PRIVATE VAR
	///////////////////////////////////////////////////////////
	
	private int id;					// unique id
	private String created_on;		// date stamp when created
	private String client_name;		// client name
	private int amount;				// work amount
	private String work_desc;		// work description
	
	///////////////////////////////////////////////////////////
	// CONSTRUCTOR
	///////////////////////////////////////////////////////////
	
	public Work(){
		
	}
	
	///////////////////////////////////////////////////////////
	// SETTER'S
	///////////////////////////////////////////////////////////
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setCreatedOn(String created_on){
		this.created_on = created_on;
	}
	
	public void setClient(String client_name){
		this.client_name = client_name;
	}
	
	public void setAmount(int amount){
		this.amount = amount;
	}
	
	public void setWorkDesc(String work_desc){
		this.work_desc = work_desc;
	}
	
	///////////////////////////////////////////////////////////
	// GETTER's
	///////////////////////////////////////////////////////////
	
	public int getId(){
		return this.id;
	}
	
	public String getCreatedOn(){
		return this.created_on;
	}
	
	public String getClient(){
		return this.client_name;
	}
	
	public int getAmount(){
		return this.amount;
	}
	
	public String getWorkDesc(){
		return this.work_desc;
	}
}
