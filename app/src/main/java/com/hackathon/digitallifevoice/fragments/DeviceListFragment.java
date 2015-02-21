package com.hackathon.digitallifevoice.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.hackathon.digitallifevoice.R;
import com.hackathon.digitallifevoice.adapters.DigitalLifeDeviceAdapter;
import com.hackathon.digitallifevoice.api.DigitalLifeController;
import com.hackathon.digitallifevoice.api.DigitalLifeDevice;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SWBRADSH on 2/21/2015.
 */
public class DeviceListFragment extends ListFragment implements DigitalLifeController.OnLoginListener, DigitalLifeController.OnDeviceRefreshListener {

    AlertDialog.Builder deleteContact;
    DigitalLifeDeviceAdapter adapter;
    private ProgressDialog progressDialog ;

    private List<DigitalLifeDevice> devices = new ArrayList<DigitalLifeDevice>();
    private DigitalLifeController dlc;
    private boolean isPicker = false;

    public String getAppId() { return PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("appid", "OE_69B642D383971614_1"); }
    public String getUserId() { return PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("username", "553474450");    }
    public String getPassword() { return PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("password", "NO-PASSWD");    }

    public String getAuthToken() { return PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("authtoken", "");    }
    public String getRequestToken() { return PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("requesttoken", "");    }
    public String getGatewayGuid() { return PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("gatewayguid", "");    }

    public void saveTokens(String authToken, String requestToken, String gatewayGuid) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this.getActivity()).edit();
                //.getPreferences().edit();
        editor.putString("authtoken", authToken);
        editor.putString("requesttoken", requestToken);
        editor.putString("gatewayguid", gatewayGuid);
        editor.apply();
    }
    Context mContext;

    public void setIsPicker(boolean isPicker) {
        this.isPicker = isPicker;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        DigitalLifeDevice device = adapter.getItem(position);

        if (isPicker) {
            // return device info to caller
            Intent mResult = new Intent();
            mResult.putExtra("name", device.getName());
            mResult.putExtra("guid", device.getDeviceID());
            mResult.putExtra("label", device.getAction());
            mResult.putExtra("operations", device.getValues());

            getActivity().setResult(Activity.RESULT_OK, mResult);
            getActivity().finish();
        } else
        {
            // Toggle device

            // retrieve the potential values this device can be set to.  ON/OFF, LOCKED/UNLOCKED, etc...
            String[] potentialValues = device.getValues();
            String pendingValue = null;
            if(potentialValues.length>1) {
                if(potentialValues[0].equalsIgnoreCase(device.getStatus())) {
                    pendingValue = potentialValues[1];
                } else {
                    pendingValue = potentialValues[0];
                }
            }

            //Toast.makeText(this.getActivity(), device.getName() + " getting set to:  " + pendingValue, Toast.LENGTH_SHORT).show();
            try {
                dlc.updateDevice(device.getDeviceID(), device.getAction(), pendingValue);
                device.setStatus(pendingValue);
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onLoginFailure(String message) {
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
        toast.show();
        progressDialog.dismiss();
    }

    public void onLogin(JSONObject data) {
        Toast toast = Toast.makeText(getActivity(), "Logged in", Toast.LENGTH_LONG);
        toast.show();
        this.saveTokens(dlc.getAuthToken(), dlc.getRequestToken(),dlc.getGatewayGUID());
        getDevices();
    }

    public void onDeviceRefreshFailure(String message) {
        Toast toast = Toast.makeText(getActivity(), "Failed to refresh devices:"+ message, Toast.LENGTH_LONG);
        toast.show();
        // clear out tokens
        this.saveTokens("", "", "");
        progressDialog.dismiss();
    }
    public void onDeviceRefresh(JSONObject data) {
        progressDialog.dismiss();
        if (adapter != null) {
            devices = dlc.fetchDevices();
            adapter = new DigitalLifeDeviceAdapter(mContext, R.layout.action_item, devices);

            setListAdapter(adapter);
            //adapter.updateItems(devices);
        }
    }

    private void getDevices() {
        dlc.setGatewayGUID(this.getGatewayGuid());
        dlc.setAuthToken(this.getAuthToken());
        dlc.setRequestToken(this.getRequestToken());
        dlc.getDevices(this);
    }
    private void loginDigitalLife() {
        dlc = DigitalLifeController.getInstance();
        dlc.init(getAppId(), "https://systest.digitallife.att.com");

        if ((this.getRequestToken().isEmpty()) || (this.getAuthToken().isEmpty()) || (this.getGatewayGuid().isEmpty()) ) {
            try {
                dlc.login(getUserId(), getPassword(), this);
            } catch (Exception e) {
                System.out.println("Logout Failed");
                e.printStackTrace();
                return;
            }
        }
        else {
            getDevices();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // FIXME - this is bad
        mContext = inflater.getContext();

        adapter = new DigitalLifeDeviceAdapter(inflater.getContext(), R.layout.action_item, devices);

        setListAdapter(adapter);
        loginDigitalLife();

        progressDialog = new ProgressDialog ( this.getActivity() ) ;
        progressDialog.setCancelable ( false ) ;
        progressDialog.setMessage ( "Retrieving devices..." ) ;
        progressDialog.setTitle ( "Please wait" ) ;
        progressDialog.setIndeterminate ( true ) ;
        progressDialog.show();

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_device_list, null);
        return root;
    }




}