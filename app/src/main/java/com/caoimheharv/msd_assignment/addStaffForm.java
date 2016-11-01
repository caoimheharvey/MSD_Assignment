package com.caoimheharv.msd_assignment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

//TO DO: Read In Pin

public class addStaffForm extends AppCompatActivity {

    DatabaseHelper myDB;

    EditText name, email, pin, phone;
    Button save, cancel;
    Switch status;
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
        status = (Switch) findViewById(R.id.switch1);

        save = (Button) findViewById(R.id.saveContact);
        cancel = (Button) findViewById(R.id.cancelContact);

        //STATUS AS A SWITCH AS TO LESSEN ERROR BY USER INPUT
        status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    status.setText("Admin");
                else
                    status.setText("Standard");
            }
        });

        //CODE IS CHECKED FOR NULL/NOT NULL AND IF ALL CODE REQUIRED IS PRESENT THEN STAFF MEMBER CAN BE SAVED
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty())
                    Toast.makeText(addStaffForm.this, "You need to enter a NAME", Toast.LENGTH_SHORT).show();

                if(email.getText().toString().isEmpty())
                    Toast.makeText(addStaffForm.this, "You need to enter an EMAIL", Toast.LENGTH_SHORT).show();

                if(pin.getText().toString().isEmpty())
                    Toast.makeText(addStaffForm.this, "You need to enter a PIN", Toast.LENGTH_SHORT).show();
                else
                    pinparsed = Integer.parseInt(pin.getText().toString());

                boolean success = myDB.insertStaff(name.getText().toString(), email.getText().toString(),
                        phone.getText().toString(), pinparsed, status.getText().toString());

                if(success)
                    Toast.makeText(addStaffForm.this, "Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(addStaffForm.this, "FAILED", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        //RETURNS USER BACK TO MENU
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
