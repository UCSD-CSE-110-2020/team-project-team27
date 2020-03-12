package com.example.wwr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView rejectText;
    TextView rejectText2;
    Button accept;
    Button rejectBtn;
    Button rejectBtn2;
    Button schedule;
    Button withdraw;
    String PWOwnerEmail;

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
        accept = findViewById(R.id.propose_btn);
        rejectBtn = findViewById(R.id.start);
        rejectBtn2 = findViewById(R.id.start2);
        schedule = findViewById(R.id.schedule_btn);
        withdraw = findViewById(R.id.withdraw_btn);
        rejectText = findViewById(R.id.declineBtnText2);
        rejectText2 = findViewById(R.id.declineBtnText);

        mediator = new FirebaseMediator();
        mediator.addProposedWalkInfo(this);
        mediator.updateTeamView();

        PWname.setText(intent.getStringExtra("RW_NAME"));
        PWloc.setText(intent.getStringExtra("PW_LOC"));
        PWtime.setText(intent.getStringExtra("PW_TIME"));
        PWdate.setText(intent.getStringExtra("PW_DATE"));
        PWfeature.setText(intent.getStringExtra("PW_FEA"));
        attendee.setText(intent.getStringExtra("PW_ATTENDEE"));
        reject.setText(intent.getStringExtra("PW_REJECT"));
        proposer.setText(intent.getStringExtra("PW_USER_NM"));
        icon.setText(intent.getStringExtra("PW_USER_INI"));
        ((GradientDrawable)icon.getBackground()).setColor(Integer.parseInt(intent.getStringExtra("PW_COLOR")));

        PWOwnerEmail = intent.getStringExtra("PW_EMAIL");

        proposedRoute = new ProposedRoute(
                intent.getStringExtra("RW_NAME"),
                intent.getStringExtra("PW_LOC"),
                intent.getStringExtra("PW_FEA"),
                intent.getStringExtra("PW_ATTENDEE"),
                intent.getStringExtra("PW_DATE"),
                intent.getStringExtra("PW_TIME"),
                "false", PWOwnerEmail,
                intent.getStringExtra("PW_COLOR"),
                intent.getStringExtra("PW_USER_NM"),
                intent.getStringExtra("PW_REJECT"));

        if(PWOwnerEmail.equals(User.getEmail())){
            // I proposed this walk
            accept.setVisibility(View.INVISIBLE);
            rejectBtn.setVisibility(View.INVISIBLE);
            rejectBtn2.setVisibility(View.INVISIBLE);
            rejectText.setVisibility(View.INVISIBLE);
            rejectText2.setVisibility(View.INVISIBLE);
        }else{
            schedule.setVisibility(View.INVISIBLE);
            withdraw.setVisibility(View.INVISIBLE);
        }

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update Attendee string in proposedRoute to add user (proposedRoute.setAttendee(proposedRoute.getAtendee() + User.getName())
                if(proposedRoute.getAttendee().contains(User.getName())){
                    Toast.makeText(getApplicationContext(),
                            "Already accepted the route", Toast.LENGTH_SHORT).show();
                }else{
                    mediator.acceptProposedWalk(PWname.getText().toString(), PWOwnerEmail);
                    String[] newAttendeeReject = proposedRoute.updateAttendee(User.getName(), proposedRoute.getAttendee(), proposedRoute.getRejected());// if user is in reject

                    proposedRoute.setAttendee(newAttendeeReject[0]);
                    proposedRoute.setReject(newAttendeeReject[1]);

                    mediator.updateTeamView(); // which produces the pending string

                    Toast.makeText(getApplicationContext(),
                        "Accepted the Proposed Walk", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectRoute("\n(not a good route)");
            }
        });

        rejectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectRoute("\n(not a good route)");
            }
        });

        rejectBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectRoute("\n(bad time)");
            }
        });

        rejectText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectRoute("\n(bad time)");
            }
        });

        PWloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchGoogleMaps();
            }
        });

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediator.scheduleProposedWalk(PWname.getText().toString(), User.getEmail());
                Toast.makeText(getApplicationContext(),
                        "Scheduled your Proposed Walk " + PWname.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediator.withdrawProposedWalk(PWname.getText().toString(), User.getEmail());
                Toast.makeText(getApplicationContext(),
                        "Withdrew your Proposed Walk" + PWname.getText().toString(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void rejectRoute(String reason){
        if(proposedRoute.getRejected().contains(User.getName() + reason)){
            Toast.makeText(getApplicationContext(),
                    "Already rejected the route", Toast.LENGTH_SHORT).show();
        }else{
            mediator.rejectProposedWalk(PWname.getText().toString(), PWOwnerEmail, reason);
            String[] newAttendeeReject = proposedRoute.updateReject(User.getName(),
                    proposedRoute.getAttendee(), proposedRoute.getRejected(), reason);

            proposedRoute.setAttendee(newAttendeeReject[0]);
            proposedRoute.setReject(newAttendeeReject[1]);

            mediator.updateTeamView(); // which produces the pending string
            Toast.makeText(getApplicationContext(),
                    "Rejected the Proposed Walk Invite", Toast.LENGTH_SHORT).show();
        }
    }

    public void launchGoogleMaps(){
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode((String) PWloc.getText()));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    // Use this method to display pending (maybe save results into instance variable and when updateParticipants
    // is called change what is displayed?
    public void getTeammates(ArrayList<String> teammatesNames, ArrayList<String> teammatesEmails,
                                      ArrayList<String> teammateColors){
        attendee.setText(ProposedRoute.getFormattedList(proposedRoute.getAttendee()));
        reject.setText(ProposedRoute.getFormattedList(proposedRoute.getRejected()));
        pending.setText(ProposedRoute.getFormattedList(getPendingTeammates(teammatesNames, teammatesEmails)));
    }

    // TODO: use email cross check instead
    public String getPendingTeammates(ArrayList<String> teammatesNames, ArrayList<String> teammatesEmails){
        System.err.println("Attendee: " + proposedRoute.getAttendee());
        System.err.println("Rejected: " + proposedRoute.getRejected());
        System.err.println("Owner: " + proposedRoute.getOwnerEmail());

        String result = "";
        for(int i = 0; i < teammatesNames.size(); i++){
            System.err.println("new Teammate " + teammatesNames.get(i) + teammatesEmails.get(i));
            if(proposedRoute.getAttendee().contains(teammatesNames.get(i)) ||
                    proposedRoute.getRejected().contains(teammatesNames.get(i) + "\n(not a good route)") ||
                    proposedRoute.getRejected().contains(teammatesNames.get(i) + "\n(bad time)") ||
                    proposedRoute.getOwnerEmail().equals(teammatesEmails.get(i))){
                continue;
            }

            result += teammatesNames.get(i) + ",";
        }

        return result;
    }

    @Override
    public void onBackPressed() {
        // this disabled back button on phone
        finish();
    }
}
