package com.datatech.halalhubs.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetConnection {

	public static boolean isAvailable(final Activity activity) {

		ConnectivityManager cm = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {

			// reloadData();

			return true;
		} else {

			return false;
		}

	}

	public static void ExitApplication(Activity activity) {

		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		activity.startActivity(startMain);
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	
	public static String stringFormat(Double text) {

		return String.format("%.2f", text);

	}

}
