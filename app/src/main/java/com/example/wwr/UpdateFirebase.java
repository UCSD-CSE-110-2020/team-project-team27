package com.example.wwr;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class UpdateFirebase {
    public static final String USER_KEY = "users";
    public static final String ROUTES_KEY = "routes";
    public static final String TEAMS_KEY = "team";
    public static final String INVITE_KEY = "invites";

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static ArrayList<FirebaseObserver> observers = new ArrayList<>();

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

    public static void inviteTeammate(String teammateEmail, String nickName) {
        //Get's collection reference to teammates data
        CollectionReference invitesCollection = db.collection(USER_KEY).document(teammateEmail).collection(INVITE_KEY);
        //Adds current user's email to teammates invite data
        Map<String, String> inviteInfo = new HashMap<>();
        inviteInfo.put("Email", User.getEmail());
        //Sender's name
        inviteInfo.put("Name", User.getName());
        //Receivers name
        inviteInfo.put("Nickname", nickName);

        invitesCollection.add(inviteInfo);
    }

    //Person who sent the invite (the email of the person you accepted the invite from)
    public static void acceptInvite(final String acceptedInviteEmail, final String acceptedInviteName){
        final CollectionReference usersCollection = db.collection(USER_KEY).document(User.getEmail()).collection(INVITE_KEY);
        final CollectionReference teammatesTeam = db.collection(USER_KEY).document(acceptedInviteEmail).collection(TEAMS_KEY);

        //Deletes invite from users invites
        usersCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                for(final QueryDocumentSnapshot document : task.getResult()){
                    System.err.println("UpdateFirebase. delete invite:" + acceptedInviteEmail);

                    final String nickname;

                    if(document.get("Email").equals(acceptedInviteEmail)){
                        System.err.println("UpdateFirebase. delete invite:" + acceptedInviteEmail);

                        nickname = (String) document.get("Nickname");


                        teammatesTeam.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                //Loop through every teammate (document) of the sender
                                /*for(DocumentSnapshot teamMember: task.getResult()){
                                    //Adding user to teammates teammates team
                                    HashMap<String, String> map = new HashMap<>();
                                    map.put("Email", User.getEmail());
                                    map.put("Name", nickname);
                                    db.collection(USER_KEY).document((String)teamMember.get("Email")).collection(TEAMS_KEY).
                                            add(map);

                                    HashMap<String, String> map2 = new HashMap<>();
                                    map.put("Email", (String)teamMember.get("Email"));
                                    map.put("Name", (String)teamMember.get("Name"));
                                    db.collection(USER_KEY).document(User.getEmail()).collection(TEAMS_KEY).
                                            add(map2);
                                }*/

                                //Adds teammate to users teammates
                                HashMap<String,String> map = new HashMap<>();
                                map.put("Email", acceptedInviteEmail);
                                map.put("Name", acceptedInviteName);
                                db.collection(USER_KEY).document(User.getEmail()).collection(TEAMS_KEY).
                                        add(map);

                                //Add user to teammates
                                HashMap<String,String> map2 = new HashMap<>();
                                map2.put("Email", User.getEmail());
                                //Should be nickname
                                map2.put("Name", (String) nickname);

                                db.collection(USER_KEY).document(acceptedInviteEmail).collection(TEAMS_KEY).
                                        add(map2);
                                usersCollection.document(document.getId()).delete();
                            }
                        });

                        break;
                    }
                }
            }
        });
    }

    public static void rejectInvite(final String acceptedInviteEmail){
        final CollectionReference usersCollection = db.collection(USER_KEY).document(User.getEmail()).collection(INVITE_KEY);

        usersCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(QueryDocumentSnapshot document : task.getResult()){
                    System.err.println("UpdateFirebase. delete invite:" + acceptedInviteEmail);
                    if(document.get("Email").equals(acceptedInviteEmail)){
                        System.err.println("UpdateFirebase. delete invite:" + acceptedInviteEmail);
                        usersCollection.document(document.getId()).delete();
                        break;
                    }
                }
            }
        });
    }

    public static int randomColorGenerator(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public static void getTeammates(final String CURRENT_VIEW){
        CollectionReference teamCollection;
        if(CURRENT_VIEW.equals("TeamPage")) {
            teamCollection = db.collection(USER_KEY + "/" + User.getEmail() + "/" + TEAMS_KEY);
        }
        else if(CURRENT_VIEW.equals("InvitePage")){
            teamCollection = db.collection(USER_KEY + "/" + User.getEmail() + "/" + INVITE_KEY);
        }
        else{
            teamCollection = null; // error case, should never happen
        }

        teamCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<String> names = new ArrayList<>();
                ArrayList<String> emails = new ArrayList<>();

                List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();

                //Get every teammates name
                for(DocumentSnapshot snapshot : snapshots){
                        names.add((String) snapshot.get("Name"));
                        emails.add((String) snapshot.get("Email"));
                }

                //Update all observers
                for(FirebaseObserver observer : observers ){
                    observer.updateTeamList(names, emails);
                }
            }
        });
    }

    public static void registerObserver(FirebaseObserver observer){
        observers.add(observer);
    }

}