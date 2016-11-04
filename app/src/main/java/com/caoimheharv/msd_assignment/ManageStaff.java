package com.caoimheharv.msd_assignment;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Allows admin to select from 3 different actions.
 * 1. Viewing all staff members and their details which exist in the database without leaving the page
 * 2. Adding a new Staff member
 * 3. Editing existing staff members
 */
public class ManageStaff extends AppCompatActivity {

    DatabaseHelper myDB;

    updateStaff uS = new updateStaff();

    Button add, update;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_staff);

        myDB = new DatabaseHelper(this);

        add = (Button) findViewById(R.id.addBtn);
        update = (Button) findViewById(R.id.updateBtn);
        //viewAll = (Button) findViewById(R.id.viewStaff);
        listView = (ListView) findViewById(R.id.stafflist);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManageStaff.this, addStaffForm.class);
                startActivity(i);
            }
        });

        Cursor res = myDB.search("SELECT * FROM staff");

        if (res.getCount() == 0) {
            // show message
            showMessage("Error", "Nothing found");
            return;
        }

        ShiftCursorAdapter cursorAdapter = new ShiftCursorAdapter(getApplicationContext(), res);
        listView.setAdapter(cursorAdapter);

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


    private void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
