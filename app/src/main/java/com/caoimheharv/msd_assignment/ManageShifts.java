package com.caoimheharv.msd_assignment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

public class ManageShifts extends AppCompatActivity {

    DatabaseHelper myDB;

    CalendarView calview;
    Button add;

    ListView listView;

    String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_shifts2);

        myDB = new DatabaseHelper(this);

        calview = (CalendarView) findViewById(R.id.calendarView);
        add = (Button) findViewById(R.id.addShift);

        listView = (ListView) findViewById(R.id.shiftsList);


        calview.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(ManageShifts.this, dayOfMonth + "/" + (month + 1) + "/" + year, Toast.LENGTH_SHORT).show();
                selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;

                //NOT WORKING
                Cursor res = myDB.search("SELECT shift._id, staff_id, staff_name, start_date, start_time, end_time FROM SHIFT" +
                        " INNER JOIN STAFF ON shift.staff_id = staff._id");//

                if (res.getCount() == 0) {
                    // show message
                    showMessage("Shifts: " + selectedDate, "No Shifts");
                    return;
                }
                try {
                    ShiftCursorAdapter cursorAdapter = new ShiftCursorAdapter(ManageShifts.this, res);
                    listView.setAdapter(cursorAdapter);
                } catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Internal Failure", Toast.LENGTH_SHORT).show();
                }

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManageShifts.this, addShift.class);
                i.putExtra("Date", selectedDate);
                startActivity(i);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View view, int position, long arg) {
                Cursor mycursor = (Cursor) av.getItemAtPosition(position);

                Intent i = new Intent(getApplicationContext(), updateShift.class);
                i.putExtra("ROWID", mycursor.getString(0));
                i.putExtra("ID", mycursor.getString(1));
                i.putExtra("NAME", mycursor.getString(2));
                i.putExtra("DATE", mycursor.getString(3));
                i.putExtra("STARTTIME", mycursor.getString(4));
                i.putExtra("ENDTIME", mycursor.getString(5));
                startActivity(i);

            }
        });
    }

    private void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
