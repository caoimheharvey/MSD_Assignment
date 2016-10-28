package com.caoimheharv.msd_assignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by CaoimheHarvey on 10/28/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "BusinessClocking.db";

    public static final String TABLE_1 = "Staff";
    public static final String TABLE_2 = "Shift";
    public static final String TABLE_3 = "Clocked_Shift";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_1 + " (staff_no INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "staff_name TEXT, staff_email TEXT, staff_phone TEXT, pin INTEGER, status TEXT)");
        db.execSQL("create table " + TABLE_2 + " (staff_no INTEGER PRIMARY KEY, " +
                "start_date TEXT, end_date TEXT, start_time TEXT, end_time TEXT)");
        db.execSQL("create table " + TABLE_3 + " (staff_no INTEGER PRIMARY KEY, " +
                "date_in TEXT, date_out TEXT, time_in TEXT, time_out TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_3);
        onCreate(db);
    }
}
