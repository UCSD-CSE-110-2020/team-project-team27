package com.example.wwr;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class InvitationActivity extends AppCompatActivity {
    private ListView inviteListUI;
    FirebaseMediator mediator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);

        inviteListUI = findViewById(R.id.invite_list);

        //Adding to mediator
        mediator = new FirebaseMediator();
        mediator.addView(this);

        mediator.updateTeamView();
    }

    public void createTeamList(ArrayList<String> teammateNames, ArrayList<String> teammatesEmails){
        ArrayList<Teammate> tst = new ArrayList<>();

        for(int i = 0; i < teammateNames.size(); i++){
            System.out.println("NAME " + teammateNames.get(i));
            tst.add(new Teammate(teammateNames.get(i), teammatesEmails.get(i)));
        }

        TeamListAdapter adapter = new TeamListAdapter(this, R.layout.invite_adapter_view_layout, tst);
        inviteListUI.setAdapter(adapter);
    }

}
