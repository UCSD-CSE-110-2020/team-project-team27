package com.example.wwr;

import android.view.View;

import java.util.ArrayList;

import static com.example.wwr.Tab2Fragment.displayTeamList;

public class FirebaseMediator implements ViewObserver, FirebaseObserver{
    private TeamPageActivity teamPageActivity;
    private InvitationActivity invitationActivity;
    private String CURRENT_VIEW;


    //From ViewObserver
    public void updateTeamView(){
        UpdateFirebase.getTeammates(CURRENT_VIEW);
    }

    //From Firebaseobserver
    public void updateTeamList(ArrayList<String> teammatesNames, ArrayList<String> teammatesEmails,
                               ArrayList<String> teammateColors){
        if(CURRENT_VIEW.equals("TeamPage")) {
            teamPageActivity.createTeamList(teammatesNames, teammatesEmails, teammateColors);
        }
        else if (CURRENT_VIEW.equals("InvitePage")){
            invitationActivity.createTeamList(teammatesNames, teammatesEmails, teammateColors);
        }
    }

    public void addView(TeamPageActivity activity){
        UpdateFirebase.registerObserver(this);
        teamPageActivity = activity;
        CURRENT_VIEW = "TeamPage";
    }
    public void addView(InvitationActivity activity){
        UpdateFirebase.registerObserver(this);
        invitationActivity = activity;
        CURRENT_VIEW = "InvitePage";
    }

    public void addTeamView(View view){
        UpdateFirebase.registerObserver(this);
    }

    public void updateTeamRoute(ArrayList<Route> teammateRoutes){
        displayTeamList(teammateRoutes);
    }
}
