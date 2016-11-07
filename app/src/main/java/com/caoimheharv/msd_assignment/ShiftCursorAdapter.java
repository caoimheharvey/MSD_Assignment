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
 */
public class ShiftCursorAdapter extends CursorAdapter {
    public ShiftCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.shift_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView name_num = (TextView) view.findViewById(R.id.nameTV);
        TextView date = (TextView) view.findViewById(R.id.dateTV);
        TextView times = (TextView) view.findViewById(R.id.timesTV);

        String name, id, starttime, endtime, day;
        id = cursor.getString(1);
        name = cursor.getString(2);
        day = cursor.getString(3);
        starttime = cursor.getString(4);
        endtime = cursor.getString(5);

        name_num.setText(name + " (" + id + ")");
        date.setText(day);
        times.setText(starttime + "-" + endtime);
    }
}
