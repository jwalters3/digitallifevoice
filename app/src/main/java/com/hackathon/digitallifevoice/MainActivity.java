package com.hackathon.digitallifevoice;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hackathon.digitallifevoice.api.DigitalLifeController;
import com.hackathon.digitallifevoice.api.DigitalLifeDevice;
import com.hackathon.digitallifevoice.data.Action;
import com.hackathon.digitallifevoice.data.DatabaseHandler;
import com.hackathon.digitallifevoice.fragments.ActionsListFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A list view that shows the current list of paired actions
 */
public class MainActivity extends BaseActivity {


    private final int ADD_DEVICE =1;
    private final int SETTINGS = 2;
    private final int DEVICE_LIST = 3;
    private final int REQ_CODE_SPEECH_INPUT = 4;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    //CharSequence[] results = data.getCharSequenceArrayExtra(RecognizerIntent.EXTRA_RESULTS);
                    ArrayList<String> speechResult = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    CharSequence[] results = speechResult.toArray(new CharSequence[speechResult.size()]);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Select");
                    builder.setItems(results, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // the user clicked on colors[which]
                        }
                    });
                    builder.show();
                }
                break;
            }
            case ADD_DEVICE: {
                if (resultCode == Activity.RESULT_OK) {
                    long actionId = data.getLongExtra("id", 0);
                    if (actionId != 0) {
                        DatabaseHandler db = new DatabaseHandler(this);
                        ActionsListFragment fragment = (ActionsListFragment) this.getFragmentManager().findFragmentById(R.id.fragment);
                        fragment.addItem(db.getAction(actionId));
                    }
                    break;
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



    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
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
        if (id == R.id.action_voicetest) {
            promptSpeechInput();
        }
        if (id == R.id.action_devices) {
            Intent myIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(myIntent, this.DEVICE_LIST);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
