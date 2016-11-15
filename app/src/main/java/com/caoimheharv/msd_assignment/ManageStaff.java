package com.caoimheharv.msd_assignment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

/**
 * Allows admin to select from 3 different actions.
 * 1. Viewing all staff members and their details which exist in the database without leaving the page
 * 2. Adding a new Staff member
 * 3. Editing existing staff members
 */
public class ManageStaff extends AppCompatActivity {

    DatabaseHelper db;

    Button add, update;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_staff);

        db = new DatabaseHelper(this);

        add = (Button) findViewById(R.id.addBtn);
        update = (Button) findViewById(R.id.updateBtn);
        listView = (ListView) findViewById(R.id.stafflist);

        displayStaff();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = (LayoutInflater.from(getApplicationContext())).inflate(R.layout.activity_add_staff_form, null);

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getApplicationContext());
                alertBuilder.setView(view);

                //from add staff form XML
                final EditText name = (EditText) view.findViewById(R.id.addName);
                final EditText email = (EditText) view.findViewById(R.id.addEmail);
                final EditText phone = (EditText) view.findViewById(R.id.addNumber);
                final EditText pin = (EditText) view.findViewById(R.id.addPin);
                final Switch status = (Switch) view.findViewById(R.id.statusSwitch);

                status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                            status.setText("Admin");
                        else
                            status.setText("Standard");
                    }
                });

                alertBuilder.setCancelable(true)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.insertStaff(name.getText().toString(),email.getText().toString(),phone.getText().toString(),
                                        Integer.parseInt(pin.getText().toString()),status.getText().toString());//

                                //Cursor
                                Cursor res = db.search("SELECT * FROM staff");
                                if (res.getCount() == 0) {
                                    Toast.makeText(getApplicationContext(), "First Insert", Toast.LENGTH_SHORT).show();
                                }


                                StaffCursorAdapter cursorAdapter = new StaffCursorAdapter(getApplicationContext(), res);
                                listView.setAdapter(cursorAdapter);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View view, int position, long arg) {
                Cursor mycursor = (Cursor) av.getItemAtPosition(position);

                Intent i = new Intent(getApplicationContext(), updateStaff.class);
                i.putExtra("ID", mycursor.getString(0));
                i.putExtra("NAME", mycursor.getString(1));
                i.putExtra("EMAIL", mycursor.getString(2));
                i.putExtra("PHONE", mycursor.getString(3));
                i.putExtra("PIN", mycursor.getString(4));
                i.putExtra("STATUS", mycursor.getString(5));
                startActivity(i);
            }
        });
    }

    private void displayStaff(){
        try {
            Cursor res = db.search("SELECT * FROM staff");

            if (res.getCount() == 0) {
                // show message
                Toast.makeText(getApplicationContext(), "Table Empty", Toast.LENGTH_SHORT).show();
                return;
            }

            StaffCursorAdapter cursorAdapter = new StaffCursorAdapter(getApplicationContext(), res);
            listView.setAdapter(cursorAdapter);
        } catch (Exception e) {
            Log.e("Error", String.valueOf(e));
        }
    }


}