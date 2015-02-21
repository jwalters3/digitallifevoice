package com.hackathon.digitallifevoice.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hackathon.digitallifevoice.R;
import com.hackathon.digitallifevoice.api.DigitalLifeDevice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by SWBRADSH on 2/21/2015.
 */
public class DigitalLifeDeviceAdapter extends ArrayAdapter<DigitalLifeDevice> {

    // declaring our ArrayList of items
    private List<DigitalLifeDevice> objects;

    /* here we must override the constructor for ArrayAdapter
    * the only variable we care about now is ArrayList<Item> objects,
    * because it is the list of objects we want to display.
    */
    public DigitalLifeDeviceAdapter(Context context, int textViewResourceId, ArrayList<DigitalLifeDevice> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }

    public DigitalLifeDeviceAdapter(Context context, int textViewResourceId, List<DigitalLifeDevice> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }

    public void updateItems(List<DigitalLifeDevice> objects)
    {
        this.objects = objects;
        this.notifyDataSetChanged();
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
            v = inflater.inflate(R.layout.device_item, null);
        }

			/*
			 * Recall that the variable position is sent in as an argument to this method.
			 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
			 * iterates through the list we sent it)
			 *
			 * Therefore, i refers to the current Item object.
			 */
        DigitalLifeDevice i = objects.get(position);

        if (i != null) {

            TextView firstLine = (TextView) v.findViewById(R.id.firstLine);
            TextView secondLine = (TextView) v.findViewById(R.id.secondLine);
            ImageView icon = (ImageView) v.findViewById(R.id.icon);


            if (secondLine != null){
                secondLine.setText(i.getName());
            }
            if (firstLine != null){
                firstLine.setText(i.getStatus());
            }
            if (i.getResourceID() > 0) {
                icon.setImageResource(i.getResourceID());
            }

        }

        // the view must be returned to our activity
        return v;

    }

}
