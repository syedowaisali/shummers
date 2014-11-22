package com.crystal.shummers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class WorkData extends ShummersData {

	public WorkData(Context context) {
		super(context);
	}
	
	// add new work to work data
	public void addNewWork(Work work){
		
		ContentValues values = new ContentValues();
		values.put(WT_CREATED_ON, work.getCreatedOn());
		values.put(WT_CLIENT_NAME, work.getClient());
		values.put(WT_AMOUNT, work.getAmount());
		values.put(WT_WORK_DESC, work.getWorkDesc());
		
		// get writable database from super class
		SQLiteDatabase db = this.getWritableDatabase();
				
		// insert new row in work table
		db.insert(WORK_TABLE, null, values);
		
		// close db connection
		db.close();
	}
	
	// update selected work
	public void updateWork(Work work){
		
		ContentValues values = new ContentValues();
		values.put(WT_CREATED_ON, work.getCreatedOn());
		values.put(WT_CLIENT_NAME, work.getClient());
		values.put(WT_AMOUNT, work.getAmount());
		values.put(WT_WORK_DESC, work.getWorkDesc());
		
		// set where clause
		String[] whereArgs = new String[]{String.valueOf(work.getId())};
		
		// get writable database from super class
		SQLiteDatabase db = this.getWritableDatabase();
		
		// update selected row in work table
		db.update(WORK_TABLE, values, WT_ID + " = ?", whereArgs);
		
		// close db connection
		db.close();
	}
	
	// get selected work
	public Work getWork(int work_id){
		
		String[] columns = new String[]{WT_ID, WT_CREATED_ON, WT_CLIENT_NAME, WT_AMOUNT, WT_WORK_DESC};
		String[] whereArgs = new String[]{String.valueOf(work_id)};
		
		// get readable database from super class
		SQLiteDatabase db = this.getReadableDatabase();
				
		Cursor cursor = db.query(WORK_TABLE, columns, WT_ID + " = ?", whereArgs, null, null, null, null);
		
		if(cursor != null){
			cursor.moveToFirst();
		}
		
		// create work object
		Work work = new Work();
		
		work.setId(cursor.getInt(cursor.getColumnIndex(WT_ID)));
		work.setCreatedOn(cursor.getString(cursor.getColumnIndex(WT_CREATED_ON)));
		work.setClient(cursor.getString(cursor.getColumnIndex(WT_CLIENT_NAME)));
		work.setAmount(cursor.getInt(cursor.getColumnIndex(WT_AMOUNT)));
		work.setWorkDesc(cursor.getString(cursor.getColumnIndex(WT_WORK_DESC)));
		
		// close cursor
		cursor.close();
		
		// return work
		return work;
	}
	
	// get all works data
	public List<Work> getAllWorks(){
		
		// select all work query
		String sql = "SELECT * FROM " + WORK_TABLE;
		
		// get readable database from super class
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		
		// create work list object
		List<Work> work_list = new ArrayList<Work>();
		
		// looping through all works rows and add to work list
		if(cursor.moveToFirst()){
			do{
				// create work object
				Work work = new Work();
				
				work.setId(cursor.getInt(cursor.getColumnIndex(WT_ID)));
				work.setCreatedOn(cursor.getString(cursor.getColumnIndex(WT_CREATED_ON)));
				work.setClient(cursor.getString(cursor.getColumnIndex(WT_CLIENT_NAME)));
				work.setAmount(cursor.getInt(cursor.getColumnIndex(WT_AMOUNT)));
				work.setWorkDesc(cursor.getString(cursor.getColumnIndex(WT_WORK_DESC)));
				
				// work to work list
				work_list.add(work);
			}
			while(cursor.moveToNext());
		}
		
		// close cursor
		cursor.close();
		
		return work_list;
	}
	
	public List<Work> getSearchWork(String key, String start_date, String end_date){
		// select all work query
				String sql = "SELECT * FROM " + WORK_TABLE
						+ " WHERE (" + WT_CREATED_ON + " >= date('" + start_date + "') AND " 
						+ WT_CREATED_ON + " < date('" + end_date + "', '+1 day')) AND "
						+ "(" + WT_CLIENT_NAME + " LIKE('%" + key + "%'))";
				Log.d("SQL", sql);
				// get readable database from super class
				SQLiteDatabase db = this.getReadableDatabase();
				Cursor cursor = db.rawQuery(sql, null);
				
				// create work list object
				List<Work> work_list = new ArrayList<Work>();
				
				// looping through all works rows and add to work list
				if(cursor.moveToFirst()){
					do{
						// create work object
						Work work = new Work();
						
						work.setId(cursor.getInt(cursor.getColumnIndex(WT_ID)));
						work.setCreatedOn(cursor.getString(cursor.getColumnIndex(WT_CREATED_ON)));
						work.setClient(cursor.getString(cursor.getColumnIndex(WT_CLIENT_NAME)));
						work.setAmount(cursor.getInt(cursor.getColumnIndex(WT_AMOUNT)));
						work.setWorkDesc(cursor.getString(cursor.getColumnIndex(WT_WORK_DESC)));
						
						// work to work list
						work_list.add(work);
					}
					while(cursor.moveToNext());
				}
				
				// close cursor
				cursor.close();
				
				return work_list;
	}
	
	// get works count
	public int getWorksCount(){
		
		// count all query
		String sql = "SELECT * FROM " + WORK_TABLE;
		
		// get readable database from super class
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		
		// count total
		int total_works = cursor.getCount();
		
		// close cursor
		cursor.close();
		
		// return total
		return total_works;
	}
	
	// delete work
	public void removeWork(int work_id){
		
		// set where clause
		String[] whereArgs = new String[]{String.valueOf(work_id)};
		
		// get writable database from super class
		SQLiteDatabase db = this.getWritableDatabase();
		
		// delete selected row in work table
		db.delete(WORK_TABLE, WT_ID + " = ?", whereArgs);
		
		// close db connection
		db.close();
	}
	
	// delete all work data
	public void removeAllWork(){
		
		// delete all work query
		String sql = "DELETE FROM " + WORK_TABLE;
		
		// get writable database from super class
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		cursor.close();
	}
}
