package com.example.testdatabase;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Log出力の拡張クラス<br>
 * 
 * */
public class Logger extends Activity {

	private static boolean DEBUGABLE_VALUE = true;
	private static boolean isDebaggable_ = false;

	private static String tag_;


	public static void startLogger(Activity activity, String tag) {
		 PackageManager manager = activity.getPackageManager();
		 ApplicationInfo appInfo = null;
		 try {
			 appInfo = manager.getApplicationInfo(activity.getPackageName(), 0);
			 if ((appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) == ApplicationInfo.FLAG_DEBUGGABLE && DEBUGABLE_VALUE == true) {
				 isDebaggable_ = true;
				 tag_ = tag;
			 }
		 } catch (NameNotFoundException e) {
			 // TODO　エラー発生時の処理を考え
		 } catch (NullPointerException e) {
			 //  TODO　エラー発生時の処理を考え
		 }
	}

	/**
	 * LogCatのタグ列の文言を整形する
	 * */
	public static String setLogcatTag(String tag, Object anyClass, String method) {
		return tag+":"+anyClass.getClass().getSimpleName()+ "#" + method;
	}

	public static void v(Object anyClass, String method, String msg) {
		if(isDebaggable_) {
			Log.v(setLogcatTag(tag_, anyClass, method), msg);
		}
	}

	public static void d(Object anyClass, String method, String msg) {
		if(isDebaggable_) {
			Log.d(setLogcatTag(tag_, anyClass, method), msg);
		}
	}

	public static void i(Object anyClass, String method, String msg) {
		if(isDebaggable_) {
			Log.i(setLogcatTag(tag_, anyClass, method), msg);
		}
	}

	public static void w(Object anyClass, String method, String msg) {
		if(isDebaggable_) {
			Log.w(setLogcatTag(tag_, anyClass, method), msg);
		}
	}

	public static void e(Object anyClass, String method, String msg) {
		if(isDebaggable_) {
			Log.e(setLogcatTag(tag_, anyClass, method), msg);
		}
	}
}