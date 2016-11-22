package com.caoimheharv.msd_assignment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
/**
 * Class is used to add a shift to the database for the admin user to view
 */
public class AddShift extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);

    Button selectSTime, selectETime, save, cancel;
    TextView viewStartTime, viewEndTime, dateDisp;
    String staff_no;

    //variables to get date and time for the shift
    int hour_x, minute_x;

    //used to toggle between clock in and clock out input
    int check;
    static final int DIALOG_ID = 0;

    // Spinner to eliminate ambiguity and input error when selecting an employee to add a shift for
    Spinner spinner;
    //stores names from database so they can be output in the spinner
    ArrayList<String> names = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shift);

        selectSTime = (Button) findViewById(R.id.selectST);
        selectETime = (Button) findViewById(R.id.getET);
        save = (Button) findViewById(R.id.saveShift);
        cancel = (Button) findViewById(R.id.cnclShift);

        viewStartTime = (TextView) findViewById(R.id.StartTime);
        viewEndTime = (TextView) findViewById(R.id.EndTime);
        dateDisp = (TextView) findViewById(R.id.dateDisp);

        /**
         * Adapter is set to display the fist array list item by default
         */
        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);

        //displays all names of all employees
        displayNames();

        /*
        GETTING DATE FROM INTENT
         */
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String date = extras.getString("Date");
            dateDisp.setText(date);
        }

        /*
        ADDING START TIME
         */
        selectSTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = 1;
                showDialog(DIALOG_ID);

            }
        });

        /*
        ADDING END TIME
         */
        selectETime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = 2;
                showDialog(DIALOG_ID);
            }
        });


        //canceling actions returning to manage shift class
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //saving the new shift data
        //TODO: ADD ERROR CHECKING HERE FOR WHEN FIELDS ARE EMPTY
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //gets the name of the person selected in spinner
                    String name = spinner.getSelectedItem().toString();
                    //gets the ID for selected person
                    Cursor c = db.search("SELECT _id FROM STAFF WHERE staff_name = \"" + name + "\"");
                    while (c.moveToNext()) {
                        staff_no = c.getString(0);
                    }
                    //checking validity of the input to ensure no fields are empty
                    if(viewEndTime.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter an END time", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(viewStartTime.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter a START time", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //inserts a new shift for selected user and displays success, returns to ManageShift class
                    boolean s = db.insertShift(Integer.parseInt(staff_no),
                            viewStartTime.getText().toString(), viewEndTime.getText().toString(),
                            dateDisp.getText().toString());
                    if (s)
                        Toast.makeText(AddShift.this, "Inserted", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(AddShift.this, "FAILED", Toast.LENGTH_LONG).show();
                    finish();
                } catch (Exception e) {
                    Log.e("Insert Error", String.valueOf(e));
                }
            }
        });

    }

    /*
    Used to create dialog to select the start and end time
     */
    protected Dialog onCreateDialog(int id)
    {
        if(id == DIALOG_ID) {
            return new TimePickerDialog(AddShift.this, timePickerListener, hour_x, minute_x, true);
        }
        return null;
    }

    /*
    checking which button was selected (in and out time) then outputting the appropiate time to the user
     */
    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour_x = hourOfDay;
            minute_x = minute;

            //adding a 0 where the minute is less than 10
            if(check == 1){
                if(minute_x > 9)
                    viewStartTime.setText(hour_x + ":" + minute_x);
                else
                    viewStartTime.setText(hour_x + ":0" + minute_x);
            }
            else if (check == 2){
                if(minute_x > 9)
                    viewEndTime.setText(hour_x + ":" + minute_x);
                else
                    viewEndTime.setText(hour_x + ":0" + minute_x);
            }//end ifelse

        }
    };

    /*
    Displaying all names into the spinner
     */
    private void displayNames()
    {
        Cursor c = db.search("Select staff_name FROM STAFF");
        while (c.moveToNext())
        {
            names.add(c.getString(0));
        }
        spinner.setAdapter(adapter);
    }
}//end addShift