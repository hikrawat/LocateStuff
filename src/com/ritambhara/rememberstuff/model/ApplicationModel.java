package com.ritambhara.rememberstuff.model;

import java.util.List;

import com.ritambhara.rememberstuff.adapter.StuffListAdapter;
import com.ritambhara.rememberstuff.utils.RSLog;
import com.ritambhara.rememberstuff.utils.db.RitambharaDB;

import android.content.Context;

/**
 * Singleton class which holds the application level stuff.
 * @author karawat
 *
 */
public class ApplicationModel 
{
	private Context mApplicationContext;
	private RitambharaDB ritambharaDb;
	private List<StuffInfo> stuffList;
	
	public int getStuffCount()
	{
		if(stuffList == null)
		{
			return 0;
		}
		else
		{
			return stuffList.size();
		}
	}
	
	public List<StuffInfo> getStuffList()
	{
		return stuffList;
	}
	
	/**
	 * Return the stuff at position pos
	 */
	public StuffInfo getStuffFromList(int pos)
	{
		StuffInfo stuff = null;
		
		try{
			stuff = stuffList.get(pos);
		}catch(IndexOutOfBoundsException e) {}
		
		return stuff;
	}
	
	public void initializeStuffDB()
	{
		if(mApplicationContext == null){
			RSLog.e(RSLog.TAG_DB, "Application Context Not set");
			return;
		}
		
		ritambharaDb = new RitambharaDB(mApplicationContext, RitambharaDB.DB_NAME, null);
		ritambharaDb.openDatabase();
		stuffList = ritambharaDb.getAllStuffFromDB();
	}
	
	public Context getApplicationContext() 
	{
		return mApplicationContext;
	}
	
	public void setApplicationContext(Context mApplicationContext) 
	{
		this.mApplicationContext = mApplicationContext;
	}
	
	public void addStuffToDB(StuffInfo stuffInfo)
	{
		// Adding this stuff to the Local list.
		stuffList.add(stuffInfo);
		ritambharaDb.insertStuffInDB(stuffInfo);
	}

	public RitambharaDB getRitambharaDB()
	{
		return ritambharaDb;
	}
	
	// Singleton related Code...
	private static ApplicationModel _instance = null;
	private ApplicationModel(){ ; }
	public static ApplicationModel getInstance()
	{
		if(_instance == null){
			_instance = new ApplicationModel();
		}
		return _instance;
	}
}
