package com.ritambhara.rememberstuff.utils;

import java.util.Locale;

/**
 * Environment of the Application. Don't create instance of this class. All the resources in this class are static. 
 * @author karawat
 */
public final class AppEnvironment 
{
	public static final String Platform = "android";
	public static final long ONE_SECOND = 1000;
	public static final long ONE_MINUTE = 60 * 1000;
	public static final long ONE_HOUR = 60 * ONE_MINUTE;
	public static final long ONE_DAY = 24 * ONE_HOUR;
	public static final long ONE_MONTH = 30 * ONE_DAY;
	public static final long ONE_YEAR = 12 * ONE_MONTH;

	
	private static boolean isTestEnv = false;
	private static String apiLevel = null;


	// To avoid Instantiation
	private AppEnvironment(){}

	public static String getDeviceLocale()
	{
		return Locale.getDefault().getLanguage();
	}

	public static void setTestEnv(boolean value)
    {
		isTestEnv = value;
    }

	public static boolean isTestEnv()
	{
		return isTestEnv;
	}

	public static void setApiLevel(String apiLevel)
	{
		AppEnvironment.apiLevel = apiLevel;
	}

	public static String getApiLevel()
	{
		return apiLevel;
	}	
}

