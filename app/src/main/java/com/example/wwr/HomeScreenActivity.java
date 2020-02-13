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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

public class HomeScreenActivity extends AppCompatActivity {
    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";
    private static final String TAG = "HomeScreenActivity";

    private TextView textSteps;
    private FitnessService fitnessService;
    private boolean debug = false;
    private Button debugAdd;
    private Button startNew;
    private Button addNew;
    private Switch debugSwitch;
    private Button clearData;
    private Button goToRoutes;

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
        startNew = findViewById(R.id.startRouteButton);
        addNew = findViewById(R.id.addButton);
        clearData = findViewById(R.id.ClearDataBase_debug);
        clearData.setVisibility(View.GONE);
        debugAdd.setVisibility(View.GONE);
        goToRoutes = findViewById(R.id.routesButton);

        SharedPreferences sp = getSharedPreferences("height", MODE_PRIVATE);
        int userHeight = sp.getInt("FEET", 0);
        int userHeight2 = sp.getInt("INCH", 0);

        SharedPreferences routeCount = getSharedPreferences("routeInfo", MODE_PRIVATE);
        if(!routeCount.contains("routeNames")){
            System.err.println("routeNames StringSet created.");
            // a count to remember how many routes to retrieve
            SharedPreferences.Editor editor = routeCount.edit();
            editor.putStringSet("routeNames", new TreeSet<String>()).apply();
            editor.putString("latestRoute", "").apply();
        }

        User.setHeight(userHeight, userHeight2);
        System.err.println("has height " + userHeight + " " + userHeight2);

        showDataBase(); // print stored info

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
                    clearData.setVisibility(View.VISIBLE);
                    setStepCount(0);
                    Toast.makeText(getApplicationContext(), "DEBUG ON", Toast.LENGTH_SHORT).show(); // display the current state for switch's
                }
                if (!debugSwitch.isChecked()) {
                    debug = false;
                    debugAdd.setVisibility(View.GONE);
                    clearData.setVisibility(View.GONE);
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
        clearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences height = getSharedPreferences("height", MODE_PRIVATE);
                SharedPreferences routes = getSharedPreferences("routeInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = height.edit();
                SharedPreferences.Editor editor2 = routes.edit();

                editor.clear().apply();
                editor2.clear().apply();

                if(!routes.contains("routeNames")){
                    System.err.println("routeNames StringSet created.");
                    // a count to remember how many routes to retrieve
                    SharedPreferences.Editor editor3 = routes.edit();
                    editor3.putStringSet("routeNames", new TreeSet<String>()).apply();
                    editor3.putString("latestRoute", "").apply();
                }

                Toast.makeText(getApplicationContext(), "Data Cleared", Toast.LENGTH_SHORT).show(); // display the current state for switch's
            }
        });
        startNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchStartAWalkActivity();
            }
        });
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAddAWalkActivity();
            }
        });
        goToRoutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchRoutesPageActivity();
            }
        });
    }

    public void launchRoutesPageActivity(){
        Intent intent = new Intent(this, RoutesPageActivity.class);
        startActivity(intent);
    }


    public void launchStartAWalkActivity(){
        Intent intent = new Intent(this, StartAWalkActivity.class);
        startActivity(intent);
    }

    public void launchAddAWalkActivity(){
        Intent intent = new Intent(this, AddAWalkActivity.class);
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
        viewIntentionalWalk();
    }

    public void launchTakeHeightActivity(){
        Intent intent = new Intent(this, TakeHeightActivity.class);
        startActivity(intent);
    }

    public void viewIntentionalWalk(){
        SharedPreferences routeCount = getSharedPreferences("routeInfo", MODE_PRIVATE);
        String latestRoute = routeCount.getString("latestRoute", "");

        if(latestRoute.compareTo("") != 0){
             System.err.println("Update Intentional Walk.");

            TextView name = findViewById(R.id.LastWalkName);
            TextView start = findViewById(R.id.lastWalkStart);
            TextView steps = findViewById(R.id.LWsteps);
            TextView dist = findViewById(R.id.LWmiles);
            TextView hour = findViewById(R.id.hour);
            TextView min = findViewById(R.id.min);
            TextView sec = findViewById(R.id.sec);

            double dist_double = Double.parseDouble(routeCount.getString(latestRoute+"_dist", ""));
            dist_double = Math.round(dist_double * 100.0) / 100.0;


            name.setText(latestRoute);
            start.setText(routeCount.getString(latestRoute+"_location", ""));
            steps.setText(Integer.toString(routeCount.getInt(latestRoute+"_step", 0)));
            dist.setText(Double.toString(dist_double));
            hour.setText(Integer.toString(routeCount.getInt(latestRoute+"_hour", 0)));
            min.setText(Integer.toString(routeCount.getInt(latestRoute+"_min", 0)));
            sec.setText(Integer.toString(routeCount.getInt(latestRoute+"_sec", 0)));
        }
    }
    @Override
    public void onBackPressed() {
        // this disabled back button on phone
    }
    public void showDataBase(){
        SharedPreferences routeCount = getSharedPreferences("routeInfo", MODE_PRIVATE);
        Set<String> routeList = routeCount.getStringSet("routeNames", null);
        String dataSet = "";
        int i = 0;
        for(String s: routeList){
            i = i + 1;
            String f = routeCount.getString(s + "_features", "");
            dataSet = dataSet + i + ". " + s + " " + f + "\n";
        }
        System.err.println("DataBase: \n" + dataSet+ " END.");
    }
}
