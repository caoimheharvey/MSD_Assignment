package com.caoimheharv.msd_assignment;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
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

    String selectedDate;

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
                selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
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

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDB.getAllData("Shift");

                if (res.getCount() == 0) {
                    // show message
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Staff No:" + res.getString(0) + "\n");
                    //buffer.append("Name:" + res.getString(1) + "\n");
                    buffer.append("Date:" + res.getString(1) + "\n");
                    buffer.append( res.getString(3) + " - " + res.getString(4) + "\n\n");
                }

                // Show all data
                showMessage("Staff Members", buffer.toString());
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
