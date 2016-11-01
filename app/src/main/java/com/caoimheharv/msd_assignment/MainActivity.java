package com.caoimheharv.msd_assignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public DatabaseHelper myDB;

    int[] temp = {1234, 0000, 1984};

    Button verify;
    EditText passcode;
    int pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(this);

        verify = (Button) findViewById(R.id.contBtn);
        passcode = (EditText) findViewById(R.id.passcode);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passcode.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "MUST ENTER PIN", Toast.LENGTH_SHORT).show();
                }
                else {
                    pin = Integer.parseInt(passcode.getText().toString());


                    boolean check = false;
                    for (int i = 0; i < temp.length; i++) {
                        if (pin == temp[i]) {
                            check = true;
                            Intent intent = new Intent(MainActivity.this, AdminMenu.class);
                            startActivity(intent);
                            break;
                        } else {
                            check = false;
                        }
                    }
                }

            }
        });
    }
}
