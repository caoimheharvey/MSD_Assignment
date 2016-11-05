package com.caoimheharv.msd_assignment;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

/**
 * Concered with updating the information in a specific shift_row, or deleting a specific shift_row
 */

public class updateStaff extends AppCompatActivity {

    DatabaseHelper myDB;

    private Button update, cancel, delete;

    private EditText name, email, phone, pin;

    private Switch status;

    String getID, get_name, get_mail, get_phone, get_pin, get_status;
    int staff_no, intPin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_staff);

        myDB = new DatabaseHelper(this);

        update = (Button) findViewById(R.id.updateBtn);
        cancel = (Button) findViewById(R.id.cancelUpdate);
        delete = (Button) findViewById(R.id.delButton);

        name = (EditText) findViewById(R.id.showName);
        email = (EditText) findViewById(R.id.showMail);
        status = (Switch) findViewById(R.id.showStatus);
        phone = (EditText) findViewById(R.id.showNum);
        pin = (EditText) findViewById(R.id.showPin);

        getID = getIntent().getExtras().getString("ID");
        get_name = getIntent().getExtras().getString("NAME");
        get_mail = getIntent().getExtras().getString("EMAIL");
        get_phone = getIntent().getExtras().getString("PHONE");
        get_pin = getIntent().getExtras().getString("PIN");
        get_status = getIntent().getExtras().getString("STATUS");

        place();

        update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    intPin = Integer.parseInt(pin.getText().toString());
                    boolean isUpdate = myDB.updateStaff(getID, name.getText().toString(),
                            email.getText().toString(), phone.getText().toString(), intPin, status.getText().toString());
                    if (isUpdate)
                        Toast.makeText(updateStaff.this, "Data Updated", Toast.LENGTH_LONG).show();

                    finish();
                }
        });

        /*
        DELETING A STAFF MEMBER
         */
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staff_no = Integer.parseInt(getID);

                Integer deletedRows = myDB.deleteData("Staff", getID);
                if(deletedRows > 0)
                    Toast.makeText(updateStaff.this,"Staff Member Removed",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(updateStaff.this,"ERROR: Could not delete",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        /*
        CANCEL ALL ACTIONS
         */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    status.setText("Admin");
                else
                    status.setText("Standard");
            }
        });
    }

    private void place()
    {
        name.setText(get_name);
        email.setText(get_mail);
        phone.setText(get_phone);
        pin.setText(get_pin); //needs to be a string

        status.setText(get_status);
        if(get_status.equals("Admin"))
            status.setChecked(true);
    }
}