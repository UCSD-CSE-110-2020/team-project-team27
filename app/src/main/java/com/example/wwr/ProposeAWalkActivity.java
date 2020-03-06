package com.example.wwr;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

// Note: this activity ask user to enter date and time for the proposed walk and click save
public class ProposeAWalkActivity extends AppCompatActivity {
    private static final String TAG = "ProposeAWalkActivity";

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView mDisplayDate;
    private TextView eReminderTime;
    private TextView routeName;
    private TextView routeLoc;
    private TextView routeFea;
    private Button save;
    private String Rname;
    private String Rloc;
    private String Rfea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propose_awalk);

        mDisplayDate = findViewById(R.id.tvDate);
        eReminderTime = findViewById(R.id.tvTime);
        save = findViewById(R.id.savePR);
        routeName = findViewById(R.id.PWname);
        routeLoc = findViewById(R.id.PWloc);
        routeFea = findViewById(R.id.PWfea);

        Rname = getIntent().getExtras().getString("name");
        Rloc = getIntent().getExtras().getString("loc");
        Rfea = getIntent().getExtras().getString("feature");

        routeName.setText(Rname);
        routeLoc.setText(Rloc);
        routeFea.setText(Rfea);

        eReminderTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ProposeAWalkActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        eReminderTime.setText( selectedHour + " : " + selectedMinute);
                    }
                }, 2, 3, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                mTimePicker.show();
            }
        });

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ProposeAWalkActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = eReminderTime.getText().toString();
                String date = mDisplayDate.getText().toString();
                if(time.equals("[HR:MIN]") || date.equals("[DD/MM/YYYY]")){
                    Toast.makeText(getApplicationContext(),
                            "Invalid information.", Toast.LENGTH_SHORT).show();
                }else {
                    UpdateFirebase.proposeARoute(new Route(Rname, Rloc, Rfea), date, time);
                    finish();
                }
            }
        });

    }
}
