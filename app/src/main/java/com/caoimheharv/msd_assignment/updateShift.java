package com.caoimheharv.msd_assignment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Updates an existing shift's details for a particular user by clicking on the list item
 * for the selected shift
 */
public class UpdateShift extends AppCompatActivity {

    DatabaseHelper myDB = new DatabaseHelper(this);

    private Button update, cancel, delete, get_time_start, get_time_end;
    private TextView view_Start, view_End, name;

    //stores data passed through intent
    String row_id, get_id, get_name, get_date, get_start, get_end;

    int hour_x, minute_x;
    int check;
    static final int DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_shift);

        update = (Button) findViewById(R.id.saveupdate);
        cancel = (Button) findViewById(R.id.cancel_button);
        delete = (Button) findViewById(R.id.delBtn);
        get_time_start = (Button) findViewById(R.id.chgeSTbtn);
        get_time_end = (Button) findViewById(R.id.chngETBtn);

        name = (TextView) findViewById(R.id.up_name_tv);
        view_Start = (TextView) findViewById(R.id.newSTtv);
        view_End = (TextView) findViewById(R.id.newETtv);

        /**
         * getting from bundle from intent
         */
        row_id = getIntent().getExtras().getString("ROWID");
        get_id = getIntent().getExtras().getString("ID");
        get_name = getIntent().getExtras().getString("NAME");
        get_date = getIntent().getExtras().getString("DATE");
        get_start = getIntent().getExtras().getString("STARTTIME");
        get_end = getIntent().getExtras().getString("ENDTIME");

        //fills in with existing data
        place();

        /*
        Updates the existing row with the new details and outputs success
         */
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    boolean isUpdate = myDB.updateShift(row_id,get_id, get_date, view_Start.getText().toString(), view_End.getText().toString());
                    if (isUpdate)
                        Toast.makeText(getApplicationContext(), "Data Updated", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "UPDATE FAILED", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

        /*
        Canceling actions and returning to manage shift class
         */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*
        Deleting a shift from the database
         */
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDB.deleteData("Shift", get_id);
                if(deletedRows > 0)
                    Toast.makeText(getApplicationContext(), get_name + " removed from Shift on " + get_date,Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"ERROR: Could not delete",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        /*
        Adding the start time
         */
        get_time_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = 1;
                showDialog(DIALOG_ID);

            }
        });

        /*
        ADDING END TIME
         */
        get_time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = 2;
                showDialog(DIALOG_ID);
            }
        });

    }

    /*
    Displays original times and date for a particular user
     */
    private void place() {
        name.setText(get_name + " (" + get_id + ")");
        view_Start.setText(get_start);
        view_End.setText(get_end);
    }

    /*
    TIME PICKER CODE
    */
    protected Dialog onCreateDialog(int id)
    {
        if(id == DIALOG_ID) {
            return new TimePickerDialog(UpdateShift.this, timePickerListener, hour_x, minute_x, true);
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
                    view_Start.setText(hour_x + ":" + minute_x);
                else
                    view_Start.setText(hour_x + ":0" + minute_x);
            }
            else if (check == 2){
                if(minute_x > 9)
                    view_End.setText(hour_x + ":" + minute_x);
                else
                    view_End.setText(hour_x + ":0" + minute_x);
            }//end ifelse
        }
    };
}
