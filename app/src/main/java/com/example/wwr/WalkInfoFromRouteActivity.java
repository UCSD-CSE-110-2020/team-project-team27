package com.example.wwr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_info_from_route);


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

        double dist_double = Double.parseDouble(sp.getString(name_i + "_dist", "0.0"));
        dist_double = Math.round(dist_double * 100.0) / 100.0;

        boolean is_favorite = sp.getBoolean(name_i + "_isFavorite", false);

        name.setText(name_i);
        local.setText(sp.getString(name_i + "_location", ""));
        steps.setText("" + sp.getInt(name_i + "_steps", 0));
        dist.setText(Double.toString(dist_double));
        hour.setText(Integer.toString(sp.getInt(name_i+"_hour", 0)));
        min.setText(Integer.toString(sp.getInt(name_i+"_min", 0)));
        sec.setText(Integer.toString(sp.getInt(name_i+"_sec", 0)));
        features.setText(expandFeatures(sp.getString(name_i+"_features", ""), is_favorite));
        notes.setText(sp.getString(name_i+"_notes", ""));

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.setCurrentRoute(new Route(name.getText().toString(), local.getText().toString()));
                launchWalkScreenActivity();
            }
        });
    }

    public void launchWalkScreenActivity(){
        Intent intent = new Intent(this, WalkScreenActivity.class);
        startActivity(intent);
        finish();
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



}
