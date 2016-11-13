package com.caoimheharv.msd_assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Bundle;

import java.sql.SQLException;

/**
 * Created by CaoimheHarvey on 10/28/16.
 * Database: BusinessClocking
 * Contains 3 tables which all link up using the staff_no.
 * Here exists the code to create tables, update rows, insert rows, and delete rows.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "BusinessClocking.db";

    public static final String TABLE_1 = "Staff";
    public static final String TABLE_2 = "Shift";
    public static final String TABLE_3 = "Clocked_Shift";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /*
    Code to create the tables in the database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABLE_1 + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "staff_name TEXT, staff_email TEXT, staff_phone TEXT, pin INTEGER, status TEXT)");
        db.execSQL("create table if not exists " + TABLE_2 + " ( _id INTEGER PRIMARY KEY  AUTOINCREMENT," +
                " staff_id INTEGER, start_date TEXT, end_date TEXT, start_time TEXT, end_time TEXT)");
        db.execSQL("create table " + TABLE_3 + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "staff_id INTEGER, start TEXT, end TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_3);
        db.execSQL("DROP TABLE IF EXISTS trial");
        onCreate(db);
    }


    /*
    CODE TO INSERT A NEW ROW INTO THE STAFF TABLE
     */
    public boolean insertStaff(String name, String email, String phone, int pin, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("staff_name", name);
        contentValues.put("staff_email", email);
        contentValues.put("staff_phone", phone);
        contentValues.put("pin", pin);
        contentValues.put("status", status);
        long res = db.insert(TABLE_1, null, contentValues);
        if (res == -1)
            return false;
        else
            return true;
    }

    /*
    CODE TO INSERT A NEW ROW INTO THE SHIFT TABLE
     */
    public boolean insertShift(int staff_no, String start_time, String end_time, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("staff_id", staff_no);
        contentValues.put("end_time", end_time);
        contentValues.put("start_time", start_time);
        contentValues.put("start_date", date);
        long res = db.insert(TABLE_2, null, contentValues);
        if (res == -1)
            return false;
        else
            return true;
    }

    /*
    CODE TO INSERT A NEW ROW INTO THE CLOCKED SHIFT TABLE
     */
    public boolean insertClocked(int staff_no, String start_dt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("staff_id", staff_no);
        contentValues.put("start", start_dt);
        long res = db.insert(TABLE_3, null, contentValues);//
        if (res == -1)
            return false;
        else
            return true;
    }


    /*
    CODE TO UPDATE A ROW IN THE STAFF TABLE
     */
    public boolean updateStaff(String id, String name, String email, String phone, int pin, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("staff_name", name);
        contentValues.put("staff_email", email);
        contentValues.put("staff_phone", phone);
        contentValues.put("status", status);
        contentValues.put("pin", pin);
        db.update(TABLE_1, contentValues, "_id = ?", new String[]{id});
        return true;
    }

    /*
    CODE TO UPDATE A ROW IN THE SHIFT TABLE
     */
    public boolean updateShift(String id, String staff_id, String date, String starttime, String endtime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", id);
        contentValues.put("staff_id", staff_id);
        contentValues.put("start_date", date);
        contentValues.put("start_time", starttime);
        contentValues.put("end_time", endtime);
        db.update(TABLE_2, contentValues, "_id = ?", new String[]{id});
        return true;
    }

    public boolean updateClocked(String id, String end_time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", id);
        contentValues.put("end", end_time);
        db.update(TABLE_3, contentValues, "_id = ?", new String[]{id});
        return true;
    }

    /*
    CODE TO DELETE A ROW
     */
    public Integer deleteData(String table, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(table, "_id = ?", new String[]{id});
    }

    /*
    CODE TO GET SPECIFIC X
    one of the parameters is the entirety of the select statement which is read in
     */

    public Cursor search(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(query, null);//
        return res;
    }

}
