package com.abdullah.dailydiary.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.Display;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.orm.SugarContext;

import java.util.Locale;

public class Globals {

	private static Globals instance = null;

	public static Globals getInstance() {
		return instance;
	}

	public static Globals getUsage(Activity activity) {
		if (instance == null) {
			instance = new Globals(activity);
		}
		instance.setActivityOnFront(activity);
		return instance;
	}
	public Locale mLocale;
	public Activity mActivity;
	public Context mContext;
	public String mPackageName;
	public int mScreenWidth;
	public int mScreenHeight;
	public static void resetGlobalObjects(){
	}

	private Globals(Activity activity) {
		mLocale = Locale.ENGLISH;
		mActivity = activity;
		mContext = activity;
		SugarContext.init(mContext);
		mPackageName = mContext.getPackageName();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			Point size = new Point();
			mActivity.getWindowManager().getDefaultDisplay().getSize(size);
			mScreenWidth = size.x;
			mScreenHeight = size.y;
		} else {
			Display display = mActivity.getWindowManager().getDefaultDisplay();
			mScreenWidth = display.getWidth();
			mScreenHeight = display.getHeight();
		}

		resetGlobalObjects();


	}

	public void resetInstance() {
		instance = null;
	}

	private void setActivityOnFront(Activity activity) {
		mActivity = activity;
		mContext = activity;
		SugarContext.init(mContext);
	}


	public void onConfigurationChanged(Configuration newConfig) {
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				Point size = new Point();
				mActivity.getWindowManager().getDefaultDisplay().getSize(size);
				mScreenWidth = size.x;
				mScreenHeight = size.y;
			} else {
				Display display = mActivity.getWindowManager().getDefaultDisplay();
				mScreenWidth = display.getWidth();
				mScreenHeight = display.getHeight();
			}
		}
	}


	public void hideSoftKeyBoard() {
		try {
			InputMethodManager inputMethodManager = (InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
			if (mActivity.getCurrentFocus() != null) {
				inputMethodManager.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void showMessage(String message) {
		Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
	}

	public void showExitMessage(){
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage("Do you want to exit application?");
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				mActivity.finish();
			}
		});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {

			}
		});
		builder.show();

	}

	public void showErrorMessage() {
		showMessage("Error Processing Your Request");
	}

	public boolean isInternetAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}