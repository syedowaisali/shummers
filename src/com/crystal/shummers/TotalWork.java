package com.crystal.shummers;

public class TotalWork {
	
	///////////////////////////////////////////////////////////
	// PRIVATE VAR
	///////////////////////////////////////////////////////////
	
	private static final TotalWork INSTANCE = new TotalWork();
	
	private String date;			// date stamp when created
	private int total;				// work total
	private int expense;			// expense
	private int profit;				// profit
	private int all_total = 0;		// save all total
	private int all_expense = 0;	// save all expense
	private int all_profit = 0;		// save all profit
	
	///////////////////////////////////////////////////////////
	// CONSTRUCTOR
	///////////////////////////////////////////////////////////
	
	public TotalWork(){
		
	}
	
	///////////////////////////////////////////////////////////
	// SETTER'S
	///////////////////////////////////////////////////////////
	
	public void setDate(String date){
		this.date = date;
	}
	
	public void setTotal(int total){
		this.total = total;
	}
	
	public void setExpense(int expense){
		this.expense = expense;
	}
	
	public void setProfit(int profit){
		this.profit = profit;
	}
	
	public void setAllTotal(int all_total){
		getInstance().all_total += all_total;
	}
	
	public void setAllExpense(int all_expense){
		getInstance().all_expense += all_expense;
	}
	
	public void reset(){
		getInstance().all_total = 0;
		getInstance().all_expense = 0;
		getInstance().all_profit = 0;
	}
	
	///////////////////////////////////////////////////////////
	// GETTER's
	///////////////////////////////////////////////////////////
	
	
	public String getCreatedOn(){
		return this.date;
	}
	
	public int getTotal(){
		return this.total;
	}
	
	public int getExpense(){
		return this.expense;
	}
	
	public int getProfit(){
		return this.profit;
	}
	
	public int getAllTotal(){
		return getInstance().all_total;
	}
	
	public int getAllExpense(){
		return getInstance().all_expense;
	}
	
	public int getAllProfit(){
		return getInstance().all_total - getInstance().all_expense;
	}
	
	///////////////////////////////////////////////////////////
	// GET INSTANCE
	///////////////////////////////////////////////////////////
	
	public static TotalWork getInstance(){
		return INSTANCE;
	}
}
