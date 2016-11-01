package com.caoimheharv.msd_assignment;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class delPage extends AppCompatActivity {

    DatabaseHelper myDb;

    Button del, cancel;
    EditText staff_id;
    TextView name, email, number, pin, status;
    int staff_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_page);

        myDb = new DatabaseHelper(this);

        del = (Button) findViewById(R.id.delStaff);
        cancel = (Button) findViewById(R.id.cancelDel);

        staff_id = (EditText) findViewById(R.id.enterID);

        name = (TextView) findViewById(R.id.getName);
        email = (TextView) findViewById(R.id.getMail);
        number = (TextView) findViewById(R.id.getNumber);
        pin = (TextView) findViewById(R.id.getPin);
        status = (TextView) findViewById(R.id.getStatus);

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (staff_id.getText().toString().isEmpty())
                    Toast.makeText(delPage.this, "Please Enter STAFF_ID", Toast.LENGTH_SHORT);
                else
                    staff_no = Integer.parseInt(staff_id.getText().toString());
                Cursor res = myDb.getStaff("Staff", staff_no);

                if (res.getCount() == 0) {
                    // show message
                    showMessage("Error", "Nothing found");
                    return;
                }

                while (res.moveToNext())
                {
                    name.setText(res.getString(1));
                    email.setText(res.getString(2));
                    number.setText(res.getString(3));
                    status.setText(res.getString(4));
                    pin.setText(res.getString(5));
                }
                //name.setText(res.getString(1).toString());

            }
        });

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
