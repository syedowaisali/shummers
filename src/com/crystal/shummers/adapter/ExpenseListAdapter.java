package com.crystal.shummers.adapter;

import java.util.List;

import com.crystal.androidtoolkit.AppHelper;
import com.crystal.shummers.Expense;
import com.crystal.shummers.ExpenseData;
import com.crystal.shummers.ExpenseList;
import com.crystal.shummers.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ExpenseListAdapter extends BaseAdapter{
	
	public static final int DELETE = 0;
	private static LayoutInflater inflater = null;
	private List<Expense> data;
	private Activity activity;
	
	
	public ExpenseListAdapter(Activity a, List<Expense> d){
		activity = a;
		data = d;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;
		final ViewHolder holder;
		
		final Expense item = data.get(position);
		//item.setPosition(position);
		
		if(convertView == null){
			vi = inflater.inflate(R.layout.expense_item, null);
			holder = new ViewHolder();
			
			holder.date_label = (TextView) vi.findViewById(R.id.eli_date);
			holder.amount_label = (TextView) vi.findViewById(R.id.eli_amount);
			holder.desc_label = (TextView) vi.findViewById(R.id.eli_desc);
			
			vi.setTag(holder);
		}
		else{
			holder = (ViewHolder) vi.getTag();
		}
		
		// setting all values in listview

		holder.date_label.setText(AppHelper.formatDate(item.getCreatedOn(), "MMM dd, yyyy"));
		holder.amount_label.setText(String.valueOf(item.getAmount()));
		holder.desc_label.setText(item.getExpenseDesc());
				
		/**
         * Listening to listview single row selected
         * **/
		/*
		vi.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		*/
		
		/**
         * Listening to listview single row selected
         * **/
		vi.setOnLongClickListener(new OnLongClickListener(){
			
			@Override
			public boolean onLongClick(View view){
				
				AlertDialog.Builder adb = new AlertDialog.Builder(activity);
				CharSequence opts[] = new CharSequence[]{"Delete"};
				adb.setItems(opts, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						switch(which){
						
						case DELETE:
							
							new AlertDialog.Builder(activity)
							.setTitle("Confirmation")
							.setMessage("Are you sure you want to delete this expense.")
							.setPositiveButton("Yes", new OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									ExpenseData ed = new ExpenseData(activity);
									ed.removeExpense(item.getId());
									
									ExpenseList.expItems.remove(position);
									ExpenseList.notifyAdapter();
									
								}
							})
							.setNegativeButton("No", new OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									
								}
							})
							.show();
							
							break;
						}
					}

				});
				//adb.setNegativeButton("Cancel", null);
				adb.setTitle("Select Options");
				AlertDialog alert = adb.create();
				alert.show();
				
				return true;
			}
		});
		
		return vi;
	}
	
	static class ViewHolder{
		TextView date_label;
		TextView amount_label;
		TextView desc_label;
	}
}
