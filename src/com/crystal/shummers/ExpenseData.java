package com.crystal.shummers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ExpenseData extends ShummersData {

	public ExpenseData(Context context) {
		super(context);
	}
	
	// add new expense to expense data
	public void addNewExpense(Expense expense){
		
		ContentValues values = new ContentValues();
		values.put(EXP_CREATED_ON, expense.getCreatedOn());
		values.put(EXP_AMOUNT, expense.getAmount());
		values.put(EXP_DESC, expense.getExpenseDesc());
		
		// get writable database from super class
		SQLiteDatabase db = this.getWritableDatabase();
				
		// insert new row in expense table
		db.insert(EXPENSE_TABLE, null, values);
		
		// close db connection
		db.close();
	}
	
	// update selected expense
	public void updateExpense(Expense expense){
		
		ContentValues values = new ContentValues();
		values.put(EXP_CREATED_ON, expense.getCreatedOn());
		values.put(EXP_AMOUNT, expense.getAmount());
		values.put(EXP_DESC, expense.getExpenseDesc());
		
		// set where clause
		String[] whereArgs = new String[]{String.valueOf(expense.getId())};
		
		// get writable database from super class
		SQLiteDatabase db = this.getWritableDatabase();
		
		// update selected row in expense table
		db.update(EXPENSE_TABLE, values, EXP_ID + " = ?", whereArgs);
		
		// close db connection
		db.close();
	}
	
	// get selected expense
	public Expense getExpense(int exp_id){
		
		String[] columns = new String[]{EXP_ID, EXP_CREATED_ON, EXP_AMOUNT, EXP_DESC};
		String[] whereArgs = new String[]{String.valueOf(exp_id)};
		
		// get readable database from super class
		SQLiteDatabase db = this.getReadableDatabase();
				
		Cursor cursor = db.query(EXPENSE_TABLE, columns, EXP_ID + " = ?", whereArgs, null, null, null, null);
		
		if(cursor != null){
			cursor.moveToFirst();
		}
		
		// create expense object
		Expense expense = new Expense();
		
		expense.setId(cursor.getInt(cursor.getColumnIndex(EXP_ID)));
		expense.setCreatedOn(cursor.getString(cursor.getColumnIndex(EXP_CREATED_ON)));
		expense.setAmount(cursor.getInt(cursor.getColumnIndex(EXP_AMOUNT)));
		expense.setExpenseDesc(cursor.getString(cursor.getColumnIndex(EXP_DESC)));
		
		// close cursor
		cursor.close();
		
		// return work
		return expense;
	}
	
	// get all expense data
	public List<Expense> getAllExpense(){
		
		// select all expense query
		String sql = "SELECT * FROM " + EXPENSE_TABLE;
		
		// get readable database from super class
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		
		// create expense list object
		List<Expense> exp_list = new ArrayList<Expense>();
		
		// looping through all expense rows and add to work list
		if(cursor.moveToFirst()){
			do{
				// create expense object
				Expense expense = new Expense();
				
				expense.setId(cursor.getInt(cursor.getColumnIndex(EXP_ID)));
				expense.setCreatedOn(cursor.getString(cursor.getColumnIndex(EXP_CREATED_ON)));
				expense.setAmount(cursor.getInt(cursor.getColumnIndex(EXP_AMOUNT)));
				expense.setExpenseDesc(cursor.getString(cursor.getColumnIndex(EXP_DESC)));
				
				// expense to expense list
				exp_list.add(expense);
			}
			while(cursor.moveToNext());
		}
		
		// close cursor
		cursor.close();
		
		return exp_list;
	}
	
	public List<Expense> getSearchExpense(String start_date, String end_date){
		// select all expense query
		String sql = "SELECT * FROM " + EXPENSE_TABLE
				+ " WHERE (" + EXP_CREATED_ON + " >= date('" + start_date + "') AND " 
				+ EXP_CREATED_ON + " < date('" + end_date + "', '+1 day')) "
				+ "ORDER BY " + EXP_CREATED_ON + " ASC ";
		
		// get readable database from super class
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		
		// create expense list object
		List<Expense> exp_list = new ArrayList<Expense>();
		
		// looping through all expenses rows and add to expense list
		if(cursor.moveToFirst()){
			do{
				// create expense object
				Expense expense = new Expense();
				
				expense.setId(cursor.getInt(cursor.getColumnIndex(EXP_ID)));
				expense.setCreatedOn(cursor.getString(cursor.getColumnIndex(EXP_CREATED_ON)));
				expense.setAmount(cursor.getInt(cursor.getColumnIndex(EXP_AMOUNT)));
				expense.setExpenseDesc(cursor.getString(cursor.getColumnIndex(EXP_DESC)));
				
				// work to expense list
				exp_list.add(expense);
			}
			while(cursor.moveToNext());
		}
		
		// close cursor
		cursor.close();
		
		return exp_list;
	}
	
	// get expenses count
	public int getExpensesCount(){
		
		// count all query
		String sql = "SELECT * FROM " + EXPENSE_TABLE;
		
		// get readable database from super class
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		
		// count total
		int total_expenses = cursor.getCount();
		
		// close cursor
		cursor.close();
		
		// return total
		return total_expenses;
	}
	
	// delete work
	public void removeExpense(int exp_id){
		
		// set where clause
		String[] whereArgs = new String[]{String.valueOf(exp_id)};
		
		// get writable database from super class
		SQLiteDatabase db = this.getWritableDatabase();
		
		// delete selected row in expense table
		db.delete(EXPENSE_TABLE, EXP_ID + " = ?", whereArgs);
		
		// close db connection
		db.close();
	}
	
	// delete all expense data
	public void removeAllExpense(){
		
		// delete all expense query
		String sql = "DELETE FROM " + EXPENSE_TABLE;
		
		// get writable database from super class
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		cursor.close();
	}
}
