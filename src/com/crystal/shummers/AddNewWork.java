package com.crystal.shummers;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class AddNewWork extends BaseActivity implements OnDateSetListener {

	private static int DATE_DIALOG_ID = 999;
	private Calendar cal;
	private TextView tvDisplayDate = null;
	
	private int year, month, day;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_new_work);
		cal = Calendar.getInstance();
		tvDisplayDate = (TextView) findViewById(R.id.prev_date);

		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DAY_OF_MONTH);
		showDate(year, month, day);
		++month;
	}
	
	// open date picker
	@SuppressWarnings("deprecation")
	public void openDatePicker(View v){
		showDialog(DATE_DIALOG_ID);
	}
	
	// save new work
	public void saveNewWork(View v){
		EditText client_name = (EditText) findViewById(R.id.txt_client_name);
		EditText amount = (EditText) findViewById(R.id.txt_price);
		EditText desc = (EditText) findViewById(R.id.txt_desc);
		
		if(client_name.getText().toString().equals("") || amount.getText().toString().equals("")){
			DialogManager dm = new DialogManager(this);
			dm.setTitle("Error").setMessage("All field required except description.").alert();
		}
		else{
			
			// create new work object
			Work work = new Work();
			work.setCreatedOn(this.year + "-" + ((this.month < 10) ? "0" + this.month : this.month) + "-" + ((this.day < 10) ? "0" + this.day : this.day));
			work.setClient(client_name.getText().toString());
			work.setAmount(Integer.parseInt(amount.getText().toString()));
			work.setWorkDesc(desc.getText().toString());
			
			// add new work to work data
			workData.addNewWork(work);
			
			// reset fields
			client_name.setText("");
			amount.setText("");
			desc.setText("");
			
			DialogManager dm = new DialogManager(this);
			dm.setTitle("Success").setMessage("Successfully add new work.").alert();
		}
	}
	
	// show date
	private void showDate(int year, int month, int day){
		tvDisplayDate.setText(new StringBuilder().append(convertMonth(month)).append(" ").append(day).append(", ").append(year));
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		if (id == 999) {
			return new DatePickerDialog(this, this, year, (month - 1), day);
		}
		return null;
	}
	
	@Override
	public void onDateSet(DatePicker view, int _year, int monthOfYear, int dayOfMonth) {
		// TODO Auto-generated method stub
		this.year = _year;
		this.month = monthOfYear + 1;
		this.day = dayOfMonth;
		
		showDate(_year, monthOfYear, dayOfMonth);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition (R.anim.open_main, R.anim.close_next);
	}

	
}
