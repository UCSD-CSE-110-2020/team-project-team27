package com.example.wwr;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateFirebase {
    public static final String USER_KEY = "users";
    public static final String ROUTES_KEY = "routes";
    public static final String TEAMS_KEY = "team";
    public static final String TM_KEY = "My Team";
    public static final String R_KEY = "My Routes";


    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference users = db.collection(USER_KEY);

    // this method might not be necessary
    public static void addNewUser(String email) {
        db.collection(USER_KEY).document(email).collection(ROUTES_KEY).document(R_KEY)
                .set(new HashMap<String, String>())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("created user document");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("failed to create user document");
                    }
                });
        db.collection(USER_KEY).document(email).collection(TEAMS_KEY).document(TM_KEY)
                .set(new HashMap<String, String>())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("created user document");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("failed to create user document");
                    }
                });
    }

    public static void addedRoute(String route) {

    }

}
