package com.example.wwr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StartAWalkActivity extends AppCompatActivity {

    private Button cancel;
    private Button start;
    private EditText name;
    private EditText local;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_awalk);
        cancel = findViewById(R.id.cancel);
        start = findViewById(R.id.save);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchHomeScreenActivity();
            }
        });
        name = findViewById(R.id.textView);
        local = findViewById(R.id.textView2);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().compareTo("") == 0 || local.getText().toString().compareTo("") == 0){
                    Toast.makeText(getApplicationContext(), "information incomplete", Toast.LENGTH_SHORT).show(); // display the current state for switch's
                }
                else{
                    // TODO: need to check for duplicate routes
                    Route curRoute = new Route(name.getText().toString(), local.getText().toString());
                    RouteList.addRoute(curRoute);
                    User.setCurrentRoute(curRoute);
                    launchWalkScreenActivity();
                }
            }
        });

        name.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    return true;
                }
                return false;
            }
        });
        local.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    return true;
                }
                return false;
            }
        });
    }
    public void launchHomeScreenActivity(){
        finish();
    }
    public void launchWalkScreenActivity(){
        Intent intent = new Intent(this, WalkScreenActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        // this disabled back button on phone
    }

}
