package com.example.wwr;

import java.util.ArrayList;

public class TeamViewFirebaseMediator implements ViewObserver, FirebaseObserver{
    private TeamPageActivity teamPageActivity;


    //From ViewObserver
    public void updateTeamView(){
        UpdateFirebase.getTeammates();
    }

    //From Firebaseobserver
    public void updateTeamList(ArrayList<String> teammatesNames, ArrayList<String> teammatesEmails){
        teamPageActivity.createTeamList(teammatesNames, teammatesEmails);
    }

    public void addView(TeamPageActivity activity){
        UpdateFirebase.registerObserver(this);
        teamPageActivity = activity;
    }
}
