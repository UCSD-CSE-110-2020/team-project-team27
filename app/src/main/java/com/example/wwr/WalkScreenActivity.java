package com.example.wwr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class WalkScreenActivity extends AppCompatActivity {

    Button stopWalk;
    TextView walkName;
    TextView timeHour;
    TextView timeMinute;
    TextView timeSecond;
    TextView miles;
    TextView steps;
    long preWalkStepCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_screen);

        stopWalk = findViewById(R.id.WSAstopWalk);
        walkName = findViewById(R.id.WSAwalkName);
        timeHour = findViewById(R.id.WSAhourCount);
        timeMinute = findViewById(R.id.WSAminuteCount);
        timeSecond = findViewById(R.id.WSAsecondCount);
        miles = findViewById(R.id.WSAmiles);
        steps = findViewById(R.id.WSAsteps);

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
    }

    private void updateWalkInfo(){
        long walkSteps = User.getSteps() - preWalkStepCount;
        steps.setText("" + walkSteps);
    }
}
