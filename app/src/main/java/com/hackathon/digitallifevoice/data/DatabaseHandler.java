package com.hackathon.digitallifevoice.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by SWBRADSH on 2/20/2015.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "digitallifevoice";

    // Contacts table name
    private static final String TABLE_ACTIONS = "actions";


    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_VOICECOMMAND = "voicecommand";
    private static final String KEY_DEVICETYPE= "devicetype";
    private static final String KEY_DEVICEGUID= "deviceguid";
    private static final String KEY_LABEL= "label";
    private static final String KEY_OPERATION= "operation";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ACTIONS_TABLE = "CREATE TABLE " + TABLE_ACTIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement,"
                + KEY_VOICECOMMAND + " TEXT,"
                + KEY_DEVICETYPE + " TEXT,"
                + KEY_DEVICEGUID + " TEXT,"
                + KEY_LABEL + " TEXT,"
                + KEY_OPERATION + " TEXT" + ")";
        db.execSQL(CREATE_ACTIONS_TABLE);


    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void wipeData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIONS);
        // Create tables again
        onCreate(db);
    }

    private String dateToSqliteDateString(Date date) {
        // The format is the same as CURRENT_TIMESTAMP: "YYYY-MM-DD HH:MM:SS"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date != null) {
            return sdf.format(date);
        }
        return null;
    }

    private Date SqliteDateStringToDate(String stringTime) {

        SimpleDateFormat formatter;
        Date time = null;
        if (stringTime == null) return null;
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        try {
            time =  (Date) formatter.parse(stringTime);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return time;
    }


    // Adding new action
    public void addAction(Action action) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_VOICECOMMAND, action.getVoiceCommand());
        values.put(KEY_DEVICETYPE, action.getDeviceType());
        values.put(KEY_DEVICEGUID, action.getDeviceGuid());
        values.put(KEY_LABEL, action.getLabel());
        values.put(KEY_OPERATION, action.getOperation());


        // Inserting Row
        long row  = db.insert(TABLE_ACTIONS, null, values);
        action.setId(row);
        db.close(); // Closing database connection

    }

    // Getting single action
    public Action getAction(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ACTIONS, new String[] { KEY_ID,
                        KEY_VOICECOMMAND, KEY_DEVICETYPE, KEY_DEVICEGUID, KEY_LABEL, KEY_OPERATION }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Action action = new Action();
        action.setId(id);
        action.setVoiceCommand(cursor.getString(1));
        action.setDeviceType(cursor.getString(2));
        action.setDeviceGuid(cursor.getString(3));
        action.setLabel(cursor.getString(4));
        action.setOperation(cursor.getString(5));

        db.close();
        return action;


    }

    // Getting All Contacts
    public List<Action> getAllActions() {
        List<Action> actionList = new ArrayList<Action>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ACTIONS ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Action action = new Action();
                action.setId(Integer.parseInt(cursor.getString(0)));
                action.setVoiceCommand(cursor.getString(1));
                action.setDeviceType(cursor.getString(2));
                action.setDeviceGuid(cursor.getString(3));
                action.setLabel(cursor.getString(4));
                action.setOperation(cursor.getString(5));

                // Adding contact to list
                actionList.add(action);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return contact list
        return actionList;


    }

    // Getting actions Count
    public int getActionsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ACTIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close();
        // return count
        return cursor.getCount();

    }
    // Updating single contact
    public int updateAction(Action action) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(KEY_VOICECOMMAND, action.getVoiceCommand());
        values.put(KEY_DEVICETYPE, action.getDeviceType());
        values.put(KEY_DEVICEGUID, action.getDeviceGuid());
        values.put(KEY_LABEL, action.getLabel());
        values.put(KEY_OPERATION, action.getOperation());


        // updating row
        return db.update(TABLE_ACTIONS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(action.getId()) });

    }

    // Deleting single action
    public void deleteAction(Action action) {


        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ACTIONS, KEY_ID + " = ?",
                new String[] { String.valueOf(action.getId()) });
        db.close();

    }


}

