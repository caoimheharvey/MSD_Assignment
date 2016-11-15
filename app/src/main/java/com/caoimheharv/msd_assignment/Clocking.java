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

    Button clockBtn;
    TextView cdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clocking);

        /**
         * GETTING STAFF ID FROM INTENT
         */

        final int staff_id = getIntent().getExtras().getInt("ID");
        staff_no = staff_id;
        clockBtn = (Button) findViewById(R.id.cButton);
        cdt = (TextView) findViewById(R.id.cDT);

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
        clockBtn.setText("Clock In");
        //Toast.makeText(getApplicationContext(), "CLOCKED OUT at " + formattedDate, Toast.LENGTH_SHORT).show();

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

            if (start != null && end != null) {
                clockIn(staff_no);
            } else {
                clockOut(r_id);
                String content = name + "(" + staff_no + ")" + " has clocked out of their shift at " + end + ".\n\n" +
                        "They clocked in at " + start + ".\r\nKind Regards, Clocking System";
                sendEmail("Staff Number: " + staff_no, content);
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
        sm.execute();
    }
}
