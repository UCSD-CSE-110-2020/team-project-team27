package com.example.wwr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WalkInfoFromProposeWalkActivity extends AppCompatActivity {
    ProposedRoute proposedRoute;
    FirebaseMediator mediator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_walk_info_from_propose);

        mediator = new FirebaseMediator();
        mediator.addProposedWalkInfo(this);

        //Callback is getTeammates()
        mediator.updateTeamView();
    }

    //Callback method after accept/reject invitation from onclick (call mediator.accept/reject)...
    public void updateParticipants(){
        mediator.getProposedWalks();
    }

<<<<<<< HEAD:app/src/main/java/com/example/wwr/WalkInfoFromProposeWalkActivity.java
    // Use this method to display pending (maybe save results into instance variable and when updateParticipants
    // is called change what is displayed?
=======
    //Use this method to display pending (maybe save results into instance variable and when updateParticipants
    // is called change what is displayed?)
>>>>>>> 8dc62ad20c67fb1eb386021300a635747210a97e:app/src/main/java/com/example/wwr/WalkInfoFromProposeWalk.java
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
