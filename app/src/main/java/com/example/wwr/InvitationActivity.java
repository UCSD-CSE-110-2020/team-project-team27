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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);

        ListView InviteListUI = findViewById(R.id.invite_list);
        ArrayList<Teammate> teammates = createTeamList();

        InviteListAdapter adapter = new InviteListAdapter(this, R.layout.invite_adapter_view_layout, teammates);
        InviteListUI.setAdapter(adapter);
    }

    public static ArrayList<Teammate> createTeamList(){
        // TODO: loop through database of current team member and return arraylist of Teammates object:
        // TODO: probably through calling a firestore function
        ArrayList<Teammate> tst = new ArrayList<>();
        tst.add(new Teammate("Alex Garza", "agarza@ucsd.edu"));
        tst.add(new Teammate("Will Hsu", "whsu@ucsd.edu"));
        tst.add(new Teammate("Ryan Bez", "rbez@ucsd.edu"));
        return tst;
    }

}
