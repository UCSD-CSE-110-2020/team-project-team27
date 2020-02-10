package com.example.wwr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FeaturesActivity extends AppCompatActivity {
    private Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features);
        done = findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchHomeScreenActivity();
            }
        });
    }
    public void launchHomeScreenActivity(){
        // don't know how to go back home yet
        /*Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);*/
    }
}
