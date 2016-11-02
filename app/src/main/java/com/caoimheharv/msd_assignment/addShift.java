package com.caoimheharv.msd_assignment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class addShift extends AppCompatActivity {

    Button selectSTime, selectETime, selectSDate, selectEDate;
    TextView viewStartTime, viewEndTime, viewSDate, viewEDate;

    int hour_x, minute_x;
    static final int DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shift);


        viewStartTime = (TextView) findViewById(R.id.StartTime);
        selectSTime = (Button) findViewById(R.id.selectST);

        selectSTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
    }


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
            if(minute_x > 9)
                viewStartTime.setText(hour_x + ":" + minute_x);
            else
                viewStartTime.setText(hour_x + ":0" + minute_x);
        }
    };
}
