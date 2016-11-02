package com.caoimheharv.msd_assignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

public class ManageShifts extends AppCompatActivity {

    DatabaseHelper myDB;

    CalendarView calview;

    Button view, add, edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_shifts2);

        myDB = new DatabaseHelper(this);

        calview = (CalendarView) findViewById(R.id.calendarView);
        view = (Button) findViewById(R.id.viewToday);
        add = (Button) findViewById(R.id.addShift);
        edit = (Button) findViewById(R.id.editShifts);


        calview.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(ManageShifts.this, dayOfMonth + "/" + (month + 1) + "/" + year, Toast.LENGTH_SHORT).show();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManageShifts.this, addShift.class);
                startActivity(i);
            }
        });
    }
}
