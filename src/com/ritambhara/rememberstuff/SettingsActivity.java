package com.ritambhara.rememberstuff;

import android.os.Bundle;
import android.view.MenuItem;

public class SettingsActivity extends BaseActivity 
{
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
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
}
