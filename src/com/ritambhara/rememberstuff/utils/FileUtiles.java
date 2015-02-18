package com.ritambhara.rememberstuff.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

/**
 * This is a utility class used to read/write files 
 * @author karawat
 *
 */
public class FileUtiles 
{

	public static void setImageView(ImageView iv, String path)
	{
		Bitmap bitmap=null;
		File f= new File(path);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        try {
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

         iv.setImageBitmap(bitmap);
	}

	public static Bitmap getImageBitmap(String imgPath)
	{
		Bitmap bitmap=null;
		File f= new File(imgPath);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		
        try {
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        return bitmap;
	}
}
