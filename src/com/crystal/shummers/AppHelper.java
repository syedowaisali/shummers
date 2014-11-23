package com.crystal.shummers;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.net.ParseException;
import android.util.Base64;

public class AppHelper {
	
	public static final String MY_PREFERENCE = "com.crystal.shummers.MyPreference";
	
	public AppHelper(){
		
	}
	
	// validate email address
	public static boolean isValidEmail(String email){
         String regExpn =
             "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                 +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                   +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                   +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                   +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                   +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

     CharSequence inputStr = email;

     Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
     Matcher matcher = pattern.matcher(inputStr);

     if(matcher.matches())
        return true;
     else
        return false;
    }
	
	// convert bytes to bitmap
	public static Bitmap decodeBitmap(String imageString){
		byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
		Bitmap bmp = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
		
		return bmp;
	}
	
	// convert bitmap image to bytes
	public static String encodeBitmap(Bitmap imageBitmap){
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		
		String ecodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

		return ecodedImage;
	}
	
	// get rounded bitmap
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
	    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(output);
	 
	    final int color = 0xff424242;
	    final Paint paint = new Paint();
	    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
	    final RectF rectF = new RectF(rect);
	    final float roundPx = 300;
	 
	    paint.setAntiAlias(true);
	    canvas.drawARGB(0, 0, 0, 0);
	    paint.setColor(color);
	    canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
	 
	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	    canvas.drawBitmap(bitmap, rect, rect, paint);
	 
	    return output;
	}
	
	// shuffle array
	public static int[] shuffleArray(int[] ar){
	    Random rnd = new Random();
	    for (int i = ar.length - 1; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      // Simple swap
	      int a = ar[index];
	      ar[index] = ar[i];
	      ar[i] = a;
	    }
	    
	    return ar;
	}

	// date format
	@SuppressLint("SimpleDateFormat")
	public static String formatDate(String dateString, String format){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date convertedDate = new Date();
	    try {
	        convertedDate = dateFormat.parse(dateString);
	    } catch (ParseException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    SimpleDateFormat postFormater = new SimpleDateFormat(format);
	    String newDateStr = postFormater.format(convertedDate);
	    return newDateStr;
	}
}