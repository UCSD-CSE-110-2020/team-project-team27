package com.example.wwr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private String fitnessServiceKey = "GOOGLE_FIT";

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("Yuh");

        FitnessServiceFactory.put(fitnessServiceKey, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(HomeScreenActivity homeScreenActivity) {
                return new GoogleFitAdapter(homeScreenActivity);
            }
        });

        launchHomeScreenActivity();
    }

    public void launchHomeScreenActivity(){
        Intent intent = new Intent(this, HomeScreenActivity.class);
        intent.putExtra(HomeScreenActivity.FITNESS_SERVICE_KEY, fitnessServiceKey);
        startActivity(intent);
    }

    public void setFitnessServiceKey(String fitnessServiceKey) {
        this.fitnessServiceKey = fitnessServiceKey;
    }
}
