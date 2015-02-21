package com.hackathon.digitallifevoice;

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
public class MainActivity extends ActionBarActivity {


    private List<DigitalLifeDevice> devices;
    private DigitalLifeController dlc;
    int ADD_DEVICE =1;

    public String getAppId() { return PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("appid", "OE_69B642D383971614_1"); }
    public String getUserId() { return PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("username", "553474450");    }
    public String getPassword() { return PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("password", "NO-PASSWD");    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_DEVICE) {
            if (resultCode == Activity.RESULT_OK) {


                ActionsListFragment fragment = (ActionsListFragment)this.getFragmentManager().findFragmentById(R.id.fragment);
                fragment.notifyDataSetChanged();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        dlc = DigitalLifeController.getInstance();
//        dlc.init(getAppId(), "https://systest.digitallife.att.com");
//        try {
//            dlc.login( getUserId(), getPassword());
//        } catch (Exception e) {
//            System.out.println("Logout Failed");
//            e.printStackTrace();
//            return;
//        }
//
//        devices = dlc.fetchDevices();



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
            return true;
        }
        if (id == R.id.action_add) {
            Intent myIntent = new Intent(this, EditActivity.class);

            startActivityForResult(myIntent, this.ADD_DEVICE);

            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
