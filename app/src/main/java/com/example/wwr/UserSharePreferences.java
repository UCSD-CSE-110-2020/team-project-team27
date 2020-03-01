package com.example.wwr;

import android.content.SharedPreferences;
import java.util.Set;

public class UserSharePreferences {

    static SharedPreferences routeSP;
    static SharedPreferences heightSP;

    public static void setRouteShared(SharedPreferences route) {
        routeSP = route;
    }
    public static void setHeightShared(SharedPreferences height) {
        heightSP = height;
    }

    public static boolean storeRoute(String name, String location, boolean test){
        Set<String> routeList = routeSP.getStringSet("routeNames", null);
        if(routeList == null){
            System.err.println("Critical Error: SharePreference not existed.");
            return false;
        }
        else if(routeList.contains(name)){
            System.err.println("Error: duplicates.");
            return false;
        }
        SharedPreferences.Editor editor = routeSP.edit();
        routeList.add(name);
        editor.putStringSet("routeNames", routeList); // store the updated route name list
        editor.putString(name+"_location", location); // store location correspond to the route
        editor.apply();
        if(test){return true;}
        UpdateFirebase.addedRoute(name, location); //update cloud database
        return true;
    }

    public static void storeRoute(String name, int[] time, double dist, int steps){
        SharedPreferences.Editor editor = routeSP.edit();
        editor.putInt(name+"_hour", time[0]); // store location correspond to the route
        editor.putInt(name+"_min", time[1]); // store location correspond to the route
        editor.putInt(name+"_sec", time[2]); // store location correspond to the route
        editor.putString(name+"_dist", Double.toString(dist)); // store location correspond to the route
        editor.putInt(name+"_step", steps);
        editor.putString("latestRoute", name);
        editor.apply();
        UpdateFirebase.updateRoute(name, time, dist, steps); //update cloud database
    }

    public static void storeRoute(String name, String features, boolean isFavorite, String notes){
        SharedPreferences.Editor editor = routeSP.edit();
        editor.putString(name+"_features", features);
        editor.putBoolean(name+"_isFavorite", isFavorite);
        editor.putString(name+"_notes", notes);
        editor.apply();
        UpdateFirebase.updateFeatures(name, features, isFavorite, notes); //update cloud database
    }
}
