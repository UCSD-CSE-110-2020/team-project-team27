package com.example.wwr;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class TakeHeightActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    static String feetText;
    static String inchesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_height);

        Button doneBtn = findViewById(R.id.done);

        Spinner feetView = findViewById(R.id.spinFeet);
        Spinner inchesView = findViewById(R.id.spinInch);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R
                .array.feet_height, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        feetView.setAdapter(adapter);
        feetView.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R
                .array.inch_height, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inchesView.setAdapter(adapter2);
        inchesView.setOnItemSelectedListener(this);

        feetView.setSelection(3);
        inchesView.setSelection(4);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int feet = Integer.parseInt(feetText);
                    int inches = Integer.parseInt(inchesText);
                    User.setHeight(feet, inches);
                    SharedPreferences sp = getSharedPreferences("height", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();

                    editor.putInt("FEET", feet);
                    editor.putInt("INCH", inches);

                    editor.apply();
                    Toast.makeText(TakeHeightActivity.this, "Saved", Toast.LENGTH_SHORT).show();
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId()== R.id.spinFeet){
                feetText = parent.getItemAtPosition(position).toString();
        }
        else{ // code for second spinner
                inchesText = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        return;
    }
}