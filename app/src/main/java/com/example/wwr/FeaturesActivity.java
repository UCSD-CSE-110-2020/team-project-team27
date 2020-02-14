package com.example.wwr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class FeaturesActivity extends AppCompatActivity {
    private Button done;
    TextView title;
    TextView notes;
    RadioGroup isLoop;
    RadioGroup isFlat;
    RadioGroup isStreet;
    RadioGroup isEven;
    RadioGroup difficulty;
    Switch isFavorite;
    SharedPreferences routeInfo;
    SharedPreferences.Editor editor;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features);

        title = findViewById(R.id.Ftitle);
        notes = findViewById(R.id.Fnotes);
        isLoop = findViewById(R.id.loopGroup);
        isFlat = findViewById(R.id.flatGroup);
        isStreet = findViewById(R.id.streetGroup);
        isEven = findViewById(R.id.evenGroup);
        difficulty = findViewById(R.id.difficultyGroup);
        isFavorite = findViewById(R.id.favorite);

        //Getting name to display
        routeInfo = getSharedPreferences("routeInfo", MODE_PRIVATE);
        name = routeInfo.getString("latestRoute", "");
        title.setText(name);

        //Adding notes to SP
        editor = routeInfo.edit();

        done = findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAllSelected()){
                    addFeatures();
                    editor.putString(name+"_notes", notes.getText().toString()).apply(); // store location correspond to the route
                    launchHomeScreenActivity();
                } else {
                    Toast.makeText(getApplicationContext(), "information incomplete", Toast.LENGTH_SHORT).show(); // display the current state for switch's
                }
            }
        });
    }
    public void launchHomeScreenActivity(){
        finish();
    }

    private boolean isAllSelected(){
        if(isLoop.getCheckedRadioButtonId() == -1 ||
            isFlat.getCheckedRadioButtonId() == -1 ||
            isEven.getCheckedRadioButtonId() == -1 ||
            isStreet.getCheckedRadioButtonId() == -1 ||
            difficulty.getCheckedRadioButtonId() == -1){
            return false;
        }

        return true;
    }

    private void addFeatures(){
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
        editor.putString(name+"_features", features);
        editor.putBoolean(name+"_isFavorite", isFavorite.isChecked());
        editor.apply();
    }
    @Override
    public void onBackPressed() {
        // this disabled back button on phone
    }
}
