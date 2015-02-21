package com.hackathon.digitallifevoice;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.hackathon.digitallifevoice.fragments.DeviceListFragment;

// This shows a list of devices and their current status
public class DeviceListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            boolean isPicker = bundle.getBoolean("isPicker");
            DeviceListFragment fragment = (DeviceListFragment)this.getFragmentManager().findFragmentById(R.id.fragment);
            fragment.setIsPicker(isPicker);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_device_list, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
