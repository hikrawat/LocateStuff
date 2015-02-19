package com.ritambhara.rememberstuff.adapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import com.ritambhara.rememberstuff.AddStuffActivity;
import com.ritambhara.rememberstuff.FullScreenViewActivity;
import com.ritambhara.rememberstuff.R;
import com.ritambhara.rememberstuff.model.ApplicationModel;
import com.ritambhara.rememberstuff.model.StuffInfo;
import com.ritambhara.rememberstuff.utils.Constants;
import com.ritambhara.rememberstuff.utils.FileUtiles;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StuffListAdapter extends BaseAdapter
{
	@Override
	public int getCount() 
	{
		List<StuffInfo> stuffList = ApplicationModel.getInstance().getStuffList();
		
		if(stuffList == null){
			return 0;
		}else{
			return stuffList.size();
		}
	}

	@Override
	public Object getItem(int position) 
	{
		List<StuffInfo> stuffList = ApplicationModel.getInstance().getStuffList();
		
		if(stuffList == null){
			return null;
		}else{
			return stuffList.get(position);
		}
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		LayoutInflater inflater = (LayoutInflater) ApplicationModel.getInstance().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = null;
//		if(position%2 == 0)
			rowView = inflater.inflate(R.layout.stuff_list_item, parent, false);
//		else
//			rowView = inflater.inflate(R.layout.stuff_list_item_alternate, parent, false);
		
		ImageView iv = (ImageView)rowView.findViewById(R.id.stuffItemImage);
		TextView titleView = (TextView)rowView.findViewById(R.id.stuffItemTitle);
		TextView descrView = (TextView)rowView.findViewById(R.id.stuffItemDescription);
		final StuffInfo stuff = ApplicationModel.getInstance().getStuffFromList(position);
		
		if(stuff == null){
			return null;
		}
		
		titleView.setText(stuff.getTitle());
		descrView.setText(stuff.getDescription());
		
		Bitmap imageBitmap = null;
		if(stuff.isImageExist())
		{
			imageBitmap = FileUtiles.getImageBitmap(stuff.getImageFilePath());
			if(imageBitmap == null){
				stuff.setImageExist(false);
			}
		}
		
		if(stuff.isImageExist())
		{
			FileUtiles.setImageView(iv, stuff.getImageFilePath());
			
			// Clicking on a valid image will show the image in full screen
			iv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
				    FullScreenViewActivity.launchImageFullScreen(stuff.getImageFilePath());
				}
			});
		}
		else
		{
			iv.setImageResource(R.drawable.no_image_available);
			iv.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//			    	ApplicationModel.getInstance().getApplicationContext().startActivityForResult(intent, Constants.IMAGE_CAPTURE_FROM_MAIN_REQUEST_CODE);
				}
			});
		}

		Button editActivityBtn = (Button)rowView.findViewById(R.id.btnStuffItemEdit);
		editActivityBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AddStuffActivity.launchAddStuffActivity(ApplicationModel.getInstance().getApplicationContext(), stuff);
			}
		});
		
		return rowView;
	}
	
}
