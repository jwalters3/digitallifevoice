package com.hackathon.digitallifevoice;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.hackathon.digitallifevoice.api.DigitalLifeController;
import com.hackathon.digitallifevoice.api.DigitalLifeDevice;
import com.hackathon.digitallifevoice.data.Action;
import com.hackathon.digitallifevoice.data.DatabaseHandler;

import java.util.List;

/**
 * A list view that shows the current list of paired actions
 */
public class MainActivity extends ActionBarActivity {


    private List<DigitalLifeDevice> devices;
    private DigitalLifeController dlc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dlc = DigitalLifeController.getInstance();
        dlc.init("OE_69B642D383971614_1", "https://systest.digitallife.att.com");
        try {
            dlc.login( "553474450", "NO-PASSWD");
        } catch (Exception e) {
            System.out.println("Logout Failed");
            e.printStackTrace();
            return;
        }

        devices = dlc.fetchDevices();
        Action a = new Action();
        DigitalLifeDevice d = devices.get(0);
        a.setOperation("on");
        a.setVoiceCommand("Turn on Front light");
        a.setLabel("switch");
        a.setDeviceType("light");
        a.setDeviceGuid("sdfsd2fsdfd");
        DatabaseHandler db = new DatabaseHandler(this);
        //db.addAction(a);


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

            startActivity(myIntent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
