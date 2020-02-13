package com.example.wwr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
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

        /*double dist_double = Double.parseDouble(sp.getString(latestRoute+"_dist", ""));
        dist_double = Math.round(dist_double * 100.0) / 100.0;

        name.setText(name_i);
        local.setText(sp.getString(name_i + "_location", ""));
        steps.setText("" + sp.getInt(name_i + "_steps", 0));
        dist.setText(Double.toString(dist_double));
        hour.setText(Integer.toString(routeCount.getInt(latestRoute+"_hour", 0)));
        min.setText(Integer.toString(routeCount.getInt(latestRoute+"_min", 0)));
        sec.setText(Integer.toString(routeCount.getInt(latestRoute+"_sec", 0)));*/
    }
}
