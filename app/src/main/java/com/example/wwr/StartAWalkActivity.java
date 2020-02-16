package com.example.wwr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class StartAWalkActivity extends AppCompatActivity {

    private Button start;
    private EditText name;
    private EditText local;
    private boolean debug = false;
    private TextView set_time;
    private EditText debugTime;
    private Switch debugSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_awalk);

        start = findViewById(R.id.save);
        name = findViewById(R.id.textView);
        local = findViewById(R.id.textView2);
        set_time = findViewById(R.id.debug_time);
        debugTime = findViewById(R.id.input);
        debugSwitch = findViewById(R.id.debugMode2);
        set_time.setVisibility(View.GONE);
        debugTime.setVisibility(View.GONE);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().compareTo("") == 0 ||
                        local.getText().toString().compareTo("") == 0){
                    // display the current state for switch's
                    Toast.makeText(getApplicationContext(), "information incomplete", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(storeRoute(name.getText().toString(), local.getText().toString())){
                        Route curRoute = new Route(name.getText().toString(), local.getText().toString());
                        storeRoute(name.getText().toString(), local.getText().toString()); // store to database
                        User.setCurrentRoute(curRoute);
                        if(debug){
                            String input = debugTime.getText().toString();
                            if(input.compareTo("") == 0){ input = "0"; }
                            User.setTime = Long.parseLong(input);
                        }
                        launchWalkScreenActivity();
                    }else{
                        // display the current state for switch's
                        Toast.makeText(getApplicationContext(),
                                "Route existed. Please enter another name.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        // DEBUG switch listener
        debugSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (debugSwitch.isChecked()) {
                    debug = true;
                    set_time.setVisibility(View.VISIBLE);
                    debugTime.setVisibility(View.VISIBLE);
                    // display the current state for switch's
                    Toast.makeText(getApplicationContext(), "DEBUG ON", Toast.LENGTH_SHORT).show();
                }
                if (!debugSwitch.isChecked()) {
                    debug = false;
                    set_time.setVisibility(View.GONE);
                    debugTime.setVisibility(View.GONE);
                    // display the current state for switch's
                    Toast.makeText(getApplicationContext(), "DEBUG OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });

        name.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) { return true; }
                return false;
            }
        });
        local.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) { return true; }
                return false;
            }
        });
    }

    public void launchWalkScreenActivity(){
        Intent intent = new Intent(this, WalkScreenActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean storeRoute(String name, String location){
        SharedPreferences routeCount = getSharedPreferences("routeInfo", MODE_PRIVATE);
        Set<String> routeList = routeCount.getStringSet("routeNames", null);
        if(routeList == null){
            System.err.println("Critical Error: SharePreference not existed.");
            return false;
        }
        else if(routeList.contains(name)){
            System.err.println("Error: duplicates.");
            return false;
        }
        SharedPreferences.Editor editor = routeCount.edit();
        routeList.add(name);
        editor.putStringSet("routeNames", routeList); // store the updated route name list
        editor.putString(name+"_location", location); // store location correspond to the route
        editor.apply();
        return true;
    }

}
