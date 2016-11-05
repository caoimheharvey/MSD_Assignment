package com.caoimheharv.msd_assignment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class clockInOut extends AppCompatActivity {

    DatabaseHelper myDB = new DatabaseHelper(this);
    Button takePhoto, clockingBtn, shiftsBtn;
    ImageView pic;
    TextView dispDT;
    int staff_id;

    boolean entered;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_in_out);

        takePhoto = (Button) findViewById(R.id.takePhoto);
        clockingBtn = (Button) findViewById(R.id.clockInOutBtn);
        shiftsBtn = (Button) findViewById(R.id.viewUpcoming);

        pic = (ImageView) findViewById(R.id.photo);

        dispDT = (TextView) findViewById(R.id.viewDateTime);

        //Setting text view to display current date and time
        setTime();

        staff_id = getIntent().getExtras().getInt("ID");
        //final String upSI = getIntent().getExtras().getString("ID");
        //staff_id = Integer.parseInt(upSI);
        clockingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat start_time = new SimpleDateFormat("HH:mm");
                SimpleDateFormat start_date = new SimpleDateFormat("dd/mm/yyyy");
                String s_date = null, e_date, s_time = null, e_time;

                if(clockingBtn.getText().toString().equals("CLOCK IN")) {
                    s_date = start_date.format(c.getTime());
                    s_time = start_time.format(c.getTime());

                    Toast.makeText(getApplicationContext(), "CLOCKING IN", Toast.LENGTH_SHORT).show();

                    boolean s = myDB.insertClocked(staff_id, s_time, "0", s_date,
                            "0");
                    if(s)
                        Toast.makeText(getApplicationContext(), "S", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), "F", Toast.LENGTH_SHORT).show();

                    clockingBtn.setText("CLOCK OUT");
                } else {

                    SimpleDateFormat end_time = new SimpleDateFormat("HH:mm");
                    SimpleDateFormat end_date = new SimpleDateFormat("dd/mm/yyyy");

                    e_date = end_date.format(c.getTime());
                    e_time = end_time.format(c.getTime());

                    boolean s = myDB.updateClocked(String.valueOf(staff_id), s_time, e_time, s_date,
                            e_date);
                    if(s)
                        Toast.makeText(getApplicationContext(), "S", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), "F", Toast.LENGTH_SHORT).show();

                    Toast.makeText(getApplicationContext(), "CLOCKING OUT", Toast.LENGTH_SHORT).show();
                    clockingBtn.setText("CLOCK IN");
                }
            }
        });

        shiftsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Cursor res = myDB.search("SELECT _id, staff.staff_name, start_time, end_time, date" +
                            "FROM SHIFT INNER JOIN STAFF ON shift._id = staff_id");
                    if (res.getCount() == 0) {
                        // show message
                        showMessage("Error", "Nothing found");
                        return;
                    }

                    StringBuffer buffer = new StringBuffer();

                    // edit layout
                    while (res.moveToNext()) {
                        buffer.append("Staff No:" + res.getString(0) + "\n");
                        buffer.append("Name:" + res.getString(1) + "\n");
                        buffer.append("Start-Time:" + res.getString(2) + "\n");
                        buffer.append("End-Time:" + res.getString(3) + "\n");
                        buffer.append("Date:" + res.getString(4) + "\n");
                    }
                    // Show all data
                    showMessage("Upcoming Shifts", buffer.toString());
                } catch (Exception e)
                {

                }
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

    private void setTime() {
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy --- HH:mm");
        String formattedDate = df.format(c.getTime());

        dispDT.setText(formattedDate);
    }

    /**
     *
     * CAMERA LAUNCHING CODE
     */
    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            pic.setImageBitmap(imageBitmap);
        }
        takePhoto.setVisibility(View.GONE);
        clockingBtn.setVisibility(View.VISIBLE);
    }
}