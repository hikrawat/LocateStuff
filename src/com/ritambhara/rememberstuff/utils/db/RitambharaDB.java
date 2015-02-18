package com.ritambhara.rememberstuff.utils.db;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.ritambhara.rememberstuff.model.StuffInfo;
import com.ritambhara.rememberstuff.utils.RSLog;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;


public class RitambharaDB extends SQLiteOpenHelper 
{
	public static final String DB_NAME = "rememberstuff_db";
	public static final String STUFF_TABLE = "stuffInfo";
	public static final String TAG = "RitambharaDB";
	
	private static final int DB_VERSION = 1;
	// FIELDS of the Table
	private static final String STUFF_ID = "stuffid";
	private static final String STUFF_TITLE = "stuffTitle";
	private static final String STUFF_DESCRIPTION = "stuffDescription";
	private static final String STUFF_IMAGE_NAME = "stuffImageName";
	private static final String STUFF_AUDIO_FILE_NAME = "stuffAudioFileName";
	private static final String STUFF_IMAGE_EXIST = "stuffImageFileExist";
	private static final String STUFF_AUDIO_EXIST = "stuffAudioFileExist";

	private static final String SCHEMA_CREATE_TABLE = 
			"CREATE TABLE IF NOT EXISTS " + STUFF_TABLE + "( " +
/*			STUFF_ID 				+ " INT PRIMARY KEY AUTO INCREMENT, " + */
			STUFF_TITLE 			+ " VARCHAR(100) PRIMARY KEY, " +
			STUFF_DESCRIPTION 		+ " VARCHAR(1024), " +
			STUFF_IMAGE_EXIST		+ " INT, " +
			STUFF_IMAGE_NAME 		+ " VARCHAR(250), " +
			STUFF_AUDIO_EXIST		+ " INT, " +
			STUFF_AUDIO_FILE_NAME 	+ " VARCHAR(250))";

	
	private static boolean sIsDataBaseExist = false;
	private int mOpenCounter;
    private SQLiteDatabase mDatabase;
	
	public RitambharaDB(Context context, String name, CursorFactory factory) {
		super(context, name, factory, DB_VERSION);
		doesDataBaseExist(context,name);
	}

	/**
	 * Check whether the given database file exists.
	 * If it does not exist, it means our app is first run and just installed.
	 * else, it not the first run
	 */
	private void doesDataBaseExist(Context context, String name)
	{
		if(getDataBaseExist()){ return; } 
	
		File database=context.getApplicationContext().getDatabasePath(name + ".db");
		if (!database.exists()) {
			RSLog.Debug(TAG, "db:" + name + "does not exist");
			updateDatabaseExistFlag();
		}
	}

	private static synchronized void updateDatabaseExistFlag()
	{
		if(sIsDataBaseExist == false){
			RSLog.Debug(TAG, "flag updated");
			sIsDataBaseExist = true;
		}
	}
	private static boolean getDataBaseExist(){
		return sIsDataBaseExist;
	}	

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL( SCHEMA_CREATE_TABLE );
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		db.execSQL("drop table if exists "+STUFF_TABLE);
		onCreate(db);
	}

	public synchronized SQLiteDatabase openDatabase() 
	{
        mOpenCounter++;
        if(mOpenCounter == 1) {
            // Opening new database
            mDatabase = getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() 
    {
        mOpenCounter--;
        if(mOpenCounter == 0) {
            // Closing database
            mDatabase.close();
        }
    }
    
    public boolean insertStuffInDB(StuffInfo stuffInfo)
    {
    	return InsertStuffInDB(stuffInfo, RitambharaDB.STUFF_TABLE);
    }
    
    public List<StuffInfo> getAllStuffFromDB()
    {
    	List<StuffInfo> stuffs = new ArrayList<StuffInfo>();

		String strSelectCommand = "SELECT * FROM " + STUFF_TABLE + " ORDER BY "+ STUFF_TITLE;
    	Cursor cursor=mDatabase.rawQuery(strSelectCommand, null);

    	while (cursor!=null && !cursor.isClosed() && cursor.moveToNext())
		{
    		StuffInfo stuffInfo=new StuffInfo();
    		stuffInfo.setTitle(cursor.getString(cursor.getColumnIndex(STUFF_TITLE)));
    		stuffInfo.setDescription(cursor.getString(cursor.getColumnIndex(STUFF_DESCRIPTION)));
    		stuffInfo.setImageExist(cursor.getInt(cursor.getColumnIndex(STUFF_IMAGE_EXIST)) == 1 ? true : false);
    		if(stuffInfo.isImageExist())
    		{
    			stuffInfo.setImageFilePath(cursor.getString(cursor.getColumnIndex(STUFF_IMAGE_NAME)));	
    		}
    		
			stuffs.add(stuffInfo);
		}
    	cursor.close();
    	
    	return stuffs;
    }
    
    private boolean InsertStuffInDB(StuffInfo stuffInfo, String TableName)
	{	
    	 //db.beginTransaction();
		 ContentValues values=new ContentValues();
		 values.put(STUFF_TITLE, stuffInfo.getTitle());
		 
		 String value = stuffInfo.getDescription();
		 values.put(STUFF_DESCRIPTION, (value == null)?"":value);
		 
		 values.put(STUFF_IMAGE_EXIST, stuffInfo.isImageExist()?1:0);
		 
		 value = stuffInfo.getImageFilePath();
		 values.put(STUFF_IMAGE_NAME, (value == null)?"":value);
		 
		 values.put(STUFF_AUDIO_EXIST, stuffInfo.isAudioExist()?1:0);
		 value = stuffInfo.getAudioFilePath();
		 values.put(STUFF_AUDIO_FILE_NAME, (value == null)?"":value);
		 //KR TODO Add Other fields
		 
		 try {
			 mDatabase.insertWithOnConflict(TableName, null, values,SQLiteDatabase.CONFLICT_REPLACE);
			 return true;
		 } catch (Exception e) {
			RSLog.e("SQLHelper", "Error in save Stuff" + stuffInfo.getTitle());
			RSLog.printStackTrace(e);
			return false;		
		}
	}
}

