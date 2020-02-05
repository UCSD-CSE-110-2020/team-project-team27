package com.example.wwr;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class TakeHeight extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_height);

        Button doneBtn = findViewById(R.id.done);

        //Setting default height to 6'0
        Spinner sp = (Spinner)findViewById(R.id.spinFeet);
        sp.setSelection(4);

        /*TextView feetView = findViewById(R.id.feet);
        TextView inchesView = findViewById(R.id.inches);

        String feetText = feetView.getText().toString();
        String inchesText = inchesView.getText().toString();

        int feet = Integer.parseInt(feetText);
        int inches = Integer.parseInt(inchesText);*/

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity();
            }
        });


    }

    public void launchMainActivity(){
        finish();
    }

}
