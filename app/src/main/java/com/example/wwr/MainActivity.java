package com.example.wwr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.wwr.fitness.FitnessService;
import com.example.wwr.fitness.FitnessServiceFactory;
import com.example.wwr.fitness.GoogleFitAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
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

        // switch to takeheight if necessry
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

        fitnessService.updateStepCount();

        // attempt to update stepcounts
        Timer t;
        TimerTask updateSteps = new TimerTask() {
            @Override
            public void run() {
                fitnessService.setup();
            }
        };
        t = new Timer();
        t.schedule(updateSteps, 0, 2000);
    }

        public void launchTakeHeightActivity(){
            Intent intent = new Intent(this, TakeHeight.class);
            startActivity(intent);
        }


    @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

//       If authentication was required during google fit setup, this will be called after the user authenticates
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
            // Remove later
            User.setSteps(stepCount);
            TextView textDist = findViewById(R.id.mileageValue);
            textDist.setText(String.valueOf(User.returnDistance()));
        }

}
