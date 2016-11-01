package com.caoimheharv.msd_assignment;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class updateStaff extends AppCompatActivity {

    DatabaseHelper myDB;

    Button update, cancel, populate, delete;

    EditText name, email, phone, status, pin, staff_id;

    boolean clicked;

    int staff_no, intPin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_staff);

        myDB = new DatabaseHelper(this);

        update = (Button) findViewById(R.id.updateBtn);
        cancel = (Button) findViewById(R.id.cancelUpdate);
        populate = (Button) findViewById(R.id.popButton);
        delete = (Button) findViewById(R.id.delButton);

        staff_id = (EditText) findViewById(R.id.getId);
        name = (EditText) findViewById(R.id.showName);
        email = (EditText) findViewById(R.id.showMail);
        status = (EditText) findViewById(R.id.showStatus);
        phone = (EditText) findViewById(R.id.showNum);
        pin = (EditText) findViewById(R.id.showPin);

        clicked = false;


        populate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (staff_id.getText().toString().isEmpty())
                    Toast.makeText(updateStaff.this, "Please Enter STAFF_ID", Toast.LENGTH_SHORT);
                else
                    staff_no = Integer.parseInt(staff_id.getText().toString());
                Cursor res = myDB.getStaff("Staff", staff_no);

                if (res.getCount() == 0) {
                    // show message
                    //showMessage("Error", "Nothing found");
                    return;
                }

                while (res.moveToNext())
                {
                    name.setText(res.getString(1));
                    email.setText(res.getString(2));
                    phone.setText(res.getString(3));
                    pin.setText(res.getString(4));
                    status.setText(res.getString(5));
                }

                clicked = true;
            }
        });


            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intPin = Integer.parseInt(pin.getText().toString());
                    boolean isUpdate = myDB.updateStaff(staff_id.getText().toString(), name.getText().toString(),
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
                if (staff_id.getText().toString().isEmpty())
                    Toast.makeText(updateStaff.this, "Please Enter STAFF_ID", Toast.LENGTH_SHORT);
                else
                    staff_no = Integer.parseInt(staff_id.getText().toString());

                Integer deletedRows = myDB.deleteData("Staff", staff_id.getText().toString());
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

    }

    private void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
