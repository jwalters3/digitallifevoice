package com.hackathon.digitallifevoice;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.hackathon.digitallifevoice.api.DigitalLifeController;
import com.hackathon.digitallifevoice.api.DigitalLifeDevice;
import com.hackathon.digitallifevoice.data.Action;
import com.hackathon.digitallifevoice.data.DatabaseHandler;
import com.hackathon.digitallifevoice.fragments.ActionsListFragment;

import java.util.List;

/**
 * A list view that shows the current list of paired actions
 */
public class MainActivity extends BaseActivity {


    int ADD_DEVICE =1;
    int SETTINGS = 2;
    int DEVICE_LIST = 3;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_DEVICE) {
            if (resultCode == Activity.RESULT_OK) {
                long actionId = data.getLongExtra("id", 0);
                if (actionId != 0) {
                    DatabaseHandler db = new DatabaseHandler(this);
                    ActionsListFragment fragment = (ActionsListFragment)this.getFragmentManager().findFragmentById(R.id.fragment);
                    fragment.addItem(db.getAction(actionId));
                }

            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent myIntent = new Intent(this, SettingsActivity.class);
            startActivityForResult(myIntent, this.SETTINGS);
            return true;
        }
        if (id == R.id.action_devices) {
            Intent myIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(myIntent, this.DEVICE_LIST);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
