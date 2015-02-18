package com.ritambhara.rememberstuff;

import com.ritambhara.rememberstuff.adapter.StuffListAdapter;
import com.ritambhara.rememberstuff.model.ApplicationModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class MainActivity extends BaseActivity 
{

	ListView stuffList;
	RelativeLayout emptyScreen;
	ApplicationModel model;
	BaseAdapter stuffListAdapter; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeApplication();
    }
    
    @Override
    protected void onResume()
    {
    	super.onResume();
    	initializeUI();
    }
    
    /**
     * Set the UI of the screen according to the stuffs present in the DB. If there is not Stuff, then it will set it to empty screen.
     * If there are stuffs in the DB then it will set the UI to the list of stuffs.
     */
    private void initializeUI()
    {
    	model = ApplicationModel.getInstance();
    	stuffList = (ListView)findViewById(R.id.stuffList);
    	emptyScreen = (RelativeLayout)findViewById(R.id.noStuffViewsContainer);
    	stuffListAdapter = new StuffListAdapter();
    	
    	if(model.getStuffCount() == 0){
    		// Set empty screen UI.
    		stuffList.setVisibility(View.GONE);
    		emptyScreen.setVisibility(View.VISIBLE);
    		return;
    	}
    	
    	stuffList.setVisibility(View.VISIBLE);
		emptyScreen.setVisibility(View.GONE);
		
		stuffList.setAdapter(stuffListAdapter);
	
    }
    
    private void initializeApplication()
    {
    	ApplicationModel.getInstance().setApplicationContext(this.getApplicationContext());
    	ApplicationModel.getInstance().initializeStuffDB();
    }

    public void launchCameraBtnClickHandler(View view)
    {
    	Toast.makeText(getApplication(), "Btn Click", Toast.LENGTH_LONG).show();
    }

    // Menu Related Code ////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private void launchAddNewStuffActivity()
    {
    	AddStuffActivity.launchAddStuffActivity(ApplicationModel.getInstance().getApplicationContext(), null);
    }
    
    private void launchFacebookLoginActivity()
    {
    	Intent intent = new Intent(this, FacebookLoginActivity.class);
    	startActivity(intent);
    }
    
    /**
     * Activity to play Video
     */
    private void launchFullScreenVideoActivity()
    {
    	Intent intent = new Intent(this, FullScreenVideoActivity.class);
    	startActivity(intent);
    }
    
    private void launchSettingsActivity()
    {
    	Intent intent = new Intent(ApplicationModel.getInstance().getApplicationContext(), SettingsActivity.class);
    	startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        int id = item.getItemId();
        switch(id)
        {
        case R.id.action_settings:
        	launchSettingsActivity();
        	break;
        case R.id.action_add_new:
//        	launchFacebookLoginActivity();
        	launchAddNewStuffActivity();
//        	launchFullScreenVideoActivity();
        	break;
       	default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
