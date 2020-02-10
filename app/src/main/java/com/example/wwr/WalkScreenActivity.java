package com.example.wwr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class WalkScreenActivity extends AppCompatActivity {

    Button stopWalk;
    TextView walkName;
    TextView location;
    TextView timeHour;
    TextView timeMinute;
    TextView timeSecond;
    TextView miles;
    TextView steps;
    long preWalkStepCount;
    long startTime = 0;

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            int hours = minutes / 60;
            seconds = seconds % 60;

            timeMinute.setText("" + minutes);
            timeSecond.setText("" + seconds);
            timeHour.setText("" + hours );

            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_screen);

        stopWalk = findViewById(R.id.WSAstopWalk);
        walkName = findViewById(R.id.WSAwalkName);
        timeHour = findViewById(R.id.WSAhourCount);
        timeMinute = findViewById(R.id.WSAminuteCount);
        timeSecond = findViewById(R.id.WSAsecondCount);
        miles = findViewById(R.id.WSAmileCount);
        steps = findViewById(R.id.WSAstepCount);
        location = findViewById(R.id.textView6);

        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);

        preWalkStepCount = User.getSteps();

        // update stepCounts each second from google fit
        TimerTask updateSteps = new TimerTask() {
            @Override
            public void run() {
                updateWalkInfo();
            }
        };
        Timer t = new Timer();
        t.schedule(updateSteps, 0, 2000);

        stopWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int[] time = {Integer.parseInt(timeHour.getText().toString()),
                        Integer.parseInt(timeMinute.getText().toString()),
                        Integer.parseInt(timeSecond.getText().toString())};
                User.getCurrentRoute().setTime(time);
                User.getCurrentRoute().setDistance(Double.parseDouble(miles.getText().toString()));
                User.getCurrentRoute().setSteps(Integer.parseInt(steps.getText().toString()));

                timerHandler.removeCallbacks(timerRunnable);

                launchFeaturesActivity();
            }
        });
    }

    private void updateWalkInfo(){
        final long walkSteps = User.getSteps() - preWalkStepCount;

        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    walkName.setText(User.getCurrentRoute().getName());
                    location.setText(User.getCurrentRoute().getStartingLocation());
                    System.err.println("Set Walk Screen to " + walkSteps);
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
        finish();
        // TODO: not going back to the right screen!!
    }

}
