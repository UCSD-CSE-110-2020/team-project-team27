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
    public static final String D_KEY = "default";


    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void addedRoute(String route, String loc) {
        CollectionReference routeCollection = db.collection(USER_KEY).document(User.getEmail()).collection(ROUTES_KEY);
        Map<String, String> routeInfo = new HashMap<>();
        // routeInfo.put("Name", route);
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
}
