package com.ritambhara.rememberstuff;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.ritambhara.rememberstuff.model.ApplicationModel;
import com.ritambhara.rememberstuff.model.StuffInfo;
import com.ritambhara.rememberstuff.utils.AppEnvironment;
import com.ritambhara.rememberstuff.utils.Constants;
import com.ritambhara.rememberstuff.utils.FileUtiles;
import com.ritambhara.rememberstuff.utils.RSLog;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity to Add more stuff to the App.
 * @author karawat
 *
 */
public class AddStuffActivity extends BaseActivity
{
	private TextView titleTxtView = null;
	private TextView descriptionTxtView = null;
	private ImageView titleImageView = null;
	private Bitmap stuffImageBitmap = null;
	private Uri stuffAudioUri;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stuff);
        
        // Getting the Views
        titleTxtView = (TextView)findViewById(R.id.titleTxt);
        descriptionTxtView = (TextView)findViewById(R.id.descriptionTxt);
        titleImageView = (ImageView)findViewById(R.id.stuffImage);
        
        initializeUI();
    }
	
	public void initializeUI()
	{
		if(stuffInfo == null){ 
			stuffInfo = new StuffInfo();
			return; 
		}
		titleTxtView.setText(stuffInfo.getTitle());
		descriptionTxtView.setText(stuffInfo.getDescription());
		if(stuffInfo.isImageExist())
		{
			//FileUtiles.setImageView(titleImageView, stuffInfo.getImageFilePath());
			
			File imgFile = new  File(stuffInfo.getImageFilePath());
			if(imgFile.exists()){
			    stuffImageBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
			    titleImageView.setImageBitmap(stuffImageBitmap);
			}
		}
	}
	
	@Override
	protected boolean isUpEnabled()
	{
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        this.finish();
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	// Listeners for Buttons in the Activity.
	public void SaveStuffToDB(View view)
	{
		TextView titleTxtView = (TextView) findViewById(R.id.titleTxt);
		String titleTxt = titleTxtView.getText().toString().trim();
		if( titleTxt.length() == 0){
			Toast.makeText(ApplicationModel.getInstance().getApplicationContext(), R.string.error_empty_title, Toast.LENGTH_LONG).show();
			return;
		}
		
		// TODO Add Stuff to DB and refresh the adapter of Main Activity.
	}
	
	public void captureImageBtnHandler(View view)
	{
    	Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
    	startActivityForResult(intent, Constants.IMAGE_CAPTURE_REQUEST_CODE);
	}

	public void recordAudioBtnHandler(View view)
	{
		Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION); 
		startActivityForResult(intent, Constants.ACTIVITY_RECORD_SOUND);
		
//		Intent intent = new Intent(this, AudioRecordActivity.class);
//    	startActivity(intent);
	}
	
	private void setStuffImage()
	{
		if(stuffImageBitmap==null){ return; }
		
		titleImageView.setImageBitmap(stuffImageBitmap);
	}
	
	// Set the play button enabled, which indicate that the audio of the stuff exist.
	private void setStuffAudio()
	{
		
	}
	
	/**
	 * When save button is clicked, we will save all the stuff.
	 * @param v
	 */
	public void saveBtnClickHandler(View v)
	{
		// Get the Title
		String stuffTitle = titleTxtView.getText().toString().trim();
		if(stuffTitle == null || stuffTitle.length() == 0)
		{
			// Can't save a Stuff without title.
			Toast.makeText(ApplicationModel.getInstance().getApplicationContext(), R.string.empty_stuff, Toast.LENGTH_LONG).show();
			titleTxtView.requestFocus();
			return;
		}
		stuffInfo.setTitle(stuffTitle);
		stuffInfo.setDescription(descriptionTxtView.getText().toString().trim());
		
		if(stuffImageBitmap == null)
		{
			stuffInfo.setImageExist(false);
			stuffInfo.setImageFilePath("");
		}
		else
		{
			// Save Image to Disk. Name of Disk will always be the same as that of title.
			try{
				ContextWrapper cw = new ContextWrapper(ApplicationModel.getInstance().getApplicationContext());
				File stuffFile = new File(cw.getFilesDir(), stuffTitle);
				
				FileOutputStream fos = openFileOutput(stuffFile.getName(), Context.MODE_PRIVATE);
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				stuffImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
				byte[] byteArray = stream.toByteArray();
				
				fos.write(byteArray);
				fos.close();
				
				stuffInfo.setImageFilePath(stuffFile.getAbsolutePath());
				stuffInfo.setImageExist(true);
			}
			catch(Exception e)
			{
				Toast.makeText(ApplicationModel.getInstance().getApplicationContext(), R.string.cannot_save_stuff_image, Toast.LENGTH_LONG).show();
				return; // Don't close this page because we are not able to save.
			}
		}
		
		// Save Audio file to the disk
		if(stuffAudioUri != null)
		{
			File sourceFile = new File(stuffAudioUri.toString());
			
			ContextWrapper cw = new ContextWrapper(ApplicationModel.getInstance().getApplicationContext());
			File destinationFile = new File(cw.getFilesDir(), stuffTitle + Constants.AUDIO_FILE_PREFIX);

			if(sourceFile.renameTo(destinationFile))
			{
				// Able to move file
				stuffInfo.setAudioExist(true);
				stuffInfo.setAudioFilePath(destinationFile.getAbsolutePath());
			}
		}
		
		// Writing Stuff Info to the DB
		ApplicationModel.getInstance().addStuffToDB(stuffInfo);
		finish();
		return;
	}
	
	public void onImageClick(View v) {
        // launch full screen activity
        Intent intent = new Intent(ApplicationModel.getInstance().getApplicationContext(), FullScreenViewActivity.class);
        intent.putExtra(FullScreenViewActivity.IMAGE_BITMAP, stuffImageBitmap);
        startActivity(intent);
	}
	
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
    	if(requestCode == Constants.IMAGE_CAPTURE_REQUEST_CODE)
    	{
    		if(resultCode == RESULT_OK)
    		{
    			stuffImageBitmap = (Bitmap) data.getExtras().get("data");
    			setStuffImage();
    		}
    	}
    	else if(requestCode == Constants.ACTIVITY_RECORD_SOUND)
    	{
    		if(resultCode == RESULT_OK)
    		{
    			stuffAudioUri = data.getData();
    			setStuffAudio();
    		}
    	}
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setStuffImage();
    }

    private static StuffInfo stuffInfo;
    public static void launchAddStuffActivity(Context context, StuffInfo _stuff)
    {
    	stuffInfo = _stuff;
    	Intent intent = new Intent(context, AddStuffActivity.class);
    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	context.startActivity(intent);
    }
}
