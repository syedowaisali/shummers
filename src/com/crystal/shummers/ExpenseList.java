package com.crystal.shummers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.crystal.shummers.adapter.ExpenseListAdapter;

public class ExpenseList extends BaseActivity implements OnDateSetListener {
	
	private static final int START_DATE = 998;
	private static final int END_DATE = 999;
	
	private Calendar calStart, calEnd;
	private TextView tvStartDate;
	private TextView tvEndDate;
	
	private int s_year, s_month, s_day, e_year, e_month, e_day;
	private int current_picker = START_DATE;
	
	ListView lv;
	public static ExpenseListAdapter adapter;
	public static List<Expense> expItems;
	ProgressDialog pDialog;
	List<Expense> items;
	
	private boolean is_data;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expense_list);
		
		tvStartDate = (TextView) findViewById(R.id.se_start_date);
		tvEndDate = (TextView) findViewById(R.id.se_end_date);
		
		calStart = Calendar.getInstance();
		calEnd = Calendar.getInstance();
		
		s_year = calStart.get(Calendar.YEAR);
		s_month = calStart.get(Calendar.MONTH);
		s_day = calStart.get(Calendar.DAY_OF_MONTH);
		
		e_year = calEnd.get(Calendar.YEAR);
		e_month = calEnd.get(Calendar.MONTH);
		e_day = calEnd.get(Calendar.DAY_OF_MONTH);
		
		showDate(tvStartDate, s_year, s_month, s_day);
		showDate(tvEndDate, e_year, e_month, e_day);
		
		++s_month;
		++e_month;
		
		expItems = new ArrayList<Expense>();
		
		lv = (ListView) findViewById(R.id.all_exp_list);
		build();
	}
	
	// open date picker
	@SuppressWarnings("deprecation")
	public void selStartDate(View v){
		current_picker = START_DATE;
		showDialog(START_DATE);
	}
	
	// open date picker
	@SuppressWarnings("deprecation")
	public void selEndDate(View v){
		current_picker = END_DATE;
		showDialog(END_DATE);
	}
	
	// show start date
	private void showDate(TextView tv, int year, int month, int day){
		tv.setText(new StringBuilder().append(convertMonth(month)).append(" ").append(day).append(", ").append(year));
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		if (id == START_DATE) {
			return new DatePickerDialog(this, this, s_year, (s_month - 1), s_day);
		}
		
		// else current date picker is end date
		else if(id == END_DATE){
			return new DatePickerDialog(this, this, e_year, (e_month - 1), e_day);
		}
	
		return null;
	}
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		// TODO Auto-generated method stub
		if(current_picker == START_DATE){
			this.s_year = year;
			this.s_month = monthOfYear + 1;
			this.s_day = dayOfMonth;
			
			showDate(tvStartDate, year, monthOfYear, dayOfMonth);
		}
		else{
			this.e_year = year;
			this.e_month = monthOfYear + 1;
			this.e_day = dayOfMonth;
			
			showDate(tvEndDate, year, monthOfYear, dayOfMonth);
		}
		
	}
	
	public void searchExpense(View v){
		String s_date = s_year + "-" + ((s_month < 10) ? "0" + s_month : s_month) + "-" + ((s_day < 10) ? "0" + s_day : s_day);
		String e_date = e_year + "-" + ((e_month < 10) ? "0" + e_month : e_month) + "-" + ((e_day < 10) ? "0" + e_day : e_day);
		
		try{
			
			yesItem();
			
			if(expItems.size() > 0){
				// clear list view
				expItems.clear();
				adapter.notifyDataSetChanged();
			}
			
			for(Expense item : expenseData.getSearchExpense(s_date, e_date)){
				expItems.add(item);
			}
			
			if(expItems.size() > 0){
				if(lv.getAdapter() == null){
					adapter = new ExpenseListAdapter(this, expItems);
					lv.setAdapter(adapter);
				}
				else{
					adapter.notifyDataSetChanged();
				}
			}
			else{
				noItem();
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition (R.anim.open_main, R.anim.close_next);
	}
	
	private void build(){
		
		// load dictionary
		new loadAllExpense().execute();
	}
	
	public void fetchItems(){
      
        // looping through all item nodes <item>
        for (Expense item : items) {
            // adding HashList to ArrayList
        	expItems.add(item);
        }
        
    	adapter = new ExpenseListAdapter(this, expItems);
        lv.setAdapter(adapter);
        
	}
	 
	public void noItem(){
		
		// log message
		log("Expense List", "no expense found.");
		
		lv.setVisibility(View.GONE);
		TextView emptyView = (TextView) findViewById(R.id.empty_exp);
		emptyView.setVisibility(View.VISIBLE);
	}

	public void yesItem(){
		
		// log message
		log("Expense List", "yes expense found.");

		TextView emptyView = (TextView) findViewById(R.id.empty_exp);
		emptyView.setVisibility(View.GONE);
		lv.setVisibility(View.VISIBLE);
	}
	
	public static void notifyAdapter(){
		adapter.notifyDataSetChanged();
	}
	
	public static void notifyInvalidate(){
		adapter.notifyDataSetInvalidated();
		adapter = null;
		expItems = null;
	}
	
	/**
     * Async Task that send a request to url
     * Gets new list view data
     * Appends to list view
     * */
    private class loadAllExpense extends AsyncTask<Void, Void, Void> {
 
        @Override
        protected void onPreExecute() {
            // Showing progress dialog before sending http request
            pDialog = new ProgressDialog(ExpenseList.this);
            pDialog.setMessage("Please wait..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        protected Void doInBackground(Void... arg0) {
        	
        	
    		String s_date = s_year + "-" + ((s_month < 10) ? "0" + s_month : s_month) + "-" + ((s_day < 10) ? "0" + s_day : s_day);
    		String e_date = e_year + "-" + ((e_month < 10) ? "0" + e_month : e_month) + "-" + ((e_day < 10) ? "0" + e_day : e_day);
    		items = expenseData.getSearchExpense(s_date, e_date);
        	if(items.size() > 0){
        		is_data = true;
        	}
 
            return null;
            
        }
 
        protected void onPostExecute(Void result) {
            // closing progress dialog

            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            
            if(is_data){
            	fetchItems();
            }
            else{
            	noItem();
            }
        }
    }
}
