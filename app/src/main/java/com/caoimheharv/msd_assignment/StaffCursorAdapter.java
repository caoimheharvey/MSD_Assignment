package com.caoimheharv.msd_assignment;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by CaoimheHarvey on 11/4/16.
 *
 * Used to view staff in list view.
 */
public class StaffCursorAdapter extends CursorAdapter {
    public StaffCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //formated based on Staff_Row XML file
        return LayoutInflater.from(context).inflate(R.layout.staff_row, parent, false);
    }

    /*
    Populates the XML fields with the correct cursor fields and outputs them to the user
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView name_num = (TextView) view.findViewById(R.id.name_num_TV);
        TextView email = (TextView) view.findViewById(R.id.email_tv);
        TextView phone = (TextView) view.findViewById(R.id.phone_tv);
        TextView pin = (TextView) view.findViewById(R.id.pin_tv);
        TextView status = (TextView) view.findViewById(R.id.status_tv);


        String name, id, mail, num, passcode, stat;
        id = cursor.getString(0);
        name = cursor.getString(1);
        mail = cursor.getString(2);
        num = cursor.getString(3);
        passcode = cursor.getString(4);
        stat = cursor.getString(5);

        name_num.setText(name + " (" + id + ")");
        email.setText(mail);
        phone.setText(num);
        pin.setText(passcode);
        status.setText(stat);

    }
}
