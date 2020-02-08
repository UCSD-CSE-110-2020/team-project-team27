package com.example.wwr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.wwr.fitness.FitnessService;
import com.example.wwr.fitness.FitnessServiceFactory;
import com.example.wwr.fitness.GoogleFitAdapter;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
        private static final String TAG = "MainActivity";
        private TextView textSteps;
        private com.example.wwr.fitness.FitnessService fitnessService;

        public static String fitnessServiceKey = "GOOGLE_FIT";


    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textSteps = findViewById(R.id.dailyStepsValue);

        // switch to takeheightActivity if it's a first time user
        if (!User.hasHeight()) {
            // switch screen
            launchTakeHeightActivity();
        }

        FitnessServiceFactory.put(fitnessServiceKey, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(MainActivity mainActivity) {
                return new GoogleFitAdapter(mainActivity);
            }
        });

        //Create app manager
        fitnessService = com.example.wwr.fitness.FitnessServiceFactory.create(fitnessServiceKey, this);
        fitnessService.setup();
        fitnessService.updateStepCount();

        // update stepCounts each second from google fit
        TimerTask updateSteps = new TimerTask() {
            @Override
            public void run() {
                fitnessService.updateStepCount();
            }
        };
        Timer t = new Timer();
        t.schedule(updateSteps, 0, 2000);

        // print last intentional walk
        viewIntentionalWalk();
    }

        public void launchTakeHeightActivity(){
            Intent intent = new Intent(this, TakeHeightActivity.class);
            startActivity(intent);
        }


    @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

        // If authentication was required during google fit setup, this will be called after the user authenticates
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == fitnessService.getRequestCode()) {
                    fitnessService.updateStepCount();
                }
            } else {
                Log.e(TAG, "ERROR, google fit result code: " + resultCode);
            }
        }

        public void setStepCount(long stepCount) {
            textSteps.setText(String.valueOf(stepCount));
            User.setSteps(stepCount);
            TextView textDist = findViewById(R.id.mileageValue);
            textDist.setText(String.valueOf(User.returnDistance()));
        }

        public void viewIntentionalWalk(){
            // the following is a dummy
            int [] time = {1, 24, 54};
            Route testRoute = new Route("Geisel Walk", "the Village", 1877, 0.7, time);
            RouteList.addRoute(testRoute);

            Route latest = RouteList.getLatest();
            TextView name = findViewById(R.id.LastWalkName);
            TextView start = findViewById(R.id.lastWalkStart);
            TextView steps = findViewById(R.id.LWsteps);
            TextView dist = findViewById(R.id.LWmiles);
            TextView hour = findViewById(R.id.hour);
            TextView min = findViewById(R.id.min);
            TextView sec = findViewById(R.id.sec);

            name.setText(latest.name);
            start.setText(latest.startingLocation);
            steps.setText(String.valueOf(latest.steps));
            dist.setText(String.valueOf(latest.distance));
            hour.setText(String.valueOf(latest.time[0]));
            min.setText(String.valueOf(latest.time[1]));
            sec.setText(String.valueOf(latest.time[2]));
        }
    public void setFitnessServiceKey(String fitnessServiceKey) {
        this.fitnessServiceKey = fitnessServiceKey;
    }
}
