package com.caoimheharv.msd_assignment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class addShift extends AppCompatActivity {

    DatabaseHelper myDB;

    Button selectSTime, selectETime, save, cancel;
    TextView viewStartTime, viewEndTime, dateDisp;
    EditText staff_no;

    int hour_x, minute_x;
    int check;
    static final int DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shift);

        myDB = new DatabaseHelper(this);

        selectSTime = (Button) findViewById(R.id.selectST);
        selectETime = (Button) findViewById(R.id.getET);
        save = (Button) findViewById(R.id.saveShift);
        cancel = (Button) findViewById(R.id.cnclShift);

        viewStartTime = (TextView) findViewById(R.id.StartTime);
        viewEndTime = (TextView) findViewById(R.id.EndTime);
        dateDisp = (TextView) findViewById(R.id.dateDisp);

        staff_no = (EditText) findViewById(R.id.getStaffNo);

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


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: ADD CODE TO CHECK IF STAFF_NO IS EMPTY
                //TODO: find way to
                boolean s = myDB.insertShift(Integer.parseInt(staff_no.getText().toString()),
                        viewStartTime.getText().toString(), viewEndTime.getText().toString(),
                        dateDisp.getText().toString());
                if(s)
                    Toast.makeText(addShift.this, "Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(addShift.this, "FAILED", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

    /*
    TIME PICKER CODE
     */
    protected Dialog onCreateDialog(int id)
    {
        if(id == DIALOG_ID) {
            return new TimePickerDialog(addShift.this, timePickerListener, hour_x, minute_x, true);
        }
        return null;
    }

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
}//end addShift
