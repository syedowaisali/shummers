package com.crystal.shummers;

import org.json.JSONException;
import org.json.JSONObject;

import com.crystal.androidtoolkit.AppHelper;
import com.crystal.androidtoolkit.ConnectionDetector;
import com.crystal.androidtoolkit.ServiceHandler;
import com.crystal.androidtoolkit.SharedPrefs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

interface ResultCallback{
	void apiResultCallback(JSONObject jsonObj);
}

public abstract class BaseActivity extends ActionBarActivity{
	
	SharedPrefs sp = SharedPrefs.getInstance();
	ConnectionDetector cd;
	AppHelper appHelper;
	WorkData workData;
	ExpenseData expenseData;
	ProgressDialog pDialog;
	ServiceHandler sh;
	public String progressMessage;
	JSONObject jsonObject;
	String api_url = Setting.WEB_URL + "?token=" + Setting.TOKEN + "&";
	ResultCallback callback;
	
	//public abstract void onBackPressed();
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		sp.init(this, Setting.MY_PREFERENCE);
		cd = new ConnectionDetector(this);
		appHelper = new AppHelper();
		workData = new WorkData(this);
		expenseData = new ExpenseData(this);
		// creating service handler class instance
		sh = new ServiceHandler();
	}
	
	public void changeFont(int res){
		TextView tv = (TextView)findViewById(res);
		Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/oswald_regular.otf");
		tv.setTypeface(typeFace);
	}
	
	
	public void toast(String message){
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
	
	public void toast(int message){
		toast(String.valueOf(message));
	}
	
	public void toast(float message){
		toast(String.valueOf(message));
	}
	
	public void toast(String message, int length){
		Toast.makeText(this, message, length).show();
	}
	
	public void toast(int message, int length){
		toast(String.valueOf(message), length);
	}
	
	public void toast(float message, int length){
		toast(String.valueOf(message), length);
	}
	
	public void log(String tag, String val){
		Log.d(tag, val);
	}
	
	public void log(String val){
		log(Class.class.getSimpleName(), val);
	}
	
	public void runUrlTask(String url, String message, ResultCallback callback){
		progressMessage = message;
		this.callback = callback;
		new runUrlAsyncTask().execute(url, url);
		
	}
	
	public void gotoActivity(Class<?> act){
		Intent intent = new Intent(this, act);
		startActivity(intent);
		overridePendingTransition (R.anim.open_next, R.anim.close_main);
	}
	
	public void gotoActivity(Intent intent){
		startActivity(intent);
		overridePendingTransition (R.anim.open_next, R.anim.close_main);
	}
	
	// check internet connection
	public boolean isNetConnect(){
		return cd.isConnectingToInternet();
	}
	// AsyncTask
	private class runUrlAsyncTask extends AsyncTask<String, Void, Void>{
		
		@Override
		protected void onPreExecute(){
			pDialog = new ProgressDialog(BaseActivity.this);
			pDialog.setMessage(progressMessage);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			String url = params[0];
			// set url
			//String url = Setting.WEB_URL + "?func=_get_auth_token&email=" + email.toString() + "&pass=" + pass.toString() + "&token=" + Setting.TOKEN;
			url = url.replace("@", "%40").replace(" ", "%20");
			
			Log.d("url", url);

			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
			
			Log.d("Response: > ", jsonStr);
			
			if(jsonStr != null){
				try{
					
					jsonObject = new JSONObject(jsonStr);
					
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
			else{
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result){
			super.onPostExecute(result);
			
			if(pDialog.isShowing())
				pDialog.dismiss();
			
			callback.apiResultCallback(jsonObject);
			
			/*if(return_status == Setting.SUCCESS){
				try {
					setToken(jsonObject.getString("token"), return_message);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(return_status == Setting.INFO){
				authFailed(return_message);
			}*/
		}
	}
	
	protected String convertMonth(int m){
		String result = "Jan";
		switch(m){
		case 0:
			result = "Jan";
			break;
			
		case 1:
			result = "Feb";
			break;
			
		case 2:
			result = "Mar";
			break;
			
		case 3:
			result = "Apr";
			break;
			
		case 4:
			result = "May";
			break;
			
		case 5:
			result = "Jun";
			break;
			
		case 6:
			result = "Jul";
			break;
			
		case 7:
			result = "Aug";
			break;
			
		case 8:
			result = "Sep";
			break;
			
		case 9:
			result = "Oct";
			break;
			
		case 10:
			result = "Nov";
			break;
			
		case 11:
			result = "Dec";
			break;
		}
		
		return result;
	}
}
