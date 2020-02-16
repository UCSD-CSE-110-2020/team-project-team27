package com.example.wwr;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;

public class AddAWalkActivity extends AppCompatActivity {
    private static final String TAG = "AddAWalkActivity";

    private Button save;
    private EditText name;
    private EditText local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_awalk);
        Log.d(TAG, "onCreate: Started.");

        save = findViewById(R.id.save);
        name = findViewById(R.id.textView);
        local = findViewById(R.id.textView2);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().compareTo("") == 0 ||
                        local.getText().toString().compareTo("") == 0){
                    // display the current state for switch's
                    Toast.makeText(getApplicationContext(),
                            "information incomplete", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(storeRoute(name.getText().toString(), local.getText().toString())){
                        launchHomeScreenActivity();
                        // display the current state for switch's
                        Toast.makeText(getApplicationContext(),
                                "information stored", Toast.LENGTH_SHORT).show();
                    }else{
                        // display the current state for switch's
                        Toast.makeText(getApplicationContext(),
                                "Route existed. Please enter another name.", Toast.LENGTH_LONG).show();
                    }
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

    public void launchHomeScreenActivity(){ finish(); }

    public boolean storeRoute(String name, String location){
        SharedPreferences routeCount = getSharedPreferences("routeInfo", MODE_PRIVATE);
        Set<String> routeList = routeCount.getStringSet("routeNames", null);

        if(routeList == null){
            System.err.println("Critical Error: SharePreference not existed.");
            return false;
        }
        else if(routeList.contains(name)){ return false; }

        SharedPreferences.Editor editor = routeCount.edit();
        routeList.add(name);

        editor.putStringSet("routeNames", routeList); // store the updated route name list
        editor.putString(name+"_location", location); // store location correspond to the route
        editor.apply();

        return true;
    }

}
