package com.caoimheharv.msd_assignment.Action_Controllers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
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

import com.caoimheharv.msd_assignment.Adapters.StaffCursorAdapter;
import com.caoimheharv.msd_assignment.R;


/**
 * Allows admin to select from 3 different actions.
 * 1. Viewing all existing staff in Database
 * 2. Adding a new staff member by clicking the button and opening an alter dialog
 * 3. Editing existing staff by clicking on their name in the list (three options, update, cancel, delete)
 */
public class ManageStaff extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);

    Button add;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_staff);

        add = (Button) findViewById(R.id.addBtn);
        listView = (ListView) findViewById(R.id.stafflist);
        //displaying all current staff in list
        displayStaff();

        //On click of the add button the addStaff() method is called
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStaff();
            }
        });

        //on click of a list item the updateStaff() method is called and 6 parameters are passed through
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View view, int position, long arg) {
                Cursor mycursor = (Cursor) av.getItemAtPosition(position);

                updateStaff(mycursor.getString(0), mycursor.getString(1), mycursor.getString(2),
                        mycursor.getString(3), mycursor.getString(4), mycursor.getString(5));

            }
        });
    }

    /*
    Outputs staff into a list for the user to see
     */
    public void displayStaff(){
        try {
            //selects all details from the staff table
            Cursor res = db.search("SELECT * FROM staff");

            if (res.getCount() == 0) {
                return;
            }
            //formats details from staff table into the list
            StaffCursorAdapter cursorAdapter = new StaffCursorAdapter(getApplicationContext(), res);
            listView.setAdapter(cursorAdapter);
        } catch (Exception e) {
            Log.e("Error", String.valueOf(e));
        }
    }

    /*
    Opens an alert dialog for the input of a new staff member
     */
    private void addStaff() {
        //links the alert dialog view to the add staff form XML file in resources
        View view = (LayoutInflater.from(ManageStaff.this)).inflate(R.layout.activity_add_staff_form, null);

        //creates an instance of the alterdialog builder
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ManageStaff.this);
        //sets the view to the alert dialof to the view initialized above
        alertBuilder.setView(view);

        //from add staff form XML
        final EditText name = (EditText) view.findViewById(R.id.addName);
        final EditText email = (EditText) view.findViewById(R.id.addEmail);
        final EditText phone = (EditText) view.findViewById(R.id.addNumber);
        final EditText pin = (EditText) view.findViewById(R.id.addPin);
        final Switch status = (Switch) view.findViewById(R.id.statusSwitch);

        //limits user error by allowing the user to select only 1 of 2 different options
        status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    status.setText("Admin");
                else
                    status.setText("Standard");
            }
        });

        //TODO: ADD ERROR CHECKING
        //builds the alert dialog and allows user to click out of dialog by hitting the background
        try {
            alertBuilder.setCancelable(true)
                    //adds a positive button for an action
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //inserts and displays new staff
                            //TODO: add error checking on PIN input

                            if (pin.getText().toString().isEmpty() || pin.getText().toString().length() < 3) {
                                Toast.makeText(getApplicationContext(), "Please Enter Pin Between 3 and 6 characters", Toast.LENGTH_SHORT).show();
                                return;
                            } else if ( name.getText().toString().isEmpty()) {
                                Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_SHORT).show();
                                return;
                            } else if (email.getText().toString().isEmpty()) {
                                Toast.makeText(getApplicationContext(), "Please Enter Email", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                db.insertStaff(name.getText().toString(), email.getText().toString(), phone.getText().toString(),
                                        Integer.parseInt(pin.getText().toString()), status.getText().toString());
                                displayStaff();

                                dialog.dismiss();
                            }

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //cancels action
                            dialog.cancel();
                        }
                    });

            //displays alert Dialog
            final Dialog dialog = alertBuilder.create();
            dialog.show();

        } catch (Exception e ){
            Log.e("ADD STAFF ERROR", String.valueOf(e));
        }
    }

    /*
    Opens an alert dialog and fills in the existing information into the edit texts
    allows user to then update or delete the selected staff member
     */
    private void updateStaff(final String id, String s_name, String s_email, String s_phone, String s_pin, String s_status){
        View view = (LayoutInflater.from(ManageStaff.this)).inflate(R.layout.activity_update_staff, null);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ManageStaff.this);
        alertBuilder.setView(view);

        //from update staff XML
        final EditText name = (EditText) view.findViewById(R.id.showName);
        final EditText email = (EditText) view.findViewById(R.id.showMail);
        final EditText phone = (EditText) view.findViewById(R.id.showNum);
        final EditText pin = (EditText) view.findViewById(R.id.showPin);
        final Switch status = (Switch) view.findViewById(R.id.showStatus);

        //filling in the edit text with pre-existing data on selected member
        name.setText(s_name); email.setText(s_email); phone.setText(s_phone);
        pin.setText(s_pin); status.setText(s_status);

        status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    status.setText("Admin");
                else
                    status.setText("Standard");
            }
        });

        alertBuilder.setCancelable(true)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Updates user and outputs success then displays all updated staff
                        boolean b = db.updateStaff(id, name.getText().toString(), email.getText().toString(), phone.getText().toString(),
                                Integer.parseInt(pin.getText().toString()), status.getText().toString());
                        if (b)
                            Toast.makeText(getApplicationContext(), name.getText().toString() + " has been Updated", Toast.LENGTH_LONG).show();
                        displayStaff();
                    }
                })
                .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //deletes selected staff member
                        Integer deletedRows = db.deleteData("Staff", id);
                        if (deletedRows > 0)
                            Toast.makeText(getApplicationContext(), "Staff Member Removed", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "ERROR: Could not delete", Toast.LENGTH_SHORT).show();
                        displayStaff();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //cancels actions
                        dialog.cancel();
                    }
                });

        //displays dialog
        Dialog dialog = alertBuilder.create();
        dialog.show();
    }
}