package com.example.wwr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartAWalkActivity extends AppCompatActivity {

    private Button cancel;
    private Button start;
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
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchWalkScreenActivity();
            }
        });

    }
    public void launchHomeScreenActivity(){
        finish();
    }
    public void launchWalkScreenActivity(){
        Intent intent = new Intent(this, AddAWalkActivity.class);
        startActivity(intent);
    }


}
