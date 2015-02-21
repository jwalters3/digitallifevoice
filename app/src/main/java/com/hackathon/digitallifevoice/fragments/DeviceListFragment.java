package com.hackathon.digitallifevoice.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.hackathon.digitallifevoice.R;
import com.hackathon.digitallifevoice.adapters.ActionsAdapter;
import com.hackathon.digitallifevoice.adapters.DigitalLifeDeviceAdapter;
import com.hackathon.digitallifevoice.api.DigitalLifeController;
import com.hackathon.digitallifevoice.api.DigitalLifeDevice;
import com.hackathon.digitallifevoice.data.Action;
import com.hackathon.digitallifevoice.data.DatabaseHandler;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SWBRADSH on 2/21/2015.
 */
public class DeviceListFragment extends ListFragment implements DigitalLifeController.OnLoginListener {

    AlertDialog.Builder deleteContact;
    DigitalLifeDeviceAdapter adapter;


    private List<DigitalLifeDevice> devices = new ArrayList<DigitalLifeDevice>();
    private DigitalLifeController dlc;

    public String getAppId() { return PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("appid", "OE_69B642D383971614_1"); }
    public String getUserId() { return PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("username", "553474450");    }
    public String getPassword() { return PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("password", "NO-PASSWD");    }
    public String getRequestToken() { return PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("requesttoken", "");    }
    public String getAuthToken() { return PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("authtoken", "");    }

    public void saveAuthToken(String token) {

    }
    Context mContext;
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        //Action action = (Action)adapter.getItem(position);
    }

    public void onLoginFailure(String message) {
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
        toast.show();
    }

    public void onLogin(JSONObject data) {
        Toast toast = Toast.makeText(getActivity(), "Logged in", Toast.LENGTH_LONG);
        toast.show();
    }

    public void onDeviceRefresh(JSONObject data) {
        if (adapter != null) {
            devices = dlc.fetchDevices();
            adapter = new DigitalLifeDeviceAdapter(mContext, R.layout.action_item, devices);

            setListAdapter(adapter);
            //adapter.updateItems(devices);
        }
    }

    private void getDevices() {

    }
    private void loginDigitalLife() {
        dlc = DigitalLifeController.getInstance();
        dlc.init(getAppId(), "https://systest.digitallife.att.com");
        if ((this.getRequestToken().isEmpty()) || (this.getAuthToken().isEmpty())) {
            try {
                dlc.login(getUserId(), getPassword(), this);
            } catch (Exception e) {
                System.out.println("Logout Failed");
                e.printStackTrace();
                return;
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // FIXME - this is bad
        mContext = inflater.getContext();

        adapter = new DigitalLifeDeviceAdapter(inflater.getContext(), R.layout.action_item, devices);

        setListAdapter(adapter);
        //setupDigitalLife();

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_device_list, null);
        return root;
    }




}