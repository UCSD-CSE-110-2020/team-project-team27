/* File: MainActivity
* Launches the app. Used to set up GoogleFit based on whether or not
* the app is being tested. Launches the home screen once the intialization
* is done.
 */

package com.example.wwr;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.wwr.fitness.FitnessService;
import com.example.wwr.fitness.FitnessServiceFactory;
import com.example.wwr.fitness.GoogleFitAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private String fitnessServiceKey = "GOOGLE_FIT";
    private static final String TAG = "MainActivity";
    public String notificationActivity = null;

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FitnessServiceFactory.put(fitnessServiceKey, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(HomeScreenActivity homeScreenActivity) {
                return new GoogleFitAdapter(homeScreenActivity);
            }
        });

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        UpdateFirebase.setDatabase(FirebaseFirestore.getInstance());


        Intent intent = getIntent();

        //Only true when clicked from notfication
        if (intent.getExtras() != null) {
            System.err.println("hi!" + intent.getExtras().size());

            notificationActivity = "Propose";
        }


        if(account == null || account.getEmail() == null || account.getDisplayName() == null) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                    requestIdToken("657041263562-5qidpm8suvcttntjj6fc8o2i6f2n7fm1.apps.googleusercontent.com")
                    .requestEmail().requestProfile()
                    .build();

            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            signIn();
        } else {
            User.setEmail(account.getEmail());
            UpdateFirebase.subscribeToNotifications();
            UpdateFirebase.getName();
            //UpdateFirebase.subscribeToNotifications();
            Log.d(TAG, "IN MAIN ELSE" + notificationActivity);
            launchHomeScreenActivity();
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 0) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            User.setEmail(account.getEmail());
            User.setName(account.getDisplayName());
            UpdateFirebase.subscribeToNotifications();
            System.err.println("Username is : " + account.getDisplayName());
            UpdateFirebase.setupUser(account.getDisplayName());
            // add document with new google login
            Log.d(TAG, "IN MAIN HANDLESIGNIN" + notificationActivity);
            launchHomeScreenActivity();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("MainActivity", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    public void launchHomeScreenActivity(){
        Intent intent = new Intent(this, HomeScreenActivity.class);
        intent.putExtra(HomeScreenActivity.FITNESS_SERVICE_KEY, fitnessServiceKey);
        Log.d(TAG, "IN MAIN" + notificationActivity);
        intent.putExtra("notificationLaunch", notificationActivity);
        startActivity(intent);
    }

}