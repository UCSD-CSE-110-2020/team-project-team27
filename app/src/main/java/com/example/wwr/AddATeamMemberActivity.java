package com.example.wwr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddATeamMemberActivity extends AppCompatActivity {

    private Button saveTeamMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ateam_member);

        saveTeamMember = findViewById(R.id.saveTeamMember);

        saveTeamMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //launchTeamPageActivity();
                finish();
            }
        });
    }

    public void launchTeamPageActivity(){
        Intent intent = new Intent(this, TeamPageActivity.class);
        startActivity(intent);
    }
}
