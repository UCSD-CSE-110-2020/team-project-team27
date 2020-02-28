package com.example.wwr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;

public class TeamPageActivity extends AppCompatActivity {
    private static final String TAG = "TeamPageActivity";

    private FloatingActionButton plus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_page);
        Log.d(TAG, "onCreate: Team Page Started.");

        plus = findViewById(R.id.teamfab);


        //ListView teamListUI = findViewById(R.id.team_list);
        //ArrayList<Teammates> teammates = new ArrayList<>();
        populateList(); //TODO update method signature, add team lists to populate with

        //TeamListAdapter adapter = new TeamListAdapter(this, R.layout.adapter_view_layout, teammates);
        //teamListUI.setAdapter(adapter);


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAddATeamMemberActivity();
            }
        });
    }

    public void launchAddATeamMemberActivity(){
        Intent intent = new Intent(this, AddATeamMemberActivity.class);
        startActivity(intent);
    }

    // TODO add population
    public void populateList(){

    }

}
