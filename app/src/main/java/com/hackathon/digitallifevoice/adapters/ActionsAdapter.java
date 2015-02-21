package com.hackathon.digitallifevoice.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hackathon.digitallifevoice.R;
import com.hackathon.digitallifevoice.data.Action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by SWBRADSH on 2/20/2015.
 */
public class ActionsAdapter  extends ArrayAdapter<Action> {

    // declaring our ArrayList of items
    private List<Action> objects;

    /* here we must override the constructor for ArrayAdapter
    * the only variable we care about now is ArrayList<Item> objects,
    * because it is the list of objects we want to display.
    */
    public ActionsAdapter(Context context, int textViewResourceId, ArrayList<Action> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }

    public ActionsAdapter(Context context, int textViewResourceId, List<Action> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }

    /*
     * we are overriding the getView method here - this is what defines how each
     * list item will look.
     */
    public View getView(int position, View convertView, ViewGroup parent){

        // assign the view we are converting to a local variable
        View v = convertView;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.US);

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.action_item, null);
        }

			/*
			 * Recall that the variable position is sent in as an argument to this method.
			 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
			 * iterates through the list we sent it)
			 *
			 * Therefore, i refers to the current Item object.
			 */
        Action i = objects.get(position);

        if (i != null) {

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.

            TextView voiceaction = (TextView) v.findViewById(R.id.voiceaction);
            TextView deviceId = (TextView) v.findViewById(R.id.deviceName);
            ImageView icon = (ImageView) v.findViewById(R.id.icon);


            if (voiceaction != null){
                voiceaction.setText("\"" + i.getVoiceCommand()+ "\"");
            }
            if (deviceId != null){
                deviceId.setText(i.getDeviceType() + " - " + i.getOperation());
            }

            if (icon != null){
                if (i.getDeviceType().equals("Door Lock")) {
                    icon.setImageResource(R.drawable.device_list_icon_lock);
                }
                if (i.getDeviceType().equals("Power Switch")) {
                    icon.setImageResource(R.drawable.device_list_icon_plug);
                }
            }

        }

        // the view must be returned to our activity
        return v;

    }

}
