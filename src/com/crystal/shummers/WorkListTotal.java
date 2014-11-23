package com.crystal.shummers;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.crystal.androidtoolkit.AppHelper;
import com.crystal.shummers.adapter.WorkListTotalAdapter;

public class WorkListTotal extends BaseActivity {
	
	public static int TOTAL = 0;
	public static int EXPENSE = 0;
	public static int PROFIT = 0;
	
	ListView lv;
	public static WorkListTotalAdapter adapter;
	public static List<TotalWork> workItems;
	ProgressDialog pDialog;
	List<TotalWork> items;
	
	private boolean is_data;
	String search = "";
	String start_date = "";
	String end_date = "";
	
	TextView tvDate;
	TextView tvTotal;
	TextView tvExpense;
	TextView tvProfit;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.worl_list_total);
		
		Bundle bundle = getIntent().getExtras();
		search = bundle.getString("search");
		start_date = bundle.getString("start_date");
		end_date = bundle.getString("end_date");
		
		workItems = new ArrayList<TotalWork>();
		
		tvDate = (TextView) findViewById(R.id.twl_date);
		tvTotal = (TextView) findViewById(R.id.twl_total);
		tvExpense = (TextView) findViewById(R.id.twl_exp);
		tvProfit = (TextView) findViewById(R.id.twl_profit);
		
		TotalWork.getInstance().reset();
		
		lv = (ListView) findViewById(R.id.total_work_lv);
		build();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition (R.anim.open_main, R.anim.close_next);
	}

	private void build(){
		
		// load dictionary
		new loadWorkTotal().execute();
	}
	
	public void fetchItems(){
		
        // looping through all item nodes <item>
        for (TotalWork item : items) {
            // adding HashList to ArrayList
        	// set total and expense
    		TotalWork.getInstance().setAllTotal(item.getTotal());
    		TotalWork.getInstance().setAllExpense(item.getExpense());
        	workItems.add(item);
        }
        
    	adapter = new WorkListTotalAdapter(this, workItems);
        lv.setAdapter(adapter);
        
        setAllTotal();
	}
	
	@SuppressLint("NewApi")
	private void setAllTotal(){
		
		Formatter t_formatter = new Formatter();
		Formatter e_formatter = new Formatter();
		Formatter p_formatter = new Formatter();
		
		String t = "Rs. " + String.valueOf(t_formatter.format("%,d", TotalWork.getInstance().getAllTotal()));
		String e = "Rs. " + String.valueOf(e_formatter.format("%,d", TotalWork.getInstance().getAllExpense()));
		String p = "Rs. " + String.valueOf(p_formatter.format("%,d", TotalWork.getInstance().getAllProfit()));
		
		tvDate.setText(AppHelper.formatDate(start_date, "MMM dd, yyyy") + "\n" + AppHelper.formatDate(end_date, "MMM dd, yyyy"));
		tvTotal.setText(t);
		tvExpense.setText(e);
		tvProfit.setText(p);
	}
	 
	public void noItem(){
		
		// log message
		log("Work List Total", "no work found");
		//lv.setVisibility(View.GONE);
		//TextView emptyView = (TextView) findViewById(R.id.empty_works);
		//emptyView.setVisibility(View.VISIBLE);
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
    private class loadWorkTotal extends AsyncTask<Void, Void, Void> {
 
        @Override
        protected void onPreExecute() {
            // Showing progress dialog before sending http request
            pDialog = new ProgressDialog(WorkListTotal.this);
            pDialog.setMessage("Please wait..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        protected Void doInBackground(Void... arg0) {
        	
        	
        	if(workData.getWorksCount() > 0){
        		is_data = true;
        		items = workData.getTotalWork(search, start_date, end_date);
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
