package com.example.wwr;

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

        Spinner feetView = findViewById(R.id.spinFeet);
        feetView.setSelection(3);
        Spinner inchesView = findViewById(R.id.spinInch);
        inchesView.setSelection(8);

        final String feetText = feetView.getSelectedItem().toString();
        final String inchesText = inchesView.getSelectedItem().toString();

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int feet = Integer.parseInt(feetText);
                    int inches = Integer.parseInt(inchesText);
                    User.setHeight(feet, inches);
                }
                catch (NumberFormatException e) {
                    System.out.println("Cannot convert measurements successfully.");

                }

                launchMainActivity();
            }
        });




    }

    public void launchMainActivity(){
        finish();
    }


}