package com.caoimheharv.msd_assignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * ADMINMENU: Allows the admin to select one of 4 options to proceed with once they
 * have logged in.
 * Uses simple Intent to link to the appropiate action.
 */

public class AdminMenu extends AppCompatActivity {

    Button clocking, staff, shifts, settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        clocking = (Button) findViewById(R.id.clockBtn);
        staff = (Button) findViewById(R.id.manStaff);
        shifts = (Button) findViewById(R.id.manShifts);
        settings = (Button) findViewById(R.id.setBtn);

        final int staff_id = getIntent().getExtras().getInt("ID");
        clocking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminMenu.this, Clocking.class);
                i.putExtra("ID", staff_id);
                startActivity(i);
            }
        });

        staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminMenu.this, ManageStaff.class);
                startActivity(i);
            }
        });

        shifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminMenu.this, ManageShifts.class);
                startActivity(i);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Settings.class);
                startActivity(i);
            }
        });
    }
}
