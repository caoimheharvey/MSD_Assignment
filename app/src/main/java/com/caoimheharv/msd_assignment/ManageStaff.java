package com.caoimheharv.msd_assignment;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManageStaff extends AppCompatActivity {

    DatabaseHelper myDB;

    Button add, update, viewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_staff);

        myDB = new DatabaseHelper(this);

        add = (Button) findViewById(R.id.addBtn);
        update = (Button) findViewById(R.id.updateBtn);
        viewAll = (Button) findViewById(R.id.viewStaff);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManageStaff.this, addStaffForm.class);
                startActivity(i);
            }
        });

        //ALLOWS ADMIN TO VIEW ALL STAFF CURRENTLY IN SYSTEM
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDB.getAllData("Staff");

                if (res.getCount() == 0) {
                    // show message
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Staff No:" + res.getString(0) + "\n");
                    buffer.append("Name:" + res.getString(1) + "\n");
                    buffer.append("Email:" + res.getString(2) + "\n");
                    buffer.append("Phone:" + res.getString(3) + "\n");
                    buffer.append("Pin:" + res.getString(4) + "\n");
                    buffer.append("Status:" + res.getString(5) + "\n\n");
                }

                // Show all data
                showMessage("Staff Members", buffer.toString());

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManageStaff.this, updateStaff.class);
                startActivity(i);
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
