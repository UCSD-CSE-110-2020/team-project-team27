package com.example.wwr;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
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

    public static FirebaseFirestore db;

    private static ArrayList<FirebaseObserver> observers = new ArrayList<>();

    // local storage of teammate info
    static ArrayList<String> names;
    static ArrayList<String> emails;
    static ArrayList<String> colors;

    public static void setDatabase(FirebaseFirestore fb){
        db = fb;
    }

    public static void setupUser(String name){
        Map<String, String> userInfo = new HashMap<>();
        // create name and color field for new registered user
        userInfo.put("Name", name);
        userInfo.put("Color", "" + randomColorGenerator());
        // create a document called [route name input] with a hashmap of route information
        db.collection(USER_KEY).document(User.getEmail()).set(userInfo);
    }

    public static void addedRoute(String route, String loc) {
        CollectionReference routeCollection = db.collection(USER_KEY).document(User.getEmail()).collection(ROUTES_KEY);
        Map<String, String> routeInfo = new HashMap<>();
        routeInfo.put("Name", route);
        routeInfo.put("Starting Location", loc);
        // create a document called [route name input] with a hashmap of route information
        routeCollection.document().set(routeInfo);
        System.err.println("route " + route  + " in the cloud to " + User.getEmail());
    }

    public static void updateRoute(final String route, final int[] time, final double dist, final int steps) {
        final CollectionReference routeCollection = db.collection(USER_KEY).document(User.getEmail()).collection(ROUTES_KEY);
        routeCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (final QueryDocumentSnapshot document : task.getResult()) {
                    if (document.get("Name").equals(route)) {
                        document.getReference().update("time", time[0] + " : " + time[1] + " : " + time[2]);
                        document.getReference().update("dist", "" + dist);
                        document.getReference().update("steps", "" + steps);
                        break;
                    }
                }
            }
        });
    }

    public static void updateFeatures(final String route, final String features, final boolean isFavorite, final String notes){
        final CollectionReference routeCollection = db.collection(USER_KEY).document(User.getEmail()).collection(ROUTES_KEY);
        routeCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (final QueryDocumentSnapshot document : task.getResult()) {
                    if (document.get("Name").equals(route)) {
                        document.getReference().update("features", features);
                        document.getReference().update("isFavorite", "" + isFavorite);
                        document.getReference().update("notes", notes);
                        break;
                    }
                }
            }
        });

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
                                for(DocumentSnapshot teamMember: queryDocumentSnapshots.getDocuments()){

                                    System.err.println("Current User's Email: " + teamMember.get("Email"));

                                    //Adding user to teammates teammates team
                                    HashMap<String, String> map = new HashMap<>();
                                    map.put("Email", User.getEmail());
                                    map.put("Name", nickname);
                                    db.collection(USER_KEY).document((String)teamMember.get("Email")).collection(TEAMS_KEY).
                                            add(map);

                                    HashMap<String, String> map2 = new HashMap<>();
                                    map2.put("Email", (String)teamMember.get("Email"));
                                    map2.put("Name", (String)teamMember.get("Name"));
                                    db.collection(USER_KEY).document(User.getEmail()).collection(TEAMS_KEY).
                                            add(map2);
                                }

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
                                //Deletes invite from users invites
                                usersCollection.document(document.getId()).delete();
                            }
                        });

                        break;
                    }
                }
            }
        });
    }

    public static void getName(){
        DocumentReference userInfo = db.collection(USER_KEY).document(User.getEmail());

        userInfo.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User.setName((String) documentSnapshot.get("Name"));
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

    public static void getTeamsRoutes(){
        System.err.println("Called getTeamsRoutes 1");

        CollectionReference teamCollection = db.collection(USER_KEY + "/" + User.getEmail() + "/" + TEAMS_KEY);

        final ArrayList<Route> routes = new ArrayList<>();

        teamCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                System.err.println("Called getTeamsRoutes2");

                for(DocumentSnapshot snapshot: queryDocumentSnapshots.getDocuments()){
                    System.err.println("Called getTeamsRoutes3");

                    final CollectionReference teammatesRoutes = db.collection(USER_KEY).document((String) snapshot.get("Email"))
                            .collection(ROUTES_KEY);
                    System.err.println("Get TeammateMate name (getTeamsRoutes method): " + snapshot.get("Name"));
                    final String userName = (String) snapshot.get("Name");
                    final String userEmail = (String) snapshot.get("Email");

                    db.collection(USER_KEY).document(userEmail).get().addOnSuccessListener(
                            new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    final String userColor = (String)documentSnapshot.get("Color");
                                    System.err.println("The Color is of " + userName + " " + userColor);

                                    teammatesRoutes.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                            for (final QueryDocumentSnapshot document : task.getResult()) {
                                                System.err.println("Get TeammateRoute name: " + (String)document.get("Name"));
                                                String name = (String)document.get("Name");
                                                String loc = (String)document.get("Starting Location");

                                                String feature = "";
                                                boolean favorite = false;
                                                int steps = 0;
                                                double dist = 0.0;
                                                int[] time = {0, 0, 0};

                                                if(document.get("features") != null) feature = (String) document.get("features");
                                                if(document.get("isFavorite") != null) favorite = Boolean.parseBoolean((String) document.get("isFavorite"));
                                                if(document.get("steps") != null) steps = Integer.parseInt((String)document.get("steps"));
                                                if(document.get("dist") != null) dist = Double.parseDouble((String)document.get("dist"));
                                                if(document.get("time") != null) {
                                                    String[] timeStr = ((String) document.get("time")).split(" : ");
                                                    System.err.println(timeStr[0] + " " + timeStr[1] + " " + timeStr[2] + " " );
                                                    time[0] = Integer.parseInt(timeStr[0]);
                                                    time[1] = Integer.parseInt(timeStr[1]);
                                                    time[2] = Integer.parseInt(timeStr[2]);
                                                }
                                                String [] userInfo = {userName, userEmail, userColor}; // (name, email, color) // TODO: modify color
                                                routes.add(new Route(name, feature, favorite, loc, steps, dist, time, userInfo));
                                                System.err.println("There are " + routes.size() + " team routes");
                                            }
                                            System.err.println("There are " + routes.size() + " team routes after main for loop with " + observers.size() + " observers");
                                            //Update all observers
                                            for(FirebaseObserver observer : observers){
                                                System.err.println("Call observer to update teamroute");
                                                observer.updateTeamRoute(routes);
                                            }
                                        }
                                    });
                                }
                            }
                    );
                }
            }
        }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                System.out.println("NOOOOO");
            }
        });
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
                names = new ArrayList<>();
                emails = new ArrayList<>();
                colors = new ArrayList<>();

                List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();

                //Get every teammates name
                for(final DocumentSnapshot snapshot : snapshots){

                        db.collection(USER_KEY).document((String)snapshot.get("Email"))
                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                names.add((String) snapshot.get("Name"));
                                emails.add((String) snapshot.get("Email"));
                                colors.add((String) documentSnapshot.get("Color"));

                                //Update all observers
                                for(FirebaseObserver observer : observers ){
                                    observer.updateTeamList(names, emails, colors);
                                }
                            }
                        });
                }
            }
        });
    }

    public static void registerObserver(FirebaseObserver observer){
        observers.add(observer);
    }

}