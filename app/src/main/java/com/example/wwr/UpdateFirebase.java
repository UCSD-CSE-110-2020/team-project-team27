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
    public static final String PROPOSED_ROUTES_KEY = "proposedRoutes";
    public static final String TEAMS_KEY = "team";
    public static final String INVITE_KEY = "invites";

    public static FirebaseFirestore db;

    private static ArrayList<FirebaseObserver> observers = new ArrayList<>();

    // local storage of teammate info
    static ArrayList<String> names;
    static ArrayList<String> emails;
    static ArrayList<String> colors;
    static ArrayList<Boolean> pending;

    //local storage of proposedRoutes info
    static ArrayList<String> attendees;
    static ArrayList<String> time;
    static ArrayList<String> date;

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

        //Adding invitee to User's team in italics
        Map<String, String> inviteeInfo = new HashMap<>();
        inviteeInfo.put("Email", teammateEmail);
        inviteeInfo.put("Name", nickName);
        inviteeInfo.put("hasAccepted", "false");
        db.collection(USER_KEY + "/" + User.getEmail() + "/" + TEAMS_KEY).add(inviteeInfo);
    }

    //Person who sent the invite (the email of the person you accepted the invite from)
    public static void acceptInvite(final String acceptedInviteEmail, final String acceptedInviteName){
        final CollectionReference usersCollection = db.collection(USER_KEY).document(User.getEmail()).collection(INVITE_KEY);
        final CollectionReference usersOldTeam = db.collection(USER_KEY).document(User.getEmail()).collection(TEAMS_KEY);
        final CollectionReference teammatesTeam = db.collection(USER_KEY).document(acceptedInviteEmail).collection(TEAMS_KEY);

        // 0. Loop through my old team
        usersOldTeam.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot oldTeamSnapShot) {

                final List<DocumentSnapshot> oldTeamList = oldTeamSnapShot.getDocuments();

            // 1. Loop through my invite folder
            usersCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<QuerySnapshot> myInvites) {
                for(final QueryDocumentSnapshot invite : myInvites.getResult()){
                    System.err.println("UpdateFirebase. delete invite:" + acceptedInviteEmail);

                    final String nickname;

                    // 2. Find the sender (who I accepted the invitation from)
                    if(invite.get("Email").equals(acceptedInviteEmail)){
                        System.err.println("UpdateFirebase. delete invite:" + acceptedInviteEmail);

                        nickname = (String) invite.get("Nickname");

                        // 3. Loop through the sender's team
                        teammatesTeam.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot sendersTeam) {
                                // 4. Loop through every teammate (document) of the sender
                                for(DocumentSnapshot sendersTeamMember: sendersTeam.getDocuments()){

                                    // 4.5. Update user in sender's team folder
                                    if(sendersTeamMember.get("Email").equals(User.getEmail())){
                                        sendersTeamMember.getReference().update("hasAccepted", "true");
                                        continue;
                                    }

                                    System.err.println("Current User's Email: " + sendersTeamMember.get("Email"));

                                    // 5. Adding user to sender's teammate's team folder
                                    HashMap<String, String> map = new HashMap<>();
                                    map.put("Email", User.getEmail());
                                    map.put("Name", nickname);
                                    db.collection(USER_KEY).document((String)sendersTeamMember.get("Email")).collection(TEAMS_KEY).
                                            add(map);

                                    // 6. Adding sender's teammate to user's team folder
                                    HashMap<String, String> map2 = new HashMap<>();
                                    map2.put("Email", (String)sendersTeamMember.get("Email"));
                                    map2.put("Name", (String)sendersTeamMember.get("Name"));
                                    db.collection(USER_KEY).document(User.getEmail()).collection(TEAMS_KEY).
                                            add(map2);

                                    // 7. Loop through user's old team (before accepting the invitation)
                                    for(int index = 0; index < oldTeamList.size(); index++){
                                        String oldMateName = (String) oldTeamList.get(index).get("Name");
                                        String oldMateEmail = (String) oldTeamList.get(index).get("Email");

                                        // 8. Adding user's old teammate to sender's teammate's team folder
                                        HashMap<String, String> map3 = new HashMap<>();
                                        map3.put("Email", oldMateEmail);
                                        map3.put("Name", oldMateName);
                                        db.collection(USER_KEY).document((String)sendersTeamMember.get("Email")).collection(TEAMS_KEY).
                                                add(map3);

                                        // 9. Adding sender's teammate to user's old teammate's team folder
                                        HashMap<String, String> map4 = new HashMap<>();
                                        map4.put("Email", (String)sendersTeamMember.get("Email"));
                                        map4.put("Name", (String)sendersTeamMember.get("Name"));
                                        db.collection(USER_KEY).document(oldMateEmail).collection(TEAMS_KEY).
                                                add(map4);

                                    }
                                }

                                // 10. Add sender to user's team folder
                                HashMap<String,String> map = new HashMap<>();
                                map.put("Email", acceptedInviteEmail);
                                map.put("Name", acceptedInviteName);
                                db.collection(USER_KEY).document(User.getEmail()).collection(TEAMS_KEY).
                                        add(map);

                                // 11. Add old user's team member to the sender's team and vice versa
                                for(int index = 0; index < oldTeamList.size(); index++){
                                    String oldMateName = (String) oldTeamList.get(index).get("Name");
                                    String oldMateEmail = (String) oldTeamList.get(index).get("Email");

                                    // 11-1. Adding user's old teammate to sender's team folder
                                    HashMap<String, String> map5 = new HashMap<>();
                                    map5.put("Email", oldMateEmail);
                                    map5.put("Name", oldMateName);
                                    db.collection(USER_KEY).document(acceptedInviteEmail).collection(TEAMS_KEY).
                                            add(map5);

                                    // 11-2. Adding sender to user's old teammate's team folder
                                    HashMap<String, String> map6 = new HashMap<>();
                                    map6.put("Email", acceptedInviteEmail);
                                    map6.put("Name", acceptedInviteName);
                                    db.collection(USER_KEY).document(oldMateEmail).collection(TEAMS_KEY).
                                            add(map6);
                                }

                                //Deletes invite from users invites
                                usersCollection.document(invite.getId()).delete();
                            }
                        });

                        break;
                    }
                }
            }
        });
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

    // TODO: need to modify this so that we use sharedpreference local data or team's data
    public static void getTeamsRoutes(){
        CollectionReference teamCollection = db.collection(USER_KEY + "/" + User.getEmail() + "/" + TEAMS_KEY);
        final ArrayList<Route> routes = new ArrayList<>();
        teamCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(queryDocumentSnapshots.size() == 0){
                    //Update all observers
                    for(FirebaseObserver observer : observers){
                        System.err.println("Call observer to update teamroute");
                        observer.updateTeamRoute(routes);
                    }
                }
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
                                                System.err.println("Get TeammateRoute name: " + document.get("Name"));
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
                                                String [] userInfo = {userName, userEmail, userColor};
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
                pending = new ArrayList<>();

                List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();

                //Get every teammates name
                for(final DocumentSnapshot snapshot : snapshots){

                        db.collection(USER_KEY).document((String)snapshot.get("Email"))
                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                names.add((String) snapshot.get("Name"));
                                emails.add((String) snapshot.get("Email"));
                                boolean notBeenAccepted = true;
                                if(snapshot.get("hasAccepted") == null || (snapshot.get("hasAccepted")).equals("true")){
                                    notBeenAccepted = false;
                                }
                                pending.add(notBeenAccepted);
                                colors.add((String) documentSnapshot.get("Color"));

                                //Update all observers
                                for(FirebaseObserver observer : observers){
                                    observer.updateTeamList(names, emails, colors, pending);
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

    // User add a propose route to the user's proposedRoutes folder
    // Each Route should have Name, Starting Location, Features, Time(Proposed walk time), Date(Proposed Walk Date), Attendees, isScheduled (default false)
    public static void proposeARoute(Route route, String date, String time){
        CollectionReference proposedRouteCollection = db.collection(USER_KEY).document(User.getEmail()).collection(PROPOSED_ROUTES_KEY);
        Map<String, String> proposedRouteInfo = new HashMap<>();
        proposedRouteInfo.put("Name", route.getName());
        proposedRouteInfo.put("Starting Location", route.getStartingLocation());
        proposedRouteInfo.put("Features", route.getFeatures());
        proposedRouteInfo.put("Time", time);
        proposedRouteInfo.put("Date", date);
        proposedRouteInfo.put("Attendees", "");
        proposedRouteInfo.put("isScheduled", "false");
        // create a document called [route name input] with a hashmap of route information
        proposedRouteCollection.document().set(proposedRouteInfo);
        System.err.println("proposed route " + route  + " in the cloud to " + User.getEmail());
    }

    // Get ProposedRoutes to ProposedRoute ArrayList to populate proposed walk screen(proposed grayout, scheduled in black)
    public static void getProposedRoutes(){
        CollectionReference proposedRoutesCollection=  db.collection(USER_KEY + "/" + User.getEmail() + "/" + PROPOSED_ROUTES_KEY);

        proposedRoutesCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                attendees = new ArrayList<>();
                date = new ArrayList<>();
                time = new ArrayList<>();

                List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();

                //Get every proposedRoutes info
                for(final DocumentSnapshot snapshot : snapshots){

                    db.collection(USER_KEY).document((String)snapshot.get("Email"))
                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            attendees.add((String) snapshot.get("Attendees"));
                            date.add((String) snapshot.get("Date"));
                            time.add((String) snapshot.get("Time"));


                            //Update all observers
                            for(FirebaseObserver observer : observers){
                                observer.updateProposedRouteList(attendees, date, time);
                            }
                        }
                    });
                }
            }
        });
    }

    // User clicked accept a certain walk. add user to the Attendees field
    public static void acceptProposedWalk(String walkname){
        CollectionReference proposedRoutesCollection = db.collection(USER_KEY + "/" + User.getEmail() + "/" + PROPOSED_ROUTES_KEY + "/" + walkname);
        proposedRoutesCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                attendees.add(User.getName()); //test to see if this works because its so simple, otherwise uncomment below and check
//                attendees = new ArrayList<>();
//
//                List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
//
//                //Get every teammates name
//                for(final DocumentSnapshot snapshot : snapshots) {
//
//                    db.collection(USER_KEY).document((String) snapshot.get("Name"))
//                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                        @Override
//                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//
//                            attendees.add((String) snapshot.get("Name"));
//
//                        }
//                    });
//                }

            }
        });

    }

    // User clicked reject a certain walk. remove user from the Attendees field
    public static void rejectProposedWalk(String walkname){
        CollectionReference proposedRoutesCollection = db.collection(USER_KEY + "/" + User.getEmail() + "/" + PROPOSED_ROUTES_KEY + "/" + walkname);
        proposedRoutesCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                attendees.remove(User.getName()); //test to see if this works because its so simple, otherwise uncomment below and check
//                attendees = new ArrayList<>();
//
//                List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
//
//                //Get every teammates name
//                for(final DocumentSnapshot snapshot : snapshots) {
//
//                    db.collection(USER_KEY).document((String) snapshot.get("Name"))
//                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                        @Override
//                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//
//                            attendees.remove((String) snapshot.get("Name"));
//
//                        }
//                    });
//                }

            }
        });

    }

    // User clicked schedule a certain walk. change isScheduled field to true
    public static void scheduleProposedWalk(String walkname){
//        final CollectionReference proposedRoutesCollection = db.collection(USER_KEY + "/" + User.getEmail() + "/" + PROPOSED_ROUTES_KEY + "/" + walkname);
//        proposedRoutesCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
//            }
//        });
    }

    // User clicked reject a certain walk. delete the walk document under the proposer's proposed walk folder
    public static void withDrawProposedWalk(String walkname){

    }
}