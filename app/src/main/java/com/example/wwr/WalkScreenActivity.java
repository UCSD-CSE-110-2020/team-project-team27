package com.example.wwr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class WalkScreenActivity extends AppCompatActivity {
    private static final String TAG = "WalkScreenActivity";

    long preWalkStepCount;
    long startTime = 0;
    private boolean debug = false;
    private TextView start_text;
    private TextView unit;
    private TextView enter_text;
    private EditText newtime;
    private TextView start_Time;
    private Switch debugSwitch;
    private Button add500_debug;
    private Button stopWalk;
    private TextView walkName;
    private TextView location;
    private TextView timeHour;
    private TextView timeMinute;
    private TextView timeSecond;
    private TextView miles;
    private TextView steps;

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            if(debug == false) {

                long millis = System.currentTimeMillis() - startTime;
                updateTimeInfo(millis);
            }
                timerHandler.postDelayed(this, 500);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_screen);
        Log.d(TAG, "onCreate: Started.");

        stopWalk = findViewById(R.id.WSAstopWalk);
        walkName = findViewById(R.id.Ftitle);
        timeHour = findViewById(R.id.WSAhourCount);
        timeMinute = findViewById(R.id.WSAminuteCount);
        timeSecond = findViewById(R.id.WSAsecondCount);
        miles = findViewById(R.id.WSAmileCount);
        steps = findViewById(R.id.WSAstepCount);
        location = findViewById(R.id.textView6);
        start_text = findViewById(R.id.textView14);
        unit = findViewById(R.id.time_input2);
        enter_text = findViewById(R.id.textView12);
        start_Time = findViewById(R.id.time_txt);
        newtime = findViewById(R.id.textView9);
        debugSwitch = findViewById(R.id.switch1);
        add500_debug = findViewById(R.id.button);
        start_text.setVisibility(View.GONE);
        unit.setVisibility(View.GONE);
        enter_text.setVisibility(View.GONE);
        start_Time.setVisibility(View.GONE);
        newtime.setVisibility(View.GONE);
        add500_debug.setVisibility(View.GONE);

        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);

        preWalkStepCount = User.getSteps();

        // update stepCounts each second from google fit
        TimerTask updateSteps = new TimerTask() {
            @Override
            public void run() {
                if(debug == false) {

                    updateWalkInfo(User.getSteps() - preWalkStepCount);
                }
            }
        };
        final Timer t = new Timer();
        t.schedule(updateSteps, 0, 2000);

        // ADD 500 step
        add500_debug.setOnClickListener(new View.OnClickListener() {
            long debugCount = 0;
            @Override
            public void onClick(View view) {
                debugCount = debugCount + 500;
                updateWalkInfo(debugCount);
                // display the current state for switch's
                Toast.makeText(getApplicationContext(), "DEBUG: added 500 steps", Toast.LENGTH_SHORT).show();
            }
        });

        stopWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long passageOfTime;
                if(debug) {
                    try {
                        passageOfTime = Long.parseLong(newtime.getText().toString()) - User.setTime;
                    }catch(NumberFormatException e){
                        passageOfTime = 0;
                    }
                    if(passageOfTime < 0){
                        passageOfTime = 0;
                    }
                    updateTimeInfo(passageOfTime);
                }
                    int[] time = {Integer.parseInt(timeHour.getText().toString()),
                            Integer.parseInt(timeMinute.getText().toString()),
                            Integer.parseInt(timeSecond.getText().toString())};
                    User.getCurrentRoute().setTime(time);
                    User.getCurrentRoute().setDistance(Double.parseDouble(miles.getText().toString()));
                    User.getCurrentRoute().setSteps(Integer.parseInt(steps.getText().toString()));
                    UserSharePreferences.storeRoute(walkName.getText().toString(), time, Double.parseDouble(miles.getText().toString()), Integer.parseInt(steps.getText().toString()));
                    timerHandler.removeCallbacks(timerRunnable);
                    t.cancel(); // stop updating walk screen

                launchFeaturesActivity();
            }
        });

        // DEBUG switch listener
        debugSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (debugSwitch.isChecked()) {
                    debug = true;
                    start_text.setVisibility(View.VISIBLE);
                    unit.setVisibility(View.VISIBLE);
                    enter_text.setVisibility(View.VISIBLE);
                    start_Time.setVisibility(View.VISIBLE);
                    newtime.setVisibility(View.VISIBLE);
                    add500_debug.setVisibility(View.VISIBLE);
                    start_Time.setText("" + User.setTime);
                    Toast.makeText(getApplicationContext(), "DEBUG ON", Toast.LENGTH_SHORT).show(); // display the current state for switch's
                }
                if (!debugSwitch.isChecked()) {
                    debug = false;
                    start_text.setVisibility(View.GONE);
                    unit.setVisibility(View.GONE);
                    enter_text.setVisibility(View.GONE);
                    start_Time.setVisibility(View.GONE);
                    newtime.setVisibility(View.GONE);
                    add500_debug.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "DEBUG OFF", Toast.LENGTH_SHORT).show(); // display the current state for switch's
                }
            }
        });
    }

    private void updateTimeInfo(long passageOfTime){
        int totalSecs = (int) (passageOfTime / 1000);
        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;

        timeMinute.setText("" + minutes);
        timeSecond.setText("" + seconds);
        timeHour.setText("" + hours);
    }

    private void updateWalkInfo(final long walkSteps){
        // final long walkSteps = User.getSteps() - preWalkStepCount;

        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    walkName.setText(User.getCurrentRoute().getName());
                    location.setText(User.getCurrentRoute().getStartingLocation());
                    steps.setText("" + walkSteps);
                    miles.setText(String.valueOf(returnDistance(walkSteps)));
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static double returnDistance(long steps){
        // got the conversion formula from https://www.inchcalculator.com/steps-distance-calculator/
        int heightinInches = User.getHeight()[0] * 12 + User.getHeight()[1];
        double distance = ((double) heightinInches) * 0.43 * ((double) steps); // need to convert from inch to ft to mil
        distance = distance/12/5280;
        distance = Math.round(distance * 100.0) / 100.0;
        return distance; // fixed value for testing
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    public void launchFeaturesActivity(){
        Intent intent = new Intent(this, FeaturesActivity.class);
        startActivity(intent);
        finish();
    }
}
