package com.example.wwr;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class TeamPageActivity extends AppCompatActivity {
    private static final String TAG = "TeamPageActivity";

    private FloatingActionButton plus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_page);
        Log.d(TAG, "onCreate: Team Page Started.");

        plus = findViewById(R.id.teamfab);


        ListView teamListUI = findViewById(R.id.team_list);
        ArrayList<Teammate> teammates = createTeamList();

        TeamListAdapter adapter = new TeamListAdapter(this, R.layout.team_adapter_view_layout, teammates);
        teamListUI.setAdapter(adapter);


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

    public static ArrayList<Teammate> createTeamList(){

        // TODO: loop through database of current team member and return arraylist of Teammates object:
        // TODO: probably through calling a firestore function
        ArrayList<Teammate> tst = new ArrayList<>();
        //System.err.println("Name:" +UpdateFirebase.getFireBaseField("agarza@ucsd.edu", "Name"));
        UpdateFirebase.getAnything(true, "users/agarza@ucsd.edu");
        tst.add(new Teammate(UpdateFirebase.getData("Name"), "agarza@ucsd.edu"));
        //tst.add(new Teammate("Will Hsu", "whsu@ucsd.edu"));
        //tst.add(new Teammate("Ryan Bez", "rbez@ucsd.edu"));
        return tst;
    }

}
