package com.crystal.shummers;

public class Expense {
	
	///////////////////////////////////////////////////////////
	// PRIVATE VAR
	///////////////////////////////////////////////////////////
	
	private int id;					// unique id
	private String created_on;		// date stamp when created
	private int amount;				// expense amount
	private String exp_desc;		// expense description
	
	///////////////////////////////////////////////////////////
	// CONSTRUCTOR
	///////////////////////////////////////////////////////////
	
	public Expense(){
		
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
	
	public void setAmount(int amount){
		this.amount = amount;
	}
	
	public void setExpenseDesc(String exp_desc){
		this.exp_desc = exp_desc;
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
	
	public int getAmount(){
		return this.amount;
	}
	
	public String getExpenseDesc(){
		return this.exp_desc;
	}
}
