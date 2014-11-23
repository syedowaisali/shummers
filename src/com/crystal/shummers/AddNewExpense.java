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

public class AddNewExpense extends BaseActivity implements OnDateSetListener {

	private static int DATE_DIALOG_ID = 999;
	private Calendar cal;
	private TextView tvDisplayDate = null;
	
	private int year, month, day;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_new_expense);
		
		cal = Calendar.getInstance();
		tvDisplayDate = (TextView) findViewById(R.id.exp_date);

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
	
	// save new expense
	public void saveNewExpense(View v){
		EditText amount = (EditText) findViewById(R.id.exp_amount);
		EditText desc = (EditText) findViewById(R.id.exp_desc);
		
		if(amount.getText().toString().equals("")){
			DialogManager dm = new DialogManager(this);
			dm.setTitle("Error").setMessage("Amount required.").alert();
		}
		else{
			
			// create new work object
			Expense expense = new Expense();
			expense.setCreatedOn(this.year + "-" + ((this.month < 10) ? "0" + this.month : this.month) + "-" + ((this.day < 10) ? "0" + this.day : this.day));
			expense.setAmount(Integer.parseInt(amount.getText().toString()));
			expense.setExpenseDesc(desc.getText().toString());
			
			// add new work to work data
			expenseData.addNewExpense(expense);
			
			// reset fields
			amount.setText("");
			desc.setText("");
			
			DialogManager dm = new DialogManager(this);
			dm.setTitle("Success").setMessage("Successfully add new expense.").alert();
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
