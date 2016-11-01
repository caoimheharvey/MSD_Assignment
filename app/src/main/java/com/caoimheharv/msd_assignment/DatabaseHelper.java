package com.caoimheharv.msd_assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
        //SQLiteDatabase db = this.getWritableDatabase();

    }

    /*
    Code to create the tables in the database
     */
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


    /*
    CODE TO INSERT A NEW ROW INTO TABLE
     */
    public boolean insert(String TABLE_NAME, String name, String email, String phone, int pin, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("staff_name", name);
        contentValues.put("staff_email", email);
        contentValues.put("staff_phone", phone);
        contentValues.put("pin", pin);
        contentValues.put("status", status);
        long res = db.insert(TABLE_NAME, null, contentValues);
        if(res == -1)
            return false;
        else
            return true;
    }

    /*
    CODE TO UPDATE A ROW
     */
    public boolean updateData(String table, String id, String name, String email, String phone, int pin, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("staff_name",name);
        contentValues.put("staff_email", email);
        contentValues.put("staff_phone", phone);
        contentValues.put("status", status);
        contentValues.put("pin",pin);
        db.update(table, contentValues, "staff_no = ?",new String[] { id });
        return true;
    }

    /*
    CODE TO DELETE A ROW
     */

    public Integer deleteData (String table, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(table, "staff_no = ?",new String[] {id});
    }

    /*
    CODE TO GET SPECIFIC STAFF BY STAFF_NO
     */

    public Cursor getStaff(String table, int id_no)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + table + " WHERE " + id_no + " = STAFF_NO", null);
        return res;
    }

    /*
    CODE TO VIEW ALL
     */

    public Cursor getAllData(String table)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ table ,null);
        return res;
    }

}
