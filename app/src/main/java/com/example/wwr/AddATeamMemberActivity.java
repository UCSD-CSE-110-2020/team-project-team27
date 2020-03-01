package com.example.wwr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddATeamMemberActivity extends AppCompatActivity {

    private Button saveTeamMember;
    private EditText email;
    private  EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ateam_member);

        saveTeamMember = findViewById(R.id.saveTeamMember);
        email = findViewById(R.id.emailText);
        name = findViewById(R.id.nameText);

        saveTeamMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailIsValid(email.getText().toString())) {
                    UpdateFirebase.inviteTeammate(email.getText().toString(), name.getText().toString());
                    finish();
                }
            }
        });
    }

    private boolean emailIsValid(String email){
        return true;
    }
}
