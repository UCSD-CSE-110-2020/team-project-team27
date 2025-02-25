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
    private ArrayList<ViewObserver> observers;
    ListView teamListUI;

    FirebaseMediator mediator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_page);
        Log.d(TAG, "onCreate: Team Page Started.");

        plus = findViewById(R.id.teamfab);

        teamListUI = findViewById(R.id.team_list);

        //Adding to mediator
        mediator = new FirebaseMediator();
        mediator.addTeamView(this);

        mediator.updateTeamView();

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
        mediator.unregister();
        finish();
    }

    public void createTeamList(ArrayList<String> teammateNames, ArrayList<String> teammatesEmails,
                               ArrayList<String> teammateColor, ArrayList<Boolean> pending){
        ArrayList<Teammate> tst = new ArrayList<>();

        for(int i = 0; i < teammateNames.size(); i++){
            int color;
            try {
                color = Integer.parseInt(teammateColor.get(i));
            } catch (NumberFormatException e){
                color = 0;
            }
            System.out.println("NAME " + teammateNames.get(i));
            tst.add(new Teammate(teammateNames.get(i), teammatesEmails.get(i), color, pending.get(i)));
        }

        TeamListAdapter adapter = new TeamListAdapter(this, R.layout.team_adapter_view_layout, tst);
        teamListUI.setAdapter(adapter);
    }

    @Override
    public void onBackPressed(){
        mediator.unregister();
        finish();
    }
}