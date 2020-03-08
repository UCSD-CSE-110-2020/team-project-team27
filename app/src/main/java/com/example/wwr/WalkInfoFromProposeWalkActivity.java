package com.example.wwr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class WalkInfoFromProposeWalkActivity extends AppCompatActivity {
    ProposedRoute proposedRoute;
    FirebaseMediator mediator;
    TextView icon;
    TextView proposer;
    TextView PWname;
    TextView PWloc;
    TextView PWtime;
    TextView PWdate;
    TextView PWfeature;
    TextView attendee;
    TextView pending;
    TextView reject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_info_from_propose);

        Intent intent = getIntent();

        icon = findViewById(R.id.icon3);
        proposer = findViewById(R.id.PWproposerNm);
        PWname = findViewById(R.id.RouteTitle);
        PWloc = findViewById(R.id.startLoc);
        PWtime = findViewById(R.id.steps);
        PWdate = findViewById(R.id.steps2);
        PWfeature = findViewById(R.id.features);
        attendee = findViewById(R.id.attendees);
        pending = findViewById(R.id.attendees4);
        reject = findViewById(R.id.cantgo);

        PWname.setText(intent.getStringExtra("RW_NAME"));
        PWloc.setText(intent.getStringExtra("PW_LOC"));
        PWtime.setText(intent.getStringExtra("PW_TIME"));
        PWdate.setText(intent.getStringExtra("PW_DATE"));
        PWfeature.setText(intent.getStringExtra("PW_FEA"));
        attendee.setText(intent.getStringExtra("PW_ATTENDEE"));
        proposer.setText(intent.getStringExtra("PW_USER_NM"));
        icon.setText(intent.getStringExtra("PW_USER_INI"));

        // TODO: Color is not working
        // System.err.println("Color is" + intent.getStringExtra("PW_COLOR"));
        // ((GradientDrawable)icon.getBackground()).setColor(Color.parseColor(intent.getStringExtra("PW_COLOR")));

        //pending.setText();
        //reject.setText();

        mediator = new FirebaseMediator();
        mediator.addProposedWalkInfo(this);
        mediator.updateTeamView();
    }

    //Callback method after accept/reject invitation
    public void updateParticipants(){
        mediator.getProposedWalks();
    }

    // Use this method to display pending (maybe save results into instance variable and when updateParticipants
    // is called change what is displayed?
    public void getTeammates(ArrayList<String> teammatesNames, ArrayList<String> teammatesEmails,
                                      ArrayList<String> teammateColors){
        //Accepted: proposedRoute.getAttendee();
        //Rejected: proposedRoute.getRejected();
        //Pending: getPendingTeammates(teammateNames);

    }

    public String getPendingTeammates(ArrayList<String> teammatesNames){
        String result = "";
        for(int i = 0; i < teammatesNames.size(); i++){
            if(proposedRoute.getAttendee().contains(teammatesNames.get(i)) ||
                    proposedRoute.getRejected().contains(teammatesNames.get(i))){
                continue;
            }

            result += teammatesNames.get(i);
        }

        return result;
    }

    @Override
    public void onBackPressed() {
        // this disabled back button on phone
        finish();
    }
}
