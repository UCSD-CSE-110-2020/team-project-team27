package com.example.wwr;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class TakeHeight extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_height);

        Button doneBtn = findViewById(R.id.done);
        doneBtn.setEnabled(false);

        EditText feetView = findViewById(R.id.feet);
        EditText inchesView = findViewById(R.id.inches);

        String feetText = feetView.getText().toString();
        String inchesText = inchesView.getText().toString();

        int feet = Integer.parseInt(feetText);

        User.setHeight();

        if(User.heightIsValid()) {

        }
    }

}
