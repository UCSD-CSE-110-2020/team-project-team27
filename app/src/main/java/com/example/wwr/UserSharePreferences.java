package com.example.wwr;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;
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

    public static boolean storeRoute(String name, String location){
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
    }
}
