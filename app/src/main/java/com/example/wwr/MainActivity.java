package com.example.wwr;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int dailyStepsTotal = 1000000; // hardcoded value for demo

        TextView dailyStepsView = findViewById(R.id.dailyStepsValue);
        dailyStepsView.setText(String.valueOf(dailyStepsTotal));

        int mileageTotal = 500; // hardcoded value for demo

        TextView mileageView = findViewById(R.id.mileageValue);
        mileageView.setText(String.valueOf(mileageTotal));

        Button startBtn = findViewById(R.id.startRouteButton);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                EditText num1View = findViewById(R.id.number_1);
                EditText num2View = findViewById(R.id.number_2);

                String num1Text = num1View.getText().toString();
                String num2Text = num2View.getText().toString();

                int num1 = toIntNullsafe(num1Text);
                int num2 = toIntNullsafe(num2Text);

                int answer = num1 * num2;

                TextView answerView = findViewById(R.id.answer);
                answerView.setText(String.valueOf(answer));
                 */
            }
        });

        Button addBtn = findViewById(R.id.addButton);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                EditText num1View = findViewById(R.id.number_1);
                EditText num2View = findViewById(R.id.number_2);

                String num1Text = num1View.getText().toString();
                String num2Text = num2View.getText().toString();

                int num1 = toIntNullsafe(num1Text);
                int num2 = toIntNullsafe(num2Text);

                int answer = num1 * num2;

                TextView answerView = findViewById(R.id.answer);
                answerView.setText(String.valueOf(answer));
                 */
            }
        });

        Button routesBtn = findViewById(R.id.routesButton);
        routesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                EditText num1View = findViewById(R.id.number_1);
                EditText num2View = findViewById(R.id.number_2);

                String num1Text = num1View.getText().toString();
                String num2Text = num2View.getText().toString();

                int num1 = toIntNullsafe(num1Text);
                int num2 = toIntNullsafe(num2Text);

                int answer = num1 * num2;

                TextView answerView = findViewById(R.id.answer);
                answerView.setText(String.valueOf(answer));
                 */
            }
        });

        /*
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
         */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
