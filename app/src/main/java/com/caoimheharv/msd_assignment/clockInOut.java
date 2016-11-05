package com.caoimheharv.msd_assignment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
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

    Button takePhoto, clockingBtn, shiftsBtn;
    ImageView pic;
    TextView dispDT;
    int staff_id;

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

        clockingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * get ID from intent
                 * if start_time is empty then set this time to start time and text to clock in
                 * else set time to clocking out time and text to clock out
                 */
                try {
                    Toast.makeText(getApplicationContext(), "Staff ID:", Toast.LENGTH_SHORT).show();
                } catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Exception", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setTime() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+ c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy --- HH:mm");
        String formattedDate = df.format(c.getTime());

        dispDT.setText(formattedDate);
    }

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
