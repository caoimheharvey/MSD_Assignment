package com.caoimheharv.msd_assignment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//TO DO: Read In Pin

public class addStaffForm extends AppCompatActivity {

    DatabaseHelper myDB;

    EditText name, email, pin, status, phone;
    Button save, cancel;
    int pinparsed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff_form);

        myDB = new DatabaseHelper(this);

        name = (EditText) findViewById(R.id.enterName);
        email = (EditText) findViewById(R.id.enterMail);
        phone = (EditText) findViewById(R.id.enterPhone);
        pin = (EditText) findViewById(R.id.enterPin);
        status = (EditText) findViewById(R.id.enterStatus);

        save = (Button) findViewById(R.id.saveContact);
        cancel = (Button) findViewById(R.id.cancelContact);

        //Code to execute when Save button is clicked
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty())
                    Toast.makeText(addStaffForm.this, "You need to enter a NAME", Toast.LENGTH_SHORT).show();

                if(email.getText().toString().isEmpty())
                    Toast.makeText(addStaffForm.this, "You need to enter an EMAIL", Toast.LENGTH_SHORT).show();

                if(status.getText().toString().isEmpty())
                    status.setText("Standard");

                if(pin.getText().toString().isEmpty())
                    Toast.makeText(addStaffForm.this, "You need to enter a PIN", Toast.LENGTH_SHORT).show();
                else
                    pinparsed = Integer.parseInt(pin.getText().toString());


                boolean success = myDB.insert("Staff", name.getText().toString(), email.getText().toString(),
                        phone.getText().toString(), pinparsed, status.getText().toString());

                if(success)
                    Toast.makeText(addStaffForm.this, "Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(addStaffForm.this, "FAILED", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        //Code to execute when Cancel button is clicked
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
