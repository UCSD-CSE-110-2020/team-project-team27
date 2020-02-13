package com.example.wwr;

import androidx.appcompat.app.AppCompatActivity;

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

        name = findViewById(R.id.Ftitle);
        local = findViewById(R.id.startLoc);
        steps = findViewById(R.id.steps);
        dist = findViewById();
        hour = findViewById();
        min = findViewById();
        sec = findViewById();
        features = findViewById();
        notes = findViewById();
        start = findViewById();

        String name_i = getIntent().getStringExtra("CLICKED_NAME");
    }
}
