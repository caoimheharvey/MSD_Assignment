/**
 * https://www.simplifiedcoding.net/android-email-app-using-javamail-api-in-android-studio/
 */
package com.caoimheharv.msd_assignment;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Clocking extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper db = new DatabaseHelper(this);

    int staff_no;

    boolean hasStart = false;

    Button clockBtn;
    ListView listView;
    TextView cdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clocking);

        //getting staff_id from intent as an int
        final int staff_id = getIntent().getExtras().getInt("ID");
        //setting the staff_id to a global variable so it can be used in multiple classes
        staff_no = staff_id;
        //initializing fields
        clockBtn = (Button) findViewById(R.id.cButton);
        cdt = (TextView) findViewById(R.id.cDT);
        listView = (ListView) findViewById(R.id.clockingList);

        //SQL statement requests all details of the shift including the name of the staff member
        //this is done by an inner join as the SHIFT table doesn't contain a name field
        //this is for the specific user who is currently in the app, from this they can only see their upcoming shifts
        Cursor res = db.search("SELECT shift._id, staff_id, staff_name, start_date, start_time, end_time FROM SHIFT" +
                " INNER JOIN STAFF ON shift.staff_id = staff._id WHERE shift.staff_id = " + staff_no);

        //Outputs the upcoming shifts the user has
        try {
            ShiftCursorAdapter cursorAdapter = new ShiftCursorAdapter(getApplicationContext(), res);
            listView.setAdapter(cursorAdapter);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Internal Failure", Toast.LENGTH_SHORT).show();
        }

        getTime();
        clockBtn.setOnClickListener(this);
    }

    private void clockIn(int staff_id)
    {
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String formattedDate = df.format(c.getTime());
        db.insertClocked(staff_id, formattedDate);
        clockBtn.setText("Clock Out");
        Toast.makeText(getApplicationContext(), "CLOCKED IN at "+ formattedDate, Toast.LENGTH_SHORT).show();
    }

    private void clockOut(String row_id)
    {
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String formattedDate = df.format(c.getTime());
        db.updateClocked(row_id, formattedDate);
        Toast.makeText(getApplicationContext(), "CLOCKED OUT at " + formattedDate, Toast.LENGTH_SHORT).show();
    }

    private void getTime()
    {
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy :: HH:mm");
        String formattedDate = df.format(c.getTime());

        cdt.setText(formattedDate);
    }

    public void onClick(View v)
    {
        try {
            String end = null, r_id = null, name = null, start = null;
            Cursor res = db.search("SELECT CLOCKED_SHIFT._id, staff_id, start, end, staff.staff_name FROM CLOCKED_SHIFT" +
                    " INNER JOIN STAFF ON CLOCKED_SHIFT.STAFF_ID = STAFF._ID");
            if (res.getCount() == 0) {
                Toast.makeText(getApplicationContext(), "First Insert", Toast.LENGTH_SHORT).show();
                clockIn(staff_no);
            }
            while(res.moveToNext()) {
                r_id = res.getString(0);
                start = res.getString(2);
                end = res.getString(3);
                name = res.getString(4);
            }

            //Log.i("Time Values", "Start: " + start + "---- End: " + end);

            if (hasStart == false) {

                clockBtn.setText("Clock In");
                clockIn(staff_no);
                Log.i("Clock In", "Start: " + start + "---- End: " + end);
                hasStart = true;
            } else {
                clockBtn.setText("Clock Out");
                clockOut(r_id);
                String content = name + "(" + staff_no + ")" + " In Time: " + start + "." +
                        " Out Time: " + end + "." +
                        "Kind Regards,\nClocking System";
                Log.i("Clock Out", "Start: " + start + "---- End: " + end);
                sendEmail("Staff Number: " + staff_no, content);
                hasStart = false;

            }




        } catch (Exception e){
            Log.e("Error", String.valueOf(e));
        }

    }

    private void sendEmail(String subj, String content) {
        String dest;
        dest = "caoimhe.e.harvey@gmail.com";

        Log.i("IN MAIL", "in sendEmail()");

        SendEmail sm = new SendEmail(this, dest, subj, content);

        //Executing sendmail to send email
        //sm.execute();
    }
}
