package com.example.wwr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class WalkInfoFromRouteActivity extends AppCompatActivity {

    TextView name;
    TextView local;
    TextView steps;
    TextView dist;
    TextView hour;
    TextView min;
    TextView sec;
    TextView features;
    TextView notes;
    Button start;
    Switch debugSwitch;
    TextView startTime;
    boolean debug = false;
    EditText Userinput;

    boolean clickedStart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_info_from_route);
        clickedStart = false;

        String name_i = getIntent().getStringExtra("CLICKED_NAME");
        System.err.println("Intent name: " + name_i);
        SharedPreferences sp = getSharedPreferences("routeInfo", MODE_PRIVATE);

        name = findViewById(R.id.Ftitle);
        local = findViewById(R.id.startLoc);
        steps = findViewById(R.id.steps);
        dist = findViewById(R.id.mile);
        hour = findViewById(R.id.hr);
        min = findViewById(R.id.min);
        sec = findViewById(R.id.sec);
        features = findViewById(R.id.features);
        notes = findViewById(R.id.notes);
        start = findViewById(R.id.start);
        debugSwitch = findViewById(R.id.switch2);
        startTime = findViewById(R.id.textView11);
        Userinput = findViewById(R.id.input);

        startTime.setVisibility(View.GONE);
        Userinput.setVisibility(View.GONE);

        double dist_double = Double.parseDouble(sp.getString(name_i + "_dist", "0.0"));
        dist_double = Math.round(dist_double * 100.0) / 100.0;

        boolean is_favorite = sp.getBoolean(name_i + "_isFavorite", false);

        name.setText(name_i);
        local.setText(sp.getString(name_i + "_location", ""));
        steps.setText("" + sp.getInt(name_i + "_step", 0));
        dist.setText(Double.toString(dist_double));
        hour.setText(Integer.toString(sp.getInt(name_i+"_hour", 0)));
        min.setText(Integer.toString(sp.getInt(name_i+"_min", 0)));
        sec.setText(Integer.toString(sp.getInt(name_i+"_sec", 0)));
        features.setText(expandFeatures(sp.getString(name_i+"_features", ""), is_favorite));
        notes.setText(sp.getString(name_i+"_notes", ""));

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(debug){
                    String input = Userinput.getText().toString();
                    if(input.compareTo("") == 0){
                        input = "0";
                    }
                    User.setTime = Long.parseLong(input);
                }
                clickedStart = true;
                User.setCurrentRoute(new Route(name.getText().toString(), local.getText().toString()));
                launchWalkScreenActivity();
            }
        });
        // DEBUG switch listener
        debugSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (debugSwitch.isChecked()) {
                    debug = true;
                    startTime.setVisibility(View.VISIBLE);
                    Userinput.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "DEBUG ON", Toast.LENGTH_SHORT).show(); // display the current state for switch's
                }
                if (!debugSwitch.isChecked()) {
                    debug = false;
                    startTime.setVisibility(View.GONE);
                    Userinput.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "DEBUG OFF", Toast.LENGTH_SHORT).show(); // display the current state for switch's
                }
            }
        });
    }

    public void launchWalkScreenActivity(){
        Intent intent = new Intent(this, WalkScreenActivity.class);
        startActivity(intent);
        if(clickedStart) {
            finish();
        }
    }

    public String expandFeatures(String features, boolean is_Favorite){
        if(features.length() == 0){
            return "";
        }
        int spaceAmount = 5;
        String result = "";

        if(features.charAt(0) == 'O'){
            result += "Out and Back" +
                    new String(new char[spaceAmount]).replace('\0', ' ');
        } else {
            result += "Loop" +
                    new String(new char[spaceAmount]).replace('\0', ' ');
        }
        if(features.charAt(2) == 'F'){
            result += "Flat" +
                    new String(new char[spaceAmount]).replace('\0', ' ');
        } else {
            result += "Hilly" +
                    new String(new char[spaceAmount]).replace('\0', ' ');
        }
        if(features.charAt(4) == 'S'){
            result += "Street" +
                    new String(new char[spaceAmount]).replace('\0', ' ');
        } else {
            result += "Trail" +
                    new String(new char[spaceAmount]).replace('\0', ' ');
        }
        if(features.charAt(6) == 'E'){
            result += "Even Surface" +
                    new String(new char[spaceAmount]).replace('\0', ' ');
        } else {
            result += "Uneven Surface" +
                    new String(new char[spaceAmount]).replace('\0', ' ');
        }
        if(features.charAt(8) == 'E'){
            result += "Easy" +
                    new String(new char[spaceAmount]).replace('\0', ' ');
        } else if(features.charAt(8) == 'M') {
            result += "Moderate" +
                    new String(new char[spaceAmount]).replace('\0', ' ');
        } else {
            result += "Difficult" +
                    new String(new char[spaceAmount]).replace('\0', ' ');
        }
        if(is_Favorite){
            result += "Favorite";
        }

        return result;
    }
    @Override
    public void onBackPressed() {
        // this disabled back button on phone
        finish();
    }



}
