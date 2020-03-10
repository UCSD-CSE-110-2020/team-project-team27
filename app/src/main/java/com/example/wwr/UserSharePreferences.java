package com.example.wwr;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Set;

public class UserSharePreferences {

    static SharedPreferences routeSP;
    static SharedPreferences heightSP;
    // static SharedPreferences teamRouteSP;

    public static void setRouteShared(SharedPreferences route) {
        routeSP = route;
    }
    public static void setHeightShared(SharedPreferences height) {
        heightSP = height;
    }
    // public static void setTeamRouteSP(SharedPreferences teamRoute){teamRouteSP = teamRoute;}

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
        System.err.println("Route in UserSharedPreference added called " + name);
        UpdateFirebase.addedRoute(name, location); //update cloud database
        return true;
    }

    public static void storeRoute(String name, String ownerEmail, int[] time, double dist, int steps, boolean myRoute){
        SharedPreferences.Editor editor = routeSP.edit();
        editor.putString("latestRoute", name);
        editor.putString("LRemail", ownerEmail);
        if(ownerEmail != null && !ownerEmail.equals(User.getEmail())){
            name = name + ownerEmail;
        }

        editor.putInt(name+"_hour", time[0]); // store location correspond to the route
        editor.putInt(name+"_min", time[1]); // store location correspond to the route
        editor.putInt(name+"_sec", time[2]); // store location correspond to the route
        editor.putString(name+"_dist", Double.toString(dist)); // store location correspond to the route
        editor.putInt(name+"_step", steps);
        editor.apply();
        if(myRoute){ UpdateFirebase.updateRoute(name, time, dist, steps);} //update cloud database
    }

    public static void storeRoute(String name, String features, boolean isFavorite, String notes, boolean myRoute){
        SharedPreferences.Editor editor = routeSP.edit();
        editor.putString(name+"_features", features);
        editor.putBoolean(name+"_isFavorite", isFavorite);
        editor.putString(name+"_notes", notes);
        editor.apply();
        if(myRoute){ UpdateFirebase.updateFeatures(name, features, isFavorite, notes); }//update cloud database
    }

    public static ArrayList<Route> updateTeamRoute(ArrayList<Route> teammateRoutes){
        Set<String> routeList = routeSP.getStringSet("routeNames", null);
        for(Route teamRoute: teammateRoutes){
            String routeName = teamRoute.getName() + teamRoute.getTeammateInfo()[1]; // append teammate email
            if(routeList.contains(teamRoute.getName())){
                System.err.println("updateTeamRoute change teammate route" + teamRoute.getName() + "with user info");
                teamRoute.setDistance(Double.parseDouble(routeSP.getString(routeName + "_dist", "0.0")));
                teamRoute.setFavorite(routeSP.getBoolean(routeName + "_isFavorite", false));
                teamRoute.setFeatures(routeSP.getString(routeName + "_features", ""));
                int[] routeTime = new int[3];
                routeTime[0] = routeSP.getInt(routeName + "_hour", 0);
                routeTime[1] = routeSP.getInt(routeName + "_min", 0);
                routeTime[2] = routeSP.getInt(routeName + "_sec", 0);
                teamRoute.setTime(routeTime);
                teamRoute.setSteps(routeSP.getInt(routeName + "_step", 0));
            }else{
                // create local copies of team routes in SP
                SharedPreferences.Editor editor = routeSP.edit();
                routeList.add(teamRoute.getName()); // add "route + email" entry to routeList
                editor.putString(routeName+"_location", teamRoute.getStartingLocation());
                editor.putInt(routeName+"_hour", teamRoute.getTime()[0]); // store location correspond to the route
                editor.putInt(routeName+"_min", teamRoute.getTime()[1]); // store location correspond to the route
                editor.putInt(routeName+"_sec", teamRoute.getTime()[2]); // store location correspond to the route
                editor.putString(routeName+"_dist", "" + teamRoute.getDistance()); // store location correspond to the route
                editor.putInt(routeName+"_step", teamRoute.getSteps());
                editor.putString(routeName+"_features", teamRoute.getFeatures());
                editor.putBoolean(routeName+"_isFavorite", false);
                editor.putString(routeName+"_notes", "");
                editor.apply();
            }
        }
        return teammateRoutes;
    }

}
