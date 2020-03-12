/* File: HomeScreenActivity
 * The home screen of the app that displays the steps, miles, etc.
 * Provides options for the user to go pages to start new routes,
 * as well as view old routes.
 */

package com.example.wwr;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.example.wwr.fitness.FitnessService;
import com.example.wwr.fitness.FitnessServiceFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;


import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

public class HomeScreenActivity extends AppCompatActivity {
    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";
    private static final String TAG = "HomeScreenActivity";
    public String notificationIntent;

    private TextView textSteps;
    private FitnessService fitnessService;
    private boolean debug = false;
    private Button debugAdd;
    private Button startNew;
    private Button teamBut;
    private Switch debugSwitch;
    private Button clearData;
    private Button goToRoutes;
    private FloatingActionButton invitation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_activity);
        Log.d(TAG, "onCreate: Started.");

        String fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);
        fitnessService = FitnessServiceFactory.create(fitnessServiceKey, this);
        fitnessService.setup();


        textSteps = findViewById(R.id.dailyStepsValue);
        debugAdd = findViewById(R.id.AddStep_debug);
        startNew = findViewById(R.id.startRouteButton);
        teamBut = findViewById(R.id.TeamButton);
        clearData = findViewById(R.id.ClearDataBase_debug);
        clearData.setVisibility(View.GONE);
        debugAdd.setVisibility(View.GONE);
        goToRoutes = findViewById(R.id.routesButton);
        invitation = findViewById(R.id.invitation_fab);

        System.err.println("The User is: " +  User.getEmail());

        SharedPreferences sp = getSharedPreferences("height", MODE_PRIVATE);
        int userHeight = sp.getInt("FEET", 0);
        int userHeight2 = sp.getInt("INCH", 0);
        UserSharePreferences.setHeightShared(sp);

        SharedPreferences routeCount = getSharedPreferences("routeInfo", MODE_PRIVATE);
        if(!routeCount.contains("routeNames")){
            System.err.println("routeNames StringSet created.");
            // a count to remember how many routes to retrieve
            SharedPreferences.Editor editor = routeCount.edit();
            editor.putStringSet("routeNames", new TreeSet<String>()).apply();
            editor.putString("latestRoute", "").apply();
        }
        UserSharePreferences.setRouteShared(routeCount);


        User.setHeight(userHeight, userHeight2);
        System.err.println("has height " + userHeight + " " + userHeight2);

        // print stored info
        showDataBase();

        // switch to takeＨeightActivity if it's a first time user
        if (!User.hasHeight()) { launchTakeHeightActivity(); }

        //switch to a notifications activity, if it exists
        notificationIntent = getIntent().getStringExtra("notificationLaunch");
        if(notificationIntent != null){
                launchActivityFromNotification();
        }

        // update stepCounts each second from google fit
        TimerTask updateSteps = new TimerTask() {
            @Override
            public void run() {
                if(!debug) { fitnessService.updateStepCount(); }
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
                    // display the current state for switch's
                    Toast.makeText(getApplicationContext(), "DEBUG ON", Toast.LENGTH_SHORT).show();
                }
                if (!debugSwitch.isChecked()) {
                    debug = false;
                    debugAdd.setVisibility(View.GONE);
                    clearData.setVisibility(View.GONE);
                    // display the current state for switch's
                    Toast.makeText(getApplicationContext(), "DEBUG OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // ADD 500 step
        debugAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStepCount(Integer.parseInt(String.valueOf(textSteps.getText()))+500);
                // display the current state for switch's
                Toast.makeText(getApplicationContext(), "DEBUG: added 500 steps", Toast.LENGTH_SHORT).show();
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
                resetIntentionalWalk();

                UpdateFirebase.clearUserRoutes();

                // display the current state for switch's
                Toast.makeText(getApplicationContext(), "Data Cleared", Toast.LENGTH_SHORT).show();
            }
        });

        startNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchStartAWalkActivity();
            }
        });
        goToRoutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchRoutesPageActivity();
            }
        });
        teamBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchTeamPageActivity();
            }
        });
        invitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {launchInviteActivity();}
        });
    }

    private void launchActivityFromNotification(){
        switch(notificationIntent){
            case "Propose":
                Intent intent = new Intent(this, RouteListsActivity.class);
                startActivity(intent);
                break;
        }

    }

    public void launchInviteActivity(){
        Intent intent = new Intent(this, InvitationActivity.class);
        startActivity(intent);
    }

    public void launchRoutesPageActivity(){
        Intent intent = new Intent(this, RouteListsActivity.class);
        // testing
        startActivity(intent);
    }


    public void launchStartAWalkActivity(){
        Intent intent = new Intent(this, StartAWalkActivity.class);
        startActivity(intent);
    }

    public void launchTeamPageActivity(){
        Intent intent = new Intent(this, TeamPageActivity.class);
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
        String lRemail = routeCount.getString("LRemail", "");

        String lRkey = latestRoute;
        if(!lRemail.equals(User.getEmail())){lRkey = latestRoute + lRemail;}

        if(latestRoute.compareTo("") != 0){
             System.err.println("Update Intentional Walk.");

            TextView name = findViewById(R.id.LastWalkName);
            TextView start = findViewById(R.id.lastWalkStart);
            TextView steps = findViewById(R.id.LWsteps);
            TextView dist = findViewById(R.id.LWmiles);
            TextView hour = findViewById(R.id.hour);
            TextView min = findViewById(R.id.min);
            TextView sec = findViewById(R.id.sec);

            double dist_double = Double.parseDouble(routeCount.getString(lRkey+"_dist", ""));
            dist_double = Math.round(dist_double * 100.0) / 100.0;

            name.setText(latestRoute);
            start.setText(routeCount.getString(lRkey+"_location", ""));
            steps.setText(Integer.toString(routeCount.getInt(lRkey+"_step", 0)));
            dist.setText(Double.toString(dist_double));
            hour.setText(Integer.toString(routeCount.getInt(lRkey+"_hour", 0)));
            min.setText(Integer.toString(routeCount.getInt(lRkey+"_min", 0)));
            sec.setText(Integer.toString(routeCount.getInt(lRkey+"_sec", 0)));
        }
    }

    @Override
    public void onBackPressed() {
        // this disabled back button on phone
    }

    public void showDataBase(){
        // purely for debug reasons, print out content in database
        SharedPreferences routeCount = getSharedPreferences("routeInfo", MODE_PRIVATE);
        Map<String,?> keys = routeCount.getAll();
        System.err.println("START");

        for(Map.Entry<String,?> entry : keys.entrySet()){
            Log.d("map values",entry.getKey() + ": " +
                    entry.getValue().toString());
        }

        System.err.println("END");
    }

    public void resetIntentionalWalk(){
        Log.d(TAG, "resetIntentionalWalk called!");
        TextView name = findViewById(R.id.LastWalkName);
        TextView start = findViewById(R.id.lastWalkStart);
        TextView steps = findViewById(R.id.LWsteps);
        TextView dist = findViewById(R.id.LWmiles);
        TextView hour = findViewById(R.id.hour);
        TextView min = findViewById(R.id.min);
        TextView sec = findViewById(R.id.sec);

        name.setText("No Walk Today!");
        start.setText("");
        steps.setText(Integer.toString(0));
        dist.setText(Double.toString(0.0));
        hour.setText(Integer.toString(0));
        min.setText(Integer.toString(0));
        sec.setText(Integer.toString(0));
    }



}
