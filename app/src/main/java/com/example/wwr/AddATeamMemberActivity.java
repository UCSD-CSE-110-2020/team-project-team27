package com.example.wwr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddATeamMemberActivity extends AppCompatActivity {

    private Button saveTeamMember;
    private EditText email;
    private EditText name;

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
                if(name.getText().toString().compareTo("") == 0 ||
                        email.getText().toString().compareTo("") == 0){
                    // display the current state for switch's
                    Toast.makeText(getApplicationContext(),
                            "information incomplete", Toast.LENGTH_SHORT).show();
                } else if(!emailIsValid(email.getText().toString())) {
                    Toast.makeText(getApplicationContext(),
                            "Invalid Email", Toast.LENGTH_LONG).show();
                } else{
                    UpdateFirebase.inviteTeammate(email.getText().toString(), name.getText().toString());
                    Toast.makeText(getApplicationContext(),
                            "invitation sent", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        email.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) { return true; }
                return false;
            }
        });

        name.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) { return true; }
                return false;
            }
        });
    }

    private boolean emailIsValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}
