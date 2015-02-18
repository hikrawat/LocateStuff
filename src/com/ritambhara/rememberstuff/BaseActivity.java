package com.ritambhara.rememberstuff;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class BaseActivity extends Activity
{
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionbar_background)));
        
        if(isUpEnabled())
        {
	        actionBar.setDisplayHomeAsUpEnabled(true);
        }
	}
	
	protected boolean isUpEnabled()
	{
		return false;
	}
	 

}
