package com.example.wwr;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.wwr.fitness.FitnessService;
import com.example.wwr.fitness.FitnessServiceFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class HomeScreenActivity extends AppCompatActivity {
    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";
    private static final String TAG = "HomeScreenActivity";

    private TextView textSteps;
    private FitnessService fitnessService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_activity);

        textSteps = findViewById(R.id.dailyStepsValue);

        String fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);
        fitnessService = FitnessServiceFactory.create(fitnessServiceKey, this);

        fitnessService.setup();

        SharedPreferences sp = getSharedPreferences("height", MODE_PRIVATE);
        int userHeight = sp.getInt("FEET", 0);
        int userHeight2 = sp.getInt("INCH", 0);

        User.setHeight(userHeight, userHeight2);
        System.err.println("has height" + userHeight + " " + userHeight2);


        // switch to takeheightActivity if it's a first time user
        if (!User.hasHeight()) {
            // switch screen
            System.err.println("went to height");
            launchTakeHeightActivity();
        }

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

    public void launchTakeHeightActivity(){
        Intent intent = new Intent(this, TakeHeightActivity.class);
        startActivity(intent);
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

        name.setText(latest.getName());
        start.setText(latest.getStartingLocation());
        steps.setText(String.valueOf(latest.getSteps()));
        dist.setText(String.valueOf(latest.getDistance()));
        hour.setText(String.valueOf(latest.getTime()[0]));
        min.setText(String.valueOf(latest.getTime()[1]));
        sec.setText(String.valueOf(latest.getTime()[2]));
    }
}
