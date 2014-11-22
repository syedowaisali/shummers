package com.crystal.shummers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.crystal.shummers.adapter.WorkListViewAdapter;

public class WorkList extends BaseActivity implements OnDateSetListener {
	
	private static final int START_DATE = 998;
	private static final int END_DATE = 999;
	
	private Calendar calStart, calEnd;
	private TextView tvStartDate;
	private TextView tvEndDate;
	private EditText searchText;
	
	private int s_year, s_month, s_day, e_year, e_month, e_day;
	private int current_picker = START_DATE;
	
	ListView lv;
	public static WorkListViewAdapter adapter;
	public static List<Work> workItems;
	ProgressDialog pDialog;
	List<Work> items;
	
	private boolean is_data;
	
	// dictionary word meaning
	Work work_item;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.work_list);
		
		tvStartDate = (TextView) findViewById(R.id.sw_start_date);
		tvEndDate = (TextView) findViewById(R.id.sw_end_date);
		searchText = (EditText) findViewById(R.id.search_key);
		
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
		
		work_item = new Work();
		workItems = new ArrayList<Work>();
		
		lv = (ListView) findViewById(R.id.all_work_list);
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
	
	public void searchWork(View v){
		String search = searchText.getText().toString();
		String s_date = s_year + "-" + ((s_month < 10) ? "0" + s_month : s_month) + "-" + ((s_day < 10) ? "0" + s_day : s_day);
		String e_date = e_year + "-" + ((e_month < 10) ? "0" + e_month : e_month) + "-" + ((e_day < 10) ? "0" + e_day : e_day);
		
		try{

			// clear list view
			workItems.clear();
			adapter.notifyDataSetChanged();
			
			for(Work item : workData.getSearchWork(search, s_date, e_date)){
				workItems.add(item);
			}
			
			adapter.notifyDataSetChanged();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void workTotal(View v){
		String search = searchText.getText().toString();
		String s_date = s_year + "-" + ((s_month < 10) ? "0" + s_month : s_month) + "-" + ((s_day < 10) ? "0" + s_day : s_day);
		String e_date = e_year + "-" + ((e_month < 10) ? "0" + e_month : e_month) + "-" + ((e_day < 10) ? "0" + e_day : e_day);
		
		Bundle bundle = new Bundle();
		bundle.putString("search", search);
		bundle.putString("start_date", s_date);
		bundle.putString("end_date", e_date);
		
		Intent intent = new Intent(this, WorkListTotal.class);
		intent.putExtras(bundle);
		gotoActivity(intent);
	}
	
	private void build(){
		
		// load dictionary
		new loadAllWorks().execute();
	}
	
	public void fetchItems(){
      
        // looping through all item nodes <item>
        for (Work item : items) {
            // adding HashList to ArrayList
        	workItems.add(item);
        }
        
    	adapter = new WorkListViewAdapter(this, workItems);
        lv.setAdapter(adapter);
	}
	 
	public void noItem(){
		
		// log message
		log("Work List", "no work found works");
		lv.setVisibility(View.GONE);
		TextView emptyView = (TextView) findViewById(R.id.empty_works);
		emptyView.setVisibility(View.VISIBLE);
	}
	
	public static void notifyAdapter(){
		adapter.notifyDataSetChanged();
	}
	
	public static void notifyInvalidate(){
		adapter.notifyDataSetInvalidated();
		adapter = null;
		workItems = null;
	}
	
	/**
     * Async Task that send a request to url
     * Gets new list view data
     * Appends to list view
     * */
    private class loadAllWorks extends AsyncTask<Void, Void, Void> {
 
        @Override
        protected void onPreExecute() {
            // Showing progress dialog before sending http request
            pDialog = new ProgressDialog(WorkList.this);
            pDialog.setMessage("Please wait..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        protected Void doInBackground(Void... arg0) {
        	
        	
        	if(workData.getWorksCount() > 0){
        		is_data = true;
        		items = workData.getAllWorks();
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
