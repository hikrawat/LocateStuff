package com.ritambhara.rememberstuff;

import com.ritambhara.rememberstuff.model.ApplicationModel;
import com.ritambhara.rememberstuff.utils.FileUtiles;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Activity to view the image in full screen mode. Since the image is either very small or distorted/cropped. It make sence to launch this activity when the Image is clicked 
 * and pass the Bitmap of the Image in the extras.
 * @author karawat
 */
public class FullScreenViewActivity extends Activity 
{
	public static String IMAGE_BITMAP = "ImageBitmap";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreen_image);
        
        Intent intent = getIntent();
        Bitmap bm =  intent.getParcelableExtra(IMAGE_BITMAP);
        
        if(bm == null)
        {
        	finish(); return;
        }
        
        ImageView iv = (ImageView)findViewById(R.id.imgDisplay);
        iv.setImageBitmap(bm);
    }

    public void closeBtnClickHandler(View view)
    {
    	finish();
    	return;
    }
    
    public static void launchImageFullScreen(String filePath)
    {
    	// launch full screen activity
		Bitmap imageBitmap = FileUtiles.getImageBitmap(filePath);

    	Intent intent = new Intent(ApplicationModel.getInstance().getApplicationContext(), FullScreenViewActivity.class);
        intent.putExtra(FullScreenViewActivity.IMAGE_BITMAP, imageBitmap);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ApplicationModel.getInstance().getApplicationContext().startActivity(intent);
    }
}
