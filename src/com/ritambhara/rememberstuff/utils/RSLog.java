package com.ritambhara.rememberstuff.utils;

import android.util.Log;


public class RSLog {

	public static String TAG_DB = "tagDb";
	public static String TAG_APP = "tagApp";
	
	// TODO : Must revert before release
	public static boolean showLogs = AppEnvironment.isTestEnv();
	
	public static void e(String tag,String msg)
	{
		if (showLogs && tag != null && msg != null)
			Log.e(tag, msg);
	}
	
	public static void d(String tag,String msg)
	{
		if (showLogs && tag != null && msg != null)
			Log.d(tag, msg);
	}
	
	public static void Debug(String tag,String msg)
	{
		if(showLogs && tag != null && msg != null)
			Log.e(tag, msg);
	}
	
	public static void printStackTrace(Exception e)
	{
		if (showLogs && e != null)
		{
			e.printStackTrace();
		}
	}
}
