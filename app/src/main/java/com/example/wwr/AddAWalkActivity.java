package com.example.wwr;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddAWalkActivity extends AppCompatActivity {

    private Button cancel;
    private Button save;
    private EditText name;
    private EditText local;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_awalk);
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchHomeScreenActivity();
            }
        });
        name = findViewById(R.id.textView);
        local = findViewById(R.id.textView2);

        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().compareTo("") == 0 || local.getText().toString().compareTo("") == 0){
                    Toast.makeText(getApplicationContext(), "information incomplete", Toast.LENGTH_SHORT).show(); // display the current state for switch's
                }
                else{
                    Toast.makeText(getApplicationContext(), "information stored", Toast.LENGTH_SHORT).show(); // display the current state for switch's
                    RouteList.addRoute(new Route(name.getText().toString(), local.getText().toString()));
                    launchHomeScreenActivity();
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
    @Override
    public void onBackPressed() {
        // this disabled back button on phone
    }

}
