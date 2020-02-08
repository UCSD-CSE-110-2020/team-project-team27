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
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class HomeScreenActivity extends AppCompatActivity {
    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";
    private static final String TAG = "HomeScreenActivity";

    private TextView textSteps;
    private FitnessService fitnessService;
    private boolean debug = false;
    private Button debugAdd;
    private Button addNew;
    private Switch debugSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_activity);

        textSteps = findViewById(R.id.dailyStepsValue);

        String fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);
        fitnessService = FitnessServiceFactory.create(fitnessServiceKey, this);

        fitnessService.setup();

        textSteps = findViewById(R.id.dailyStepsValue);
        debugAdd = findViewById(R.id.AddStep_debug);
        addNew = findViewById(R.id.startRouteButton);
        debugAdd.setVisibility(View.GONE);

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
                if(!debug) {
                    fitnessService.updateStepCount();
                }
            }
        };
        Timer t = new Timer();
        t.schedule(updateSteps, 0, 2000);

        // print last intentional walk
        viewIntentionalWalk();

        debugSwitch = findViewById(R.id.debugMode);
        // DEBUG switch listener
        debugSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (debugSwitch.isChecked()) {
                    debug = true;
                    debugAdd.setVisibility(View.VISIBLE);
                    setStepCount(0);
                    Toast.makeText(getApplicationContext(), "DEBUG ON", Toast.LENGTH_SHORT).show(); // display the current state for switch's
                }
                if (!debugSwitch.isChecked()) {
                    debug = false;
                    debugAdd.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "DEBUG OFF", Toast.LENGTH_SHORT).show(); // display the current state for switch's
                }
            }
        });

        // ADD 500 step
        debugAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStepCount(Integer.parseInt(String.valueOf(textSteps.getText()))+500);
                Toast.makeText(getApplicationContext(), "DEBUG: added 500 steps", Toast.LENGTH_SHORT).show(); // display the current state for switch's
            }
        });
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchStartAWalkActivity();
            }
        });
    }


    public void launchStartAWalkActivity(){
        Intent intent = new Intent(this, StartAWalkActivity.class);
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

    public void launchTakeHeightActivity(){
        Intent intent = new Intent(this, TakeHeightActivity.class);
        startActivity(intent);
    }

    public void viewIntentionalWalk(){
        // the following is a dummy
        int [] time = {0, 10, 54};
        Route testRoute = new Route("Apple Store", "UTC", 1000, 0.5, time);
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
