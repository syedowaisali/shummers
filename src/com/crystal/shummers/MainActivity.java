package com.crystal.shummers;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	// goto add new work activity
	public void gotoAddNewWork(View v){
		gotoActivity(AddNewWork.class);
	}
	
	// goto add new expense activity
	public void gotoAddNewExp(View v){
		gotoActivity(AddNewExpense.class);
	}
	
	// goto view all work activity
	public void gotoViewWork(View v){
		gotoActivity(WorkList.class);
	}
	
	// goto view all expenses activity
	public void gotoViewExp(View v){
		gotoActivity(ExpenseList.class);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition (R.anim.open_main, R.anim.close_next);
	}
}
