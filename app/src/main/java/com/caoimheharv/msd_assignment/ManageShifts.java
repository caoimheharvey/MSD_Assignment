package com.caoimheharv.msd_assignment;
/**
 * In this class the admin user can add, update and view shifts.
 * If the user wishes to add a shift they can click on the date they wish to add the shift
 * then clock the add button which re-directs them to a form in which they can add the detail,
 * same applies to the update button.
 * If the user wishes to view they can click on the date they wish to view and all shifts will be
 * displayed for that specific date.
 */
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

public class ManageShifts extends AppCompatActivity {

    DatabaseHelper myDB = new DatabaseHelper(this);

    CalendarView calview;
    Button add;

    ListView listView;

    String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_shifts2);

        calview = (CalendarView) findViewById(R.id.calendarView);
        add = (Button) findViewById(R.id.addShift);

        listView = (ListView) findViewById(R.id.shiftsList);

        //actions for when a date is clicked in the calendar
        calview.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                //displays the selected date as a toast
                Toast.makeText(ManageShifts.this, dayOfMonth + "/" + (month + 1) + "/" + year, Toast.LENGTH_SHORT).show();
                selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;

                try{
                    //gets all the shift details for the selected date
                    //TODO: still buggy, need to improve
                    Cursor res = myDB.search("SELECT shift._id, staff_id, staff_name, start_date, start_time, end_time FROM SHIFT" +
                        " INNER JOIN STAFF ON shift.staff_id = staff._id WHERE start_date = \"" + selectedDate + "\"");

                    //if there are no shifts display message
                    if (res.getCount() == 0) {
                        showMessage("Shifts: " + selectedDate, "No Shifts");
                        return;
                    }

                    ShiftCursorAdapter cursorAdapter = new ShiftCursorAdapter(ManageShifts.this, res);
                    listView.setAdapter(cursorAdapter);
                } catch (Exception e) {
                    Log.e("SELECTING CALENDAR", String.valueOf(e));
                }

            }
        });

        /*
        brings user to add shift class
         */
       add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManageShifts.this, AddShift.class);
                i.putExtra("Date", selectedDate);
                startActivity(i);
            }
        });

        /*
        Sends data to the Shift Adapter to arrange how the data will be displayed on the list
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View view, int position, long arg) {
                Cursor mycursor = (Cursor) av.getItemAtPosition(position);

                Intent i = new Intent(getApplicationContext(), UpdateShift.class);
                //Attaching data to the intent
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

    //Shows alert dialog message, takes in message title and message content
    private void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
