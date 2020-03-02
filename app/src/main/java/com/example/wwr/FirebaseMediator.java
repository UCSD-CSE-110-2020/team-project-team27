package com.example.wwr;

import java.util.ArrayList;

public class FirebaseMediator implements ViewObserver, FirebaseObserver{
    private TeamPageActivity teamPageActivity;
    private InvitationActivity invitationActivity;
    private String CURRENT_VIEW;


    //From ViewObserver
    public void updateTeamView(){
        UpdateFirebase.getTeammates(CURRENT_VIEW);
    }

    //From Firebaseobserver
    public void updateTeamList(ArrayList<String> teammatesNames, ArrayList<String> teammatesEmails){
        if(CURRENT_VIEW.equals("TeamPage")) {
            teamPageActivity.createTeamList(teammatesNames, teammatesEmails);
        }
        else if (CURRENT_VIEW.equals("InvitePage")){
            invitationActivity.createTeamList(teammatesNames, teammatesEmails);
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
}
