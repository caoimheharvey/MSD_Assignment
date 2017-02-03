package com.caoimheharv.msd_assignment.Visual_Menus;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.caoimheharv.msd_assignment.Action_Controllers.DatabaseHelper;
import com.caoimheharv.msd_assignment.R;

/*
 This activity defines a log in page where users enter their unique pin which is
 then checked against all pins in the database and allows access if the pin belongs
 to an active staff member. If the pin belongs to an active staff member, the pin
 is checked to see if it belongs to an Admin or a Standard User.
 */
public class MainActivity extends AppCompatActivity {

    //creating an instance of the database
    public DatabaseHelper myDB = new DatabaseHelper(this);

    Button verify;
    //pin entered in the XML file
    EditText passcode;
    //pin entered by user stored as an int
    int pin;
    //array of all pins for each user in the database
    int[] pinStored = new int[50];
    //array of all statuses per user in the database
    String[] status = new String[50];
    //array of staff ID's per user in the database
    int[] IDs = new int[50];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing the XML features
        verify = (Button) findViewById(R.id.contBtn);
        passcode = (EditText) findViewById(R.id.passcode);

        //on button click
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //try catch used to prevent app from crashing when invalid pin entered (0)
                try {
                    //checks if field data is less than 3
                    if (passcode.getText().toString().length() < 3) {
                        Toast.makeText(MainActivity.this, "Value entered is too short", Toast.LENGTH_SHORT).show();
                    //user override pin, must be entered TWICE to grant access
                    } else if (pin == 12345) {
                        //intent to bring override to Admin Menu
                        Intent intent = new Intent(MainActivity.this, AdminMenu.class);

                        //transfering data via intents through classes
                        intent.putExtra("ID", 1);

                        //setting text for field to blank
                        passcode.setText("");
                        startActivity(intent);
                    //if the pin field isn't empty and isn't 212 the following will execute
                    } else {

                        //cursor to get relevant details from STAFF table
                        Cursor res = myDB.search("SELECT pin, status, _id FROM STAFF");

                        int x = 0; //counter for while loop parsing

                        //while loop to parse values from cursor results into arrays
                        while (res.moveToNext()) {
                            pinStored[x] = res.getInt(0);
                            status[x] = res.getString(1);
                            IDs[x] = res.getInt(2);
                            x++;
                        }

                        pin = Integer.parseInt(passcode.getText().toString());

                        int count = 0; //counter for for loop


                        //checks if a pin is stored in the database, if it is then break loop and check user status,
                        //else increase counter by 1
                        for (int i = 0; i < pinStored.length; i++) {
                            if (pin == pinStored[i]) {
                                //check status
                                checkStatus(status[i], IDs[i]);
                                passcode.setText("");
                                break;
                            } else {
                                count++;
                            }
                        }
                        //if counter is the same as the array length display error message
                        if (count == pinStored.length) {
                            Toast.makeText(MainActivity.this, "Not recognized pin", Toast.LENGTH_SHORT).show();
                            passcode.setText("");
                        }//end if
                    }//end else
                } catch (Exception e){
                    //sets text to 0 and outputs exception in log
                    passcode.setText("");
                    Log.e("Error", String.valueOf(e));
                }
            }//end inner annoynymous
        });//end verify
    }//end onCreate

    /*
    Method to check status of user based on their ID and then passes them to the
    appropiate page
     */
    private void checkStatus(String status, int id)
    {
        if(status.equals("Admin")) {
            Toast.makeText(MainActivity.this, "Admin", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, AdminMenu.class);
            intent.putExtra("ID", id);
            startActivity(intent);
        }
        else {
            Toast.makeText(MainActivity.this, "Standard", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, Clocking.class);
            intent.putExtra("ID", id);
            startActivity(intent);
        }
    }
}
