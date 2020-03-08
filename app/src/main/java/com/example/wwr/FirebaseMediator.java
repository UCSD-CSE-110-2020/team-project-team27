package com.example.wwr;

import android.app.Activity;
import android.view.View;

import java.util.ArrayList;

public class FirebaseMediator implements ViewObserver, FirebaseObserver{
    private String CURRENT_VIEW = "";
    private Object callingObject;

    public void unregister(){
        UpdateFirebase.unregisterObserver(this);
    }



    public void inviteSuccessful(boolean isSuccessful){
        if(callingObject.getClass() != Tab2Fragment.class){
            return;
        }
        // update teammateRoutes with user walk info is the user has gone on a walk
        ((AddATeamMemberActivity) callingObject).displayTeamList(isSuccessful);
    }


    //From TeamPageActivity
    public void addTeamView(Object callingObject){
        UpdateFirebase.registerObserver(this);
        this.callingObject = callingObject;
        CURRENT_VIEW = "TeamPage";
    }

    //From TeamPageActivity
    public void updateTeamView(){
        UpdateFirebase.getTeammates(CURRENT_VIEW);
    }

    //From UpdateFirebase
    public void updateTeamList(ArrayList<String> teammatesNames, ArrayList<String> teammatesEmails,
                               ArrayList<String> teammateColors, ArrayList<Boolean> pending){
        if(CURRENT_VIEW.equals("TeamPage")) {
            if(callingObject.getClass() != TeamPageActivity.class){
                return;
            }
            ((TeamPageActivity) callingObject).
            createTeamList(teammatesNames, teammatesEmails, teammateColors, pending);
        } else if (CURRENT_VIEW.equals("InvitePage")){
            if(callingObject.getClass() != InvitationActivity.class){
                return;
            }
            ((InvitationActivity) callingObject).
                    createTeamList(teammatesNames, teammatesEmails, teammateColors);
        } else if (CURRENT_VIEW.equals("WalkInfoFromProposeWalk")){
            ((WalkInfoFromProposeWalkActivity) callingObject).
                    getTeammates(teammatesNames, teammatesEmails, teammateColors);
        }
    }



    //From TabFragment2
    public void addRouteView(Object callingObject){
        UpdateFirebase.registerObserver(this);
        this.callingObject = callingObject;
    }

    //From TabFragment2
    public void getTeamRoutes(){
        UpdateFirebase.getTeamsRoutes();
    }

    //From UpdateFirebase
    public void updateTeamRoute(ArrayList<Route> teammateRoutes){
        if(callingObject.getClass() != Tab2Fragment.class){
            return;
        }
        // update teammateRoutes with user walk info is the user has gone on a walk
        teammateRoutes = UserSharePreferences.updateTeamRoute(teammateRoutes);
        ((Tab2Fragment) callingObject).displayTeamList(teammateRoutes);
    }


    //From InvitePageActivity
    public void addInviteActivity(Object callingObject){
        UpdateFirebase.registerObserver(this);
        this.callingObject = callingObject;
        CURRENT_VIEW = "InvitePage";
    }

    //Defined eariler in updateTeamList

    //From UpdateFirebase
    public void updateInviteList(ArrayList<String> teammatesNames, ArrayList<String> teammatesEmails,
                                 ArrayList<String> teammateColors, ArrayList<Boolean> pending){
        if(callingObject.getClass() != InvitationActivity.class){
            return;
        }
        ((InvitationActivity) callingObject).createTeamList(teammatesNames, teammatesEmails, teammateColors);
    }



    //From Tab3
    public void addTab3(Object callingObject){
        UpdateFirebase.registerObserver(this);
        this.callingObject = callingObject;
    }

    //From Tab3
    public void getProposedWalks(){
        UpdateFirebase.getProposedRoutes();
    }

    //From UpdateFirebase
    public void updateProposedRouteList(ArrayList<ProposedRoute> proposedRouteArrayList){
        //will probably need to call a new method displayProposedRoutes in Tab3Fragment
        //similar to displayTeamList in Tab2Fragment
        if(callingObject.getClass() != Tab3Fragment.class){
            return;
        }
        ((Tab3Fragment) callingObject).displayProposedRoutes(proposedRouteArrayList);
    }

    //From WalkInfoFromProposeWalk
    void addProposedWalkInfo(Object callingObject){
        UpdateFirebase.registerObserver(this);
        this.callingObject = callingObject;
        CURRENT_VIEW = "WalkInfoFromProposeWalk";
    }

    //From WalkInfoProposedRouteActivity
    public void acceptProposedWalk(String walkname, String walkOwner){
        UpdateFirebase.acceptProposedWalk(walkname, walkOwner);
    }

    //From WalkInfoProposedRouteActivity
    public void rejectProposedWalk(String walkname, String walkOwner){
        UpdateFirebase.rejectProposedWalk(walkname, walkOwner);
    }

    //From WalkInfoProposedRouteActivity
    public void scheduleProposedWalk(String walkname, String walkOwner){
        UpdateFirebase.scheduleProposedWalk(walkname, walkOwner);
    }

    //From WalkInfoProposedRouteActivity
    public void withdrawProposedWalk(String walkname, String walkOwner){
        UpdateFirebase.withDrawProposedWalk(walkname, walkOwner);
    }

    //Possibly callback fomr schedule/withdraw to reload page

    //From UpdateFirebase
    public void updateParticipants(){
        if(callingObject.getClass() != WalkInfoFromProposeWalkActivity.class){
            return;
        }

        ((WalkInfoFromProposeWalkActivity) callingObject).updateParticipants();
    }


}
