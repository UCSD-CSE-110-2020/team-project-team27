package com.example.wwr;

import android.graphics.Color;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class UpdateFirebase {
    public static final String USER_KEY = "users";
    public static final String ROUTES_KEY = "routes";
    public static final String TEAMS_KEY = "team";
    public static final String INVITE_KEY = "invites";

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static Map<String, Object> data;

    public static void setupUser(String name){
        Map<String, String> userInfo = new HashMap<>();
        // create name and color field for new registered user
        userInfo.put("Name", name);
        userInfo.put("Color", ""+randomColorGenerator());
        // create a document called [route name input] with a hashmap of route information
        db.collection(USER_KEY).document(User.getEmail()).set(userInfo);
    }

    public static void addedRoute(String route, String loc) {
        CollectionReference routeCollection = db.collection(USER_KEY).document(User.getEmail()).collection(ROUTES_KEY);
        Map<String, String> routeInfo = new HashMap<>();
        routeInfo.put("Starting Location", loc);
        // create a document called [route name input] with a hashmap of route information
        routeCollection.document(route).set(routeInfo);
    }

    public static void updateRoute(String route, int[] time, double dist, int steps) {
        CollectionReference routeCollection = db.collection(USER_KEY).document(User.getEmail()).collection(ROUTES_KEY);
        routeCollection.document(route).update("time", time[0] + " : " + time[1] + " : " + time[2]);
        routeCollection.document(route).update("dist", "" + dist);
        routeCollection.document(route).update("steps", "" + steps);
    }

    public static void updateFeatures(String route, String features, boolean isFavorite, String notes){
        CollectionReference routeCollection = db.collection(USER_KEY).document(User.getEmail()).collection(ROUTES_KEY);
        routeCollection.document(route).update("features", features);
        routeCollection.document(route).update("isFavorite", "" + isFavorite);
        routeCollection.document(route).update("notes", notes);
    }

    public static void inviteTeammate(String teammateEmail) {
        //Get's collection reference to teammates data
        CollectionReference invitesCollection = db.collection(USER_KEY).document(teammateEmail).collection(INVITE_KEY);
        //Adds current user's email to teammates invite data
        Map<String, String> inviteInfo = new HashMap<>();
        inviteInfo.put("email", User.getEmail());
        invitesCollection.document(User.getEmail()).set(inviteInfo);
    }

    public static void acceptInvite(final String acceptedInviteEmail){
        final CollectionReference usersCollection = db.collection(USER_KEY).document(User.getEmail()).collection(INVITE_KEY);

        /*usersCollection.document(acceptedInviteEmail).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> dc = queryDocumentSnapshots.getDocuments();

                for(int i = 0; i < dc.size(); i++){
                    Map<String, Object> map = dc.get(i).getData();

                    if(!map.containsKey(acceptedInviteEmail)){
                        continue;
                    }

                    usersCollection.document()
                }
            }
        });*/

        //Deletes invite from users invites
        usersCollection.document(acceptedInviteEmail).delete();

        //Adds teammate to users teammates
        HashMap<String,String> map = new HashMap<>();
        map.put(acceptedInviteEmail, acceptedInviteEmail);
        db.collection(USER_KEY).document(User.getEmail()).collection(TEAMS_KEY).document(acceptedInviteEmail)
         .set(map);

        //Add user to teammates
        HashMap<String,String> map2 = new HashMap<>();
        map2.put(User.getEmail(), User.getEmail());
        db.collection(USER_KEY).document(acceptedInviteEmail).collection(TEAMS_KEY).document(User.getEmail())
                .set(map2);
    }

    public static int randomColorGenerator(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public static void getFireBaseField(String email, String fieldType, final TeamPageActivity activity){
        if(!(fieldType.equals("Color") || fieldType.equals("Name"))){
            //return null; // ERROR!!! in getFireBaseField
        }
        final String field = fieldType;
        // need to check for nonexisting emails
        DocumentReference docRef = db.collection(USER_KEY).document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    //System.err.println("getFireBaseField docuemnt...");
                    if (document.exists()) {
                        System.err.println("getFireBaseField get data: " + field + " " + document.getString(field));
                    }
                }
                else{
                    System.err.println("getFireBaseField method failed");
                }
            }
        });

    }




    public static void getAnything(boolean isDocument, String path){
        DocumentReference documentReference;
        CollectionReference collectionReference;
        if(isDocument){
            documentReference = db.document(path);
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    System.out.println("Data: " + document.getData());
                    helpGetAnything(document.getData());
                    // method
                }
            });
        } else{
            collectionReference = db.collection(path);
        }
    }

    private static void helpGetAnything(Map<String, Object> data){
        UpdateFirebase.data = data;
    }

    public static String getData(String type){
        Map<String, Object> tmpData;
        while(UpdateFirebase.data == null) {}

        tmpData = UpdateFirebase.data;
        UpdateFirebase.data = null;
        return (String)tmpData.get(type);
    }

}
