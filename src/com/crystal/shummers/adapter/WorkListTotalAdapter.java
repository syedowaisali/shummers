package com.crystal.shummers.adapter;

import java.util.List;

import com.crystal.shummers.AppHelper;
import com.crystal.shummers.R;
import com.crystal.shummers.TotalWork;
import com.crystal.shummers.WorkData;
import com.crystal.shummers.WorkList;
import com.crystal.shummers.WorkListTotal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WorkListTotalAdapter extends BaseAdapter{
	
	public static final int DELETE = 0;
	private static LayoutInflater inflater = null;
	private List<TotalWork> data;
	private Activity activity;
	
	
	public WorkListTotalAdapter(Activity a, List<TotalWork> d){
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
		
		final TotalWork item = data.get(position);
		//item.setPosition(position);
		
		if(convertView == null){
			vi = inflater.inflate(R.layout.work_total_item, null);
			holder = new ViewHolder();
			
			holder.wrapper = (LinearLayout) vi.findViewById(R.id.wti_wrapper);
			holder.date_label = (TextView) vi.findViewById(R.id.wti_date);
			holder.total = (TextView) vi.findViewById(R.id.wti_total);
			holder.expense = (TextView) vi.findViewById(R.id.wti_exp);
			holder.profit = (TextView) vi.findViewById(R.id.wti_profit);
			
			vi.setTag(holder);
		}
		else{
			holder = (ViewHolder) vi.getTag();
		}
		
		// setting all values in listview
		
		String color_code = ((position % 2) == 0) ? "#110000" : "#000011";
		
		holder.wrapper.setBackgroundColor(Color.parseColor(color_code));
		holder.date_label.setText(AppHelper.formatDate(item.getCreatedOn(), "MMM dd, yyyy"));
		holder.total.setText(String.valueOf(item.getTotal()));
		holder.expense.setText(String.valueOf(item.getExpense()));
		holder.profit.setText(String.valueOf(item.getProfit()));
		
		return vi;
	}
	
	static class ViewHolder{
		LinearLayout wrapper;
		TextView date_label;
		TextView total;
		TextView expense;
		TextView profit;
	}
}
