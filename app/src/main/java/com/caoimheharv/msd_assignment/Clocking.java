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

public class Clocking extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);

    Button clockBtn;
    TextView cdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clocking);

        /**
         * GETTING STAFF ID FROM INTENT
         */
        final int staff_no = getIntent().getExtras().getInt("ID");

        clockBtn = (Button) findViewById(R.id.cButton);
        cdt = (TextView) findViewById(R.id.cDT);

        getTime();
        clockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String end = null, r_id = null;
                    Cursor res = db.search("SELECT * FROM CLOCKED_SHIFT");
                    if (res.getCount() == 0) {
                        Toast.makeText(getApplicationContext(), "First Insert", Toast.LENGTH_SHORT).show();
                        clockIn(staff_no);
                    }
                    while(res.moveToNext()) {
                        r_id = res.getString(0);

                        end = res.getString(3);
                    }

                    if (end != null)
                        clockIn(staff_no);
                    else
                        clockOut(r_id);

                    Log.i("TABLE ROW", r_id + " ---- " + staff_no + " ----- " + end);

                } catch (Exception e){
                    Log.e("Error", String.valueOf(e));
                }
            }
        });
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
        Toast.makeText(getApplicationContext(), "CLOCKED OUT at " + formattedDate, Toast.LENGTH_SHORT).show();

    }

    private void getTime()
    {
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy :: HH:mm");
        String formattedDate = df.format(c.getTime());

        cdt.setText(formattedDate);
    }
}
