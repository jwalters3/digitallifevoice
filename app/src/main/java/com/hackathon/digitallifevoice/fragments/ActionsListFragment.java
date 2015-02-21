package com.hackathon.digitallifevoice.fragments;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.gc.materialdesign.views.ButtonFloat;
import com.hackathon.digitallifevoice.EditActivity;
import com.hackathon.digitallifevoice.R;
import com.hackathon.digitallifevoice.adapters.ActionsAdapter;
import com.hackathon.digitallifevoice.data.Action;
import com.hackathon.digitallifevoice.data.DatabaseHandler;

public class ActionsListFragment extends ListFragment  {

    AlertDialog.Builder deleteContact;
    ActionsAdapter adapter;
    int ADD_DEVICE =1;

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Action action = (Action)adapter.getItem(position);
        deleteItemPrompt(action);
    }

    public void addItem(Action a) {
        adapter.add(a);
        adapter.notifyDataSetChanged();
    }


    private void deleteItemPrompt(final Action action)
    {

        deleteContact = new AlertDialog.Builder(getActivity())
                .setTitle("Remove Action?")
                .setMessage("Remove this action?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHandler db = new DatabaseHandler(getActivity());
                        db.deleteAction(action);
                        adapter.remove(action);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false);
        deleteContact.show();
    }
    void showEdit() {
        Intent myIntent = new Intent(getActivity(), EditActivity.class);
        getActivity().startActivityForResult(myIntent, this.ADD_DEVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        DatabaseHandler db = new DatabaseHandler(inflater.getContext());
        List<Action> actions = db.getAllActions();

        adapter = new ActionsAdapter(inflater.getContext(), R.layout.action_item, actions);

        setListAdapter(adapter);
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_actions_list, null);
        ButtonFloat addButton = (ButtonFloat) root.findViewById(R.id.buttonFloat);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEdit();
            }
        });
        return root;
    }




}