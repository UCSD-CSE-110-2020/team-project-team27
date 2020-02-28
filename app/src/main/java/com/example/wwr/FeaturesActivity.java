package com.example.wwr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class FeaturesActivity extends AppCompatActivity {
    private static final String TAG = "FeaturesActivity";

    private Button done;
    private TextView title;
    private TextView notes;
    private RadioGroup isLoop;
    private RadioGroup isFlat;
    private RadioGroup isStreet;
    private RadioGroup isEven;
    private RadioGroup difficulty;
    private Switch isFavorite;
    private SharedPreferences routeInfo;
    private SharedPreferences.Editor editor;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features);
        Log.d(TAG, "onCreate: Started.");

        title = findViewById(R.id.Ftitle);
        notes = findViewById(R.id.Fnotes);
        isLoop = findViewById(R.id.loopGroup);
        isFlat = findViewById(R.id.flatGroup);
        isStreet = findViewById(R.id.streetGroup);
        isEven = findViewById(R.id.evenGroup);
        difficulty = findViewById(R.id.difficultyGroup);
        isFavorite = findViewById(R.id.favorite);
        done = findViewById(R.id.done);

        //Getting name to display
        routeInfo = getSharedPreferences("routeInfo", MODE_PRIVATE);
        name = routeInfo.getString("latestRoute", "");
        title.setText(name);

        //Adding notes to SP
        editor = routeInfo.edit();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAllSelected()){
                    String features = addFeatures();
                    UserSharePreferences.storeRoute(name, features, isFavorite.isChecked(), notes.getText().toString());
                    // editor.putString(name+"_notes", notes.getText().toString()).apply();
                    launchHomeScreenActivity();
                } else {
                    // display the current state for switch's
                    Toast.makeText(getApplicationContext(), "information incomplete", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void launchHomeScreenActivity(){
        finish();
    }

    boolean isAllSelected(){
        if(isLoop.getCheckedRadioButtonId() == -1 || isLoop == null ||
            isFlat.getCheckedRadioButtonId() == -1 || isFlat == null ||
            isEven.getCheckedRadioButtonId() == -1 || isEven == null ||
            isStreet.getCheckedRadioButtonId() == -1 || isStreet == null ||
            difficulty.getCheckedRadioButtonId() == -1 || difficulty == null){
            return false;
        }

        return true;
    }

    private String addFeatures(){
        String features = "";
        if(isLoop.getCheckedRadioButtonId() != R.id.loop) {
            features += "L ";
        } else {
            features += "O ";
        }
        if(isFlat.getCheckedRadioButtonId() == R.id.flat) {
            features += "F ";
        } else {
            features += "H ";
        }
        if(isStreet.getCheckedRadioButtonId() == R.id.street) {
            features += "S ";
        } else {
            features += "T ";
        }
        if(isEven.getCheckedRadioButtonId() == R.id.even) {
            features += "E ";
        } else {
            features += "U ";
        }
        if(difficulty.getCheckedRadioButtonId() == R.id.easy) {
            features += "E";
        } else if (difficulty.getCheckedRadioButtonId() == R.id.moderate){
            features += "M";
        } else {
            features += "D";
        }

        System.err.println("Features Stored: " + features);
        /*editor.putString(name+"_features", features);
        editor.putBoolean(name+"_isFavorite", isFavorite.isChecked());

        editor.apply();*/
        return features;
    }

    @Override
    public void onBackPressed() {
        // this disabled back button on phone
    }
}
